package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BibliothequeService {

    Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeService.class);

    /*Methode pour obtenir tous les sites de la base de données*/
    public List<BibliothequeDTO> getAllBibliotheques() {
        List<BibliothequeDTO> result1 =(List<BibliothequeDTO>) siteRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<BibliothequeDTO>();
        }
    }

}
