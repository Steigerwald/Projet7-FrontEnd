package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.SearchDTO;
import Project7.FrontEnd.form.LivreForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LivreService {

    @Autowired
    public BibliothequeService bibliothequeService;

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    /*Methode pour obtenir tous les livres de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivres() throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        List<LivreDTO> tousLivres = mapper.readValue(new URL("http://localhost:9090/livre/"),new TypeReference<List<LivreDTO>>(){});
        if(tousLivres.size() > 0) {
            logger.info(" retour liste tousLivres car la taille de laliste >0 "+tousLivres);
            logger.info(" valeur livre "+tousLivres.get(0));
            return tousLivres;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }

    /*Methode pour obtenir tous les livres disponibles de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivresDisponibles() throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        List<LivreDTO> tousLivresDisponibles = mapper.readValue(new URL("http://localhost:9090/livre/disponibles"),new TypeReference<List<LivreDTO>>(){});
        if(tousLivresDisponibles.size() > 0) {
            logger.info(" retour liste tousLivresDisponibles car la taille de laliste >0 "+tousLivresDisponibles);
            logger.info(" valeur du premier livre disponible "+tousLivresDisponibles.get(0));
            logger.info(" valeur de l'auteur du premier livre "+tousLivresDisponibles.get(0).getAuteur());
            return tousLivresDisponibles;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }

    /*Methode pour envoyer une recherche de livre à l'API rest*/
    public List<LivreDTO> sendSearchLivre(SearchDTO newSearch) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
/*
        var values = new HashMap<String, String>() {{
            put("nomCategorie", "Policier");
        }};
*/
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(newSearch);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/livre/search"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        List<LivreDTO> listM = mapper.readValue(response.body(), new TypeReference<List<LivreDTO>>(){});
        return listM;
    }
    /*Methode pour récupérer tous les livres recherchés de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivresRecherches() throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        List<LivreDTO> tousLivresRecherches = mapper.readValue(new URL("http://localhost:9090/livre/search"),new TypeReference<List<LivreDTO>>(){});
        if(tousLivresRecherches.size() > 0) {
            logger.info(" retour liste tousLivres car la taille de laliste >0 "+tousLivresRecherches);
            logger.info(" valeur livre "+tousLivresRecherches.get(0));
            logger.info(" valeur livre "+tousLivresRecherches.get(0).getAuteur());
            return tousLivresRecherches;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }

    /*Methode pour obtenir un livre disponible de la base de données de l'API rest*/
    public LivreDTO getLivreById(int id) throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        LivreDTO livreById = mapper.readValue(new URL("http://localhost:9090/livre/"+id),new TypeReference<LivreDTO>(){});
        if(livreById!=null) {
            logger.info(" l'id du livre trouvé est :  "+livreById.getIdLivre());
            return livreById;
        } else {
            logger.info(" retour de nul car pas d'élément et de livre trouvé ");
            return null;
        }
    }

    /*Methode pour transformer un livreDTO en livreForm
    public LivreForm transformerLivreDTOEnLivreForm(LivreDTO livreDTO){
        LivreForm livreForm = new LivreForm ();
        livreForm.setIdLivre(livreDTO.getIdLivre());
        livreForm.setTitre(livreDTO.getTitre());
        livreForm.setAuteur(livreDTO.getAuteur());
        livreForm.setResume(livreDTO.getResume());
        livreForm.setNombrePages(livreDTO.getNombrePages());
        livreForm.setNomCategorie(livreDTO.getNomCategorie());
        livreForm.setPrixLocation(livreDTO.getPrixLocation());
        livreForm.setEtatLivre(livreDTO.getEtatLivre());
        logger.info(" retour valeur de bibliotheque de livreDTO"+livreDTO.getBibliotheque());
        livreForm.setBibliotheque( String.valueOf(livreDTO.getBibliotheque().getIdBibliotheque()));
        return livreForm;
    }*/

    /*Methode pour transformer une liste de livreDTO en liste de livreForm
    public List<LivreForm> transformerListeLivreDTOEnListeLivreForm(List<LivreDTO> livres){
        List<LivreForm> listeLivresForm =new ArrayList<>();
        for (LivreDTO livre:livres) {
            listeLivresForm.add(transformerLivreDTOEnLivreForm(livre));
        }
        return listeLivresForm;
    }*/

    /*Methode pour transformer un livreForm en livreDTO*/
    public LivreDTO transformerLivreFormEnLivreDTO(LivreForm livreForm) throws ParseException, IOException {
        LivreDTO livreDTO = new LivreDTO ();
        livreDTO.setIdLivre(livreForm.getIdLivre());
        livreDTO.setTitre(livreForm.getTitre());
        livreDTO.setAuteur(livreForm.getAuteur());
        //SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        //livreDTO.setPublication(simpleDateFormat01.parse(livreForm.getPublication()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1= format.parse ( livreForm.getPublication());
        livreDTO.setPublication(date1);
        livreDTO.setResume(livreForm.getResume());
        livreDTO.setNombrePages(livreForm.getNombrePages());
        livreDTO.setNomCategorie(livreForm.getNomCategorie());
        //SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        //livreDTO.setDateAchat(simpleDateFormat02.parse(livreForm.getDateAchat()));
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2= format1.parse (livreForm.getDateAchat());
        livreDTO.setDateAchat(date2);
        livreDTO.setPrixLocation(livreForm.getPrixLocation());
        livreDTO.setEtatLivre(livreForm.getEtatLivre());
        livreDTO.setDisponibilite(true);
        logger.info(" retour valeur de bibliotheque de livreForm "+livreForm.getBibliotheque());
        BibliothequeDTO bibliothequetouve = bibliothequeService.getBibliothequeById(Integer.parseInt(livreForm.getBibliotheque()));
        livreDTO.setBibliotheque(bibliothequetouve);
        return livreDTO;
    }

    /*Methode pour transformer une liste de livreForm en liste de livreDTO*/
    public List<LivreDTO> transformerListeLivreFormEnListeLivreDTO(List<LivreForm> livres) throws ParseException, IOException {
        List<LivreDTO> listeLivresDTO =new ArrayList<>();
        for (LivreForm livre:livres) {
            listeLivresDTO.add(transformerLivreFormEnLivreDTO(livre));
        }
        return listeLivresDTO;
    }


    /*Methode pour envoyer un livre à l'API pour enregistrement*/
    public LivreDTO enregistrerUnLivre(LivreDTO livre) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(livre);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/livre/addLivre"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<LivreDTO>(){});

    }
}
