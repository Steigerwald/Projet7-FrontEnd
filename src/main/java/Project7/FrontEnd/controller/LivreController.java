package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    public LivreService livreService;


    /* controller pour avoir tous les livres*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllLivres(Model model, Principal principal) throws IOException {
        List<LivreDTO> livres = livreService.getAllLivres();
        model.addAttribute("livres",livres);
        return "livre/ListeLivres";
    }

}
