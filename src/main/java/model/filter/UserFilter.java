package model.filter;

public class UserFilter extends Filter{
    private String userEmail;
    public UserFilter(String userEmail){
        this.userEmail=userEmail;
    }
    public String buildQuery() {
        String query = "SELECT * FROM user";
        boolean firstCondition = true;

        if (this.userEmail != null) {
            if (firstCondition) {
                query += " WHERE";
                firstCondition = false;
            } else {
                query += " AND";
            }
            query += " email LIKE '" + this.userEmail + "%'";
        }

        return query;
    }

}
