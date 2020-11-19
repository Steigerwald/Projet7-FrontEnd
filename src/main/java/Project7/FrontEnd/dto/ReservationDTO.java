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
    private Boolean prolongation;
    private Boolean isactif;
    private UserDTO user;

    public String toStringDateReservation() {
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("MM-yyyy");
        return simpleDateFormat01.format(dateReservation);
    }

    public String toStringDateDeRetrait() {
        if (dateDeRetrait != null) {
            SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat02.format(dateDeRetrait);
        }else {
            return "pas encore retiré";
        }
    }
}
