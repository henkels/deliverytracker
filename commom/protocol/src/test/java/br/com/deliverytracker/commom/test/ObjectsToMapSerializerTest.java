package br.com.deliverytracker.commom.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.deliverytracker.commom.ToMapSerializer;

public class ObjectsToMapSerializerTest {

    private void doTest(String expected, Object object) {
        String actual = ToMapSerializer.toJson(object);
        assertEquals(expected, actual);
    }

    //////////////////////////////////////////// Object ///////////////////////////////////////

    //    private static final String EMPTY_OBJECT = "{}";

    private static final String OBJECT_01 = "{\r\"int1\":\"11\"\r}";

    @Test
    public void test001() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 11;
        doTest(OBJECT_01, object);
    }

    private static final String OBJECT_02 = "{\r\"int1\":\"12\",\r\"object\":\"REF_1\"\r}";

    @Test
    public void test002() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 12;
        object.object = new PojoSimpleInner();
        doTest(OBJECT_02, object);
    }

    private static final String OBJECT_03 = "{\r\"int1\":\"13\",\r\"object\":\"REF_1\",\r\"REF_1.int1\":\"14\"\r}";

    @Test
    public void test003() {
        PojoSimpleOuter object = new PojoSimpleOuter();
        object.int1 = 13;
        object.object = new PojoSimpleInner();
        object.object.int1 = 14;
        doTest(OBJECT_03, object);
    }

    private static final String OBJECT_04 = "{\r" + //
                                            "\"int1\":\"13\",\r\"object\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r\"REF_1.object\":\"REF_2\",\r" + //
                                            "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"REF_3\",\r" + //
                                            "\"REF_3.int1\":\"16\"\r" + //
                                            "}";

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
        doTest(OBJECT_04, object);
    }

    private static final String OBJECT_05 = "{\r" + //
                                            "\"int1\":\"13\",\r" + "\"object1\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r" + //
                                            "\"object2\":\"REF_1\"\r" + //
                                            "}";

    @Test
    public void test005() {
        PojoCrossRef object = new PojoCrossRef();
        object.int1 = 13;
        object.object1 = new PojoSimpleInner();
        object.object1.int1 = 14;
        object.object2 = object.object1;
        doTest(OBJECT_05, object);
    }

    private static final String OBJECT_06 = "{\r" + //
                                            "\"int1\":\"13\",\r\"object\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r\"REF_1.object\":\"REF_2\",\r" + //
                                            "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"\"\r" + //
                                            "}";

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
        doTest(OBJECT_06, object);
    }

    private static final String OBJECT_07 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test007() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_07, object);
    }

    private static final String OBJECT_08 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test008() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = new byte[2];
        doTest(OBJECT_08, object);
    }

    private static final String OBJECT_09 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test009() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.arrayData = new byte[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_09, object);
    }

    private static final String OBJECT_10 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test010() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_10, object);
    }

    private static final String OBJECT_11 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test011() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = new Byte[2];
        doTest(OBJECT_11, object);
    }

    private static final String OBJECT_12 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test012() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.arrayData = new Byte[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_12, object);
    }

    private static final String OBJECT_13 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test013() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_13, object);
    }

    private static final String OBJECT_14 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test014() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = new short[2];
        doTest(OBJECT_14, object);
    }

    private static final String OBJECT_15 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test015() {
        PojoPrimitiveShortArray object = new PojoPrimitiveShortArray();
        object.int1 = 13;
        object.arrayData = new short[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_15, object);
    }

    private static final String OBJECT_16 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test016() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_16, object);
    }

    private static final String OBJECT_17 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test017() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = new Short[2];
        doTest(OBJECT_17, object);
    }

    private static final String OBJECT_18 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test018() {
        PojoShortArray object = new PojoShortArray();
        object.int1 = 13;
        object.arrayData = new Short[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_18, object);
    }

    private static final String OBJECT_19 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test019() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_19, object);
    }

    private static final String OBJECT_20 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test020() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = new int[2];
        doTest(OBJECT_20, object);
    }

    private static final String OBJECT_21 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test021() {
        PojoPrimitiveIntArray object = new PojoPrimitiveIntArray();
        object.int1 = 13;
        object.arrayData = new int[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_21, object);
    }

    private static final String OBJECT_22 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test022() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_22, object);
    }

    private static final String OBJECT_23 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test023() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = new Integer[2];
        doTest(OBJECT_23, object);
    }

    private static final String OBJECT_24 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test024() {
        PojoIntegerArray object = new PojoIntegerArray();
        object.int1 = 13;
        object.arrayData = new Integer[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_24, object);
    }

    private static final String OBJECT_25 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test025() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_25, object);
    }

    private static final String OBJECT_26 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test026() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = new long[2];
        doTest(OBJECT_26, object);
    }

    private static final String OBJECT_27 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test027() {
        PojoPrimitiveLongArray object = new PojoPrimitiveLongArray();
        object.int1 = 13;
        object.arrayData = new long[2];
        object.arrayData[0] = 1;
        object.arrayData[1] = -1;
        doTest(OBJECT_27, object);
    }

    private static final String OBJECT_28 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test028() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_28, object);
    }

    private static final String OBJECT_29 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test029() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = new Long[2];
        doTest(OBJECT_29, object);
    }

    private static final String OBJECT_30 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1,-1\"\r" + //
                                            "}";

    @Test
    public void test030() {
        PojoLongArray object = new PojoLongArray();
        object.int1 = 13;
        object.arrayData = new Long[2];
        object.arrayData[0] = 1l;
        object.arrayData[1] = -1l;
        doTest(OBJECT_30, object);
    }

    private static final String OBJECT_31 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test031() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_31, object);
    }

    private static final String OBJECT_32 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test032() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = new float[2];
        doTest(OBJECT_32, object);
    }

    private static final String OBJECT_33 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"3.4028235E38,-1.4E-45\"\r" + //
                                            "}";

    @Test
    public void test033() {
        PojoPrimitiveFloatArray object = new PojoPrimitiveFloatArray();
        object.int1 = 13;
        object.arrayData = new float[2];
        object.arrayData[0] = Float.MAX_VALUE;
        object.arrayData[1] = Float.MIN_VALUE * -1f;
        doTest(OBJECT_33, object);
    }

    private static final String OBJECT_34 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test034() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_34, object);
    }

    private static final String OBJECT_35 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test035() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = new Float[2];
        doTest(OBJECT_35, object);
    }

    private static final String OBJECT_36 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"3.4028235E38,-1.4E-45\"\r" + //
                                            "}";

    @Test
    public void test037() {
        PojoFloatArray object = new PojoFloatArray();
        object.int1 = 13;
        object.arrayData = new Float[2];
        object.arrayData[0] = Float.MAX_VALUE;
        object.arrayData[1] = Float.MIN_VALUE * -1f;
        doTest(OBJECT_36, object);
    }

    private static final String OBJECT_38 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test038() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_38, object);
    }

    private static final String OBJECT_39 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"0,0\"\r" + //
                                            "}";

    @Test
    public void test039() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = new double[2];
        doTest(OBJECT_39, object);
    }

    private static final String OBJECT_40 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1.7976931348623157E308,-4.9E-324\"\r" + //
                                            "}";

    @Test
    public void test040() {
        PojoPrimitiveDoubleArray object = new PojoPrimitiveDoubleArray();
        object.int1 = 13;
        object.arrayData = new double[2];
        object.arrayData[0] = Double.MAX_VALUE;
        object.arrayData[1] = Double.MIN_VALUE * -1d;
        doTest(OBJECT_40, object);
    }

    private static final String OBJECT_41 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test041() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = null;
        doTest(OBJECT_41, object);
    }

    private static final String OBJECT_42 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"N,N\"\r" + //
                                            "}";

    @Test
    public void test042() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = new Double[2];
        doTest(OBJECT_42, object);
    }

    private static final String OBJECT_43 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"1.7976931348623157E308,-4.9E-324\"\r" + //
                                            "}";

    @Test
    public void test043() {
        PojoDoubleArray object = new PojoDoubleArray();
        object.int1 = 13;
        object.arrayData = new Double[2];
        object.arrayData[0] = Double.MAX_VALUE;
        object.arrayData[1] = Double.MIN_VALUE * -1d;
        doTest(OBJECT_43, object);
    }

    private static final String OBJECT_44 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test044() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        doTest(OBJECT_44, object);
    }

    private static final String OBJECT_45 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"\\,\"\r" + //
                                            "}";

    @Test
    public void test045() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        object.arrayData = new String[] { "," };
        doTest(OBJECT_45, object);
    }

    private static final String OBJECT_46 = "{\r" + //
                                            "\"int1\":\"13\",\r\"arrayData\":\"A,N,A A,A\"A,A\rA\"\r" + //
                                            "}";

    @Test
    public void test046() {
        PojoStringArray object = new PojoStringArray();
        object.int1 = 13;
        object.arrayData = new String[] { "A", null, "A A", ",B,b,N,", ",", "\\,a", "A\"A", "A\rA" };
        doTest(OBJECT_46, object);
    }

}
