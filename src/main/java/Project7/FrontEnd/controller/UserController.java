package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public ReservationService reservationService;

    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    /* controller de la page de présentation */
    @RequestMapping(path="home",method = RequestMethod.GET)
    public String formPresentation(){
        logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
        return "home/home";
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
