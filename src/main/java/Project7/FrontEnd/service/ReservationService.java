package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.ReservationDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

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
}
