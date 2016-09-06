package br.com.deliverytracker.commom.test;

import static org.junit.Assert.assertEquals;

import br.com.deliverytracker.commom.ToMapSerializer;

public class SerializeTestUtils {

    private static int c = 0;

    public static void reset() {
        c = 0;
    }

    public static void nextCase() {
        c++;
    }

    public static void doSerializeTest(String expected, Object object) {
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

    public static void doUnserializeTest(String tested) {
        try {
            Object actual = ToMapSerializer.fromJson(tested);
            assertEquals(tested, ToMapSerializer.toJson(actual));
        } catch (Throwable e) {
            System.out.println(tested);
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
                                           "\"REF_2.int1\":\"15\",\r" + //
                                           "\"REF_2.object\":\"ROOT\"\r" + //
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
    public static final String OBJECT_45A = "{\r" + //
                                            "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                            "\"int1\":\"13\",\r" + //
                                            "\"arrayData.b64\":\"LA==\"\r" + //
                                            "}";
    public static final String OBJECT_45B = "{\r" + //
                                            "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                            "\"int1\":\"13\",\r" + //
                                            "\"arrayData.b64\":\"Tg==\"\r" + //
                                            "}";
    public static final String OBJECT_46 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStringArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData.b64\":\"QQ==,N,QSBB,LEIsYixOLA==,LA==,XCxh,QSJB,QQ1B\"\r" + //
                                           "}";
    public static final String OBJECT_47 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\"\r" + //
                                           "}";
    /**
     * array do tipo igual ao campo e elementos de tipo diferente do array
     */
    public static final String OBJECT_48 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_2.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner\",\r" + //
                                           "\"REF_2.int1\":\"11\",\r" + //
                                           "\"REF_3.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner\",\r" + //
                                           "\"REF_1\":\"REF_2,REF_3\"\r" + //
                                           "}";
    /**
     * array do tipo do diferente do campo, e elementos de tipo iguais ao do array
     */
    public static final String OBJECT_49 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_1.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner[]\",\r" + //
                                           "\"REF_2.int1\":\"11\",\r" + //
                                           "\"REF_1\":\"REF_2\"\r" + //
                                           "}";
    public static final String OBJECT_50 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoObjectArray2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"arrayData\":\"REF_1\",\r" + //
                                           "\"REF_2.int1\":\"11\",\r" + //
                                           "\"REF_1\":\"REF_2\"\r" + //
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
                                           "\"REF_1.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner[]\",\r" + //
                                           "\"REF_1\":\"N\"\r" + //
                                           "}";
    public static final String OBJECT_53 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object\":\"REF_1\",\r" + //
                                           "\"REF_1.class\":\"br.com.deliverytracker.commom.test.PojoSimpleInner\"\r" + //
                                           "}";
    public static final String OBJECT_54 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoSimpleOuter2\",\r" + //
                                           "\"int1\":\"13\",\r" + //
                                           "\"object\":\"REF_1\",\r" + //
                                           "\"REF_1.class\":\"java.lang.Integer\",\r" + //
                                           "\"REF_1\":\"1\"\r" + //
                                           "}";

    public static final String CONST_001 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoString\"\r" + //
                                           "}";
    public static final String CONST_002 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                           "\"st1\":\"A\"\r" + //
                                           "}";
    public static final String CONST_003 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                           "\"st1\":\"A\\\"A\"\r" + //
                                           "}";
    public static final String CONST_004 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoString\",\r" + //
                                           "\"st1\":\"A\\\"B\\\"\\\"C\"\r" + //
                                           "}";
    public static final String CONST_005 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStrings\",\r" + //
                                           "\"st1\":\"A\"\r" + //
                                           "}";
    public static final String CONST_006 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoStrings\",\r" + //
                                           "\"st1\":\"A\",\r" + //
                                           "\"st2\":\"B\"\r" + //
                                           "}";
    public static final String CONST_007 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveBoolean\"\r" + //
                                           "}";
    public static final String CONST_008 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveBoolean\",\r" + //
                                           "\"bool1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_009 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\"\r" + //
                                           "}";
    public static final String CONST_010 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\",\r" + //
                                           "\"bool1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_011 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoBoolean\",\r" + //
                                           "\"bool1\":\"0\"\r" + //
                                           "}";
    public static final String CONST_012 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\"\r" + //
                                           "}";
    public static final String CONST_013 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                           "\"byte1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_014 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                           "\"byte1\":\"-128\"\r" + //
                                           "}";
    public static final String CONST_015 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveByte\",\r" + //
                                           "\"byte1\":\"127\"\r" + //
                                           "}";
    public static final String CONST_016 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\"\r" + //
                                           "}";
    public static final String CONST_017 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                           "\"byte1\":\"0\"\r" + //
                                           "}";
    public static final String CONST_018 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                           "\"byte1\":\"-128\"\r" + //
                                           "}";
    public static final String CONST_019 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoByte\",\r" + //
                                           "\"byte1\":\"127\"\r" + //
                                           "}";
    public static final String CONST_020 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\"\r" + //
                                           "}";
    public static final String CONST_021 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                           "\"short1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_022 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                           "\"short1\":\"-32768\"\r" + //
                                           "}";
    public static final String CONST_023 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveShort\",\r" + //
                                           "\"short1\":\"32767\"\r" + //
                                           "}";
    public static final String CONST_024 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\"\r" + //
                                           "}";
    public static final String CONST_025 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                           "\"short1\":\"0\"\r" + //
                                           "}";
    public static final String CONST_026 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                           "\"short1\":\"-32768\"\r" + //
                                           "}";
    public static final String CONST_027 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoShort\",\r" + //
                                           "\"short1\":\"32767\"\r" + //
                                           "}";
    public static final String CONST_028 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\"\r" + //
                                           "}";
    public static final String CONST_029 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                           "\"int1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_030 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                           "\"int1\":\"-2147483648\"\r" + //
                                           "}";
    public static final String CONST_031 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveInt\",\r" + //
                                           "\"int1\":\"2147483647\"\r" + //
                                           "}";
    public static final String CONST_032 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\"\r" + //
                                           "}";
    public static final String CONST_033 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                           "\"int1\":\"0\"\r" + //
                                           "}";
    public static final String CONST_034 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                           "\"int1\":\"-2147483648\"\r" + //
                                           "}";
    public static final String CONST_035 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoInteger\",\r" + //
                                           "\"int1\":\"2147483647\"\r" + //
                                           "}";
    public static final String CONST_036 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\"\r" + //
                                           "}";
    public static final String CONST_037 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                           "\"long1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_038 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                           "\"long1\":\"-9223372036854775808\"\r" + //
                                           "}";
    public static final String CONST_039 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveLong\",\r" + //
                                           "\"long1\":\"9223372036854775807\"\r" + //
                                           "}";
    public static final String CONST_040 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\"\r" + //
                                           "}";
    public static final String CONST_041 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                           "\"long1\":\"0\"\r" + //
                                           "}";
    public static final String CONST_042 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                           "\"long1\":\"-9223372036854775808\"\r" + //
                                           "}";
    public static final String CONST_043 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoLong\",\r" + //
                                           "\"long1\":\"9223372036854775807\"\r" + //
                                           "}";
    public static final String CONST_044 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\"\r" + //
                                           "}";
    public static final String CONST_045 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_046 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.4E-45\"\r" + //
                                           "}";
    public static final String CONST_047 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.17549435E-38\"\r" + //
                                           "}";
    public static final String CONST_048 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"3.4028235E38\"\r" + //
                                           "}";
    public static final String CONST_049 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.234567\"\r" + //
                                           "}";
    public static final String CONST_050 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.2345678\"\r" + //
                                           "}";
    public static final String CONST_051 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.2345679\"\r" + //
                                           "}";
    public static final String CONST_052 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"0.001\"\r" + //
                                           "}";
    public static final String CONST_053 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.0E-4\"\r" + //
                                           "}";
    public static final String CONST_054 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.2345678E-9\"\r" + //
                                           "}";
    public static final String CONST_055 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1234567\"\r" + //
                                           "}";
    public static final String CONST_056 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.2345678E7\"\r" + //
                                           "}";
    public static final String CONST_057 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.23456792E8\"\r" + //
                                           "}";
    public static final String CONST_058 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1000\"\r" + //
                                           "}";
    public static final String CONST_059 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"10000\"\r" + //
                                           "}";
    public static final String CONST_060 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"1.23456784E16\"\r" + //
                                           "}";
    public static final String CONST_061 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1\"\r" + //
                                           "}";
    public static final String CONST_062 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.4E-45\"\r" + //
                                           "}";
    public static final String CONST_063 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.17549435E-38\"\r" + //
                                           "}";
    public static final String CONST_064 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-3.4028235E38\"\r" + //
                                           "}";
    public static final String CONST_065 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.234567\"\r" + //
                                           "}";
    public static final String CONST_066 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.2345678\"\r" + //
                                           "}";
    public static final String CONST_067 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.2345679\"\r" + //
                                           "}";
    public static final String CONST_068 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-0.001\"\r" + //
                                           "}";
    public static final String CONST_069 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.0E-4\"\r" + //
                                           "}";
    public static final String CONST_070 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.2345678E-9\"\r" + //
                                           "}";
    public static final String CONST_071 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1234567\"\r" + //
                                           "}";
    public static final String CONST_072 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.2345678E7\"\r" + //
                                           "}";
    public static final String CONST_073 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.23456792E8\"\r" + //
                                           "}";
    public static final String CONST_074 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1000\"\r" + //
                                           "}";
    public static final String CONST_075 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-10000\"\r" + //
                                           "}";
    public static final String CONST_076 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveFloat\",\r" + //
                                           "\"float1\":\"-1.23456784E16\"\r" + //
                                           "}";
    public static final String CONST_077 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\"\r" + //
                                           "}";
    public static final String CONST_078 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1\"\r" + //
                                           "}";
    public static final String CONST_079 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"4.9E-324\"\r" + //
                                           "}";
    public static final String CONST_080 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"2.2250738585072014E-308\"\r" + //
                                           "}";
    public static final String CONST_081 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.7976931348623157E308\"\r" + //
                                           "}";
    public static final String CONST_082 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.2345678901234567\"\r" + //
                                           "}";
    public static final String CONST_083 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.2345678901234567\"\r" + //
                                           "}";
    public static final String CONST_084 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.234567890123457\"\r" + //
                                           "}";
    public static final String CONST_085 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"0.001\"\r" + //
                                           "}";
    public static final String CONST_086 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.0E-4\"\r" + //
                                           "}";
    public static final String CONST_087 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.2345678901234567E-17\"\r" + //
                                           "}";
    public static final String CONST_088 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1234567\"\r" + //
                                           "}";
    public static final String CONST_089 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.234567890123456E15\"\r" + //
                                           "}";
    public static final String CONST_090 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.2345678901234568E17\"\r" + //
                                           "}";
    public static final String CONST_091 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1000000\"\r" + //
                                           "}";
    public static final String CONST_092 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.0E7\"\r" + //
                                           "}";
    public static final String CONST_093 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"1.234567890123456E32\"\r" + //
                                           "}";
    public static final String CONST_094 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1\"\r" + //
                                           "}";
    public static final String CONST_095 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-4.9E-324\"\r" + //
                                           "}";
    public static final String CONST_096 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-2.2250738585072014E-308\"\r" + //
                                           "}";
    public static final String CONST_097 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.7976931348623157E308\"\r" + //
                                           "}";
    public static final String CONST_098 = "{\r" + //
                                           "\"class\":\"br.com.deliverytracker.commom.test.PojoPrimitiveDouble\",\r" + //
                                           "\"double1\":\"-1.2345678901234567\"\r" + //
                                           "}";
    public static final String CONST_099 = "{\r" + //
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
