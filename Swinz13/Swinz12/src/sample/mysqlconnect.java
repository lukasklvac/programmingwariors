package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;


/**
 *
 */
public class mysqlconnect {

    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/servis","root","");
            // JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public static ObservableList<Cars> getDataCars(){
        Connection conn = ConnectDb();
        ObservableList<Cars> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from cars");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Cars(Integer.parseInt(rs.getString("car_id")), rs.getString("name"), rs.getString("date"), rs.getString("time"), rs.getString("type"), rs.getInt("phone")));
            }
        } catch (Exception e) {
        }
        return list;
    }

}