package model.filter;

public class StaffFilter extends Filter{
    private String staffEmail;
    public StaffFilter(String staffEmail){
        this.staffEmail=staffEmail;
    }
    @Override
    public String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM staff WHERE 1=1");
        if (this.staffEmail != null) {
            query.append(" AND email LIKE '").append(this.staffEmail).append("%'");
        }
        return query.toString();
    }

}
