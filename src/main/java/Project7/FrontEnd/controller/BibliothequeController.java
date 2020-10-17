package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/bibliotheque")
public class BibliothequeController {

    @Autowired
    public BibliothequeService bibliothequeService;


    /* controller pour avoir toutes les bibliotheques*/
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String getAllBibliotheques(Model model) throws IOException {
        //Recupérer les données json via la méthode getAllNotes(), les convertir en objets java et les stocker dans une liste
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();
        //Liste stockée à son tour dans un ModelMap renvoyée dans la réponse.
        ModelMap mp = new ModelMap("bibliotheques", bibliotheques);
        model.addAttribute("bibliotheques",mp);
        return "ListeBibliotheques";
    }

}
