package br.com.deliverytracker.commom.test;

import org.junit.Test;

public class ObjectsSerializeTest {

    private void doTest(String expected, Object object) {
        SerializeTestUtils.doSerializeTest(expected, object);
    }

    //////////////////////////////////////////// Object ///////////////////////////////////////

    @Test
    public void test001() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 11;
        doTest(SerializeTestUtils.OBJECT_01, object);
    }

    @Test
    public void test002() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 12;
        object.object = new PojoSimpleInner();
        doTest(SerializeTestUtils.OBJECT_02, object);
    }

    @Test
    public void test003() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 13;
        object.object = new PojoSimpleInner();
        object.object.int1 = 14;
        doTest(SerializeTestUtils.OBJECT_03, object);
    }

    @Test
    public void test004() {
        PojoHierarquical object = new PojoHierarquical();
        object.int1 = 13;
        object.object = new PojoHierarquical();
        object.object.int1 = 14;
        object.object.object = new PojoHierarquical();
        object.object.object.int1 = 15;
        object.object.object.object = new PojoHierarquical();
        object.object.object.object.int1 = 16;
        doTest(SerializeTestUtils.OBJECT_04, object);
    }

    @Test
    public void test005() {
        PojoCrossRef object = new PojoCrossRef();
        object.int1 = 13;
        object.object1 = new PojoSimpleInner();
        object.object1.int1 = 14;
        object.object2 = object.object1;
        doTest(SerializeTestUtils.OBJECT_05, object);
    }

    @Test
    public void test006() {
        PojoHierarquical object = new PojoHierarquical();
        object.int1 = 13;
        object.object = new PojoHierarquical();
        object.object.int1 = 14;
        object.object.object = new PojoHierarquical();
        object.object.object.int1 = 15;
        //referencia circular
        object.object.object.object = object;
        doTest(SerializeTestUtils.OBJECT_06, object);
    }

    @Test
    public void test007() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_07, object);
    }

    @Test
    public void test008() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = new byte[2];
        doTest(SerializeTestUtils.OBJECT_08, object);
    }

    @Test
    public void test009() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = new byte[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_09, object);
    }

    @Test
    public void test010() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_10, object);
    }

    @Test
    public void test011() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = new Byte[2];
        doTest(SerializeTestUtils.OBJECT_11, object);
    }

    @Test
    public void test012() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = new Byte[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_12, object);
    }

    @Test
    public void test013() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_13, object);
    }

    @Test
    public void test014() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = new short[2];
        doTest(SerializeTestUtils.OBJECT_14, object);
    }

    @Test
    public void test015() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = new short[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_15, object);
    }

    @Test
    public void test016() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_16, object);
    }

    @Test
    public void test017() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = new Short[2];
        doTest(SerializeTestUtils.OBJECT_17, object);
    }

    @Test
    public void test018() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = new Short[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_18, object);
    }

    @Test
    public void test019() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_19, object);
    }

    @Test
    public void test020() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = new int[2];
        doTest(SerializeTestUtils.OBJECT_20, object);
    }

    @Test
    public void test021() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = new int[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_21, object);
    }

    @Test
    public void test022() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_22, object);
    }

    @Test
    public void test023() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = new Integer[2];
        doTest(SerializeTestUtils.OBJECT_23, object);
    }

    @Test
    public void test024() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = new Integer[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_24, object);
    }

    @Test
    public void test025() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_25, object);
    }

    @Test
    public void test026() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = new long[2];
        doTest(SerializeTestUtils.OBJECT_26, object);
    }

    @Test
    public void test027() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = new long[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(SerializeTestUtils.OBJECT_27, object);
    }

    @Test
    public void test028() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_28, object);
    }

    @Test
    public void test029() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = new Long[2];
        doTest(SerializeTestUtils.OBJECT_29, object);
    }

    @Test
    public void test030() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = new Long[2];
        object.arrayData[0] = 1l;
        object.arrayData[1] = -1l;
        doTest(SerializeTestUtils.OBJECT_30, object);
    }

    @Test
    public void test031() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_31, object);
    }

    @Test
    public void test032() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = new float[2];
        doTest(SerializeTestUtils.OBJECT_32, object);
    }

    @Test
    public void test033() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = new float[2];
        object.arrayData[0] = Float.MAX_VALUE;
        object.arrayData[1] = Float.MIN_VALUE * -1f;
        doTest(SerializeTestUtils.OBJECT_33, object);
    }

    @Test
    public void test034() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_34, object);
    }

    @Test
    public void test035() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = new Float[2];
        doTest(SerializeTestUtils.OBJECT_35, object);
    }

    @Test
    public void test037() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = new Float[2];
        object.arrayData[0] = Float.MAX_VALUE;
        object.arrayData[1] = Float.MIN_VALUE * -1f;
        doTest(SerializeTestUtils.OBJECT_36, object);
    }

    @Test
    public void test038() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_38, object);
    }

    @Test
    public void test039() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = new double[2];
        doTest(SerializeTestUtils.OBJECT_39, object);
    }

    @Test
    public void test040() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = new double[2];
        object.arrayData[0] = Double.MAX_VALUE;
        object.arrayData[1] = Double.MIN_VALUE * -1d;
        doTest(SerializeTestUtils.OBJECT_40, object);
    }

    @Test
    public void test041() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(SerializeTestUtils.OBJECT_41, object);
    }

    @Test
    public void test042() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = new Double[2];
        doTest(SerializeTestUtils.OBJECT_42, object);
    }

    @Test
    public void test043() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = new Double[2];
        object.arrayData[0] = Double.MAX_VALUE;
        object.arrayData[1] = Double.MIN_VALUE * -1d;
        doTest(SerializeTestUtils.OBJECT_43, object);
    }

    @Test
    public void test044() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        doTest(SerializeTestUtils.OBJECT_44, object);
    }

    @Test
    public void test045() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        object.arrayData = new String[] { "," };
        doTest(SerializeTestUtils.OBJECT_45, object);
    }

    @Test
    public void test046() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        object.arrayData = new String[] { "A", null, "A A", ",B,b,N,", ",", "\\,a", "A\"A", "A\rA" };
        doTest(SerializeTestUtils.OBJECT_46, object);
    }

    @Test
    public void test047() {
        PojoObjectArray object = new PojoObjectArray();
        object.int1 = 13;
        doTest(SerializeTestUtils.OBJECT_47, object);
    }

    @Test
    public void test048() {
        PojoObjectArray object = new PojoObjectArray();
        object.int1 = 13;
        // array do tipo do campo
        object.arrayData = new Object[] { new PojoSimpleInner() };
        ((PojoSimpleInner) object.arrayData[0]).int1 = 11;
        doTest(SerializeTestUtils.OBJECT_48, object);
    }

    @Test
    public void test049() {
        PojoObjectArray object = new PojoObjectArray();
        object.int1 = 13;
        // array do tipo do campo
        object.arrayData = new PojoSimpleInner[] { new PojoSimpleInner() };
        ((PojoSimpleInner) object.arrayData[0]).int1 = 11;
        doTest(SerializeTestUtils.OBJECT_49, object);
    }

    @Test
    public void test050() {
        PojoObjectArray2 object = new PojoObjectArray2();
        object.int1 = 13;
        object.arrayData = new PojoSimpleInner[] { new PojoSimpleInner() };
        object.arrayData[0].int1 = 11;
        doTest(SerializeTestUtils.OBJECT_50, object);
    }

    @Test
    public void test051() {
        PojoObjectArray2 object = new PojoObjectArray2();
        object.int1 = 13;
        object.arrayData = new PojoSimpleInner[] { null };
        doTest(SerializeTestUtils.OBJECT_51, object);
    }

    @Test
    public void test052() {
        PojoSimpleOuter2 object = new PojoSimpleOuter2();
        object.int1 = 13;
        object.object = new PojoSimpleInner[] { null };
        doTest(SerializeTestUtils.OBJECT_52, object);
    }

    @Test
    public void test053() {
        PojoSimpleOuter2 object = new PojoSimpleOuter2();
        object.int1 = 13;
        object.object = new PojoSimpleInner();
        doTest(SerializeTestUtils.OBJECT_53, object);
    }

    @Test
    public void test054() {
        PojoSimpleOuter2 object = new PojoSimpleOuter2();
        object.int1 = 13;
        object.object = Integer.valueOf(1);
        doTest(SerializeTestUtils.OBJECT_54, object);
    }

}
