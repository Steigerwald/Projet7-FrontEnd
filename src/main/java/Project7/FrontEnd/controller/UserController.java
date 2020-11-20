package Project7.FrontEnd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/")
public class UserController {

    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    /* controller de la page de présentation */
    @RequestMapping(path="home",method = RequestMethod.GET)
    public String formPresentation(){
        logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
        return "home/home";
    }

    /* controller de la page de présentation */
    @RequestMapping(path="user/EspacePersonnel",method = RequestMethod.GET)
    public String EspacePersonnel(){
        logger.info(" on est passe par la avant l'appel de la page ZspacePersonnel");
        return "user/EspacePerso";
    }


    /* controller pour avoir un role par id de l'API*/

}
