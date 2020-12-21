package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LoginForm;
import Project7.FrontEnd.form.UserForm;
import Project7.FrontEnd.service.AuthService;
import Project7.FrontEnd.service.ReservationService;
import Project7.FrontEnd.service.UserService;
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
    public String formPresentation(Principal principal,Model model) throws IOException, InterruptedException {
        logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
        return "home/home";
    }

    /* controller de la page de login */
    @RequestMapping(path="user/login",method = RequestMethod.GET)
    public String formLogin(Model model){
        LoginForm newUser =new LoginForm();
        model.addAttribute("utilisateur",newUser);
        UserDTO user = new UserDTO();
        model.addAttribute("user",user);
        logger.info(" on est dans la page du login");
        return "user/userLogin";
    }

    /* controller de la page de logout */
    @RequestMapping(path="user/logout",method = RequestMethod.GET)
    public String formLogout(Principal principal,Model model) throws IOException, InterruptedException {
        UserDTO newUserDTO=new UserDTO();
        newUserDTO.setMailUser(principal.getName());
        UserDTO userConnecte = userService.getUserByMail(newUserDTO);
        model.addAttribute("user", userConnecte);
        logger.info(" on est dans la page du logout");
        authService.authentification=false;
        authService.memoireToken="null";
        return "user/userLogin";
    }

    /* controller pour recevoir un mail et mot de passe pour obtenir l'autorisation*/
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String getUserConnexion(LoginForm utilisateur,Principal principal,Model model) throws IOException, InterruptedException {
        logger.info(" on est dans la requete post  du login ");
        logger.info(" le mail de l'uilisateur est: "+utilisateur.getUserName());
        String token =userService.getTokenByMailAndMotDePasse(utilisateur);
        switch (token) {
            case "not found" :
                authService.authentification=false;
                return "redirect:/user/login?error";
            case "mot de passe invalide":
                authService.authentification=false;
                return "redirect:/user/login?error";
            case "null":
                authService.authentification=false;
                return "redirect:/user/login?error";
            default:
                authService.memoireToken=token;
                authService.authentification=true;
                logger.info(" la valeur du token est: "+authService.memoireToken);
                UserDTO newUserDTO=new UserDTO();
                newUserDTO.setMailUser(utilisateur.getUserName());
                UserDTO userConnecte=userService.getUserByMail(newUserDTO);
                model.addAttribute("user",userConnecte);
                return "redirect:/home";
        }
    }

    /* controller de la page de création de compte */
    @RequestMapping(path="user/registration/create",method = RequestMethod.GET)
    public String formRegistrationUser(Model model){
        UserForm newUser =new UserForm();
        model.addAttribute("user",newUser);
        logger.info(" on est dans la page de création du compte User");
        return "user/addUser";

    }

    /* controller pour creer un user dans la base de données */
    @RequestMapping(path="user/addUser",method = RequestMethod.POST)
    public String createUser(UserForm user,Model model) throws IOException, InterruptedException {
        logger.info(" on est dans la requete post  de addUser ");
        UserDTO userDTO =userService.transformerUserFormEnUserDTO(user);
        userService.createUserDansBaseDeDonnees(userDTO);
        return "redirect:/user/login";
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
        return "user/espacePerso";
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
        return "user/espaceAdmin";
    }

    /* controller pour avoir un role par id de l'API*/

}
