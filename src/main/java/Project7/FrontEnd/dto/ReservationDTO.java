package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ReservationDTO {

    private int idReservation;
    private String etatReservation;
    private Date dateReservation;
    private Date dateDeRetrait;
    private int delaiDeLocation;
    private Boolean isactif;
    private UserDTO user;

    public String getDateReservation() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(dateReservation);
        return dateString;
    }

    public String getDateDeRetrait() {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = dateFormat.format(dateDeRetrait);
        return dateString;
    }

}
