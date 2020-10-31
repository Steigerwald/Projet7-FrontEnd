package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.SearchDTO;
import Project7.FrontEnd.service.LivreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    public LivreService livreService;

    Logger logger = (Logger) LoggerFactory.getLogger(LivreController.class);

    /* controller pour avoir tous les livres*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllLivres(Model model, Principal principal) throws IOException, ParseException {
        List<LivreDTO> livres = livreService.getAllLivres();
        logger.info(" retour valeutr des livres du controller "+livres.get(0));
        model.addAttribute("livres",livres);
        return "livre/listeLivres";
    }

    /* controller pour avoir tous les livres*/
    @RequestMapping(value="/all/disponibles",method = RequestMethod.GET)
    public String getAllLivresDisponibles(Model model, Principal principal) throws IOException, ParseException {
        List<LivreDTO> livres = livreService.getAllLivresDisponibles();
        logger.info(" retour valeur de la liste livres du controller "+livres.get(0));
        model.addAttribute("livresDisponibles",livres);
        return "livre/listeLivresDisponibles";
    }

    /* controller pour afficher la page recherche de livres par catégorie*/
    @RequestMapping(value="/search",method = RequestMethod.GET)
    public String RechercherLivres(Model model, Principal principal) throws IOException, ParseException {
        SearchDTO rechercheCriteres=new SearchDTO();
        model.addAttribute("search",rechercheCriteres);
        return "/livre/rechercheLivre";
    }

    /* controller pour recevoir une recherche d'un livre*/
    @RequestMapping(value="/search/mycriteres",method = RequestMethod.POST)
    public String getSearchLivre(SearchDTO search, Model model, Principal principal) throws IOException, ParseException, InterruptedException {
        List<LivreDTO> listeLivresRecherches =livreService.sendSearchLivre(search);
        logger.info(" retour valeur date publication du premier livre "+listeLivresRecherches.get(0).getPublication());
        logger.info(" retour valeur de search du controller "+search.getAuteur()+" "+search.getNomCategorie()+" "+search.getTitre());
        model.addAttribute("livresRecherches", listeLivresRecherches);
        return "livre/listeLivresRecherches";
    }

    /* controller pour avoir le détail du livre */
    @RequestMapping(path="/details/{id}",method = RequestMethod.GET)
    public String getDetailsLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, ParseException {
        //User userConnecte = userService.getUserByMail(principal.getName());
        LivreDTO livreDetail = livreService.getLivreById(id);
        model.addAttribute("livre", livreDetail);
        //model.addAttribute("user", userConnecte);
        return "livre/livreDetails"; //view
    }

}
