package Crossbox;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.*;

public class TheMain {
    public static Connection dataSource() throws URISyntaxException, SQLException {

        String ConnectionString ="jdbc:postgresql://ec2-44-206-89-185.compute-1.amazonaws.com:5432/d3ub8hubeshogo";
        String username = "tguwfnedyttdwg";
        String password = "b1a6beb6723d749cd56dd886eb4ec0ba0d2bfcb9246d6baef2839e340d6e938f";

        return DriverManager.getConnection(ConnectionString, username, password);
    }
    public static void main(String[] args) throws SQLException, InterruptedException {
        Socket socket = null;
        try {
            Connection con = dataSource();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hostport WHERE id = 1");
            rs.next();
            socket = new Socket(rs.getString(2), rs.getInt(3));
//            socket = new Socket("localhost",8000);
            Client client = new Client(socket);
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e) {
            int err = JOptionPane.showOptionDialog(null, "Server Down!Please Try Again Later.\n"+e.getMessage(), "Server Error", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            e.getMessage();
            System.out.println("Server Down");

            if(err == 0) {
                System.exit(0);
            }


        }


    }
}
