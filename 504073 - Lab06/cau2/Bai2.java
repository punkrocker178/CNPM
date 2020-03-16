package cau2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bai2 {
    Connection con;

    public Bai2() throws SQLException {
        this.initConn();
    }

    public void initConn() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:33060/School","root","root");  
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
    }
}