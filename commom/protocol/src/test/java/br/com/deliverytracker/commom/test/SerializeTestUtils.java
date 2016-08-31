package br.com.deliverytracker.commom.test;

import static org.junit.Assert.assertEquals;

import br.com.deliverytracker.commom.ToMapSerializer;

public class SerializeTestUtils {

    private static int c = 0;

    public static void doTest(String expected, Object object) {
        c++;
        String actual = ToMapSerializer.toJson(object);
        try {
            assertEquals(expected, actual);
        } catch (Throwable e) {
            actual = actual.replaceAll("\"", "\\\\\"");
            actual = actual.replaceAll("\r", "\\\\r\" + //\r\"");
            System.out.print("public static final String CONST_");
            System.out.print(c);
            System.out.print(" = \"");
            System.out.print(actual);
            System.out.println("\";");
            throw e;
        }
    }

    public static final String OBJECT_01 = "{\r\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter\",\r\"int1\":\"11\"\r}";
    public static final String OBJECT_02 = "{\r\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter\",\r\"int1\":\"12\",\r\"object\":\"REF_1\"\r}";
    public static final String OBJECT_03 = "{\r\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter\",\r\"int1\":\"13\",\r\"object\":\"REF_1\",\r\"REF_1.int1\":\"14\"\r}";
    public static final String OBJECT_04 = "{\r\"class\":\"br.com.deliverytracker.commom.test.PojoHierarquical\",\r" + "\"int1\":\"13\",\r" + "\"object\":\"REF_1\",\r" + "\"REF_1.int1\":\"14\",\r" + "\"REF_1.object\":\"REF_2\",\r" + "\"REF_2.int1\":\"15\",\r\"REF_2.object\":\"REF_3\",\r\"REF_3.int1\":\"16\"\r}";
    public static final String OBJECT_05 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoCrossRef\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object1\":\"REF_1\",\r" + //
                                           "\"REF_1.int1\":\"14\",\r" + //
                                           "\"object2\":\"REF_1\"\r" + //
                                           "}";
    public static final String OBJECT_06 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoHierarquical\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object\":\"REF_1\",\r" + //
                                           "\"REF_1.int1\":\"14\",\r" + //
                                           "\"REF_1.object\":\"REF_2\",\r" + //
                                           "\"REF_2.int1\":\"15\"\r" + //
                                           "}";
    public static final String OBJECT_07 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByteArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_08 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByteArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_09 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByteArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_10 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByteArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_11 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByteArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_12 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByteArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_13 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShortArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_14 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShortArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_15 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShortArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_16 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShortArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_17 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShortArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_18 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShortArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_19 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveIntArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_20 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveIntArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_21 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveIntArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_22 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoIntegerArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_23 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoIntegerArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_24 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoIntegerArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_25 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLongArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_26 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLongArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_27 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLongArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_28 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLongArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_29 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLongArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_30 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLongArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1,-1\"\r" + //
                                           "}";
    public static final String OBJECT_31 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloatArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_32 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloatArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_33 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloatArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"3.4028235E38,-1.4E-45\"\r" + //
                                           "}";
    public static final String OBJECT_34 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoFloatArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_35 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoFloatArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_36 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoFloatArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"3.4028235E38,-1.4E-45\"\r" + //
                                           "}";
    public static final String OBJECT_38 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDoubleArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_39 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDoubleArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"0,0\"\r" + //
                                           "}";
    public static final String OBJECT_40 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDoubleArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1.7976931348623157E308,-4.9E-324\"\r" + //
                                           "}";
    public static final String OBJECT_41 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoDoubleArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_42 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoDoubleArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N,N\"\r" + //
                                           "}";
    public static final String OBJECT_43 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoDoubleArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"1.7976931348623157E308,-4.9E-324\"\r" + //
                                           "}";
    public static final String OBJECT_44 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_45 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"\\,\"\r" + //
                                           "}";
    public static final String OBJECT_46 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                           "\"int1\":\"13\",\r\"arrayData\":\"A,N,A A,\\,B\\,b\\,N\\,,\\,,\\\\,a,A\"A,A\rA\"\r" + //
                                           "}";
    public static final String OBJECT_47 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    public static final String OBJECT_48 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"REF_2\",\r" + //
                                           "\"REF_2.int1\":\"11\"\r" + //
                                           "}";
    public static final String OBJECT_49 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"REF_2\",\r" + //
                                           "\"REF_1.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner\",\r" + //
                                           "\"REF_2.int1\":\"11\"\r" + //
                                           "}";
    public static final String OBJECT_50 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"REF_2\",\r" + //
                                           "\"REF_2.int1\":\"11\"\r" + //
                                           "}";
    public static final String OBJECT_51 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N\"\r" + //
                                           "}";
    public static final String OBJECT_52 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object\":\"REF_1\",\r" + //
                                           "\"REF_1\":\"N\"\r" + //
                                           "}";
    public static final String OBJECT_53 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object\":\"REF_1\",\r" + //
                                           "\"object.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner\"\r" + //
                                           "}";
    public static final String OBJECT_54 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"objectclass\":\"java.lang.Integer\",\r" + //
                                           "\"object\":\"1\"\r" + //
                                           "}";

    public static final String CONST_1 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoString\"\r" + //
                                         "}";
    public static final String CONST_2 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                         "\"st1\":\"A\"\r" + //
                                         "}";
    public static final String CONST_3 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                         "\"st1\":\"A\"\"A\"\r" + //
                                         "}";
    public static final String CONST_4 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                         "\"st1\":\"A\"\"B\"\"C\"\r" + //
                                         "}";
    public static final String CONST_5 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoStrings\",\r" + //
                                         "\"st1\":\"A\"\r" + //
                                         "}";
    public static final String CONST_6 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoStrings\",\r" + //
                                         "\"st1\":\"A\",\r" + //
                                         "\"st2\":\"B\"\r" + //
                                         "}";
    public static final String CONST_7 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveBoolean\"\r" + //
                                         "}";
    public static final String CONST_8 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveBoolean\",\r" + //
                                         "\"bool1\":\"1\"\r" + //
                                         "}";
    public static final String CONST_9 = "{\r" + //
                                         "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\"\r" + //
                                         "}";
    public static final String CONST_10 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\",\r" + //
                                          "\"bool1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_11 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\",\r" + //
                                          "\"bool1\":\"0\"\r" + //
                                          "}";
    public static final String CONST_12 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\"\r" + //
                                          "}";
    public static final String CONST_13 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                          "\"byte1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_14 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                          "\"byte1\":\"-128\"\r" + //
                                          "}";
    public static final String CONST_15 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                          "\"byte1\":\"127\"\r" + //
                                          "}";
    public static final String CONST_16 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\"\r" + //
                                          "}";
    public static final String CONST_17 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                          "\"byte1\":\"0\"\r" + //
                                          "}";
    public static final String CONST_18 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                          "\"byte1\":\"-128\"\r" + //
                                          "}";
    public static final String CONST_19 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                          "\"byte1\":\"127\"\r" + //
                                          "}";
    public static final String CONST_20 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\"\r" + //
                                          "}";
    public static final String CONST_21 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                          "\"short1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_22 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                          "\"short1\":\"-32768\"\r" + //
                                          "}";
    public static final String CONST_23 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                          "\"short1\":\"32767\"\r" + //
                                          "}";
    public static final String CONST_24 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\"\r" + //
                                          "}";
    public static final String CONST_25 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                          "\"short1\":\"0\"\r" + //
                                          "}";
    public static final String CONST_26 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                          "\"short1\":\"-32768\"\r" + //
                                          "}";
    public static final String CONST_27 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                          "\"short1\":\"32767\"\r" + //
                                          "}";
    public static final String CONST_28 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\"\r" + //
                                          "}";
    public static final String CONST_29 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                          "\"int1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_30 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                          "\"int1\":\"-2147483648\"\r" + //
                                          "}";
    public static final String CONST_31 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                          "\"int1\":\"2147483647\"\r" + //
                                          "}";
    public static final String CONST_32 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\"\r" + //
                                          "}";
    public static final String CONST_33 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                          "\"int1\":\"0\"\r" + //
                                          "}";
    public static final String CONST_34 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                          "\"int1\":\"-2147483648\"\r" + //
                                          "}";
    public static final String CONST_35 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                          "\"int1\":\"2147483647\"\r" + //
                                          "}";
    public static final String CONST_36 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\"\r" + //
                                          "}";
    public static final String CONST_37 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                          "\"long1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_38 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                          "\"long1\":\"-9223372036854775808\"\r" + //
                                          "}";
    public static final String CONST_39 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                          "\"long1\":\"9223372036854775807\"\r" + //
                                          "}";
    public static final String CONST_40 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\"\r" + //
                                          "}";
    public static final String CONST_41 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                          "\"long1\":\"0\"\r" + //
                                          "}";
    public static final String CONST_42 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                          "\"long1\":\"-9223372036854775808\"\r" + //
                                          "}";
    public static final String CONST_43 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                          "\"long1\":\"9223372036854775807\"\r" + //
                                          "}";
    public static final String CONST_44 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\"\r" + //
                                          "}";
    public static final String CONST_45 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_46 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.4E-45\"\r" + //
                                          "}";
    public static final String CONST_47 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.17549435E-38\"\r" + //
                                          "}";
    public static final String CONST_48 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"3.4028235E38\"\r" + //
                                          "}";
    public static final String CONST_49 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.234567\"\r" + //
                                          "}";
    public static final String CONST_50 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.2345678\"\r" + //
                                          "}";
    public static final String CONST_51 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.2345679\"\r" + //
                                          "}";
    public static final String CONST_52 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"0.001\"\r" + //
                                          "}";
    public static final String CONST_53 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.0E-4\"\r" + //
                                          "}";
    public static final String CONST_54 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.2345678E-9\"\r" + //
                                          "}";
    public static final String CONST_55 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1234567\"\r" + //
                                          "}";
    public static final String CONST_56 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.2345678E7\"\r" + //
                                          "}";
    public static final String CONST_57 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.23456792E8\"\r" + //
                                          "}";
    public static final String CONST_58 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1000\"\r" + //
                                          "}";
    public static final String CONST_59 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"10000\"\r" + //
                                          "}";
    public static final String CONST_60 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"1.23456784E16\"\r" + //
                                          "}";
    public static final String CONST_61 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1\"\r" + //
                                          "}";
    public static final String CONST_62 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.4E-45\"\r" + //
                                          "}";
    public static final String CONST_63 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.17549435E-38\"\r" + //
                                          "}";
    public static final String CONST_64 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-3.4028235E38\"\r" + //
                                          "}";
    public static final String CONST_65 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.234567\"\r" + //
                                          "}";
    public static final String CONST_66 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.2345678\"\r" + //
                                          "}";
    public static final String CONST_67 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.2345679\"\r" + //
                                          "}";
    public static final String CONST_68 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-0.001\"\r" + //
                                          "}";
    public static final String CONST_69 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.0E-4\"\r" + //
                                          "}";
    public static final String CONST_70 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.2345678E-9\"\r" + //
                                          "}";
    public static final String CONST_71 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1234567\"\r" + //
                                          "}";
    public static final String CONST_72 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.2345678E7\"\r" + //
                                          "}";
    public static final String CONST_73 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.23456792E8\"\r" + //
                                          "}";
    public static final String CONST_74 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1000\"\r" + //
                                          "}";
    public static final String CONST_75 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-10000\"\r" + //
                                          "}";
    public static final String CONST_76 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                          "\"float1\":\"-1.23456784E16\"\r" + //
                                          "}";
    public static final String CONST_77 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\"\r" + //
                                          "}";
    public static final String CONST_78 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1\"\r" + //
                                          "}";
    public static final String CONST_79 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"4.9E-324\"\r" + //
                                          "}";
    public static final String CONST_80 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"2.2250738585072014E-308\"\r" + //
                                          "}";
    public static final String CONST_81 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.7976931348623157E308\"\r" + //
                                          "}";
    public static final String CONST_82 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.2345678901234567\"\r" + //
                                          "}";
    public static final String CONST_83 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.2345678901234567\"\r" + //
                                          "}";
    public static final String CONST_84 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.234567890123457\"\r" + //
                                          "}";
    public static final String CONST_85 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"0.001\"\r" + //
                                          "}";
    public static final String CONST_86 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.0E-4\"\r" + //
                                          "}";
    public static final String CONST_87 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.2345678901234567E-17\"\r" + //
                                          "}";
    public static final String CONST_88 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1234567\"\r" + //
                                          "}";
    public static final String CONST_89 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.234567890123456E15\"\r" + //
                                          "}";
    public static final String CONST_90 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.2345678901234568E17\"\r" + //
                                          "}";
    public static final String CONST_91 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1000000\"\r" + //
                                          "}";
    public static final String CONST_92 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.0E7\"\r" + //
                                          "}";
    public static final String CONST_93 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"1.234567890123456E32\"\r" + //
                                          "}";
    public static final String CONST_94 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-1\"\r" + //
                                          "}";
    public static final String CONST_95 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-4.9E-324\"\r" + //
                                          "}";
    public static final String CONST_96 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-2.2250738585072014E-308\"\r" + //
                                          "}";
    public static final String CONST_97 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-1.7976931348623157E308\"\r" + //
                                          "}";
    public static final String CONST_98 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-1.2345678901234567\"\r" + //
                                          "}";
    public static final String CONST_99 = "{\r" + //
                                          "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                          "\"double1\":\"-1.2345678901234567\"\r" + //
                                          "}";
    public static final String CONST_100 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.234567890123457\"\r" + //
                                           "}";
    public static final String CONST_101 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-0.001\"\r" + //
                                           "}";
    public static final String CONST_102 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.0E-4\"\r" + //
                                           "}";
    public static final String CONST_103 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.2345678901234567E-17\"\r" + //
                                           "}";
    public static final String CONST_104 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1234567\"\r" + //
                                           "}";
    public static final String CONST_105 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.234567890123456E15\"\r" + //
                                           "}";
    public static final String CONST_106 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.2345678901234568E17\"\r" + //
                                           "}";
    public static final String CONST_107 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1000000\"\r" + //
                                           "}";
    public static final String CONST_108 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.0E7\"\r" + //
                                           "}";
    public static final String CONST_109 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.234567890123456E32\"\r" + //
                                           "}";
    public static final String CONST_110 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoFloat\"\r" + //
                                           "}";
    public static final String CONST_111 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoFloat\",\r" + //
                                           "\"float1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_112 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoDouble\"\r" + //
                                           "}";
    public static final String CONST_113 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoDouble\",\r" + //
                                           "\"double1\":\"1\"\r" + //
                                           "}";

}
