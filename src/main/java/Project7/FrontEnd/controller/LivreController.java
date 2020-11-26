package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.SearchDTO;
import Project7.FrontEnd.form.LivreForm;
import Project7.FrontEnd.service.BibliothequeService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    public LivreService livreService;

    @Autowired
    public BibliothequeService bibliothequeService;

    Logger logger = (Logger) LoggerFactory.getLogger(LivreController.class);

    /* controller pour avoir tous les livres*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllLivres(Model model, Principal principal) throws IOException, ParseException {
        List<LivreDTO> livres = livreService.getAllLivres();
        List<Integer> nombres=livreService.obtenirNombreExempalaires(livres);
        logger.info(" retour valeur des livres du controller "+livres.get(0));
        model.addAttribute("livres",livres);
        model.addAttribute("nombre",nombres);
        return "livre/listeLivres";
    }

    /* controller pour avoir tous les livres*/
    @RequestMapping(value="/exemplaires/{id}",method = RequestMethod.GET)
    public String getAllExeemplaires(Model model, Principal principal,@PathVariable("id") int id) throws IOException, ParseException {
        List<LivreDTO> livresExemplaires = livreService.getAllExemplairesById(id);
        model.addAttribute("nombre",livresExemplaires.size());
        model.addAttribute("livresExemplaires",livresExemplaires);
        return "livre/listeExemplaires";
    }

    /* controller pour avoir tous les livres disponibles*/
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
        logger.info(" retour valeur de search du controller "+search.getAuteur()+" "+search.getNomCategorie()+" "+search.getTitre());
        model.addAttribute("livresRecherches", listeLivresRecherches);
        return "livre/listeLivresRecherches";
    }

    /* controller pour avoir le détail du livre */
    @RequestMapping(path="/details/{id}",method = RequestMethod.GET)
    public String getDetailsLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, ParseException {
        //User userConnecte = userService.getUserByMail(principal.getName());
        LivreDTO livreDetail = livreService.getLivreById(id);
        model.addAttribute("livre",livreDetail);
        //model.addAttribute("user", userConnecte);
        return "livre/livreDetails"; //view
    }

    /* controller pour ajouter un livre dans la bibliotheque*/
    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String addLivre(Model model, Principal principal) throws IOException, ParseException {
        LivreForm newLivre = new LivreForm();
        logger.info(" retour valeur de newLivre "+newLivre.toString());
        model.addAttribute("livre",newLivre);
        logger.info(" retour valeur de bibliotheque de newLivre "+newLivre.getBibliotheque());
        model.addAttribute("titreFormLivre","ajouter un livre dans la bibliothèque");
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();
        model.addAttribute("bibliotheques",bibliotheques);
        return "livre/addLivre";//view
    }

    /* controller pour envoyer un ajout d'un livre pour l'API*/
    @RequestMapping(value="/createLivre",method = RequestMethod.POST)
    public String createLivreOrUpdateLivre(LivreForm livre,Model model) throws IOException, InterruptedException, ParseException {
        logger.info(" retour valeur de bibliotheque de livre du controller create "+livre.getBibliotheque());
        LivreDTO livreEnregistre =livreService.enregistrerUnLivre(livreService.transformerLivreFormEnLivreDTO(livre));
        logger.info(" retour valeur de search du controller "+livreEnregistre.getAuteur()+" "+livreEnregistre.getNomCategorie()+" "+livreEnregistre.getTitre());
        return "redirect:/livre/all";
    }

    /* controller pour effacer un livre de l'API*/
    @RequestMapping(path="/effacer/{id}",method = RequestMethod.POST)
    public String effacerLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, ParseException, InterruptedException {
        LivreDTO livreDetail = livreService.getLivreById(id);
        livreService.effacerUnLivre(livreDetail);
        return "redirect:/livre/all"; //view
    }

    /* controller pour modifier un livre dans la bibliotheque*/
    @RequestMapping(value="/modify/{id}",method = RequestMethod.POST)
    public String modifyLivre(Model model, Principal principal,@PathVariable("id") int id) throws IOException, ParseException {
        LivreDTO livreDetail = livreService.getLivreById(id);
        LivreForm livreFormModifie = livreService.transformerLivreDTOEnLivreForm(livreDetail);
        logger.info(" retour valeur de livreFormModifie "+livreFormModifie.toString());
        model.addAttribute("livre",livreFormModifie);
        logger.info(" retour valeur de bibliotheque de livreFormModifie "+livreFormModifie.getBibliotheque());
        model.addAttribute("titreFormLivre","modifier un livre dans la bibliothèque");
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();
        model.addAttribute("bibliotheques",bibliotheques);
        return "livre/modifyLivre";//view
    }

    /* controller pour modifier un livre de l'API*/
    @RequestMapping(path="/modifier",method = RequestMethod.POST)
    public String modifierLivre(LivreForm livre,Model model,Principal principal) throws IOException, ParseException, InterruptedException {
        LivreDTO livreDTO=livreService.transformerLivreFormEnLivreDTO(livre);
        LivreDTO livreModifie= livreService.modifierUnLivre(livreDTO);
        model.addAttribute("livre",livreModifie);
        return "redirect:/livre/all"; //view
    }

}
