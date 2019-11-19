package at.spin2time.handlers;

import at.spin2time.exceptions.S2TRunntimeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionClass {

    public Statement connect(){
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

    public boolean userExists(String username){
        boolean res = false;
        Statement st = connect();
        try{

            res = st.execute("select exists(SELECT u_id from u_users where u_username = '"+username+"');");
            st.close();

        }catch (SQLException e){
            S2TRunntimeException exception = new S2TRunntimeException("User "+username+" wurde nicht gefunden.");
        }
        return res;
    }

    public boolean isProjectMember(String username, String projectId){
        try(Statement st = connect()){

            String userid = selectQueryBuilder("select u_id from u_users where u_username = '"+username+"'").get(0).toString();

            if(st.execute("select exists(SELECT * from pm_projectmembers where pm_u_id = "+userid+");")){

                st.close();

                return true;
            }

        }catch (SQLException e){
            S2TRunntimeException exception = new S2TRunntimeException("User "+username+" ist kein Projektmitglied von Projekt "+projectId);
        }
        return false;
    }

    public boolean projectExists(String projectid){
        boolean res = false;
        try(Statement st = connect()){

            res = st.execute("select exists(SELECT p_name from p_projects where p_id = "+projectid+");");
            st.close();

        }catch (SQLException e){
            S2TRunntimeException exception = new S2TRunntimeException("Project "+projectid+" wurde nicht gefunden.");
        }
        return res;
    }


    public void stopTimeTracking (String name, String now_date) {
        try(Statement st = connect()) {
            st.execute("CALL StopTime('" + name + "','" + now_date + "');");
        } catch (SQLException e) {
            S2TRunntimeException exception = new S2TRunntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }
    }

    public void startTimeTracking (String name, String now_date, String projectid) {
        try(Statement st = connect()) {
            st.execute("CALL StartTime('" + name + "','" + now_date + "','" + projectid + "');");
            st.close();
        } catch (SQLException e) {
            S2TRunntimeException exception = new S2TRunntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }

    }

}
