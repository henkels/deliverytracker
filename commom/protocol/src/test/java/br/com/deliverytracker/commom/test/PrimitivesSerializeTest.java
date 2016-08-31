package br.com.deliverytracker.commom.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.deliverytracker.commom.ToMapSerializer;

public class PrimitivesSerializeTest {

    private void doTest(String expected, Object object) {
        SerializeTestUtils.doTest(expected, object);
    }

    private static final String EMPTY_OBJECT = "{}";

    /**
     * Empty Object
     */
    @Test
    public void test001() {
        doTest(EMPTY_OBJECT, new PojoString());
    }

    private static final String SIMPLE_STRING = "{\r\"st1\":\"A\"\r}";

    /**
     * Simple String
     */
    @Test
    public void test002() {
        PojoString object = new PojoString();
        object.st1 = "A";
        doTest(SIMPLE_STRING, object);
    }

    private static final String DOUBLE_QUOTE_STRING = "{\r\"st1\":\"A\"\"A\"\r}";

    /**
     * String that contains "
     */
    @Test
    public void test003() {
        PojoString object = new PojoString();
        object.st1 = "A\"A";
        doTest(DOUBLE_QUOTE_STRING, object);
    }

    private static final String TWO_DOUBLE_QUOTE_STRING = "{\r\"st1\":\"A\"\"B\"\"C\"\r}";

    /**
     * String that contains 2 "
     */
    @Test
    public void test004() {
        PojoString object = new PojoString();
        object.st1 = "A\"B\"C";
        doTest(TWO_DOUBLE_QUOTE_STRING, object);
    }

    private static final String TWO_STRINGS_01 = "{\r\"st1\":\"A\"\r}";

    @Test
    public void test005() {
        PojoStrings object = new PojoStrings();
        object.st1 = "A";
        doTest(TWO_STRINGS_01, object);
    }

    private static final String TWO_STRINGS_02 = "{\r\"st1\":\"A\",\r\"st2\":\"B\"\r}";

    @Test
    public void test006() {
        PojoStrings object = new PojoStrings();
        object.st1 = "A";
        object.st2 = "B";
        doTest(TWO_STRINGS_02, object);
    }

    @Test
    public void test007() {
        PojoPrimitiveBoolean object = new PojoPrimitiveBoolean();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BOOL_01 = "{\r\"bool1\":\"1\"\r}";

    @Test
    public void test008() {
        PojoPrimitiveBoolean object = new PojoPrimitiveBoolean();
        object.bool1 = true;
        doTest(BOOL_01, object);
    }

    @Test
    public void test009() {
        PojoBoolean object = new PojoBoolean();
        doTest(EMPTY_OBJECT, object);
    }

    @Test
    public void test010() {
        PojoBoolean object = new PojoBoolean();
        object.bool1 = true;
        doTest(BOOL_01, object);
    }

    private static final String BOOL_02 = "{\r\"bool1\":\"0\"\r}";

    @Test
    public void test011() {
        PojoBoolean object = new PojoBoolean();
        object.bool1 = false;
        doTest(BOOL_02, object);
    }

    @Test
    public void test012() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BYTE_01 = "{\r\"byte1\":\"1\"\r}";

    @Test
    public void test013() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = 1;
        doTest(BYTE_01, object);
    }

    private static final String BYTE_02 = "{\r\"byte1\":\"-128\"\r}";

    @Test
    public void test014() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(BYTE_02, object);
    }

    private static final String BYTE_03 = "{\r\"byte1\":\"127\"\r}";

    @Test
    public void test015() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(BYTE_03, object);
    }

    @Test
    public void test016() {
        PojoByte object = new PojoByte();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String BYTE_04 = "{\r\"byte1\":\"0\"\r}";

    @Test
    public void test017() {
        PojoByte object = new PojoByte();
        object.byte1 = 0;
        doTest(BYTE_04, object);
    }

    @Test
    public void test018() {
        PojoByte object = new PojoByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(BYTE_02, object);
    }

    @Test
    public void test019() {
        PojoByte object = new PojoByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(BYTE_03, object);
    }

    @Test
    public void test020() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String SHORT_01 = "{\r\"short1\":\"1\"\r}";

    @Test
    public void test021() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = 1;
        doTest(SHORT_01, object);
    }

    private static final String SHORT_02 = "{\r\"short1\":\"-32768\"\r}";

    @Test
    public void test022() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SHORT_02, object);
    }

    private static final String SHORT_03 = "{\r\"short1\":\"32767\"\r}";

    @Test
    public void test023() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SHORT_03, object);
    }

    @Test
    public void test024() {
        PojoShort object = new PojoShort();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String SHORT_04 = "{\r\"short1\":\"0\"\r}";

    @Test
    public void test025() {
        PojoShort object = new PojoShort();
        object.short1 = 0;
        doTest(SHORT_04, object);
    }

    @Test
    public void test026() {
        PojoShort object = new PojoShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SHORT_02, object);
    }

    @Test
    public void test027() {
        PojoShort object = new PojoShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SHORT_03, object);
    }

    @Test
    public void test028() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String INT_01 = "{\r\"int1\":\"1\"\r}";

    @Test
    public void test029() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = 1;
        doTest(INT_01, object);
    }

    private static final String INT_02 = "{\r\"int1\":\"-2147483648\"\r}";

    @Test
    public void test030() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = Integer.MIN_VALUE;
        doTest(INT_02, object);
    }

    private static final String INT_03 = "{\r\"int1\":\"2147483647\"\r}";

    @Test
    public void test031() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = Integer.MAX_VALUE;
        doTest(INT_03, object);
    }

    @Test
    public void test032() {
        PojoInteger object = new PojoInteger();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String INT_04 = "{\r\"int1\":\"0\"\r}";

    @Test
    public void test033() {
        PojoInteger object = new PojoInteger();
        object.int1 = 0;
        doTest(INT_04, object);
    }

    @Test
    public void test034() {
        PojoInteger object = new PojoInteger();
        object.int1 = Integer.MIN_VALUE;
        doTest(INT_02, object);
    }

    @Test
    public void test035() {
        PojoInteger object = new PojoInteger();
        object.int1 = Integer.MAX_VALUE;
        doTest(INT_03, object);
    }

    @Test
    public void test036() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String LONG_01 = "{\r\"long1\":\"1\"\r}";

    @Test
    public void test037() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = 1;
        doTest(LONG_01, object);
    }

    private static final String LONG_02 = "{\r\"long1\":\"-9223372036854775808\"\r}";

    @Test
    public void test038() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = Long.MIN_VALUE;
        doTest(LONG_02, object);
    }

    private static final String LONG_03 = "{\r\"long1\":\"9223372036854775807\"\r}";

    @Test
    public void test039() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = Long.MAX_VALUE;
        doTest(LONG_03, object);
    }

    @Test
    public void test040() {
        PojoLong object = new PojoLong();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String LONG_04 = "{\r\"long1\":\"0\"\r}";

    @Test
    public void test041() {
        PojoLong object = new PojoLong();
        object.long1 = 0L;
        doTest(LONG_04, object);
    }

    @Test
    public void test042() {
        PojoLong object = new PojoLong();
        object.long1 = Long.MIN_VALUE;
        doTest(LONG_02, object);
    }

    @Test
    public void test043() {
        PojoLong object = new PojoLong();
        object.long1 = Long.MAX_VALUE;
        doTest(LONG_03, object);
    }

    @Test
    public void test044() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String FLOAT_01 = "{\r\"float1\":\"1\"\r}";

    @Test
    public void test045() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1;
        doTest(FLOAT_01, object);
    }

    private static final String FLOAT_02 = "{\r\"float1\":\"1.4E-45\"\r}";

    @Test
    public void test046() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_VALUE;
        doTest(FLOAT_02, object);
    }

    private static final String FLOAT_03 = "{\r\"float1\":\"1.17549435E-38\"\r}";

    @Test
    public void test047() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_NORMAL;
        doTest(FLOAT_03, object);
    }

    private static final String FLOAT_04 = "{\r\"float1\":\"3.4028235E38\"\r}";

    @Test
    public void test048() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MAX_VALUE;
        doTest(FLOAT_04, object);
    }

    private static final String FLOAT_05 = "{\r\"float1\":\"1.234567\"\r}";

    @Test
    public void test049() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.234567F;
        doTest(FLOAT_05, object);
    }

    private static final String FLOAT_06 = "{\r\"float1\":\"1.2345678\"\r}";

    @Test
    public void test050() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.2345678F;
        doTest(FLOAT_06, object);
    }

    private static final String FLOAT_07 = "{\r\"float1\":\"1.2345679\"\r}";

    @Test
    public void test051() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.23456789F;
        doTest(FLOAT_07, object);
    }

    private static final String FLOAT_08 = "{\r\"float1\":\"0.001\"\r}";

    @Test
    public void test052() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.001F;
        doTest(FLOAT_08, object);
    }

    private static final String FLOAT_09 = "{\r\"float1\":\"1.0E-4\"\r}";

    @Test
    public void test053() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0001F;
        doTest(FLOAT_09, object);
    }

    private static final String FLOAT_10 = "{\r\"float1\":\"1.2345678E-9\"\r}";

    @Test
    public void test054() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0000000012345678F;
        doTest(FLOAT_10, object);
    }

    private static final String FLOAT_11 = "{\r\"float1\":\"1234567\"\r}";

    @Test
    public void test055() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1234567F;
        doTest(FLOAT_11, object);
    }

    private static final String FLOAT_12 = "{\r\"float1\":\"1.2345678E7\"\r}";

    @Test
    public void test056() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678F;
        doTest(FLOAT_12, object);
    }

    private static final String FLOAT_13 = "{\r\"float1\":\"1.23456792E8\"\r}";

    @Test
    public void test057() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 123456789F;
        doTest(FLOAT_13, object);
    }

    private static final String FLOAT_14 = "{\r\"float1\":\"1000\"\r}";

    @Test
    public void test058() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1000F;
        doTest(FLOAT_14, object);
    }

    private static final String FLOAT_15 = "{\r\"float1\":\"10000\"\r}";

    @Test
    public void test059() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 10000F;
        doTest(FLOAT_15, object);
    }

    private static final String FLOAT_16 = "{\r\"float1\":\"1.23456784E16\"\r}";

    @Test
    public void test060() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678000000000F;
        doTest(FLOAT_16, object);
    }

    private static final String FLOAT_17 = "{\r\"float1\":\"-1\"\r}";

    @Test
    public void test061() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = -1;
        doTest(FLOAT_17, object);
    }

    private static final String FLOAT_18 = "{\r\"float1\":\"-1.4E-45\"\r}";

    @Test
    public void test062() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_VALUE * -1f;
        doTest(FLOAT_18, object);
    }

    private static final String FLOAT_19 = "{\r\"float1\":\"-1.17549435E-38\"\r}";

    @Test
    public void test063() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_NORMAL * -1f;
        doTest(FLOAT_19, object);
    }

    private static final String FLOAT_20 = "{\r\"float1\":\"-3.4028235E38\"\r}";

    @Test
    public void test064() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MAX_VALUE * -1f;
        doTest(FLOAT_20, object);
    }

    private static final String FLOAT_21 = "{\r\"float1\":\"-1.234567\"\r}";

    @Test
    public void test065() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.234567F * -1F;
        doTest(FLOAT_21, object);
    }

    private static final String FLOAT_22 = "{\r\"float1\":\"-1.2345678\"\r}";

    @Test
    public void test066() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.2345678F * -1f;
        doTest(FLOAT_22, object);
    }

    private static final String FLOAT_23 = "{\r\"float1\":\"-1.2345679\"\r}";

    @Test
    public void test067() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.23456789F * -1f;
        doTest(FLOAT_23, object);
    }

    private static final String FLOAT_24 = "{\r\"float1\":\"-0.001\"\r}";

    @Test
    public void test068() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.001F * -1f;
        doTest(FLOAT_24, object);
    }

    private static final String FLOAT_25 = "{\r\"float1\":\"-1.0E-4\"\r}";

    @Test
    public void test069() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0001F * -1f;
        doTest(FLOAT_25, object);
    }

    private static final String FLOAT_26 = "{\r\"float1\":\"-1.2345678E-9\"\r}";

    @Test
    public void test070() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0000000012345678F * -1f;
        doTest(FLOAT_26, object);
    }

    private static final String FLOAT_27 = "{\r\"float1\":\"-1234567\"\r}";

    @Test
    public void test071() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1234567F * -1f;
        doTest(FLOAT_27, object);
    }

    private static final String FLOAT_28 = "{\r\"float1\":\"-1.2345678E7\"\r}";

    @Test
    public void test072() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678F * -1f;
        doTest(FLOAT_28, object);
    }

    private static final String FLOAT_29 = "{\r\"float1\":\"-1.23456792E8\"\r}";

    @Test
    public void test073() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 123456789F * -1f;
        doTest(FLOAT_29, object);
    }

    private static final String FLOAT_30 = "{\r\"float1\":\"-1000\"\r}";

    @Test
    public void test074() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1000F * -1f;
        doTest(FLOAT_30, object);
    }

    private static final String FLOAT_31 = "{\r\"float1\":\"-10000\"\r}";

    @Test
    public void test075() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 10000F * -1f;
        doTest(FLOAT_31, object);
    }

    private static final String FLOAT_32 = "{\r\"float1\":\"-1.23456784E16\"\r}";

    @Test
    public void test076() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678000000000F * -1f;
        doTest(FLOAT_32, object);
    }

    @Test
    public void test077() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        doTest(EMPTY_OBJECT, object);
    }

    private static final String DOUBLE_01 = "{\r\"double1\":\"1\"\r}";

    @Test
    public void test078() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1;
        doTest(DOUBLE_01, object);
    }

    private static final String DOUBLE_02 = "{\r\"double1\":\"4.9E-324\"\r}";

    @Test
    public void test079() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_VALUE;
        doTest(DOUBLE_02, object);
    }

    private static final String DOUBLE_03 = "{\r\"double1\":\"2.2250738585072014E-308\"\r}";

    @Test
    public void test080() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_NORMAL;
        doTest(DOUBLE_03, object);
    }

    private static final String DOUBLE_04 = "{\r\"double1\":\"1.7976931348623157E308\"\r}";

    @Test
    public void test081() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MAX_VALUE;
        doTest(DOUBLE_04, object);
    }

    private static final String DOUBLE_05 = "{\r\"double1\":\"1.2345678901234567\"\r}";

    @Test
    public void test082() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234567D;
        doTest(DOUBLE_05, object);
    }

    private static final String DOUBLE_06 = "{\r\"double1\":\"1.2345678901234567\"\r}";

    @Test
    public void test083() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.23456789012345678D;
        doTest(DOUBLE_06, object);
    }

    private static final String DOUBLE_07 = "{\r\"double1\":\"1.234567890123457\"\r}";

    @Test
    public void test084() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234569D;
        doTest(DOUBLE_07, object);
    }

    private static final String DOUBLE_08 = "{\r\"double1\":\"0.001\"\r}";

    @Test
    public void test085() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.001D;
        doTest(DOUBLE_08, object);
    }

    private static final String DOUBLE_09 = "{\r\"double1\":\"1.0E-4\"\r}";

    @Test
    public void test086() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.0001D;
        doTest(DOUBLE_09, object);
    }

    private static final String DOUBLE_10 = "{\r\"double1\":\"1.2345678901234567E-17\"\r}";

    @Test
    public void test087() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.000000000000000012345678901234567D;
        doTest(DOUBLE_10, object);
    }

    private static final String DOUBLE_11 = "{\r\"double1\":\"1234567\"\r}";

    @Test
    public void test088() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567D;
        doTest(DOUBLE_11, object);
    }

    private static final String DOUBLE_12 = "{\r\"double1\":\"1.234567890123456E15\"\r}";

    @Test
    public void test089() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567890123456D;
        doTest(DOUBLE_12, object);
    }

    private static final String DOUBLE_13 = "{\r\"double1\":\"1.2345678901234568E17\"\r}";

    @Test
    public void test090() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345678D;
        doTest(DOUBLE_13, object);
    }

    private static final String DOUBLE_14 = "{\r\"double1\":\"1000000\"\r}";

    @Test
    public void test091() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1000000D;
        doTest(DOUBLE_14, object);
    }

    private static final String DOUBLE_15 = "{\r\"double1\":\"1.0E7\"\r}";

    @Test
    public void test092() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 10000000D;
        doTest(DOUBLE_15, object);
    }

    private static final String DOUBLE_16 = "{\r\"double1\":\"1.234567890123456E32\"\r}";

    @Test
    public void test093() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345600000000000000000D;
        doTest(DOUBLE_16, object);
    }

    private static final String DOUBLE_17 = "{\r\"double1\":\"-1\"\r}";

    @Test
    public void test094() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1 * -1d;
        doTest(DOUBLE_17, object);
    }

    private static final String DOUBLE_18 = "{\r\"double1\":\"-4.9E-324\"\r}";

    @Test
    public void test095() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_VALUE * -1d;
        doTest(DOUBLE_18, object);
    }

    private static final String DOUBLE_19 = "{\r\"double1\":\"-2.2250738585072014E-308\"\r}";

    @Test
    public void test096() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_NORMAL * -1d;
        doTest(DOUBLE_19, object);
    }

    private static final String DOUBLE_20 = "{\r\"double1\":\"-1.7976931348623157E308\"\r}";

    @Test
    public void test097() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MAX_VALUE * -1d;
        doTest(DOUBLE_20, object);
    }

    private static final String DOUBLE_21 = "{\r\"double1\":\"-1.2345678901234567\"\r}";

    @Test
    public void test098() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234567D * -1d;
        doTest(DOUBLE_21, object);
    }

    private static final String DOUBLE_22 = "{\r\"double1\":\"-1.2345678901234567\"\r}";

    @Test
    public void test099() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.23456789012345678D * -1d;
        doTest(DOUBLE_22, object);
    }

    private static final String DOUBLE_23 = "{\r\"double1\":\"-1.234567890123457\"\r}";

    @Test
    public void test100() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234569D * -1d;
        doTest(DOUBLE_23, object);
    }

    private static final String DOUBLE_24 = "{\r\"double1\":\"-0.001\"\r}";

    @Test
    public void test101() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.001D * -1d;
        doTest(DOUBLE_24, object);
    }

    private static final String DOUBLE_25 = "{\r\"double1\":\"-1.0E-4\"\r}";

    @Test
    public void test102() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.0001D * -1d;
        doTest(DOUBLE_25, object);
    }

    private static final String DOUBLE_26 = "{\r\"double1\":\"-1.2345678901234567E-17\"\r}";

    @Test
    public void test103() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.000000000000000012345678901234567D * -1d;
        doTest(DOUBLE_26, object);
    }

    private static final String DOUBLE_27 = "{\r\"double1\":\"-1234567\"\r}";

    @Test
    public void test104() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567D * -1d;
        doTest(DOUBLE_27, object);
    }

    private static final String DOUBLE_28 = "{\r\"double1\":\"-1.234567890123456E15\"\r}";

    @Test
    public void test105() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567890123456D * -1d;
        doTest(DOUBLE_28, object);
    }

    private static final String DOUBLE_29 = "{\r\"double1\":\"-1.2345678901234568E17\"\r}";

    @Test
    public void test106() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345678D * -1d;
        doTest(DOUBLE_29, object);
    }

    private static final String DOUBLE_30 = "{\r\"double1\":\"-1000000\"\r}";

    @Test
    public void test107() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1000000D * -1d;
        doTest(DOUBLE_30, object);
    }

    private static final String DOUBLE_31 = "{\r\"double1\":\"-1.0E7\"\r}";

    @Test
    public void test108() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 10000000D * -1d;
        doTest(DOUBLE_31, object);
    }

    private static final String DOUBLE_32 = "{\r\"double1\":\"-1.234567890123456E32\"\r}";

    @Test
    public void test109() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345600000000000000000D * -1d;
        doTest(DOUBLE_32, object);
    }
    
    private static final String FLOAT_33 = "{}";

    @Test
    public void test110() {
        PojoFloat object = new PojoFloat();
        doTest(FLOAT_33, object);
    }
    
    private static final String FLOAT_34 = "{\r\"float1\":\"1\"\r}";

    @Test
    public void test111() {
        PojoFloat object = new PojoFloat();
        object.float1 = 1f;
        doTest(FLOAT_34, object);
    }
    
    private static final String DOUBLE_33 = "{}";

    @Test
    public void test112() {
        PojoDouble object = new PojoDouble();
        doTest(DOUBLE_33, object);
    }
    
    private static final String DOUBLE_34 = "{\r\"double1\":\"1\"\r}";

    @Test
    public void test113() {
        PojoDouble object = new PojoDouble();
        object.double1 = 1d;
        doTest(DOUBLE_34, object);
    }
}
