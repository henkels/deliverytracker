package protocol;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.deliverytracker.commom.XMPPMessage;

public class XMPPMessageTest {

    private void doTest(String expected, XMPPMessage message) {
        String actual = message.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void test01() {
        doTest("{}", new XMPPMessage());
    }

}
