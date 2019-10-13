package at.spin2time.handlers;

import java.sql.*;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StartTimeTrackingIntentHandler implements IntentRequestHandler {

    private Connection con;
    private String queryResult = "";

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select p_name from p_projects");
            while(rs.next())
                queryResult += (rs.getString(1));
            con.close();
        }
        catch (Exception e){
            System.out.println("NullPointer Exception ensteht bei der connect methode");
        }
    }

    //Method only is only a template for the future insert method
    //Method useless, only for test purposes
    /*private String getProjects(Connection con){
        StringBuilder res = null;
        try{
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select p_name from p_projects;");
            while(resultSet.next()){
                res.append(" " + resultSet.getString(1));
            }
            return res.toString();
        }
        catch (Exception e){
            return e.toString();
        }
    }*/

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("StartTimeTrackingIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        connect();

        Intent intent = intentRequest.getIntent();

        String username = intent.getSlots().get("name").getValue();
        String projectId = intent.getSlots().get("projectname").getValue();


        try{

            String dbtest = queryResult.toString();

            //Zu Testzwecken standard antwort
            String speechText = "Slot username enthält: "+username+" und slot projectId enthält: "+ projectId + "  Projekt in der Datenbank: "+dbtest;

            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("Spin2Time", speechText)
                    .build();

        }
        catch (Exception e){
            //Zu Testzwecken
            //Exception sollte nicht ausgegeben werden
            queryResult = null;
            return input.getResponseBuilder()
                    .withSpeech("Exception entsteht in handle Methode")
                    .withSimpleCard("Spin2Time", "Exception entsteht in handle Methode")
                    .build();
        }

    }

    /*@Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        String speechText = "StartTimeTrackingIntent erfolgreich aufgerufen!";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Spin2Time", speechText)
                .build();
    }*/
}
