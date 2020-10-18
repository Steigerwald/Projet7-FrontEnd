package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivreService {

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    /*Methode pour obtenir tous les livres de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivres() throws IOException {

        ObjectMapper mapper =new ObjectMapper();
        List<LivreDTO> tousLivres = mapper.readValue(new URL("http://localhost:9090/livre/"),List.class);
        if(tousLivres.size() > 0) {
            logger.info(" retour liste tousLivres car la taille de laliste >0 "+tousLivres);
            return tousLivres;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }

}
