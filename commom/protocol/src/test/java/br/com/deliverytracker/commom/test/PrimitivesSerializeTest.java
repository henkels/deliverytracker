package br.com.deliverytracker.commom.test;

import org.junit.Test;

public class PrimitivesSerializeTest {

    private void doTest(String expected, Object object) {
        SerializeTestUtils.doSerializeTest(expected, object);
    }

    /**
     * Empty Object
     */
    @Test
    public void test001() {
        doTest(SerializeTestUtils.CONST_001, new PojoString());
    }

    /**
     * Simple String
     */
    @Test
    public void test002() {
        PojoString object = new PojoString();
        object.st1 = "A";
        doTest(SerializeTestUtils.CONST_002, object);
    }

    /**
     * String that contains "
     */
    @Test
    public void test003() {
        PojoString object = new PojoString();
        object.st1 = "A\"A";
        doTest(SerializeTestUtils.CONST_003, object);
    }

    /**
     * String that contains 2 "
     */
    @Test
    public void test004() {
        PojoString object = new PojoString();
        object.st1 = "A\"B\"C";
        doTest(SerializeTestUtils.CONST_004, object);
    }

    @Test
    public void test005() {
        PojoStrings object = new PojoStrings();
        object.st1 = "A";
        doTest(SerializeTestUtils.CONST_005, object);
    }

    @Test
    public void test006() {
        PojoStrings object = new PojoStrings();
        object.st1 = "A";
        object.st2 = "B";
        doTest(SerializeTestUtils.CONST_006, object);
    }

    @Test
    public void test007() {
        PojoPrimitiveBoolean object = new PojoPrimitiveBoolean();
        doTest(SerializeTestUtils.CONST_007, object);
    }

    @Test
    public void test008() {
        PojoPrimitiveBoolean object = new PojoPrimitiveBoolean();
        object.bool1 = true;
        doTest(SerializeTestUtils.CONST_008, object);
    }

    @Test
    public void test009() {
        PojoBoolean object = new PojoBoolean();
        doTest(SerializeTestUtils.CONST_009, object);
    }

    @Test
    public void test010() {
        PojoBoolean object = new PojoBoolean();
        object.bool1 = true;
        doTest(SerializeTestUtils.CONST_010, object);
    }

    @Test
    public void test011() {
        PojoBoolean object = new PojoBoolean();
        object.bool1 = false;
        doTest(SerializeTestUtils.CONST_011, object);
    }

    @Test
    public void test012() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        doTest(SerializeTestUtils.CONST_012, object);
    }

    @Test
    public void test013() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = 1;
        doTest(SerializeTestUtils.CONST_013, object);
    }

    @Test
    public void test014() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_014, object);
    }

    @Test
    public void test015() {
        PojoPrimitiveByte object = new PojoPrimitiveByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_015, object);
    }

    @Test
    public void test016() {
        PojoByte object = new PojoByte();
        doTest(SerializeTestUtils.CONST_016, object);
    }

    @Test
    public void test017() {
        PojoByte object = new PojoByte();
        object.byte1 = 0;
        doTest(SerializeTestUtils.CONST_017, object);
    }

    @Test
    public void test018() {
        PojoByte object = new PojoByte();
        object.byte1 = Byte.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_018, object);
    }

    @Test
    public void test019() {
        PojoByte object = new PojoByte();
        object.byte1 = Byte.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_019, object);
    }

    @Test
    public void test020() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        doTest(SerializeTestUtils.CONST_020, object);
    }

    @Test
    public void test021() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = 1;
        doTest(SerializeTestUtils.CONST_021, object);
    }

    @Test
    public void test022() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_022, object);
    }

    @Test
    public void test023() {
        PojoPrimitiveShort object = new PojoPrimitiveShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_023, object);
    }

    @Test
    public void test024() {
        PojoShort object = new PojoShort();
        doTest(SerializeTestUtils.CONST_024, object);
    }

    @Test
    public void test025() {
        PojoShort object = new PojoShort();
        object.short1 = 0;
        doTest(SerializeTestUtils.CONST_025, object);
    }

    @Test
    public void test026() {
        PojoShort object = new PojoShort();
        object.short1 = Short.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_026, object);
    }

    @Test
    public void test027() {
        PojoShort object = new PojoShort();
        object.short1 = Short.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_027, object);
    }

    @Test
    public void test028() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        doTest(SerializeTestUtils.CONST_028, object);
    }

    @Test
    public void test029() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = 1;
        doTest(SerializeTestUtils.CONST_029, object);
    }

    @Test
    public void test030() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = Integer.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_030, object);
    }

    @Test
    public void test031() {
        PojoPrimitiveInt object = new PojoPrimitiveInt();
        object.int1 = Integer.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_031, object);
    }

    @Test
    public void test032() {
        PojoInteger object = new PojoInteger();
        doTest(SerializeTestUtils.CONST_032, object);
    }

    @Test
    public void test033() {
        PojoInteger object = new PojoInteger();
        object.int1 = 0;
        doTest(SerializeTestUtils.CONST_033, object);
    }

    @Test
    public void test034() {
        PojoInteger object = new PojoInteger();
        object.int1 = Integer.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_034, object);
    }

    @Test
    public void test035() {
        PojoInteger object = new PojoInteger();
        object.int1 = Integer.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_035, object);
    }

    @Test
    public void test036() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        doTest(SerializeTestUtils.CONST_036, object);
    }

    @Test
    public void test037() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = 1;
        doTest(SerializeTestUtils.CONST_037, object);
    }

    @Test
    public void test038() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = Long.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_038, object);
    }

    @Test
    public void test039() {
        PojoPrimitiveLong object = new PojoPrimitiveLong();
        object.long1 = Long.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_039, object);
    }

    @Test
    public void test040() {
        PojoLong object = new PojoLong();
        doTest(SerializeTestUtils.CONST_040, object);
    }

    @Test
    public void test041() {
        PojoLong object = new PojoLong();
        object.long1 = 0L;
        doTest(SerializeTestUtils.CONST_041, object);
    }

    @Test
    public void test042() {
        PojoLong object = new PojoLong();
        object.long1 = Long.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_042, object);
    }

    @Test
    public void test043() {
        PojoLong object = new PojoLong();
        object.long1 = Long.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_043, object);
    }

    @Test
    public void test044() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        doTest(SerializeTestUtils.CONST_044, object);
    }

    @Test
    public void test045() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1;
        doTest(SerializeTestUtils.CONST_045, object);
    }

    @Test
    public void test046() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_046, object);
    }

    @Test
    public void test047() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_NORMAL;
        doTest(SerializeTestUtils.CONST_047, object);
    }

    @Test
    public void test048() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_048, object);
    }

    @Test
    public void test049() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.234567F;
        doTest(SerializeTestUtils.CONST_049, object);
    }

    @Test
    public void test050() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.2345678F;
        doTest(SerializeTestUtils.CONST_050, object);
    }

    @Test
    public void test051() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.23456789F;
        doTest(SerializeTestUtils.CONST_051, object);
    }

    @Test
    public void test052() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.001F;
        doTest(SerializeTestUtils.CONST_052, object);
    }

    @Test
    public void test053() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0001F;
        doTest(SerializeTestUtils.CONST_053, object);
    }

    @Test
    public void test054() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0000000012345678F;
        doTest(SerializeTestUtils.CONST_054, object);
    }

    @Test
    public void test055() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1234567F;
        doTest(SerializeTestUtils.CONST_055, object);
    }

    @Test
    public void test056() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678F;
        doTest(SerializeTestUtils.CONST_056, object);
    }

    @Test
    public void test057() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 123456789F;
        doTest(SerializeTestUtils.CONST_057, object);
    }

    @Test
    public void test058() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1000F;
        doTest(SerializeTestUtils.CONST_058, object);
    }

    @Test
    public void test059() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 10000F;
        doTest(SerializeTestUtils.CONST_059, object);
    }

    @Test
    public void test060() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678000000000F;
        doTest(SerializeTestUtils.CONST_060, object);
    }

    @Test
    public void test061() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = -1;
        doTest(SerializeTestUtils.CONST_061, object);
    }

    @Test
    public void test062() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_VALUE * -1f;
        doTest(SerializeTestUtils.CONST_062, object);
    }

    @Test
    public void test063() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MIN_NORMAL * -1f;
        doTest(SerializeTestUtils.CONST_063, object);
    }

    @Test
    public void test064() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = Float.MAX_VALUE * -1f;
        doTest(SerializeTestUtils.CONST_064, object);
    }

    @Test
    public void test065() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.234567F * -1F;
        doTest(SerializeTestUtils.CONST_065, object);
    }

    @Test
    public void test066() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.2345678F * -1f;
        doTest(SerializeTestUtils.CONST_066, object);
    }

    @Test
    public void test067() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1.23456789F * -1f;
        doTest(SerializeTestUtils.CONST_067, object);
    }

    @Test
    public void test068() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.001F * -1f;
        doTest(SerializeTestUtils.CONST_068, object);
    }

    @Test
    public void test069() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0001F * -1f;
        doTest(SerializeTestUtils.CONST_069, object);
    }

    @Test
    public void test070() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 0.0000000012345678F * -1f;
        doTest(SerializeTestUtils.CONST_070, object);
    }

    @Test
    public void test071() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1234567F * -1f;
        doTest(SerializeTestUtils.CONST_071, object);
    }

    @Test
    public void test072() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678F * -1f;
        doTest(SerializeTestUtils.CONST_072, object);
    }

    @Test
    public void test073() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 123456789F * -1f;
        doTest(SerializeTestUtils.CONST_073, object);
    }

    @Test
    public void test074() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 1000F * -1f;
        doTest(SerializeTestUtils.CONST_074, object);
    }

    @Test
    public void test075() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 10000F * -1f;
        doTest(SerializeTestUtils.CONST_075, object);
    }

    @Test
    public void test076() {
        PojoPrimitiveFloat object = new PojoPrimitiveFloat();
        object.float1 = 12345678000000000F * -1f;
        doTest(SerializeTestUtils.CONST_076, object);
    }

    @Test
    public void test077() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        doTest(SerializeTestUtils.CONST_077, object);
    }

    @Test
    public void test078() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1;
        doTest(SerializeTestUtils.CONST_078, object);
    }

    @Test
    public void test079() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_VALUE;
        doTest(SerializeTestUtils.CONST_079, object);
    }

    @Test
    public void test080() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_NORMAL;
        doTest(SerializeTestUtils.CONST_080, object);
    }

    @Test
    public void test081() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MAX_VALUE;
        doTest(SerializeTestUtils.CONST_081, object);
    }

    @Test
    public void test082() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234567D;
        doTest(SerializeTestUtils.CONST_082, object);
    }

    @Test
    public void test083() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.23456789012345678D;
        doTest(SerializeTestUtils.CONST_083, object);
    }

    @Test
    public void test084() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234569D;
        doTest(SerializeTestUtils.CONST_084, object);
    }

    @Test
    public void test085() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.001D;
        doTest(SerializeTestUtils.CONST_085, object);
    }

    @Test
    public void test086() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.0001D;
        doTest(SerializeTestUtils.CONST_086, object);
    }

    @Test
    public void test087() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.000000000000000012345678901234567D;
        doTest(SerializeTestUtils.CONST_087, object);
    }

    @Test
    public void test088() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567D;
        doTest(SerializeTestUtils.CONST_088, object);
    }

    @Test
    public void test089() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567890123456D;
        doTest(SerializeTestUtils.CONST_089, object);
    }

    @Test
    public void test090() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345678D;
        doTest(SerializeTestUtils.CONST_090, object);
    }

    @Test
    public void test091() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1000000D;
        doTest(SerializeTestUtils.CONST_091, object);
    }

    @Test
    public void test092() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 10000000D;
        doTest(SerializeTestUtils.CONST_092, object);
    }

    @Test
    public void test093() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345600000000000000000D;
        doTest(SerializeTestUtils.CONST_093, object);
    }

    @Test
    public void test094() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1 * -1d;
        doTest(SerializeTestUtils.CONST_094, object);
    }

    @Test
    public void test095() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_VALUE * -1d;
        doTest(SerializeTestUtils.CONST_095, object);
    }

    @Test
    public void test096() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MIN_NORMAL * -1d;
        doTest(SerializeTestUtils.CONST_096, object);
    }

    @Test
    public void test097() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = Double.MAX_VALUE * -1d;
        doTest(SerializeTestUtils.CONST_097, object);
    }

    @Test
    public void test098() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234567D * -1d;
        doTest(SerializeTestUtils.CONST_098, object);
    }

    @Test
    public void test099() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.23456789012345678D * -1d;
        doTest(SerializeTestUtils.CONST_099, object);
    }

    @Test
    public void test100() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1.2345678901234569D * -1d;
        doTest(SerializeTestUtils.CONST_100, object);
    }

    @Test
    public void test101() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.001D * -1d;
        doTest(SerializeTestUtils.CONST_101, object);
    }

    @Test
    public void test102() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.0001D * -1d;
        doTest(SerializeTestUtils.CONST_102, object);
    }

    @Test
    public void test103() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 0.000000000000000012345678901234567D * -1d;
        doTest(SerializeTestUtils.CONST_103, object);
    }

    @Test
    public void test104() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567D * -1d;
        doTest(SerializeTestUtils.CONST_104, object);
    }

    @Test
    public void test105() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1234567890123456D * -1d;
        doTest(SerializeTestUtils.CONST_105, object);
    }

    @Test
    public void test106() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345678D * -1d;
        doTest(SerializeTestUtils.CONST_106, object);
    }

    @Test
    public void test107() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 1000000D * -1d;
        doTest(SerializeTestUtils.CONST_107, object);
    }

    @Test
    public void test108() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 10000000D * -1d;
        doTest(SerializeTestUtils.CONST_108, object);
    }

    @Test
    public void test109() {
        PojoPrimitiveDouble object = new PojoPrimitiveDouble();
        object.double1 = 123456789012345600000000000000000D * -1d;
        doTest(SerializeTestUtils.CONST_109, object);
    }
    
    @Test
    public void test110() {
        PojoFloat object = new PojoFloat();
        doTest(SerializeTestUtils.CONST_110, object);
    }
    
    @Test
    public void test111() {
        PojoFloat object = new PojoFloat();
        object.float1 = 1f;
        doTest(SerializeTestUtils.CONST_111, object);
    }
    
    @Test
    public void test112() {
        PojoDouble object = new PojoDouble();
        doTest(SerializeTestUtils.CONST_112 , object);
    }
    
    @Test
    public void test113() {
        PojoDouble object = new PojoDouble();
        object.double1 = 1d;
        doTest(SerializeTestUtils.CONST_113, object);
    }
}
