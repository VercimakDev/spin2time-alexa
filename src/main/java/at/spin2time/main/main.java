package at.spin2time.main;

import at.spin2time.handlers.ConnectionClass;

import java.sql.*;
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
    /*
    This class is for test purposes only
    This class will be removed after finishing the development of the Alexa skill
     */
    public static void main(String [] args) {
        ConnectionClass connection = new ConnectionClass();
        connection.removeVoiceProfile("daniel");
    }

}
