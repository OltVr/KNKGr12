package model.filter;

public class UserFilter extends Filter{
    private String userEmail;
    public UserFilter(String staffEmail){
        this.userEmail=staffEmail;
    }
    @Override
    public String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM user WHERE 1=1");
        if (this.userEmail != null) {
            query.append(" AND email LIKE '").append(this.userEmail).append("%'");
        }
        return query.toString();
    }

}
