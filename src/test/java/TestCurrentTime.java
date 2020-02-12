import at.spin2time.handlers.ConnectionClass;
import at.spin2time.handlers.TimeManagmentClass;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class TestCurrentTime {

    @Test
    public void testIdeal() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getHourAgo();
        connection.stopTimeTracking("test",time.getNow());
        connection.startTimeTracking("test",timedata,"1");
        Assert.assertEquals("[Sie haben 1 Stunden und 0 Minuten gearbeitet]", connection.getTimeWorkedTillNow("test"));
        connection.stopTimeTracking("test",time.getNow());
    }
    @Test
    public void testNoUser() {
        ConnectionClass connection = new ConnectionClass();
        connection.getTimeWorkedTillNow("random123");
        Assert.assertEquals("[null]",connection.getTimeWorkedTillNow("random123"));
    }
    @Test
    public void testNoRunningEntry() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getNow();
        connection.insertQueryBuilder("INSERT into u_users Value(456,\"test2\",\"test2\",\"123\",null);");
        connection.stopTimeTracking("test",timedata);
        Assert.assertEquals("[null]",connection.getTimeWorkedTillNow(""));
        connection.insertQueryBuilder("Delete from u_users where u_id = 456;");
    }
}
