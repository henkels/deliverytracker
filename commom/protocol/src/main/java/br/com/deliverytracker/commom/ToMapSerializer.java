package br.com.deliverytracker.commom;

import java.awt.HeadlessException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class ToMapSerializer {

    private static final String OBJECT_CONTEXT_PREFIX = "REF_";

    private static final String CLASS_CONTEXT_ID = "class";

    private ToMapSerializer() {
    }

    private static class ObjCtxBuilder {

        private int currentNewId = -1;

        private Map<Object, Integer> ctxsMap = new HashMap<>();

        private StringBuilder innerBuildContext(Integer ctxId) {
            if (ctxId == 0) {
                return new StringBuilder();
            }
            StringBuilder ret = new StringBuilder(OBJECT_CONTEXT_PREFIX);
            ret.append(Integer.toString(ctxId));
            return ret;
        }

        private StringBuilder getExistingObjCtx(Object object) {
            Integer ctxId = ctxsMap.get(object);
            if (ctxId == null) {
                return null;
            }
            return innerBuildContext(ctxId);
        }

        private StringBuilder getNewObjCtx(Object object) {
            currentNewId++;
            Integer oldCtxId = ctxsMap.put(object, currentNewId);
            if (oldCtxId != null) {
                ctxsMap.put(object, oldCtxId);
                currentNewId--;
                throw new RuntimeException(String.format("The object %s already was contextualied!", object));
            }
            return innerBuildContext(currentNewId);
        }
    }

    private interface IMapSerializer {

        void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder);
    }

    private static class ObjectSerializer implements IMapSerializer {

        protected void serializeMainContextData(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
        }

        protected void serializeObjectContextData(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            int len = ctx.length();
            Field[] fields = object.getClass().getFields();
            for (Field field : fields) {
                ctx.setLength(len);
                ctx.append(field.getName());
                try {
                    Object value = field.get(object);
                    if (value != null) {
                        IMapSerializer serializer = getSerializer(field, value);
                        serializer.serializeTo(value, data, ctx, ctxBuilder);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            if (objCtx.length() != 0) {
                data.put(ctx.toString(), objCtx.toString());
                ctx.append('.');
                objCtx.append('.');
            }
            if (notSerializedYet) {
                serializeMainContextData(object, data, ctx, ctxBuilder);
                serializeObjectContextData(object, data, objCtx, ctxBuilder);
            }
        }
    }

    private static class ClassAndObjectSerializer extends ObjectSerializer {

        protected void serializeMainContextData(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            ctx.append(CLASS_CONTEXT_ID);
            Class<? extends Object> clazz = object.getClass();
            // Salve a classe em questão
            data.put(ctx.toString(), clazz.getCanonicalName());
            super.serializeMainContextData(object, data, ctx, ctxBuilder);
        }
    }

    private static class ObjectArraySerializer implements IMapSerializer {

        protected IMapSerializer getSerializer() {
            return OBJECT_SERIALIZER;
        }

        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            data.put(ctx.toString(), objCtx.toString());
            if (notSerializedYet) {
                int objCtxLen = objCtx.length();
                prepareSerializeTo(object, data, objCtx);
                Object[] aux = (Object[]) object;
                StringBuilder val = new StringBuilder();
                for (Object inner : aux) {
                    if (inner == null) {
                        val.append(ARRAY_NULL_FLAG);
                    } else {
                        getSerializer().serializeTo(inner, data, objCtx, ctxBuilder);
                        StringBuilder innerCtx = ctxBuilder.getExistingObjCtx(inner);
                        val.append(innerCtx.toString());
                    }
                    val.append(ARRAY_VALUE_SEPARATOR);
                }
                int valLen = val.length();
                if (valLen > 1) {
                    val.setLength(valLen - 1);
                }
                objCtx.setLength(objCtxLen);
                data.put(objCtx.toString(), val.toString());
            }
        }

        protected void prepareSerializeTo(Object object, Map<String, String> data, StringBuilder ctx) {
        }
    }

    private static class ClassAndObjectArraySerializer extends ObjectArraySerializer {

        protected IMapSerializer getSerializer() {
            return CLASS_OBJECT_SERIALIZER;
        }
    }

    private static class PrimitiveInObjectSerializer implements IMapSerializer {

        private final IMapSerializer primitiveSerializer;

        private PrimitiveInObjectSerializer(IMapSerializer primitiveSerializer) {
            this.primitiveSerializer = primitiveSerializer;
        }

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            int length = ctx.length();
            ctx.append(CLASS_CONTEXT_ID);
            data.put(ctx.toString(), object.getClass().getCanonicalName());
            ctx.setLength(length);
            primitiveSerializer.serializeTo(object, data, ctx, ctxBuilder);
        }
    }

    private static abstract class AbstractBasicSerializer implements IMapSerializer {

        protected void serializeTo(String value, Map<String, String> data, StringBuilder ctx) {
            data.put(ctx.toString(), value);
        }
    }

    private static final String BOOL_TRUE = "1";

    private static final String BOOL_FASE = "0";

    private static class BooleanPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Boolean b = (Boolean) value;
            if (b) {
                serializeTo(BOOL_TRUE, data, ctx);
            }
        }
    }

    private static class BooleanSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo((Boolean) value ? BOOL_TRUE : BOOL_FASE, data, ctx);
        }
    }

    private static class BytePrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Byte i = (Byte) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class ByteSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(((Byte) value).toString(), data, ctx);
        }
    }

    private static class ShortPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Short i = (Short) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class ShortSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(((Short) value).toString(), data, ctx);
        }
    }

    private static class IntPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Integer i = (Integer) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class IntegerSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(((Integer) value).toString(), data, ctx);
        }
    }

    private static class LongPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Long i = (Long) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class LongSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(((Long) value).toString(), data, ctx);
        }
    }

    private static String prepareFloat(String value) {
        if (value.endsWith(".0")) {
            value = value.substring(0, value.length() - 2);
        }
        return value;
    }

    private static class FloatPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Float i = (Float) value;
            if (i != 0f) {
                serializeTo(prepareFloat(i.toString()), data, ctx);
            }
        }
    }

    private static class FloatSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(prepareFloat(((Float) value).toString()), data, ctx);
        }
    }

    private static class DoublePrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            Double i = (Double) value;
            if (i != 0d) {
                serializeTo(prepareFloat(i.toString()), data, ctx);
            }
        }
    }

    private static class DoubleSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(prepareFloat(((Double) value).toString()), data, ctx);
        }
    }

    private static class StringPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            serializeTo(object.toString(), data, ctx);
        }
    }

    private static final String ARRAY_NULL_FLAG = "N";
    private static final String ARRAY_VALUE_SEPARATOR = ",";
    private static final String ARRAY_STRING_SCAPE_SIMBOL = "\\\\";
    private static final String ARRAY_STRING_SCAPED_SIMBOL = "\\\\";
    private static final String ARRAY_STRING_SCAPED_VALUE_SEPARATOR = "\\\\,";

    private static abstract class AbstractPrimitiveArraySerializer extends AbstractBasicSerializer {

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            data.put(ctx.toString(), objCtx.toString());
            if (notSerializedYet) {
                int objCtxLen = objCtx.length();
                int arrayLen = getArrayLen(object);
                StringBuilder val = new StringBuilder();

                for (int i = 0; i < arrayLen; i++) {
                    val.append(getArrayVal(object, i));
                    val.append(ARRAY_VALUE_SEPARATOR);
                }
                int valLen = val.length();
                if (valLen > 1) {
                    val.setLength(valLen - 1);
                }
                objCtx.setLength(objCtxLen);
                serializeTo(val.toString(), data, objCtx);
            }
        }

        protected abstract int getArrayLen(Object object);

        protected abstract String getArrayVal(Object object, int i);
    }

    private static abstract class AbstractArraySerializer<T extends Number> extends AbstractBasicSerializer {

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            data.put(ctx.toString(), objCtx.toString());
            if (notSerializedYet) {
                int objCtxLen = objCtx.length();
                @SuppressWarnings("unchecked")
                T[] values = (T[]) object;
                StringBuilder val = new StringBuilder();

                for (T value : values) {
                    if (value == null) {
                        val.append(ARRAY_NULL_FLAG);
                    } else {
                        val.append(getVal(value));
                    }
                    val.append(ARRAY_VALUE_SEPARATOR);
                }
                int valLen = val.length();
                if (valLen > 1) {
                    val.setLength(valLen - 1);
                }
                objCtx.setLength(objCtxLen);
                serializeTo(val.toString(), data, objCtx);
            }
        }

        protected String getVal(T t) {
            return t.toString();
        }
    }

    private static class PrimitiveByteArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((byte[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return Byte.toString(((byte[]) object)[i]);
        }
    }

    private static class ByteArraySerializer extends AbstractArraySerializer<Byte> {
    }

    private static class PrimitiveShortArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((short[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return Short.toString(((short[]) object)[i]);
        }
    }

    private static class ShortArraySerializer extends AbstractArraySerializer<Short> {
    }

    private static class PrimitiveIntArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((int[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return Integer.toString(((int[]) object)[i]);
        }
    }

    private static class IntegerArraySerializer extends AbstractArraySerializer<Integer> {
    }

    private static class PrimitiveLongArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((long[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return Long.toString(((long[]) object)[i]);
        }
    }

    private static class LongArraySerializer extends AbstractArraySerializer<Long> {
    }

    private static class PrimitiveFloatArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((float[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return prepareFloat(Float.toString(((float[]) object)[i]));
        }
    }

    private static abstract class AbstractFloatArraySerializer<T extends Number> extends AbstractArraySerializer<T> {

        @Override
        final protected String getVal(Number t) {
            return prepareFloat(t.toString());
        }
    }

    private static class FloatArraySerializer extends AbstractFloatArraySerializer<Float> {
    }

    private static class PrimitiveDoubleArraySerializer extends AbstractPrimitiveArraySerializer {

        @Override
        protected int getArrayLen(Object object) {
            return ((double[]) object).length;
        }

        @Override
        protected String getArrayVal(Object object, int i) {
            return prepareFloat(Double.toString(((double[]) object)[i]));
        }
    }

    private static class DoubleArraySerializer extends AbstractFloatArraySerializer<Double> {
    }

    private static class StringArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (String currValue : ((String[]) object)) {
                if (currValue == null) {
                    val.append(ARRAY_NULL_FLAG);
                } else {
                    // after this, all '\' are transformed to '\\'  
                    currValue = currValue.replaceAll(ARRAY_STRING_SCAPE_SIMBOL, ARRAY_STRING_SCAPED_SIMBOL);
                    // after this, all ',' are transformed to '\,'
                    currValue = currValue.replaceAll(ARRAY_VALUE_SEPARATOR, ARRAY_STRING_SCAPED_VALUE_SEPARATOR);
                    val.append(currValue);
                }
                val.append(ARRAY_VALUE_SEPARATOR);
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static final IMapSerializer OBJECT_SERIALIZER = new ObjectSerializer();

    private static final IMapSerializer CLASS_OBJECT_SERIALIZER = new ClassAndObjectSerializer();

    private static final IMapSerializer OBJECT_ARRAY_SERIALIZER = new ObjectArraySerializer();

    private static final IMapSerializer CLASS_OBJECT_ARRAY_SERIALIZER = new ClassAndObjectArraySerializer();

    private static final Map<Class<?>, IMapSerializer> PRIMITIVE_SERIALIZERS = buildPrimitiveSerilizers();

    private static Map<Class<?>, IMapSerializer> buildPrimitiveSerilizers() {
        Map<Class<?>, IMapSerializer> ret = new HashMap<>();

        ret.put(boolean.class, new BooleanPrimitiveSerializer());
        ret.put(Boolean.class, new BooleanSerializer());

        ret.put(byte.class, new BytePrimitiveSerializer());
        ret.put(Byte.class, new ByteSerializer());
        ret.put(byte[].class, new PrimitiveByteArraySerializer());
        ret.put(Byte[].class, new ByteArraySerializer());

        ret.put(short.class, new ShortPrimitiveSerializer());
        ret.put(Short.class, new ShortSerializer());
        ret.put(short[].class, new PrimitiveShortArraySerializer());
        ret.put(Short[].class, new ShortArraySerializer());

        ret.put(int.class, new IntPrimitiveSerializer());
        ret.put(Integer.class, new IntegerSerializer());
        ret.put(int[].class, new PrimitiveIntArraySerializer());
        ret.put(Integer[].class, new IntegerArraySerializer());

        ret.put(long.class, new LongPrimitiveSerializer());
        ret.put(Long.class, new LongSerializer());
        ret.put(long[].class, new PrimitiveLongArraySerializer());
        ret.put(Long[].class, new LongArraySerializer());

        ret.put(float.class, new FloatPrimitiveSerializer());
        ret.put(Float.class, new FloatSerializer());
        ret.put(float[].class, new PrimitiveFloatArraySerializer());
        ret.put(Float[].class, new FloatArraySerializer());

        ret.put(double.class, new DoublePrimitiveSerializer());
        ret.put(Double.class, new DoubleSerializer());
        ret.put(double[].class, new PrimitiveDoubleArraySerializer());
        ret.put(Double[].class, new DoubleArraySerializer());

        ret.put(String.class, new StringPrimitiveSerializer());
        ret.put(String[].class, new StringArraySerializer());

        return ret;
    }

    private static final IMapSerializer getSerializer(Field field, Object currentValue) {
        Class<?> fieldType = field.getType();
        // é primitivo?
        IMapSerializer serializer = PRIMITIVE_SERIALIZERS.get(fieldType);
        if (serializer != null) {
            return serializer;
        }

        Class<?> valueType = currentValue.getClass();
        serializer = PRIMITIVE_SERIALIZERS.get(valueType);
        if (serializer != null) {
            return new PrimitiveInObjectSerializer(serializer);
        }
        if (valueType.isArray()) {
            if (valueType.getComponentType().equals(fieldType.getComponentType())) {
                return OBJECT_ARRAY_SERIALIZER;
            }
            return CLASS_OBJECT_ARRAY_SERIALIZER;
        }

        if (valueType.equals(fieldType)) {
            return OBJECT_SERIALIZER;
        }
        return CLASS_OBJECT_SERIALIZER;

    }

    private static void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
        CLASS_OBJECT_SERIALIZER.serializeTo(object, data, ctx, ctxBuilder);
    }

    private static void serializeTo(Object object, Map<String, String> data, ObjCtxBuilder ctxBuilder) {
        serializeTo(object, data, new StringBuilder(), ctxBuilder);
    }

    public static Map<String, String> serialize(Object object) {
        Map<String, String> ret = new LinkedHashMap<>();
        serializeTo(object, ret, new ObjCtxBuilder());
        return ret;
    }

    static public String toJson(Object object) {
        Map<String, String> map = serialize(object);
        StringBuilder sb = new StringBuilder("{\r\"");
        for (Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\":\"");
            String value = entry.getValue();
            value = value.replace("\"", "\\\"");
            sb.append(value);
            sb.append("\",\r\"");
        }
        int len = sb.length();
        if (len > 3) {
            // tem objetos
            sb.setLength(len - 3);
            sb.append("\r}");
            return sb.toString();
        }
        return "{}";
    }

    private interface IMapUnserializer {

        Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap);
    }

    //    private static abstract class AbstractBasicUnserializer implements IMapUnserializer {
    //
    //        protected Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap) {
    //            data.put(ctx.toString(), value);
    //        }
    //    }

    private static class StringPrimitiveUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap) {
            return data.get(ctx.toString());
        }
        //
        //        @Override
        //        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
        //            String val = object.toString();
        //            val = val.replaceAll("\"", "\"\"");
        //            serializeTo(val, data, ctx);
        //        }
    }

    private static final IMapUnserializer OBJECT_UNSERIALIZER = null;//new ObjectSerializer();

    private static final IMapUnserializer CLASS_OBJECT_UNSERIALIZER = null;// = new ClassAndObjectSerializer();

    private static final IMapUnserializer OBJECT_ARRAY_UNSERIALIZER = null;// = new ObjectArraySerializer();

    private static final IMapUnserializer CLASS_OBJECT_ARRAY_UNSERIALIZER = null;// = new ClassAndObjectArraySerializer();

    private static final Map<Class<?>, IMapUnserializer> PRIMITIVE_UNSERIALIZERS = buildPrimitiveUnserilizers();

    private static Map<Class<?>, IMapUnserializer> buildPrimitiveUnserilizers() {
        Map<Class<?>, IMapUnserializer> ret = new HashMap<>();
        //
        //        ret.put(boolean.class, new BooleanPrimitiveSerializer());
        //        ret.put(Boolean.class, new BooleanSerializer());
        //
        //        ret.put(byte.class, new BytePrimitiveSerializer());
        //        ret.put(Byte.class, new ByteSerializer());
        //        ret.put(byte[].class, new PrimitiveByteArraySerializer());
        //        ret.put(Byte[].class, new ByteArraySerializer());
        //
        //        ret.put(short.class, new ShortPrimitiveSerializer());
        //        ret.put(Short.class, new ShortSerializer());
        //        ret.put(short[].class, new PrimitiveShortArraySerializer());
        //        ret.put(Short[].class, new ShortArraySerializer());
        //
        //        ret.put(int.class, new IntPrimitiveSerializer());
        //        ret.put(Integer.class, new IntegerSerializer());
        //        ret.put(int[].class, new PrimitiveIntArraySerializer());
        //        ret.put(Integer[].class, new IntegerArraySerializer());
        //
        //        ret.put(long.class, new LongPrimitiveSerializer());
        //        ret.put(Long.class, new LongSerializer());
        //        ret.put(long[].class, new PrimitiveLongArraySerializer());
        //        ret.put(Long[].class, new LongArraySerializer());
        //
        //        ret.put(float.class, new FloatPrimitiveSerializer());
        //        ret.put(Float.class, new FloatSerializer());
        //        ret.put(float[].class, new PrimitiveFloatArraySerializer());
        //        ret.put(Float[].class, new FloatArraySerializer());
        //
        //        ret.put(double.class, new DoublePrimitiveSerializer());
        //        ret.put(Double.class, new DoubleSerializer());
        //        ret.put(double[].class, new PrimitiveDoubleArraySerializer());
        //        ret.put(Double[].class, new DoubleArraySerializer());
        //
        ret.put(String.class, new StringPrimitiveUnserializer());
        //        ret.put(String[].class, new StringArraySerializer());
        //
        return ret;
    }

    private static IMapUnserializer getUnserializer(Field field/*, Map<String, String> data, StringBuilder ctx*/) {
        Class<?> fieldType = field.getType();
        // é primitivo?
        IMapUnserializer unserializer = PRIMITIVE_UNSERIALIZERS.get(fieldType);
        if (unserializer != null) {
            return unserializer;
        }

        throw new RuntimeException("Tipo não suportado:" + fieldType.getCanonicalName());
        //        ctx.append(CLASS_CONTEXT_SUFFIX);
        //        
        //        data.get(key)
        //
        //        Class<?> valueType = currentValue.getClass();
        //        unserializer = PRIMITIVE_SERIALIZERS.get(valueType);
        //        if (unserializer != null) {
        //            return new PrimitiveInObjectSerializer(unserializer);
        //        }
        //        if (valueType.isArray()) {
        //            if (valueType.getComponentType().equals(fieldType.getComponentType())) {
        //                return OBJECT_ARRAY_SERIALIZER;
        //            }
        //            return CLASS_OBJECT_ARRAY_SERIALIZER;
        //        }
        //
        //        if (valueType.equals(fieldType)) {
        //            return OBJECT_SERIALIZER;
        //        }
        //        return CLASS_OBJECT_SERIALIZER;
        //
    }

    private static void unserialize(Map<String, String> data, StringBuilder ctx, Object object) {
        int len = ctx.length();
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            ctx.setLength(len);
            ctx.append(field.getName());
            IMapUnserializer unserializer = getUnserializer(field);
            Object value = unserializer.unserializeFrom(data, ctx, null);
            try {
                field.set(object, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Object unserialize(Map<String, String> data, StringBuilder ctx, Class<?> clazz) {
        Object object;
        try {
            object = clazz.newInstance();
            unserialize(data, ctx, object);
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object unserialize(Map<String, String> data, StringBuilder ctx) {
        int len = ctx.length();
        ctx.append(CLASS_CONTEXT_ID);
        String className = data.get(ctx.toString());
        if (className == null) {
            return null;
        }
        ctx.setLength(len);
        try {
            Class<?> clazz = ToMapSerializer.class.getClassLoader().loadClass(className);
            return unserialize(data, ctx, clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object unserialize(Map<String, String> map) {
        return unserialize(map, new StringBuilder());
    }

    public static Object fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        @SuppressWarnings("unchecked")
        Map<String, String> map = gson.fromJson(json, LinkedHashMap.class);
        return unserialize(map);
    }

}
