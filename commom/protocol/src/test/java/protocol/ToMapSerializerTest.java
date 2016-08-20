package protocol;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.deliverytracker.commom.ToMapSerializer;

public class ToMapSerializerTest {

    private void doTest(String expected, Object object) {
        String actual = ToMapSerializer.toJson(object);
        assertEquals(expected, actual);
    }

    public static class ClazzString {

        public String st1;
    }

    public static class ClazzStrings {

        public String st1;
        public String st2;
    }

    public static class ClazzPrimitiveBoolean {
        public boolean bool1;
    }

    public static class ClazzBoolean {
        public Boolean bool1;
    }

    public static class ClazzPrimitiveByte {
        public byte byte1;
    }

    public static class ClazzByte {
        public Byte byte1;
    }

    public static class ClazzPrimitiveShort {
        public short short1;
    }

    public static class ClazzShort {
        public Short short1;
    }

    public static class ClazzPrimitiveInt {
        public int int1;
    }

    public static class ClazzInteger {
        public Integer int1;
    }

    public static class ClazzPrimitiveLong {
        public long long1;
    }

    public static class ClazzLong {
        public Long long1;
    }

    public static class ClazzPrimitiveFloat {
        public float float1;
    }

    public static class ClazzFloat {
        public Float float1;
    }

    public static class ClazzPrimitiveDouble {
        public double double1;
    }

    public static class ClazzDouble {
        public Double double1;
    }

    private static final String EMPTY_OBJECT = "{}";

    /**
     * Empty Object
     */
    @Test
    public void test01() {
        doTest(EMPTY_OBJECT, new ClazzString());
    }

    private static final String SIMPLE_STRING = "{\r\"st1\":\"A\"\r}";

    /**
     * Simple String
     */
    @Test
    public void test02() {
        ClazzString object = new ClazzString();
        object.st1 = "A";
        doTest(SIMPLE_STRING, object);
    }

    private static final String DOUBLE_QUOTE_STRING = "{\r\"st1\":\"A\"\"A\"\r}";

    /**
     * String that contains "
     */
    @Test
    public void test03() {
        ClazzString object = new ClazzString();
        object.st1 = "A\"A";
        doTest(DOUBLE_QUOTE_STRING, object);
    }

    private static final String TWO_DOUBLE_QUOTE_STRING = "{\r\"st1\":\"A\"\"B\"\"C\"\r}";

    /**
     * String that contains 2 "
     */
    @Test
    public void test04() {
        ClazzString object = new ClazzString();
        object.st1 = "A\"B\"C";
        doTest(TWO_DOUBLE_QUOTE_STRING, object);
    }

    private static final String TWO_STRINGS_01 = "{\r\"st1\":\"A\"\r}";

    @Test
    public void test05() {
        ClazzStrings object = new ClazzStrings();
        object.st1 = "A";
        doTest(TWO_STRINGS_01, object);
    }

    private static final String TWO_STRINGS_02 = "{\r\"st1\":\"A\",\r\"st2\":\"B\"\r}";

    @Test
    public void test06() {
        ClazzStrings object = new ClazzStrings();
        object.st1 = "A";
        object.st2 = "B";
        doTest(TWO_STRINGS_02, object);
    }
    
    @Test
    public void test07() {
        ClazzPrimitiveBoolean object = new ClazzPrimitiveBoolean();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BOOL_01 = "{\r\"bool1\":\"1\"\r}";

    @Test
    public void test08() {
        ClazzPrimitiveBoolean object = new ClazzPrimitiveBoolean();
        object.bool1 = true;
        doTest(BOOL_01, object);
    }

    @Test
    public void test09() {
        ClazzBoolean object = new ClazzBoolean();
        doTest(EMPTY_OBJECT, object);
    }

    @Test
    public void test10() {
        ClazzBoolean object = new ClazzBoolean();
        object.bool1 = true;
        doTest(BOOL_01, object);
    }

    private static final String BOOL_02 = "{\r\"bool1\":\"0\"\r}";

    @Test
    public void test11() {
        ClazzBoolean object = new ClazzBoolean();
        object.bool1 = false;
        doTest(BOOL_02, object);
    }

    @Test
    public void test12() {
        ClazzPrimitiveByte object = new ClazzPrimitiveByte();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BYTE_01 = "{\r\"byte1\":\"1\"\r}";

    @Test
    public void test13() {
        ClazzPrimitiveByte object = new ClazzPrimitiveByte();
        object.byte1 = 1;
        doTest(BYTE_01, object);
    }

    private static final String BYTE_02 = "{\r\"byte1\":\"-128\"\r}";

    @Test
    public void test14() {
        ClazzPrimitiveByte object = new ClazzPrimitiveByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(BYTE_02, object);
    }

    private static final String BYTE_03 = "{\r\"byte1\":\"127\"\r}";

    @Test
    public void test15() {
        ClazzPrimitiveByte object = new ClazzPrimitiveByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(BYTE_03, object);
    }

    @Test
    public void test16() {
        ClazzByte object = new ClazzByte();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BYTE_04 = "{\r\"byte1\":\"0\"\r}";

    @Test
    public void test17() {
        ClazzByte object = new ClazzByte();
        object.byte1 = 0;
        doTest(BYTE_04, object);
    }

    @Test
    public void test18() {
        ClazzByte object = new ClazzByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(BYTE_02, object);
    }

    @Test
    public void test19() {
        ClazzByte object = new ClazzByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(BYTE_03, object);
    }

    @Test
    public void test20() {
        ClazzPrimitiveShort object = new ClazzPrimitiveShort();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String SHORT_01 = "{\r\"short1\":\"1\"\r}";

    @Test
    public void test21() {
        ClazzPrimitiveShort object = new ClazzPrimitiveShort();
        object.short1 = 1;
        doTest(SHORT_01, object);
    }

    private static final String SHORT_02 = "{\r\"short1\":\"-32768\"\r}";

    @Test
    public void test22() {
        ClazzPrimitiveShort object = new ClazzPrimitiveShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SHORT_02, object);
    }

    private static final String SHORT_03 = "{\r\"short1\":\"32767\"\r}";

    @Test
    public void test23() {
        ClazzPrimitiveShort object = new ClazzPrimitiveShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SHORT_03, object);
    }

    @Test
    public void test24() {
        ClazzShort object = new ClazzShort();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String SHORT_04 = "{\r\"short1\":\"0\"\r}";

    @Test
    public void test25() {
        ClazzShort object = new ClazzShort();
        object.short1 = 0;
        doTest(SHORT_04, object);
    }

    @Test
    public void test26() {
        ClazzShort object = new ClazzShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SHORT_02, object);
    }

    @Test
    public void test27() {
        ClazzShort object = new ClazzShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SHORT_03, object);
    }

    @Test
    public void test28() {
        ClazzPrimitiveInt object = new ClazzPrimitiveInt();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String INT_01 = "{\r\"int1\":\"1\"\r}";

    @Test
    public void test29() {
        ClazzPrimitiveInt object = new ClazzPrimitiveInt();
        object.int1 = 1;
        doTest(INT_01, object);
    }

    private static final String INT_02 = "{\r\"int1\":\"-2147483648\"\r}";

    @Test
    public void test30() {
        ClazzPrimitiveInt object = new ClazzPrimitiveInt();
        object.int1 = Integer.MIN_VALUE;
        doTest(INT_02, object);
    }

    private static final String INT_03 = "{\r\"int1\":\"2147483647\"\r}";

    @Test
    public void test31() {
        ClazzPrimitiveInt object = new ClazzPrimitiveInt();
        object.int1 = Integer.MAX_VALUE;
        doTest(INT_03, object);
    }

    @Test
    public void test32() {
        ClazzInteger object = new ClazzInteger();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String INT_04 = "{\r\"int1\":\"0\"\r}";

    @Test
    public void test33() {
        ClazzInteger object = new ClazzInteger();
        object.int1 = 0;
        doTest(INT_04, object);
    }

    @Test
    public void test34() {
        ClazzInteger object = new ClazzInteger();
        object.int1 = Integer.MIN_VALUE;
        doTest(INT_02, object);
    }

    @Test
    public void test35() {
        ClazzInteger object = new ClazzInteger();
        object.int1 = Integer.MAX_VALUE;
        doTest(INT_03, object);
    }

    @Test
    public void test36() {
        ClazzPrimitiveLong object = new ClazzPrimitiveLong();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String LONG_01 = "{\r\"long1\":\"1\"\r}";

    @Test
    public void test37() {
        ClazzPrimitiveLong object = new ClazzPrimitiveLong();
        object.long1 = 1;
        doTest(LONG_01, object);
    }

    private static final String LONG_02 = "{\r\"long1\":\"-9223372036854775808\"\r}";

    @Test
    public void test38() {
        ClazzPrimitiveLong object = new ClazzPrimitiveLong();
        object.long1 = Long.MIN_VALUE;
        doTest(LONG_02, object);
    }

    private static final String LONG_03 = "{\r\"long1\":\"9223372036854775807\"\r}";

    @Test
    public void test39() {
        ClazzPrimitiveLong object = new ClazzPrimitiveLong();
        object.long1 = Long.MAX_VALUE;
        doTest(LONG_03, object);
    }

    @Test
    public void test40() {
        ClazzLong object = new ClazzLong();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String LONG_04 = "{\r\"long1\":\"0\"\r}";

    @Test
    public void test41() {
        ClazzLong object = new ClazzLong();
        object.long1 = 0L;
        doTest(LONG_04, object);
    }

    @Test
    public void test42() {
        ClazzLong object = new ClazzLong();
        object.long1 = Long.MIN_VALUE;
        doTest(LONG_02, object);
    }

    @Test
    public void test43() {
        ClazzLong object = new ClazzLong();
        object.long1 = Long.MAX_VALUE;
        doTest(LONG_03, object);
    }

    @Test
    public void test44() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String FLOAT_01 = "{\r\"float1\":\"1\"\r}";

    @Test
    public void test45() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = 1;
        doTest(FLOAT_01, object);
    }

    private static final String FLOAT_02 = "{\r\"float1\":\"1.4E-45\"\r}";

    @Test
    public void test46() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = Float.MIN_VALUE;
        doTest(FLOAT_02, object);
    }

    private static final String FLOAT_03 = "{\r\"float1\":\"1.17549435E-38\"\r}";

    @Test
    public void test47() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = Float.MIN_NORMAL;
        doTest(FLOAT_03, object);
    }

    private static final String FLOAT_04 = "{\r\"float1\":\"3.4028235E38\"\r}";

    @Test
    public void test48() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = Float.MAX_VALUE;
        doTest(FLOAT_04, object);
    }

    private static final String FLOAT_05 = "{\r\"float1\":\"3.4028235E38\"\r}";

    @Test
    public void test49() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = 3.3333333333333333333333333333333333F;
        doTest(FLOAT_05, object);
    }

    private static final String FLOAT_06 = "{\r\"float1\":\"3.4028235E38\"\r}";

    @Test
    public void test50() {
        ClazzPrimitiveFloat object = new ClazzPrimitiveFloat();
        object.float1 = 3.3333333333333333333333333333333333F;
        doTest(FLOAT_06, object);
    }

//    private static final String INT_02 = "{\r\"int1\":\"-2147483648\"\r}";
//
//    @Test
//    public void test14() {
//        ClazzInt object = new ClazzInt();
//        object.int1 = Integer.MIN_VALUE;
//        doTest(INT_02, object);
//    }
//
//    private static final String INT_03 = "{\r\"int1\":\"2147483647\"\r}";
//
//    @Test
//    public void test15() {
//        ClazzInt object = new ClazzInt();
//        object.int1 = Integer.MAX_VALUE;
//        doTest(INT_03, object);
//    }
//
//    @Test
//    public void test16() {
//        ClazzInteger object = new ClazzInteger();
//        doTest(EMPTY_OBJECT, object);
//    }
//
//    private static final String INT_04 = "{\r\"int1\":\"0\"\r}";
//
//    @Test
//    public void test17() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = 0;
//        doTest(INT_04, object);
//    }
//
//    @Test
//    public void test18() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = Integer.MIN_VALUE;
//        doTest(INT_02, object);
//    }
//
//    @Test
//    public void test19() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = Integer.MAX_VALUE;
//        doTest(INT_03, object);
//    }
//
//    @Test
//    public void test12() {
//        ClazzInt object = new ClazzInt();
//        doTest(EMPTY_OBJECT, object);
//    }
//
//    private static final String INT_01 = "{\r\"int1\":\"1\"\r}";
//
//    @Test
//    public void test13() {
//        ClazzInt object = new ClazzInt();
//        object.int1 = 1;
//        doTest(INT_01, object);
//    }
//
//    private static final String INT_02 = "{\r\"int1\":\"-2147483648\"\r}";
//
//    @Test
//    public void test14() {
//        ClazzInt object = new ClazzInt();
//        object.int1 = Integer.MIN_VALUE;
//        doTest(INT_02, object);
//    }
//
//    private static final String INT_03 = "{\r\"int1\":\"2147483647\"\r}";
//
//    @Test
//    public void test15() {
//        ClazzInt object = new ClazzInt();
//        object.int1 = Integer.MAX_VALUE;
//        doTest(INT_03, object);
//    }
//
//    @Test
//    public void test16() {
//        ClazzInteger object = new ClazzInteger();
//        doTest(EMPTY_OBJECT, object);
//    }
//
//    private static final String INT_04 = "{\r\"int1\":\"0\"\r}";
//
//    @Test
//    public void test17() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = 0;
//        doTest(INT_04, object);
//    }
//
//    @Test
//    public void test18() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = Integer.MIN_VALUE;
//        doTest(INT_02, object);
//    }
//
//    @Test
//    public void test19() {
//        ClazzInteger object = new ClazzInteger();
//        object.int1 = Integer.MAX_VALUE;
//        doTest(INT_03, object);
//    }

}
