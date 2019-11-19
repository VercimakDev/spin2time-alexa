import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.CurrentTimeIntentHandler;
import at.spin2time.handlers.StopTimeTrackingIntentHandler;
import at.spin2time.handlers.TimeManagmentClass;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestStopTimeTracking {

    @Test
    public void testIdeal() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getNow();
        //connection.insertQueryBuilder("insert into u_users values(6,'test','test','123test');");
        connection.insertQueryBuilder("insert into wt_worktable values(null,1,'2019-10-25 00:00:00',null,6,null);");
        connection.stopTimeTracking("test",timedata);
        List<String> data;
             data  = connection.selectQueryBuilder("SELECT wt_stop  from wt_worktable\n" +
                "inner join spin2timedb.u_users \n" +
                "ON wt_u_id = u_id\n" +
                "WHERE u_username = \"test\"\n" +
                "Order by wt_id DESC\n" +
                "Limit 1;");
        Assert.assertEquals(data.get(0), timedata );
    }
    @Test
    public void testSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        StopTimeTrackingIntentHandler handler = new StopTimeTrackingIntentHandler();
        String timedata = time.getNow();
        boolean result;
        result = false;
        if (connection.userExists("daniel")) {
            handler.stopTimetracking("daniel");
            result = true;
        }
        Assert.assertEquals(true, result);
    }
    @Test
    public void testNoSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        StopTimeTrackingIntentHandler handler = new StopTimeTrackingIntentHandler();
        String timedata = time.getNow();
        boolean result;
        result = false;
        if (connection.userExists("iiiiiiizzzzz")) {
            handler.stopTimetracking("iiiiiiizzzzz");
            result = true;
        }
        Assert.assertEquals(false, result);
    }
    @Test
    public void noOpenTimeEntry() {

    }
}
