package Project7.FrontEnd.form;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import lombok.Data;

import java.text.SimpleDateFormat;


@Data
public class LivreForm {
    private int idLivre;
    private String titre;
    private String auteur;
    private String publication;
    private String resume;
    private String nombrePages;
    private String nomCategorie;
    private String dateAchat;
    private int prixLocation;
    private String etatLivre;
    private String bibliotheque;
    }

