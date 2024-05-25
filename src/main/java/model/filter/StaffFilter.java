package model.filter;

public class StaffFilter extends Filter{
    private String staffEmail;
    public StaffFilter(String staffEmail){
        this.staffEmail=staffEmail;
    }
    public String buildQuery() {
        String query = "SELECT * FROM staff";
        boolean firstCondition = true;

        if (this.staffEmail != null) {
            if (firstCondition) {
                query += " WHERE";
                firstCondition = false;
            } else {
                query += " AND";
            }
            query += " email LIKE '" + this.staffEmail + "%'";
        }

        return query;
    }

}
