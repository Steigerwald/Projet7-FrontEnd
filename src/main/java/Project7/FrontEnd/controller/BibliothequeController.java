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
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllBibliotheques(Model model,Principal principal) throws IOException {
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();
        ModelMap mp = new ModelMap("bibliotheques", bibliotheques);
        model.addAttribute("bibliotheques",mp);
        return "bibliotheque/ListeBibliotheques";
    }
}
