package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.service.AuthService;
import Project7.FrontEnd.service.BibliothequeService;
import Project7.FrontEnd.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    /* controller pour avoir toutes les bibliotheques*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllBibliotheques(Model model) throws IOException, InterruptedException {
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();

        if(authService.getUserConnecte()!=null){
            model.addAttribute("role",authService.getUserConnecte().getRole().getNomRole());
        }else{
            model.addAttribute("role","null");
        }
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("bibliotheques",bibliotheques);
        return responseService.gestionDeReponseHttp(responseService.getResponseStatut(),"bibliotheque/listeBibliotheques");
    }

}
