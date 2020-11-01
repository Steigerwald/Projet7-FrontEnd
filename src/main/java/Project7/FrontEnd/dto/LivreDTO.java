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

    @DateTimeFormat (pattern="MM-yyyy")
    private Date publication;

    private String resume;
    private String nombrePages;
    private String nomCategorie;

    @DateTimeFormat(pattern="dd-MM-yy")
    private Date dateAchat;

    private int prixLocation;
    private String etatLivre;
    private Boolean disponibilite;
    private ReservationDTO reservation;


    public LivreDTO() throws ParseException {
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("MM/yyyy");
        String date1 = "10/2020";
        publication=simpleDateFormat01.parse(date1);
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date2 = "30/10/2020 12:30";
        dateAchat=simpleDateFormat02.parse(date2);
        setDisponibilite(true);
    }

    public String getPublication() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("MM/yyyy");
        if (publication!=null){
        String dateString = dateFormat.format(publication);
        return dateString;
        }else{
            return null;
        }
    }

    public String getDateAchat() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (dateAchat!=null){
        String dateString = dateFormat.format(dateAchat);
        return dateString;
        }else{
            return null;
        }
    }

    public String getDisponibilite() {
        if (disponibilite){
            return "oui";
        } else{
            return "non";
        }
    }

}
