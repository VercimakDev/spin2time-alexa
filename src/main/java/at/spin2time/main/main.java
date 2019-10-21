package at.spin2time.main;

import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.StartTimeTrackingIntentHandler;

import java.sql.*;
import java.util.ArrayList;
/*
Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select p_name from p_projects");
            while(rs.next())
                System.out.println(rs.getString(1));
            con.close();
 */

public class main {
    static StartTimeTrackingIntentHandler st;
    /*
    This class is for test purposes only
    This class willbe removed after finishing the development of the Alexa skill
     */


    public static void main(String [] args){
        ConnectionClass cc = new ConnectionClass();
        try {

            System.out.println(cc.selectQueryBuilder("select * from spin2timedb.p_projects;").get(0));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
