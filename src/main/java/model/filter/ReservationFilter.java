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
        String query = "SELECT * FROM reservation";
        boolean firstCondition = true;

        if (this.userEmail != null && !this.userEmail.isEmpty()) {
            if (firstCondition) {
                query += " WHERE";
                firstCondition = false;
            } else {
                query += " AND";
            }
            query += " email LIKE ?";
        }

        if (this.reservationID != null) {
            if (firstCondition) {
                query += " WHERE";
                firstCondition = false;
            } else {
                query += " AND";
            }
            query += " reservationID = ?";
        }

        return query;
    }

}
