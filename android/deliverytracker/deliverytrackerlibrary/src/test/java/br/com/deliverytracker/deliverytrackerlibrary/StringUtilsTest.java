package br.com.deliverytracker.deliverytrackerlibrary;

import org.junit.Assert;
import org.junit.Test;

import br.com.deliverytracker.deliverytrackerlibrary.StringUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringUtilsTest {

    @Test
    public void testEmailToName01() throws Exception {
        Assert.assertEquals("Tarcisio Henkels", StringUtils.emailToName("tarcisio.henkels@gmail.com"));
    }

    @Test
    public void testEmailToName02() throws Exception {
        Assert.assertEquals("Tarcisio Henkels", StringUtils.emailToName("tarcisio_henkels@gmail.com"));
    }

    @Test
    public void testEmailToName03() throws Exception {
        Assert.assertEquals("Tarcisio Henkels", StringUtils.emailToName("tarcisio-henkels@gmail.com"));
    }

    @Test
    public void testEmailToName04() throws Exception {
        Assert.assertEquals("TARCISIOHENKELS", StringUtils.emailToName("TARCISIOHENKELS@gmail.com"));
    }

    @Test
    public void testEmailToName05() throws Exception {
        Assert.assertEquals("Tarcisio Henkels", StringUtils.emailToName("tarcisioHenkels@gmail.com"));
    }

    @Test
    public void testEmailToName06() throws Exception {
        Assert.assertEquals("Tarcisio Henkels", StringUtils.emailToName("tarcisio.Henkels@gmail.com"));
    }

    @Test
    public void testEmailToName07() throws Exception {
        Assert.assertEquals("Th", StringUtils.emailToName("th@gmail.com"));
    }

    @Test
    public void testEmailToName08() throws Exception {
        Assert.assertEquals("TH", StringUtils.emailToName("TH@gmail.com"));
    }

    @Test
    public void testEmailToName09() throws Exception {
        Assert.assertEquals("T H", StringUtils.emailToName("T.H@gmail.com"));
    }

    @Test
    public void testEmailToName10() throws Exception {
        Assert.assertEquals("T H", StringUtils.emailToName("t.h@gmail.com"));
    }
}