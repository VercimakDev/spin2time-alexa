package at.spin2time.handlers;

import java.sql.*;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StartTimeTrackingIntentHandler implements IntentRequestHandler {

    private Connection con;

    public ResultSet connect(){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select p_name from p_projects");
            //con.close();
            return rs;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("StartTimeTrackingIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        Intent intent = intentRequest.getIntent();

        //String username = intent.getSlots().get("name").getValue();
        //String projectId = intent.getSlots().get("projectname").getValue();


        try{

            String response = testResponseBuilder();
            //Zu Testzwecken standard antwort
            //String speechText = "Slot username enthält: "+username+" und slot projectId enthält: "+ projectId + "  Projekt in der Datenbank: "+dbtest;
            String speechText = "Test Ausgabedaten";
            return input.getResponseBuilder()
                    //.withSpeech(speechText)
                    .withSpeech(response)
                    .withSimpleCard("Spin2Time", speechText)
                    .build();

        }
        catch (Exception e){
            //Zu Testzwecken
            //Exception sollte nicht ausgegeben werden
            return input.getResponseBuilder()
                    .withSpeech(e.toString())
                    .withSimpleCard("Spin2Time", e.getMessage())
                    .build();
        }

    }

    public String testResponseBuilder () throws SQLException {
        ResultSet rs = connect();
        String dbbuilder = "";
        if(rs != null) {
            while (rs.next()) {
                dbbuilder= dbbuilder+ " "+ rs.getString(1);
            }
        }

        String dbtest = dbbuilder.toString();

        con.close();
        return dbtest;
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