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


<<<<<<< HEAD
    public static void main(String [] args){
       // ConnectionClass cc = new ConnectionClass();
          //  System.out.println(cc.selectQueryBuilder("select * from spin2timedb.p_projects;").get(1));
           // TimeManagmentClass time = new TimeManagmentClass();
           // System.out.print(time.getNow());
=======
    public static void main(String [] args) throws SQLException {
        ConnectionClass cc = new ConnectionClass();

        Statement st = cc.connect();
        String username = "daniel";
        String userid = cc.selectQueryBuilder("select u_id from u_users where u_username = '"+username+"'").get(0).toString();
        System.out.println(st.execute("select exists(SELECT wt_id from wt_worktable where wt_u_id = "+userid+" and wt_stop = null)"));
            //TimeManagmentClass time = new TimeManagmentClass();
            //System.out.print(time.getNow());
>>>>>>> 97c4f053c1ef3b77d2ca4f3449c9f8e18d115cea
           // cc.insertQueryBuilder("insert into wt_worktable values(null,1,'2019-10-22 16:00:00',null,2,null);");
           // TimeManagmentClass time = new TimeManagmentClass();

           // cc.stopTimeTracking("daniel",time.getNow());

    }

}
