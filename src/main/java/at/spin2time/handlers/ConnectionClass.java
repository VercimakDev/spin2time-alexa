package at.spin2time.handlers;

import at.spin2time.exceptions.S2TRunntimeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionClass {

    private Statement connect(){
        Connection con;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            //rs = stmt.executeQuery("select p_name from p_projects");
            //con.close();
            return stmt;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
    public List selectQueryBuilder (String query) {
        ArrayList<String> sqllist = new ArrayList<String>();
        Statement st = connect();
        ResultSet rs = null;
        try {
            if (st != null) {
                rs = st.executeQuery(query);
            }
            int i = 0;
            if (rs != null) {
                while (rs.next()) {
                    sqllist.add(i, rs.getString(1));
                    i++;
                }
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            S2TRunntimeException exception = new S2TRunntimeException("Bei dem Selectstatement" +
                    " ist ein Fehler aufgetreten");
        }
        return sqllist;
    }
    public boolean insertQueryBuilder (String query) {
        boolean rs = false;
        try (Statement st = connect()) {

            rs = st.execute(query);
            st.close();

        } catch (SQLException e) {
            S2TRunntimeException exception = new S2TRunntimeException("Bei dem Insertstatement" +
                    " ist ein Fehler aufgetreten");
        }
        return rs;
    }
    public void stopTimeTracking (String name, String now_date) {
        try(Statement st = connect()) {
            st.execute("CALL StopTime('" + name + "','" + now_date + "');");
        } catch (SQLException e) {
            S2TRunntimeException exception = new S2TRunntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }

    }
}
