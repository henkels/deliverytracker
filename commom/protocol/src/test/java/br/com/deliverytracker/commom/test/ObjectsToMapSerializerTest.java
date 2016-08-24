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
        object.byteArray = null;
        doTest(OBJECT_07, object);
    }

    private static final String OBJECT_08 = "{\r" + //
                                            "\"int1\":\"13\",\r\"byteArray\":\"0 0\"\r" + //
                                            "}";

    @Test
    public void test008() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.byteArray = new byte[2];
        doTest(OBJECT_08, object);
    }

    private static final String OBJECT_09 = "{\r" + //
                                            "\"int1\":\"13\",\r\"byteArray\":\"1 -1\"\r" + //
                                            "}";

    @Test
    public void test009() {
        PojoPrimitiveByteArray object = new PojoPrimitiveByteArray();
        object.int1 = 13;
        object.byteArray = new byte[2];
        object.byteArray[0] = 1;
        object.byteArray[1] = -1;
        doTest(OBJECT_09, object);
    }

    private static final String OBJECT_10 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test010() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.byteArray = null;
        doTest(OBJECT_10, object);
    }

    private static final String OBJECT_11 = "{\r" + //
                                            "\"int1\":\"13\",\r\"byteArray\":\"N N\"\r" + //
                                            "}";

    @Test
    public void test011() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.byteArray = new Byte[2];
        doTest(OBJECT_11, object);
    }

    private static final String OBJECT_12 = "{\r" + //
                                            "\"int1\":\"13\",\r\"byteArray\":\"1 -1\"\r" + //
                                            "}";

    @Test
    public void test012() {
        PojoByteArray object = new PojoByteArray();
        object.int1 = 13;
        object.byteArray = new Byte[2];
        object.byteArray[0] = 1;
        object.byteArray[1] = -1;
        doTest(OBJECT_12, object);
    }

}
