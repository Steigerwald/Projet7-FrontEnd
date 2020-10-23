package Project7.FrontEnd.controller;

import Project7.FrontEnd.service.LivreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


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

}
