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

    public static class ClassArrayRefObject {

        public int int1;
        public ClassSimpleInnerObject[] objects;
    }

//    private static final String EMPTY_OBJECT = "{}";

    //////////////////////////////////////////// Object ///////////////////////////////////////

    private static final String OBJECT_01 = "{\r\"int1\":\"11\"\r}";

    @Test
    public void test110() {
        ClassSimpleOuterObject object = new ClassSimpleOuterObject();
        object.int1 = 11;
        doTest(OBJECT_01, object);
    }

    private static final String OBJECT_02 = "{\r\"int1\":\"12\",\r\"object\":\"REF_1\"\r}";

    @Test
    public void test111() {
        ClassSimpleOuterObject object = new ClassSimpleOuterObject();
        object.int1 = 12;
        object.object = new ClassSimpleInnerObject();
        doTest(OBJECT_02, object);
    }

    private static final String OBJECT_03 = "{\r\"int1\":\"13\",\r\"object\":\"REF_1\",\r\"REF_1.int1\":\"14\"\r}";

    @Test
    public void test112() {
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
    public void test113() {
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
    public void test114() {
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
    public void test116() {
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
                                            "\"int1\":\"13\",\r\"object\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r\"REF_1.object\":\"REF_2\",\r" + //
                                            "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"\"\r" + //
                                            "}";

    @Test
    public void test117() {
        ClassArrayRefObject object = new ClassArrayRefObject();
        object.int1 = 13;
        object.objects = null;
        doTest(OBJECT_07, object);
    }

    private static final String OBJECT_08 = "{\r" + //
                                            "\"int1\":\"13\",\r\"object\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r\"REF_1.object\":\"REF_2\",\r" + //
                                            "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"\"\r" + //
                                            "}";

    @Test
    public void test118() {
        ClassArrayRefObject object = new ClassArrayRefObject();
        object.int1 = 13;
        object.objects = new ClassSimpleInnerObject[2];
        doTest(OBJECT_08, object);
    }

    private static final String OBJECT_09 = "{\r" + //
                                            "\"int1\":\"13\",\r\"object\":\"REF_1\",\r" + //
                                            "\"REF_1.int1\":\"14\",\r\"REF_1.object\":\"REF_2\",\r" + //
                                            "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"\"\r" + //
                                            "}";

    @Test
    public void test119() {
        ClassArrayRefObject object = new ClassArrayRefObject();
        object.int1 = 13;
        object.objects = new ClassSimpleInnerObject[2];
        object.objects[0] = new ClassSimpleInnerObject();
        object.objects[0].int1 = 14;
        object.objects[1] = new ClassSimpleInnerObject();
        object.objects[1].int1 = 15;
        doTest(OBJECT_09, object);
    }

}
