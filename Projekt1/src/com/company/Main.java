package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/autoservis?useUnicode=true&useJDBCCCompilantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            Connection con = DriverManager.getConnection(url, user, pass);
            if(con!=null)
            {
                System.out.println("Připojeno k databázi.");

            }

            String query = "select * from vozy";
            var statement = con.prepareStatement(query);
            ResultSet r = statement.executeQuery();
            while (r.next())
            {
                int id = r.getInt("id");
                String brand = r.getString("Znacka");
                        String model = r.getString("Model");
                int year = r.getInt("Rok vyroby");
                String spz = r.getString("SPZ");
                System.out.printf("%-10s%-20s%-20s%-10s%-20s\n",id, brand,model, year, spz);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
