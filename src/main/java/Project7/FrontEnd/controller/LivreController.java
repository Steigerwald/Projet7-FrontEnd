package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.SearchDTO;
import Project7.FrontEnd.service.LivreService;
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
        logger.info(" retour valeutr des livres du controller "+livres.get(0));
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
        livreService.sendSearchLivre(search);
        logger.info(" retour valeur de search du controller "+search.getAuteur()+" "+search.getNomCategorie()+" "+search.getTitre());
        return "redirect:/livre/all/recherches";
    }

    /* controller pour avoir le resultat de la recherche Livre*/
    @RequestMapping(value="/all/recherches",method = RequestMethod.GET)
    public String getAllLivresRecherches(Model model, Principal principal) throws IOException, ParseException {
        List<LivreDTO> livresRecherches = livreService.getAllLivresRecherches();
        logger.info(" retour valeur des livres du controller "+livresRecherches.get(0));
        model.addAttribute("livresRecherches",livresRecherches);
        return "livre/listeLivresRecherches";
    }


}
