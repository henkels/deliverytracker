package br.com.deliverytracker.commom.test;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectsUnserializeTest {
    
    @BeforeClass
    public static void beforeClass(){
        SerializeTestUtils.reset();
    }

    @After
    public void after(){
        SerializeTestUtils.nextCase();
    }

    private void doTest(String expected) {
        SerializeTestUtils.doUnserializeTest(expected);
    }

    //////////////////////////////////////////// Object ///////////////////////////////////////

    @Test
    public void test001() {
        doTest(SerializeTestUtils.OBJECT_01);
    }

    @Test
    public void test002() {
        doTest(SerializeTestUtils.OBJECT_02);
    }

    @Test
    public void test003() {
        doTest(SerializeTestUtils.OBJECT_03);
    }

    @Test
    public void test004() {
        doTest(SerializeTestUtils.OBJECT_04);
    }

    @Test
    public void test005() {
        doTest(SerializeTestUtils.OBJECT_05);
    }

    @Test
    public void test006() {
        doTest(SerializeTestUtils.OBJECT_06);
    }

    @Test
    public void test007() {
        doTest(SerializeTestUtils.OBJECT_07);
    }

    @Test
    public void test008() {
        doTest(SerializeTestUtils.OBJECT_08);
    }

    @Test
    public void test009() {
        doTest(SerializeTestUtils.OBJECT_09);
    }

    @Test
    public void test010() {
        doTest(SerializeTestUtils.OBJECT_10);
    }

    @Test
    public void test011() {
        doTest(SerializeTestUtils.OBJECT_11);
    }

    @Test
    public void test012() {
        doTest(SerializeTestUtils.OBJECT_12);
    }

    @Test
    public void test013() {
        doTest(SerializeTestUtils.OBJECT_13);
    }

    @Test
    public void test014() {
        doTest(SerializeTestUtils.OBJECT_14);
    }

    @Test
    public void test015() {
        doTest(SerializeTestUtils.OBJECT_15);
    }

    @Test
    public void test016() {
        doTest(SerializeTestUtils.OBJECT_16);
    }

    @Test
    public void test017() {
        doTest(SerializeTestUtils.OBJECT_17);
    }

    @Test
    public void test018() {
        doTest(SerializeTestUtils.OBJECT_18);
    }

    @Test
    public void test019() {
        doTest(SerializeTestUtils.OBJECT_19);
    }

    @Test
    public void test020() {
        doTest(SerializeTestUtils.OBJECT_20);
    }

    @Test
    public void test021() {
        doTest(SerializeTestUtils.OBJECT_21);
    }

    @Test
    public void test022() {
        doTest(SerializeTestUtils.OBJECT_22);
    }

    @Test
    public void test023() {
        doTest(SerializeTestUtils.OBJECT_23);
    }

    @Test
    public void test024() {
        doTest(SerializeTestUtils.OBJECT_24);
    }

    @Test
    public void test025() {
        doTest(SerializeTestUtils.OBJECT_25);
    }

    @Test
    public void test026() {
        doTest(SerializeTestUtils.OBJECT_26);
    }

    @Test
    public void test027() {
        doTest(SerializeTestUtils.OBJECT_27);
    }

    @Test
    public void test028() {
        doTest(SerializeTestUtils.OBJECT_28);
    }

    @Test
    public void test029() {
        doTest(SerializeTestUtils.OBJECT_29);
    }

    @Test
    public void test030() {
        doTest(SerializeTestUtils.OBJECT_30);
    }

    @Test
    public void test031() {
        doTest(SerializeTestUtils.OBJECT_31);
    }

    @Test
    public void test032() {
        doTest(SerializeTestUtils.OBJECT_32);
    }

    @Test
    public void test033() {
        doTest(SerializeTestUtils.OBJECT_33);
    }

    @Test
    public void test034() {
        doTest(SerializeTestUtils.OBJECT_34);
    }

    @Test
    public void test035() {
        doTest(SerializeTestUtils.OBJECT_35);
    }

    @Test
    public void test037() {
        doTest(SerializeTestUtils.OBJECT_36);
    }

    @Test
    public void test038() {
        doTest(SerializeTestUtils.OBJECT_38);
    }

    @Test
    public void test039() {
        doTest(SerializeTestUtils.OBJECT_39);
    }

    @Test
    public void test040() {
        doTest(SerializeTestUtils.OBJECT_40);
    }

    @Test
    public void test041() {
        doTest(SerializeTestUtils.OBJECT_41);
    }

    @Test
    public void test042() {
        doTest(SerializeTestUtils.OBJECT_42);
    }

    @Test
    public void test043() {
        doTest(SerializeTestUtils.OBJECT_43);
    }

    @Test
    public void test044() {
        doTest(SerializeTestUtils.OBJECT_44);
    }

    @Test
    public void test045() {
        doTest(SerializeTestUtils.OBJECT_45A);
        doTest(SerializeTestUtils.OBJECT_45B);
    }

    @Test
    public void test046() {
        doTest(SerializeTestUtils.OBJECT_46);
    }

    @Test
    public void test047() {
        doTest(SerializeTestUtils.OBJECT_47);
    }

    @Test
    public void test048() {
        doTest(SerializeTestUtils.OBJECT_48);
    }

    @Test
    public void test049() {
        doTest(SerializeTestUtils.OBJECT_49);
    }

    @Test
    public void test050() {
        doTest(SerializeTestUtils.OBJECT_50);
    }

    @Test
    public void test051() {
        doTest(SerializeTestUtils.OBJECT_51);
    }

    @Test
    public void test052() {
        doTest(SerializeTestUtils.OBJECT_52);
    }

    @Test
    public void test053() {
        doTest(SerializeTestUtils.OBJECT_53);
    }

    @Test
    public void test054() {
        doTest(SerializeTestUtils.OBJECT_54);
    }

}
