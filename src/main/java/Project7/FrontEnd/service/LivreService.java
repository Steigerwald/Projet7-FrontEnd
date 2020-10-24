package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LivreService {

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    /*Methode pour obtenir tous les livres de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivres() throws IOException, ParseException {
        ObjectMapper mapper =new ObjectMapper();
        List<LivreDTO> tousLivres = mapper.readValue(new URL("http://localhost:9090/livre/"),List.class);
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
        List<LivreDTO> tousLivresDisponibles = mapper.readValue(new URL("http://localhost:9090/livre/disponibles"),List.class);
        if(tousLivresDisponibles.size() > 0) {
            logger.info(" retour liste tousLivres car la taille de laliste >0 "+tousLivresDisponibles);
            logger.info(" valeur livre "+tousLivresDisponibles.get(0));
            return tousLivresDisponibles;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }



}
