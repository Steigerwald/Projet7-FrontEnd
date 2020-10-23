package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
public class LivreDTO {

    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd");
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
    private Boolean disponibilite;
    private ReservationDTO reservation;

}
