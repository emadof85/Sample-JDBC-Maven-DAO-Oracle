package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    protected Connection conn = null;
    public DBConnection() {
        //step1 load the driver class
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        //step2 create  the connection object
            this.conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle Driver Class not found !!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Unable to open the connection to hr !!");
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Unable to close connection");
            }
            conn = null;
        }
    }
}
