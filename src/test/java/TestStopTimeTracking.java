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

/*public class TestStopTimeTracking {

    @Test
    public void TestIdeal() {
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
<<<<<<< HEAD
    @Test
    public void TestNoSuchUser() {
        ConnectionClass connection = new ConnectionClass();
        TimeManagmentClass time = new TimeManagmentClass();
        String timedata = time.getNow();
        connection.stopTimeTracking("name",timedata);
        
    }
}
=======
}*/
>>>>>>> 97c4f053c1ef3b77d2ca4f3449c9f8e18d115cea
