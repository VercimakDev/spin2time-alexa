package at.spin2time.main;

import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.StartTimeTrackingIntentHandler;
import at.spin2time.handlers.TimeManagmentClass;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    public static void main(String [] args) throws SQLException {
        ConnectionClass cc = new ConnectionClass();

        Statement st = cc.connect();
        String username = "daniel";
        String userid = cc.selectQueryBuilder("select u_id from u_users where u_username = '"+username+"'").get(0).toString();
        System.out.println(st.execute("select exists(SELECT * from pm_projectmembers where pm_u_id = "+userid+");"));
            //TimeManagmentClass time = new TimeManagmentClass();
            //System.out.print(time.getNow());
           // cc.insertQueryBuilder("insert into wt_worktable values(null,1,'2019-10-22 16:00:00',null,2,null);");
           // TimeManagmentClass time = new TimeManagmentClass();

           // cc.stopTimeTracking("daniel",time.getNow());


    }

}
