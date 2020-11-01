package Project7.FrontEnd.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class LivreDTO {

    private int idLivre;
    private String titre;
    private String auteur;

    @DateTimeFormat (pattern="yyyy-MM")
    private Date publication;

    private String resume;
    private String nombrePages;
    private String nomCategorie;

    @DateTimeFormat(pattern="yyyy-MM-dd")
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

    public void setPublication(String publication) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mi:ss");

        this.publication = sdf.parse(publication);
    }
}
