package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BibliothequeService {

    Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeService.class);

    /*Methode pour obtenir tous les sites de la base de données*/
    public List<BibliothequeDTO> getAllBibliotheques() throws IOException {

        ObjectMapper mapper =new ObjectMapper();
        List<BibliothequeDTO> toutesBibliotheques = mapper.readValue(new URL("http://localhost:5500/my-notes"), List.class);
        if(toutesBibliotheques.size() > 0) {
            logger.info(" retour liste toutesBibliotheques car la taille de laliste >0 ");
            return toutesBibliotheques;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesBibliotheques ");
            return new ArrayList<BibliothequeDTO>();
        }
    }
}
