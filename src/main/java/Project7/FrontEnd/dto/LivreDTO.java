package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class LivreDTO {

    private int idLivre;
    private String titre;
    private String auteur;
    private Date publication;
    private String resume;
    private String nombrePages;
    private String nomCategorie;
    private Date dateAchat;
    private int prixLocation;
    private String etatLivre;
    private Boolean disponibilite;
    private ReservationDTO reservation;
    private BibliothequeDTO bibliotheque;

    public LivreDTO() throws ParseException {
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("MM-yyyy-dd HH:mm:ss");
        String date1 = "2005-04-28 02:45:30";
        publication=simpleDateFormat01.parse(date1);
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("MM-yyyy-dd HH:mm:ss");
        String date2 = "2005-04-28 02:45:30";
        dateAchat=simpleDateFormat02.parse(date2);
        setDisponibilite(true);
    }
}
