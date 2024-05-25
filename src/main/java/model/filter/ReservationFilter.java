package model.filter;

public class ReservationFilter extends Filter{
    private String userEmail;
    private Integer reservationID;
    public ReservationFilter(String userEmail, Integer reservationID){
        this.userEmail=userEmail;
        this.reservationID=reservationID;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public Integer getReservationID() {
        return reservationID;
    }
    @Override
    public String buildQuery() {
        String query = "SELECT * FROM reservation WHERE 1=1";

        if (this.userEmail != null && !this.userEmail.isEmpty()) {
            query+=" AND email LIKE ?";
        }
        if (this.reservationID != null) {
            query+=" AND reservationID = ?";
        }

        return query;
    }
}
