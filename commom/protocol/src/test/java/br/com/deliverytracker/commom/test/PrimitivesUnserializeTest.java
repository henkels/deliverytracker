package br.com.deliverytracker.commom.test;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrimitivesUnserializeTest {
    
    @BeforeClass
    public static void beforeClass(){
        SerializeTestUtils.reset();
    }

    @After
    public void after(){
        SerializeTestUtils.nextCase();
    }

    private void doTest(String value) {
        SerializeTestUtils.doUnserializeTest(value);
    }

    /**
     * Empty Object
     */
    @Test
    public void test001() {
        doTest(SerializeTestUtils.CONST_001);
    }

    /**
     * Simple String
     */
    @Test
    public void test002() {
        doTest(SerializeTestUtils.CONST_002);
    }

    /**
     * String that contains "
     */
    @Test
    public void test003() {
        doTest(SerializeTestUtils.CONST_003);
    }

    /**
     * String that contains 2 "
     */
    @Test
    public void test004() {
        doTest(SerializeTestUtils.CONST_004);
    }

    @Test
    public void test005() {
        doTest(SerializeTestUtils.CONST_005);
    }

    @Test
    public void test006() {
        doTest(SerializeTestUtils.CONST_006);
    }

    @Test
    public void test007() {
        doTest(SerializeTestUtils.CONST_007);
    }

    @Test
    public void test008() {
        doTest(SerializeTestUtils.CONST_008);
    }

    @Test
    public void test009() {
        doTest(SerializeTestUtils.CONST_009);
    }

    @Test
    public void test010() {
        doTest(SerializeTestUtils.CONST_010);
    }

    @Test
    public void test011() {
        doTest(SerializeTestUtils.CONST_011);
    }

    @Test
    public void test012() {
        doTest(SerializeTestUtils.CONST_012);
    }

    @Test
    public void test013() {
        doTest(SerializeTestUtils.CONST_013);
    }

    @Test
    public void test014() {
        doTest(SerializeTestUtils.CONST_014);
    }

    @Test
    public void test015() {
        doTest(SerializeTestUtils.CONST_015);
    }

    @Test
    public void test016() {
        doTest(SerializeTestUtils.CONST_016);
    }

    @Test
    public void test017() {
        doTest(SerializeTestUtils.CONST_017);
    }

    @Test
    public void test018() {
        doTest(SerializeTestUtils.CONST_018);
    }

    @Test
    public void test019() {
        doTest(SerializeTestUtils.CONST_019);
    }

    @Test
    public void test020() {
        doTest(SerializeTestUtils.CONST_020);
    }

    @Test
    public void test021() {
        doTest(SerializeTestUtils.CONST_021);
    }

    @Test
    public void test022() {
        doTest(SerializeTestUtils.CONST_022);
    }

    @Test
    public void test023() {
        doTest(SerializeTestUtils.CONST_023);
    }

    @Test
    public void test024() {
        doTest(SerializeTestUtils.CONST_024);
    }

    @Test
    public void test025() {
        doTest(SerializeTestUtils.CONST_025);
    }

    @Test
    public void test026() {
        doTest(SerializeTestUtils.CONST_026);
    }

    @Test
    public void test027() {
        doTest(SerializeTestUtils.CONST_027);
    }

    @Test
    public void test028() {
        doTest(SerializeTestUtils.CONST_028);
    }

    @Test
    public void test029() {
        doTest(SerializeTestUtils.CONST_029);
    }

    @Test
    public void test030() {
        doTest(SerializeTestUtils.CONST_030);
    }

    @Test
    public void test031() {
        doTest(SerializeTestUtils.CONST_031);
    }

    @Test
    public void test032() {
        doTest(SerializeTestUtils.CONST_032);
    }

    @Test
    public void test033() {
        doTest(SerializeTestUtils.CONST_033);
    }

    @Test
    public void test034() {
        doTest(SerializeTestUtils.CONST_034);
    }

    @Test
    public void test035() {
        doTest(SerializeTestUtils.CONST_035);
    }

    @Test
    public void test036() {
        doTest(SerializeTestUtils.CONST_036);
    }

    @Test
    public void test037() {
        doTest(SerializeTestUtils.CONST_037);
    }

    @Test
    public void test038() {
        doTest(SerializeTestUtils.CONST_038);
    }

    @Test
    public void test039() {
        doTest(SerializeTestUtils.CONST_039);
    }

    @Test
    public void test040() {
        doTest(SerializeTestUtils.CONST_040);
    }

    @Test
    public void test041() {
        doTest(SerializeTestUtils.CONST_041);
    }

    @Test
    public void test042() {
        doTest(SerializeTestUtils.CONST_042);
    }

    @Test
    public void test043() {
        doTest(SerializeTestUtils.CONST_043);
    }

    @Test
    public void test044() {
        doTest(SerializeTestUtils.CONST_044);
    }

    @Test
    public void test045() {
        doTest(SerializeTestUtils.CONST_045);
    }

    @Test
    public void test046() {
        doTest(SerializeTestUtils.CONST_046);
    }

    @Test
    public void test047() {
        doTest(SerializeTestUtils.CONST_047);
    }

    @Test
    public void test048() {
        doTest(SerializeTestUtils.CONST_048);
    }

    @Test
    public void test049() {
        doTest(SerializeTestUtils.CONST_049);
    }

    @Test
    public void test050() {
        doTest(SerializeTestUtils.CONST_050);
    }

    @Test
    public void test051() {
        doTest(SerializeTestUtils.CONST_051);
    }

    @Test
    public void test052() {
        doTest(SerializeTestUtils.CONST_052);
    }

    @Test
    public void test053() {
        doTest(SerializeTestUtils.CONST_053);
    }

    @Test
    public void test054() {
        doTest(SerializeTestUtils.CONST_054);
    }

    @Test
    public void test055() {
        doTest(SerializeTestUtils.CONST_055);
    }

    @Test
    public void test056() {
        doTest(SerializeTestUtils.CONST_056);
    }

    @Test
    public void test057() {
        doTest(SerializeTestUtils.CONST_057);
    }

    @Test
    public void test058() {
        doTest(SerializeTestUtils.CONST_058);
    }

    @Test
    public void test059() {
        doTest(SerializeTestUtils.CONST_059);
    }

    @Test
    public void test060() {
        doTest(SerializeTestUtils.CONST_060);
    }

    @Test
    public void test061() {
        doTest(SerializeTestUtils.CONST_061);
    }

    @Test
    public void test062() {
        doTest(SerializeTestUtils.CONST_062);
    }

    @Test
    public void test063() {
        doTest(SerializeTestUtils.CONST_063);
    }

    @Test
    public void test064() {
        doTest(SerializeTestUtils.CONST_064);
    }

    @Test
    public void test065() {
        doTest(SerializeTestUtils.CONST_065);
    }

    @Test
    public void test066() {
        doTest(SerializeTestUtils.CONST_066);
    }

    @Test
    public void test067() {
        doTest(SerializeTestUtils.CONST_067);
    }

    @Test
    public void test068() {
        doTest(SerializeTestUtils.CONST_068);
    }

    @Test
    public void test069() {
        doTest(SerializeTestUtils.CONST_069);
    }

    @Test
    public void test070() {
        doTest(SerializeTestUtils.CONST_070);
    }

    @Test
    public void test071() {
        doTest(SerializeTestUtils.CONST_071);
    }

    @Test
    public void test072() {
        doTest(SerializeTestUtils.CONST_072);
    }

    @Test
    public void test073() {
        doTest(SerializeTestUtils.CONST_073);
    }

    @Test
    public void test074() {
        doTest(SerializeTestUtils.CONST_074);
    }

    @Test
    public void test075() {
        doTest(SerializeTestUtils.CONST_075);
    }

    @Test
    public void test076() {
        doTest(SerializeTestUtils.CONST_076);
    }

    @Test
    public void test077() {
        doTest(SerializeTestUtils.CONST_077);
    }

    @Test
    public void test078() {
        doTest(SerializeTestUtils.CONST_078);
    }

    @Test
    public void test079() {
        doTest(SerializeTestUtils.CONST_079);
    }

    @Test
    public void test080() {
        doTest(SerializeTestUtils.CONST_080);
    }

    @Test
    public void test081() {
        doTest(SerializeTestUtils.CONST_081);
    }

    @Test
    public void test082() {
        doTest(SerializeTestUtils.CONST_082);
    }

    @Test
    public void test083() {
        doTest(SerializeTestUtils.CONST_083);
    }

    @Test
    public void test084() {
        doTest(SerializeTestUtils.CONST_084);
    }

    @Test
    public void test085() {
        doTest(SerializeTestUtils.CONST_085);
    }

    @Test
    public void test086() {
        doTest(SerializeTestUtils.CONST_086);
    }

    @Test
    public void test087() {
        doTest(SerializeTestUtils.CONST_087);
    }

    @Test
    public void test088() {
        doTest(SerializeTestUtils.CONST_088);
    }

    @Test
    public void test089() {
        doTest(SerializeTestUtils.CONST_089);
    }

    @Test
    public void test090() {
        doTest(SerializeTestUtils.CONST_090);
    }

    @Test
    public void test091() {
        doTest(SerializeTestUtils.CONST_091);
    }

    @Test
    public void test092() {
        doTest(SerializeTestUtils.CONST_092);
    }

    @Test
    public void test093() {
        doTest(SerializeTestUtils.CONST_093);
    }

    @Test
    public void test094() {
        doTest(SerializeTestUtils.CONST_094);
    }

    @Test
    public void test095() {
        doTest(SerializeTestUtils.CONST_095);
    }

    @Test
    public void test096() {
        doTest(SerializeTestUtils.CONST_096);
    }

    @Test
    public void test097() {
        doTest(SerializeTestUtils.CONST_097);
    }

    @Test
    public void test098() {
        doTest(SerializeTestUtils.CONST_098);
    }

    @Test
    public void test099() {
        doTest(SerializeTestUtils.CONST_099);
    }

    @Test
    public void test100() {
        doTest(SerializeTestUtils.CONST_100);
    }

    @Test
    public void test101() {
        doTest(SerializeTestUtils.CONST_101);
    }

    @Test
    public void test102() {
        doTest(SerializeTestUtils.CONST_102);
    }

    @Test
    public void test103() {
        doTest(SerializeTestUtils.CONST_103);
    }

    @Test
    public void test104() {
        doTest(SerializeTestUtils.CONST_104);
    }

    @Test
    public void test105() {
        doTest(SerializeTestUtils.CONST_105);
    }

    @Test
    public void test106() {
        doTest(SerializeTestUtils.CONST_106);
    }

    @Test
    public void test107() {
        doTest(SerializeTestUtils.CONST_107);
    }

    @Test
    public void test108() {
        doTest(SerializeTestUtils.CONST_108);
    }

    @Test
    public void test109() {
        doTest(SerializeTestUtils.CONST_109);
    }

    @Test
    public void test110() {
        doTest(SerializeTestUtils.CONST_110);
    }

    @Test
    public void test111() {
        doTest(SerializeTestUtils.CONST_111);
    }

    @Test
    public void test112() {
        doTest(SerializeTestUtils.CONST_112);
    }

    @Test
    public void test113() {
        doTest(SerializeTestUtils.CONST_113);
    }
}
