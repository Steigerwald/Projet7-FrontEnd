package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.SearchDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LoginForm;
import Project7.FrontEnd.form.UserForm;
import Project7.FrontEnd.service.AuthService;
import Project7.FrontEnd.service.ReservationService;
import Project7.FrontEnd.service.UserService;
import org.apache.tomcat.jni.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public ReservationService reservationService;

    @Autowired
    public UserService userService;

    @Autowired
    public AuthService authService;


    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    /* controller de la page de présentation */
    @RequestMapping(path="home",method = RequestMethod.GET)
    public String formPresentation(){
        logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
        return "home/home";
    }

    /* controller de la page de login */
    @RequestMapping(path="user/login",method = RequestMethod.GET)
    public String formLogin(Model model){
        LoginForm newUser =new LoginForm();
        model.addAttribute("utilisateur",newUser);
        logger.info(" on est dans la page du login");
        return "user/UserLogin";
    }

    /* controller de la page de logout */
    @RequestMapping(path="user/logout",method = RequestMethod.GET)
    public String formLogout(){
        logger.info(" on est dans la page du logout");
        return "user/UserLogin";
    }

    /* controller pour recevoir un mail et mot de passe pour obtenir l'autorisation*/
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String getUserConnexion(LoginForm utilisateur, Model model, Principal principal) throws IOException, ParseException, InterruptedException {
        logger.info(" on est dans la requete post  du login ");
        String token =userService.getTokenByMailAndMotDePasse(utilisateur);
        switch (token) {
            case "not found" :
                return "redirect:/user/login?error";
            case "mot de passe invalide":
                return "redirect:/user/login?error";
            default:
                authService.memoriserBearer(utilisateur.getUserName(),token);
                logger.info(" la valeur du token est: "+token);
                return "home/home";
        }
    }

    /* controller de la page de espace perso */
    @RequestMapping(path="user/EspacePersonnel",method = RequestMethod.GET)
    public String EspacePersonnel(Model model) throws IOException, InterruptedException {
        logger.info(" on est passe par la avant l'appel de la page EspacePersonnel");
        List<ReservationDTO> listeReservations = reservationService.getAllReservations();
        listeReservations=reservationService.verifierListeReservations(listeReservations);
        List<String> listeDates =reservationService.calculerDateLimitesDeretraitDUneListeDeReservation(listeReservations);
        model.addAttribute("reservations",listeReservations);
        model.addAttribute("dates",listeDates);
        return "user/EspacePerso";
    }

    /* controller de la page de espace admin */
    @RequestMapping(path="user/EspaceAdministration",method = RequestMethod.GET)
    public String EspaceAdministration(Model model) throws IOException, InterruptedException {
        logger.info(" on est passe par la avant l'appel de la page EspaceAdministration");
        List<ReservationDTO> listeReservationsAValider = reservationService.getAllReservationsAValider();
        List<ReservationDTO> listeReservationsEnCours = reservationService.getAllReservationsEnCours();
        listeReservationsEnCours=reservationService.verifierListeReservations(listeReservationsEnCours);
        List<String> listeDates =reservationService.calculerDateLimitesDeretraitDUneListeDeReservation(listeReservationsEnCours);
        logger.info(" retour valeur des réservation à valider du controller "+listeReservationsAValider);
        model.addAttribute("reservations",listeReservationsAValider);
        model.addAttribute("reservationsEnCours",listeReservationsEnCours);
        model.addAttribute("dates",listeDates);
        return "user/EspaceAdmin";
    }

    /* controller pour avoir un role par id de l'API*/

}
