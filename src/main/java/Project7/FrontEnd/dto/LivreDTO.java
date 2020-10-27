package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class LivreDTO {

    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd");
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

    public String getPublication() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM");
        String dateString = dateFormat.format(publication);
        return dateString;
    }

    public String getDateAchat() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = dateFormat.format(dateAchat);
        return dateString;
    }

    public String getDisponibilite() {
        if (disponibilite){
            return "oui";
        } else{
            return "non";
        }
    }


}
