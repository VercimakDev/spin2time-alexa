package at.spin2time.handlers;

import at.spin2time.exceptions.S2TRuntimeException;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for Database-interaction methods
 * + simple check methods
 */
@Log4j2
public class ConnectionClass {

    /**
     * Connect method to initiate the connection between the Alexa skill application and the s2t database
     * @return statement
     */
    public Statement connect(){
        Connection con;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            return stmt;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Method to execute a SQL select statement
     * @param query Select query, which should be executed
     * @return List of results
     */
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
            S2TRuntimeException exception = new S2TRuntimeException("Bei dem Selectstatement" +
                    " ist ein Fehler aufgetreten");
        }
        return sqllist;
    }

    /**
     * Method to execute SQL insert queries
     * @param query SQL query
     * @return true/false
     */
    public boolean insertQueryBuilder (String query) {
        boolean rs = false;
        try (Statement st = connect()) {

            rs = st.execute(query);
            st.close();

        } catch (SQLException e) {
            S2TRuntimeException exception = new S2TRuntimeException("Bei dem Insertstatement" +
                    " ist ein Fehler aufgetreten");
        }
        return rs;
    }

    /**
     * Method to check if a user exists in the database
     * @param username that should be checked
     * @return true/false
     */
    public boolean userExists(String username){
        List list = selectQueryBuilder("SELECT u_id from u_users where u_username = '"+username+"';");
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Method to check if a user is a member of a specific project
     * @param username the users name
     * @param projectId four-digit number of project
     * @return true/false
     */
    public boolean isProjectMember(String username, String projectId){

        String userid = selectQueryBuilder("select u_id from u_users where u_username = '"+username+"'").get(0).toString();
        List list = selectQueryBuilder("SELECT * from pm_projectmembers where pm_u_id = "+userid+" and pm_p_id = "+projectId);
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Method to create the relation between the username and the specific voiceid
     * @param username of the user in the s2t database
     * @param voiceId of the personalized request, is a attribute which is recognized by Alexa if the user has created a voice profile
     * @return true/false if successful or not
     */
    public boolean saveUserRelation(String username, String voiceId){
        boolean rs = false;
        try (Statement st = connect()) {
            rs = st.execute("update u_users set u_voiceid='"+voiceId+"' where u_username='"+username+"';");
            st.close();
            return rs;
        } catch (SQLException e) {
            log.error("An exception occured in saveUserRelation method");
            S2TRuntimeException exception = new S2TRuntimeException("Bei dem Updatestatement" +
                    " ist ein Fehler aufgetreten");
        }
        return rs;
    }

    /**
     * Method to get the username of a personalized request
     * @param personId The Ammazon.PersonID of the request sent to Alexa
     * @return username related to the personalized request
     */
    public String getUserFromVoiceId(String personId){
        if(personId != null) {
            List list = selectQueryBuilder("select u_username from u_users where u_voiceid = '" + personId + "'");
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0).toString();
            }
        }
        else{
            return null;
        }
    }

    /**
     * Method to check if a project exists in the database
     * @param projectid four-digit project number
     * @return true/false
     */
    public boolean projectExists(String projectid){

        List list = selectQueryBuilder("SELECT p_name from p_projects where p_id = "+projectid);
        return !list.isEmpty();
    }

    /**
     * Method to remove Voice-Profile from a users S2T-Account
     * @param username from the voice-id
     * @return true - if successful, false - if not successful
     */
    public boolean removeVoiceProfile(String username){
        if(username != null) {
            if (userExists(username)) {
                if (hasPersonId(username)) {
                    boolean rs = false;
                    try (Statement st = connect()) {
                        st.execute("update u_users set u_voiceid = null where u_username='" + username + "';");
                        rs = st.execute("Select exists (Select * from u_users where u_username = \"konsti\" and u_voiceid IS NULL);");
                        st.close();
                        return rs;
                    } catch (SQLException e) {
                        log.error("An exception occured in removeVoiceProfile method");
                        S2TRuntimeException exception = new S2TRuntimeException("Bei dem Updatestatement" +
                                " ist ein Fehler aufgetreten");
                    }
                    return rs;
                }
            }
        }
        else{
            return false;
        }
        return false;
    }

    /**
     * This method returns true or false based on the if the user has already started an unfinished time-entry
     * true means the user already has a time-entry, false means he doesnt
     * @param username
     * @return true, false
     */
    public boolean checkDoubleEntry(String username){
        String userid = selectQueryBuilder("select u_id from u_users where u_username = '"+username+"'").get(0).toString();

        List list = selectQueryBuilder("SELECT wt_id from wt_worktable where wt_u_id = "+userid+" and wt_stop is null");

        if(list.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * Method to check if a specific user already has an personid stored in his account
     * @param username of a specific user
     * @return true/false
     */
    public boolean hasPersonId(String username){
        return selectQueryBuilder("select u_voiceid from u_users where u_username = '" + username + "'").get(0) != null;
    }

    /**
     * Method for StopTimeTrackingIntentHandler that executes the StopTime procedure in the database
     * @param name username of the user who stops time-tracking
     * @param now_date the present date
     */
    public void stopTimeTracking (String name, String now_date) {
        try(Statement st = connect()) {
            st.execute("CALL StopTime('" + name + "','" + now_date + "');");
        } catch (SQLException e) {
            S2TRuntimeException exception = new S2TRuntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }
    }

    /**
     * Method for StartTimeTrackingIntentHandler that executes the StartTime procedure in the database
     * @param name username of user who starts his time-tracking
     * @param now_date present date
     * @param projectid 4-digit number of project on which the user starts working
     */
    public void startTimeTracking (String name, String now_date, String projectid) {
        try(Statement st = connect()) {
            st.execute("CALL StartTime('" + name + "','" + now_date + "','" + projectid + "');");
            st.close();
        } catch (SQLException e) {
            S2TRuntimeException exception = new S2TRuntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }

    }

    // never used
    public String getStartTimeEntry(String name){
        String id = selectQueryBuilder("SELECT u_id from u_users where u_username = '"+name+"';").get(0).toString();
        try(Statement st = connect()) {

            st.close();
        } catch (SQLException e) {
            S2TRuntimeException exception = new S2TRuntimeException("Bei der Datenbankabfrage" +
                    " ist ein Fehler aufgetreten");
        }
        return id;
    }

    /**
     * Method to get the amount of time a user worked until now
     * @param name username
     * @return time
     */
    public String getTimeWorkedTillNow(String name) {
        String text = selectQueryBuilder("SELECT hours_and_minutes_worked('"+ name +"');").toString();
        return text;
    }

    /**
     * Method to get the amount of time a user worked in the current month
     * @param name username
     * @param projectid 4-digit project number
     * @return time
     */
    public String getTimeWorkedThisMonth(String name, String projectid) {
        String text = selectQueryBuilder("Select month_hours_worked('"+name+"','"+projectid+"');").toString();
        return text;
    }

    public List getUserProjects(String name) {
        String userid = selectQueryBuilder("select u_id from u_users where u_username = '"+name+"'").get(0).toString();
        List projects = selectQueryBuilder("SELECT p_id FROM p_projects JOIN pm_projectmembers ON p_id = pm_p_id WHERE pm_u_id ="+userid);
        return projects;
    }
}
