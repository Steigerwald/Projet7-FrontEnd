package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.SearchDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LivreService {

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);


    public RestTemplate restTemplate;


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
        List<LivreDTO> listM = mapper.readValue(response.body(), List.class);
        for (Iterator iterator = listM.iterator(); iterator.hasNext();) {
            LinkedHashMap linkedMap = (LinkedHashMap) iterator.next();
            System.out.println(linkedMap);
        }
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

}
