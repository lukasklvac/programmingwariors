package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class Controller  implements Initializable{

    @FXML
    private TableView<Cars> table_cars;

    @FXML
    private TableColumn<Cars, Integer> col_id;

    @FXML
    private TableColumn<Cars, String> col_name;

    @FXML
    private TableColumn<Cars, String> col_date;

    @FXML
    private TableColumn<Cars, String> col_time ;

    @FXML
    private TableColumn<Cars, String> col_type;


    @FXML
    private TableColumn<Cars, Integer> col_phone;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_date;

    @FXML
    private TextField txt_time;

    @FXML
    private TextField txt_type;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_phone;

    @FXML
    private TextField filterField;


    ObservableList<Cars> listM;
    ObservableList<Cars> dataList;



    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void Add_users (){
        conn = mysqlconnect.ConnectDb();
        String sql = "insert into cars (name, date, time, type, phone)values(?,?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, txt_date.getText());
            pst.setString(3, txt_time.getText());
            pst.setString(4, txt_type.getText());
            pst.setString(5, txt_phone.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Rezervace byla úspěšně přidána");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    //////// metoda vybrání ///////
    @FXML
    void getSelected (MouseEvent event){
        index = table_cars.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index).toString());
        txt_date.setText(col_date.getCellData(index).toString());
        txt_time.setText(col_time.getCellData(index).toString());
        txt_type.setText(col_type.getCellData(index).toString());
        txt_phone.setText(col_phone.getCellData(index).toString());

    }

    public void Edit (){
        try {
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_id.getText();
            String value2 = txt_name.getText();
            String value3 = txt_date.getText();
            String value4 = txt_time.getText();
            String value5 = txt_type.getText();
            String value6 = txt_phone.getText();
            String sql = "update cars set car_id= '"+value1+"',name= '"+value2+"',date= '"+
                    value3+"',time= '"+value4+"',type= '"+value5+"' , phone= '"+value6+"' where car_id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Rezervace byla úspěšně aktualizovaná");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
        conn = mysqlconnect.ConnectDb();
        String sql = "delete from cars where car_id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Rezervace byla úspěšně smazána");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Cars,Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Cars,String>("name"));
        col_date.setCellValueFactory(new PropertyValueFactory<Cars,String>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<Cars,String>("time"));
        col_type.setCellValueFactory(new PropertyValueFactory<Cars,String>("type"));
        col_phone.setCellValueFactory(new PropertyValueFactory<Cars,Integer>("phone"));

        listM = mysqlconnect.getDataCars();
        table_cars.setItems(listM);
    }




    @FXML
    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<Cars,Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Cars,String>("name"));
        col_date.setCellValueFactory(new PropertyValueFactory<Cars,String>("date"));
        col_time.setCellValueFactory(new PropertyValueFactory<Cars,String>("time"));
        col_type.setCellValueFactory(new PropertyValueFactory<Cars,String>("type"));
        col_phone.setCellValueFactory(new PropertyValueFactory<Cars,Integer>("phone"));

        dataList = mysqlconnect.getDataCars();
        table_cars.setItems(dataList);
        FilteredList<Cars> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches username
                } else if (person.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }else if (person.getTime().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }
                else if (String.valueOf(person.getType()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches email

                else
                    return false; // Does not match.
            });
        });
        SortedList<Cars> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_cars.comparatorProperty());
        table_cars.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        search_user();
        // Code Source in description
    }
}