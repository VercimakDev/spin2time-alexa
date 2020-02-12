import at.spin2time.handlers.ConnectionClass;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Statement;

public class TestRemovePersonalization {
    @Test
    public void testIdeal() throws Exception {
        ConnectionClass connection = new ConnectionClass();
        Statement st = connection.connect();
        st.execute("update u_users set u_voiceid = 'amzn1.ask.person.AG4D7KLTY2ZG5BUI3O53ZY5PWXG53RB43A6ILWHI5Y2DCGI33YU4IOHKCLHDXRJW7JFO2GSAXKF5W2JFJE4YHHS73TN77HWZANDKFFAP' where u_username = 'konsti';");
        Assert.assertEquals(true,connection.removeVoiceProfile("konsti"));
        st.execute("update u_users set u_voiceid = 'amzn1.ask.person.AG4D7KLTY2ZG5BUI3O53ZY5PWXG53RB43A6ILWHI5Y2DCGI33YU4IOHKCLHDXRJW7JFO2GSAXKF5W2JFJE4YHHS73TN77HWZANDKFFAP' where u_username = 'konsti';");
    }
}
