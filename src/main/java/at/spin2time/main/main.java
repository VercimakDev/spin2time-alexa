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
    public static void main(String [] args){
        ConnectionClass cc = new ConnectionClass();
        String username = cc.getUserFromVoiceId("class User {\n" +
                "    userId: amzn1.ask.account.AHY2TDS6PBIOQY5FO7XEFET2IVXLIEBKXZNQT5ZYXNPXWSZKOQ66N4ZVFPYLR7RDOEQK43KGAC2DZXCJTK5HKJIET64JNENL4BZIB7YUOEWN44ENWLKUCDGNNQESTR4F3BEQSGT2OFE2XGSSCUWP7NKDAW5JXD3DDTNTO5RXGECS66Q6YDUICVMJ6SNSCTNR5C3NKGGIUDGNRBA\n" +
                "    accessToken: null\n" +
                "    permissions: null\n" +
                "}");
        System.out.println(username);
    }

}
