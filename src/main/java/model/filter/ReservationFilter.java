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
        StringBuilder query = new StringBuilder("SELECT * FROM reservation WHERE 1=1");

        if (this.userEmail != null && !this.userEmail.isEmpty()) {
            query.append(" AND email LIKE ?");
        }
        if (this.reservationID != null) {
            query.append(" AND reservationID = ?");
        }

        return query.toString();
    }
}
