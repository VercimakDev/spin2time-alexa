package at.spin2time.handlers;

import java.sql.*;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class StartTimeTrackingIntentHandler implements IntentRequestHandler {

    private ResultSet connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://spin2timedb.cyadrtpulaz9.eu-west-1.rds.amazonaws.com:3306/spin2timedb","admin","spin2time");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select p_name from p_projects");

            con.close();
            return rs;
        }
        catch (Exception e){
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

        Slot usernameSlot = intent.getSlots().get("name");
        Slot projectIdSlot = intent.getSlots().get("projectname");

        String username = usernameSlot.getValue();
        String projectId = projectIdSlot.getValue();

        ResultSet rs = connect();

        try{
            String dbtest = null;
            if (rs != null) {
                dbtest = rs.getString(2);
            }

            String speechText = "Slot username enthält: "+username+" und slot projectId enthält: "+ projectId + "  1.Projekt in der Datenbank: "+dbtest;

            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("Spin2Time", speechText)
                    .build();

        }
        catch (Exception e){
            return input.getResponseBuilder()
                    .withSpeech(e.toString())
                    .withSimpleCard("Spin2Time", e.toString())
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
