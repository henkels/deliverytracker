package protocol;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.deliverytracker.commom.ToMapSerializer;

public class ObjectsToMapSerializerTest {

    private void doTest(String expected, Object object) {
        String actual = ToMapSerializer.toJson(object);
        assertEquals(expected, actual);
    }

    public static class ClassSimpleInnerObject {

        public int int1;
    }

    public static class ClassSimpleOuterObject {

        public int int1;
        public ClassSimpleInnerObject object;
    }

    public static class ClassHierarquicalObject {

        public int int1;
        public ClassHierarquicalObject object;
    }

    public static class ClassCrossRefObject {

        public int int1;
        public ClassSimpleInnerObject object1;
        public ClassSimpleInnerObject object2;
    }

    public static class ClassPrimitiveByteArrayObject {

        public int int1;
        public byte[] bytes;
    }

    public static class ClassByteArrayObject {

        public int int1;
        public Byte[] bytes;
    }

    //    private static final String EMPTY_OBJECT = "{}";

    //////////////////////////////////////////// Object ///////////////////////////////////////

    private static final String OBJECT_01 = "{\r\"int1\":\"11\"\r}";

    @Test
    public void test001() {
        ClassSimpleOuterObject object = new ClassSimpleOuterObject();
        object.int1 = 11;
        doTest(OBJECT_01, object);
    }

    private static final String OBJECT_02 = "{\r\"int1\":\"12\",\r\"object\":\"REF_1\"\r}";

    @Test
    public void test002() {
        ClassSimpleOuterObject object = new ClassSimpleOuterObject();
        object.int1 = 12;
        object.object = new ClassSimpleInnerObject();
        doTest(OBJECT_02, object);
    }

    private static final String OBJECT_03 = "{\r\"int1\":\"13\",\r\"object\":\"REF_1\",\r\"REF_1.int1\":\"14\"\r}";

    @Test
    public void test003() {
        ClassSimpleOuterObject object = new ClassSimpleOuterObject();
        object.int1 = 13;
        object.object = new ClassSimpleInnerObject();
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
        ClassHierarquicalObject object = new ClassHierarquicalObject();
        object.int1 = 13;
        object.object = new ClassHierarquicalObject();
        object.object.int1 = 14;
        object.object.object = new ClassHierarquicalObject();
        object.object.object.int1 = 15;
        object.object.object.object = new ClassHierarquicalObject();
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
        ClassCrossRefObject object = new ClassCrossRefObject();
        object.int1 = 13;
        object.object1 = new ClassSimpleInnerObject();
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
        ClassHierarquicalObject object = new ClassHierarquicalObject();
        object.int1 = 13;
        object.object = new ClassHierarquicalObject();
        object.object.int1 = 14;
        object.object.object = new ClassHierarquicalObject();
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
        ClassPrimitiveByteArrayObject object = new ClassPrimitiveByteArrayObject();
        object.int1 = 13;
        object.bytes = null;
        doTest(OBJECT_07, object);
    }

    private static final String OBJECT_08 = "{\r" + //
                                            "\"int1\":\"13\",\r\"bytes\":\"0 0\"\r" + //
                                            "}";

    @Test
    public void test008() {
        ClassPrimitiveByteArrayObject object = new ClassPrimitiveByteArrayObject();
        object.int1 = 13;
        object.bytes = new byte[2];
        doTest(OBJECT_08, object);
    }

    private static final String OBJECT_09 = "{\r" + //
                                            "\"int1\":\"13\",\r\"bytes\":\"1 -1\"\r" + //
                                            "}";

    @Test
    public void test009() {
        ClassPrimitiveByteArrayObject object = new ClassPrimitiveByteArrayObject();
        object.int1 = 13;
        object.bytes = new byte[2];
        object.bytes[0] = 1;
        object.bytes[1] = -1;
        doTest(OBJECT_09, object);
    }

    private static final String OBJECT_10 = "{\r" + //
                                            "\"int1\":\"13\"\r" + //
                                            "}";

    @Test
    public void test010() {
        ClassByteArrayObject object = new ClassByteArrayObject();
        object.int1 = 13;
        object.bytes = null;
        doTest(OBJECT_10, object);
    }

    private static final String OBJECT_11 = "{\r" + //
                                            "\"int1\":\"13\",\r\"bytes\":\"N N\"\r" + //
                                            "}";

    @Test
    public void test011() {
        ClassByteArrayObject object = new ClassByteArrayObject();
        object.int1 = 13;
        object.bytes = new Byte[2];
        doTest(OBJECT_11, object);
    }

    private static final String OBJECT_12 = "{\r" + //
                                            "\"int1\":\"13\",\r\"bytes\":\"1 -1\"\r" + //
                                            "}";

    @Test
    public void test012() {
        ClassByteArrayObject object = new ClassByteArrayObject();
        object.int1 = 13;
        object.bytes = new Byte[2];
        object.bytes[0] = 1;
        object.bytes[1] = -1;
        doTest(OBJECT_12, object);
    }

}
