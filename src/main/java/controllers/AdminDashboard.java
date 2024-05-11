package controllers;

import App.Navigator;
import database.DatabaseUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Room;
import model.dto.InsertRoomDto;
import repository.UserRepository;
import service.AdminService;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private AnchorPane roomPane;

    @FXML
    private void handleLogout(MouseEvent me) {
        Navigator.navigate(me,Navigator.LOGIN_PAGE);
    }

    @FXML
    private TextField txtRoom;

    @FXML
    private TextField txtFloor;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox<String> roomType;

    @FXML
    private ComboBox<Integer> Capacity;

    @FXML
    private ComboBox<Integer> Beds;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomNumber_col;

    @FXML
    private TableColumn<Room, String> floorNumber_col;

    @FXML
    private TableColumn<Room, String> roomType_col;

    @FXML
    private TableColumn<Room, String> bedNumber_col;


    @FXML
    private TableColumn<Room, String> price_col;

    @FXML
    private TableColumn<Room, String> capacity_col;

    @FXML
    private TableColumn<Room, String> Available_col;
    @FXML
    private Text txtRoomsBooked;
    @FXML
    private Text txtTotalIncome;


    public void updateTotalIncome(){
        Connection conn= null;
        PreparedStatement statement=null;
        ResultSet rez=null;
        try {
            String query="SELECT SUM(r.price) AS total_price FROM rooms r JOIN reservation res ON r.roomNumber = res.roomNumber";
            conn=DatabaseUtil.getConnection();
            statement= conn.prepareStatement(query);
            rez=statement.executeQuery();
            if(rez.next()){
                int totali=rez.getInt("total_price");
                txtTotalIncome.setText(String.valueOf(totali+" $"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRoomsBooked(){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
        String query= "SELECT COUNT(*) as Rooms_booked FROM reservation WHERE reservationDate = ?";
        connection= DatabaseUtil.getConnection();
        statement =connection.prepareStatement(query);
        statement.setDate(1,java.sql.Date.valueOf(LocalDate.now()));
        result=statement.executeQuery();
        if (result.next()){
            int roomsCount= result.getInt("Rooms_booked");
            txtRoomsBooked.setText(String.valueOf(roomsCount));
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }




    @FXML
    private void handleAdd(){
        InsertRoomDto RoomData = new InsertRoomDto(
                Integer.parseInt(this.txtRoom.getText()),
                Integer.parseInt(this.txtFloor.getText()),
                this.roomType.getSelectionModel().getSelectedItem(),
                this.Capacity.getSelectionModel().getSelectedItem(),
                this.Beds.getSelectionModel().getSelectedItem(),
                Double.parseDouble(this.txtPrice.getText())
        );

        boolean b = AdminService.addRoom(RoomData);
        if (!(b)) {
            System.out.println("Room already exists");
        } else {
            showList();
            clear();
        }

    }

    @FXML
    private void handleDashboard(){
        dashboardPane.setVisible(true);
        roomPane.setVisible(false);
    }

    @FXML
    private void handleRooms(){
        dashboardPane.setVisible(false);
        roomPane.setVisible(true);
    }

    private void showList(){
        ObservableList<Room> listData = UserRepository.ListRoom();

        roomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        floorNumber_col.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
        roomType_col.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        bedNumber_col.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        Available_col.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            String availability = room.isAvailable() ? "Available" : "Unavailable";
            return new SimpleStringProperty(availability);
        });

        roomTable.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomType.getItems().addAll("Sea View", "City View");
        Capacity.getItems().addAll(1, 2,3,4,5,6,7,8);
        Beds.getItems().addAll(1, 2,3,4);
        showList();
        updateRoomsBooked();
        updateTotalIncome();
    }

    private void clear(){
        txtRoom.setText("");
        txtFloor.setText("");
        txtPrice.setText("");
        roomType.getSelectionModel().clearSelection();
        roomType.setPromptText("Type");
        Capacity.getSelectionModel().clearSelection();
        Capacity.setPromptText("Capacity");
        Beds.getSelectionModel().clearSelection();
        Beds.setPromptText("Number of Beds");
    }

    @FXML
    private void  roomSelect(){
        Room room = roomTable.getSelectionModel().getSelectedItem();
//        int num = roomTable.getSelectionModel().getSelectedIndex();

        if (room!=null){
        txtRoom.setText(String.valueOf(room.getRoomNumber()));
        txtFloor.setText(String.valueOf(room.getFloorNumber()));
        txtPrice.setText(String.valueOf(room.getPrice()));}

    }
}
