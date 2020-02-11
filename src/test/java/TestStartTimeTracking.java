import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.StartPersTimeTrackingIntentHandler;
import at.spin2time.handlers.StopPersTimeTrackingIntentHandler;
import at.spin2time.handlers.TimeManagmentClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestStartTimeTracking {

    @Test
    public void testIdeal (){
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getNow();
        connection.startTimeTracking("test", timedata,"1");
        List<String> data;
        data  = connection.selectQueryBuilder("SELECT wt_start  from wt_worktable\n" +
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
        String timedata = time.getNow();
        boolean result = false;
        if (connection.userExists("test")) {
            result = true;
        }
        Assert.assertEquals(true,result);
        connection.stopTimeTracking("test",time.getNow());
    }

    @Test
    public void testNoSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        boolean result = false;
        if (connection.userExists("izzz")) {
            result = true;
        }
        Assert.assertEquals(false,result);
    }

    @Test
    public void testDoubleEntry() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getNow();
        connection.startTimeTracking("test", timedata, "1");
        boolean result = false;
        if(connection.checkDoubleEntry("test") == true) {
            result = true;
        }
        Assert.assertEquals(true,result);
        connection.stopTimeTracking("test",time.getNow());
    }

    @Test
    public void testIsTeammember() {
        ConnectionClass connection = new ConnectionClass();
        boolean result = false;
        if(connection.isProjectMember("test","1")) {
            result = true;
        }
        Assert.assertEquals(true,result);
    }
    @Test
    public void testIsNotTeammember() {
        ConnectionClass connection = new ConnectionClass();
        boolean result = false;
        if(!connection.isProjectMember("test","6")) {
            result = true;
        }
        Assert.assertEquals(true,result);
    }
}

