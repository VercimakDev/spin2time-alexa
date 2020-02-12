import at.spin2time.handlers.ConnectionClass;
import org.junit.Assert;
import org.junit.Test;

public class TestListProjects {
    @Test
    public void testIdeal() {
        ConnectionClass connection = new ConnectionClass();
        Assert.assertEquals("[1, 6363, 8410]",connection.getUserProjects("test").toString());
    }
}
