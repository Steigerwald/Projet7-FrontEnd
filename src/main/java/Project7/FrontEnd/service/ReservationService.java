package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LivreForm;
import Project7.FrontEnd.form.ReservationForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    public UserService userService;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationService.class);

    /*Methode pour obtenir toutes les reservations de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservations() throws IOException {
        ObjectMapper mapper =new ObjectMapper();
        List<ReservationDTO> toutesReservations = mapper.readValue(new URL("http://localhost:9090/reservation/"),new TypeReference<List<ReservationDTO>>(){});
        if(toutesReservations.size() > 0) {
            logger.info(" retour liste toutesReservations car la taille de laliste >0 "+toutesReservations);
            return toutesReservations;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservations ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour transformer une reservationForm en reservationDTO*/
    public ReservationDTO transformerReservationFormEnReservationDTO(ReservationForm reservationForm,UserDTO userDTO) throws ParseException, IOException {
        ReservationDTO reservationDTO = new ReservationDTO ();
/*
        reservationDTO.setIdReservation(reservationForm.getIdReservation());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1= format.parse ( reservationForm.getDateReservation());
        reservationDTO.setDateReservation(date1);
        reservationDTO.setDelaiDeLocation(reservationForm.getDelaiDeLocation());

 */
        reservationDTO.setIsactif(true);
        reservationDTO.setUser(userDTO);
        return reservationDTO;
    }

    /*Methode pour creer une réservation à l'API rest*/
    public ReservationDTO createReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(reservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/addReservation"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }

    /*Methode pour obtenir une réservation disponible de la base de données de l'API rest*/
    public ReservationDTO getReservationById(int id) throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        ReservationDTO reservationById = mapper.readValue(new URL("http://localhost:9090/reservation/"+id),new TypeReference<ReservationDTO>(){});
        if(reservationById!=null) {
            logger.info(" l'id de la reservation trouvée est :  "+reservationById.getIdReservation());
            return reservationById;
        } else {
            logger.info(" retour de nul car pas d'élément et de reservation trouvée ");
            return null;
        }
    }

    /*Methode pour obtenir toutes les reservations à valider de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsAValider() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsAValider = mapper.readValue(new URL("http://localhost:9090/reservation/AValider"), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsAValider.size() > 0) {
            logger.info(" retour liste toutesReservations car la taille de laliste >0 " + toutesReservationsAValider);
            return toutesReservationsAValider;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservations ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour obtenir toutes les reservations en cours de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsEnCours() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsEnCours = mapper.readValue(new URL("http://localhost:9090/reservation/EnCours"), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsEnCours.size() > 0) {
            logger.info(" retour liste toutesReservationsEnCours car la taille de laliste >0 " + toutesReservationsEnCours);
            return toutesReservationsEnCours;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservationsEnCours ");
            return new ArrayList<ReservationDTO>();
        }
    }

        /*Methode pour retirer une reservation de la base de données de l'API rest*/
        public ReservationDTO retirerReservation(ReservationDTO reservation) throws IOException, InterruptedException {
            Date today = new Date();
            reservation.setDateDeRetrait(today);
            reservation.setEtatReservation("en cours de pret");
            HttpClient client = HttpClient.newHttpClient();
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(reservation);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9090/reservation/"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info(" reponse du body "+response.body());
            System.out.println(response.body());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
        }

    /*Methode pour enregistrer un retour d'une reservation de la base de données de l'API rest*/
    public ReservationDTO retournerReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        Date today = new Date();
        reservation.setDateDeRetour(today);
        reservation.setEtatReservation("retournée");
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(reservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }



}
