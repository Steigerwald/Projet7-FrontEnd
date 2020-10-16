package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@RequestMapping("/bibliotheque")
public class BibliothequeController {

    /* controller pour avoir toutes les bibliotheques*/
    @RequestMapping(path="/",method = RequestMethod.GET)
    public String getAllBibliotheques(Model model) {
        List<BibliothequeDTO> toutesBibliotheques= bibliothequeService.findAll();
        model.addAttribute("bibliotheques",toutesBibliotheques);
        return "ListeBibliotheques";
    }

}
