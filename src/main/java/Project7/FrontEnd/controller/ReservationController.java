package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    public ReservationService reservationService;

    /* controller pour avoir toutes les reservations*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllReservations(Model model, Principal principal) throws IOException {
        List<ReservationDTO> listeReservations = reservationService.getAllReservations();
        model.addAttribute("reservations",listeReservations);
        return "reservation/listeReservations";
    }

}
