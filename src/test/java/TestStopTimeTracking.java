import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.StopPersTimeTrackingIntentHandler;
import at.spin2time.handlers.TimeManagmentClass;
import org.junit.Assert;
import org.junit.Test;

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
        connection.stopTimeTracking("test",time.getNow());
    }
    @Test
    public void testSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        StopPersTimeTrackingIntentHandler handler = new StopPersTimeTrackingIntentHandler();
        String timedata = time.getNow();
        boolean result;
        result = false;
        if (connection.userExists("test")) {
            handler.stopTimetracking("test");
            result = true;
        }
        Assert.assertEquals(true, result);
        connection.stopTimeTracking("test",time.getNow());
    }
    @Test
    public void testNoSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        StopPersTimeTrackingIntentHandler handler = new StopPersTimeTrackingIntentHandler();
        String timedata = time.getNow();
        boolean result;
        result = false;
        if (connection.userExists("iiiiiiizzzzz")) {
            handler.stopTimetracking("iiiiiiizzzzz");
            result = true;
        }
        Assert.assertEquals(false, result);
        connection.stopTimeTracking("test",time.getNow());
    }
    @Test
    public void noOpenTimeEntry() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        connection.insertQueryBuilder("insert into u_users values(765,'noOpenTime','noOpenTime','123test',null);");
        Assert.assertEquals(false,connection.checkDoubleEntry("noOpenTime"));
    }
    @Test
    public void openTimeEntry() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        connection.insertQueryBuilder("insert into wt_worktable values(null,1,'2019-10-25 00:00:00',null,6,null);");
        Assert.assertEquals(true,connection.checkDoubleEntry("test"));
        connection.stopTimeTracking("test",time.getNow());
    }
}
