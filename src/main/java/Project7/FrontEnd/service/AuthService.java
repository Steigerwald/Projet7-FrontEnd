package Project7.FrontEnd.service;

import Project7.FrontEnd.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    Logger logger = (Logger) LoggerFactory.getLogger(AuthService.class);

    /*Methode pour mémoriser le Bearer de la session*/
    public Map<String,String> memoriserBearer (String userName,String token){
        Map<String,String> memoire=new HashMap<>();
        memoire.put(userName,token);
      return memoire;
    };

    /*Methode pour vérifier l'authentification Bearer pour chaque controller*/

}
