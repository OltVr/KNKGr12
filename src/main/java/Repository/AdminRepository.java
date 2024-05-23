package Repository;

import database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository {

    public static int calculateTotalIncome(){
        Connection con=null;
        PreparedStatement statement=null;
        ResultSet rez=null;

        try{
            String Query= "SELECT SUM(DATEDIFF(res.checkOutDate, res.checkInDate) * r.price) AS total_price FROM rooms r JOIN reservation res ON r.roomNumber = res.roomNumber";
            con= DatabaseUtil.getConnection();
            statement=con.prepareStatement(Query);
            rez=statement.executeQuery();

            if (rez.next()){
                int price= rez.getInt("total_price");
                return price;
            }
            return 0;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
