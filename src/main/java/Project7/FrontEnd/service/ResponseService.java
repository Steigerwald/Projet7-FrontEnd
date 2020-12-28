package Project7.FrontEnd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    Logger logger = (Logger) LoggerFactory.getLogger(ResponseService.class);


    /*Methode pour gérer la réponse négative ou autre réponse en cas de non identificaion*/
    public String gestionDeReponseHttp (String reponse){
        if (reponse.equals("403")){
            return "/login";
        }

        return "/login";
    }
}
