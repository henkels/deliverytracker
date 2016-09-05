package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.deliverytracker.utils.Base64Utils;

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

        void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType);
    }

    private static final class ObjectSerializer implements IMapSerializer {

        protected void serializeMainContextData(Object object, Map<String, String> data, StringBuilder ctx, Class<?> fieldType) {
            Class<?> clazz = object.getClass();
            if (clazz != fieldType) {
                ctx.append(CLASS_CONTEXT_ID);
                // Salve a classe em questão
                data.put(ctx.toString(), clazz.getCanonicalName());
            }
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
                        Class<?> fieldType = field.getType();
                        IMapSerializer serializer = getSerializer(fieldType, value.getClass());
                        serializer.serializeTo(value, data, ctx, ctxBuilder, fieldType);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
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
                serializeMainContextData(object, data, ctx, fieldType);
                serializeObjectContextData(object, data, objCtx, ctxBuilder);
            }
        }
    }

    private static class ObjectArraySerializer implements IMapSerializer {

        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            data.put(ctx.toString(), objCtx.toString());
            if (notSerializedYet) {
                int objCtxLen = objCtx.length();
                Class<?> arrayClass = object.getClass();
                if (!arrayClass.equals(fieldType)) {
                    objCtx.append(".");
                    objCtx.append(CLASS_CONTEXT_ID);
                    data.put(objCtx.toString(), String.format("%s[]", arrayClass.getCanonicalName()));
                    objCtx.setLength(objCtxLen);
                }
                Object[] aux = (Object[]) object;
                StringBuilder val = new StringBuilder();
                for (Object inner : aux) {
                    if (inner == null) {
                        val.append(ARRAY_NULL_FLAG);
                    } else {
                        IMapSerializer serializer = getSerializer(fieldType, inner.getClass());
                        serializer.serializeTo(inner, data, objCtx, ctxBuilder, fieldType.getComponentType());
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
    }

    private static class PrimitiveInObjectSerializer implements IMapSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            int length = ctx.length();
            ctx.append(CLASS_CONTEXT_ID);
            data.put(ctx.toString(), object.getClass().getCanonicalName());
            ctx.setLength(length);
            IMapSerializer innerSerializer = getSerializer(fieldType, object.getClass());
            innerSerializer.serializeTo(object, data, ctx, ctxBuilder, fieldType);
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
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Boolean b = (Boolean) value;
            if (b) {
                serializeTo(BOOL_TRUE, data, ctx);
            }
        }
    }

    private static class BooleanSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo((Boolean) value ? BOOL_TRUE : BOOL_FASE, data, ctx);
        }
    }

    private static class BytePrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Byte i = (Byte) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class ByteSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(((Byte) value).toString(), data, ctx);
        }
    }

    private static class ShortPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Short i = (Short) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class ShortSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(((Short) value).toString(), data, ctx);
        }
    }

    private static class IntPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Integer i = (Integer) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class IntegerSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(((Integer) value).toString(), data, ctx);
        }
    }

    private static class LongPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Long i = (Long) value;
            if (i != 0) {
                serializeTo(i.toString(), data, ctx);
            }
        }
    }

    private static class LongSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
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
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Float i = (Float) value;
            if (i != 0f) {
                serializeTo(prepareFloat(i.toString()), data, ctx);
            }
        }
    }

    private static class FloatSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(prepareFloat(((Float) value).toString()), data, ctx);
        }
    }

    private static class DoublePrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            Double i = (Double) value;
            if (i != 0d) {
                serializeTo(prepareFloat(i.toString()), data, ctx);
            }
        }
    }

    private static class DoubleSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object value, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(prepareFloat(((Double) value).toString()), data, ctx);
        }
    }

    private static final String ARRAY_NULL_FLAG = "N";
    private static final String ARRAY_VALUE_SEPARATOR = ",";

    private static abstract class AbstractPrimitiveArraySerializer extends AbstractBasicSerializer {

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
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

        public final void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
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

    private static class StringPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            serializeTo(object.toString(), data, ctx);
        }
    }

    private static final String BASE_64_STRING_CTX = ".b64";

    private static class StringArraySerializer extends AbstractBasicSerializer {

        private String buildStringArrayValue(String[] strings) {
            StringBuilder val = new StringBuilder();
            for (String currValue : strings) {
                if (currValue == null) {
                    val.append(ARRAY_NULL_FLAG);
                } else {
                    if (currValue.contains(ARRAY_VALUE_SEPARATOR) | currValue.contains(ARRAY_NULL_FLAG)) {
                        return null;
                    }
                    val.append(currValue);
                }
                val.append(ARRAY_VALUE_SEPARATOR);
            }
            val.setLength(val.length() - 1);
            return val.toString();
        }

        private String buildB64StringArrayValue(String[] strings) {
            StringBuilder val = new StringBuilder();
            for (String currValue : strings) {
                if (currValue == null) {
                    val.append(ARRAY_NULL_FLAG);
                } else {
                    val.append(Base64Utils.encode(currValue));
                }
                val.append(ARRAY_VALUE_SEPARATOR);
            }
            val.setLength(val.length() - 1);
            return val.toString();
        }

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder, Class<?> fieldType) {
            String[] strings = (String[]) object;
            String value = buildStringArrayValue(strings);
            if (value == null) {
                value = buildB64StringArrayValue(strings);
                ctx.append(BASE_64_STRING_CTX);
            }
            serializeTo(value, data, ctx);
        }
    }

    private static final IMapSerializer OBJECT_SERIALIZER = new ObjectSerializer();

    private static final IMapSerializer OBJECT_ARRAY_SERIALIZER = new ObjectArraySerializer();

    private static final IMapSerializer PRIMITIVE_IN_OBJECT_SERIALIZER = new PrimitiveInObjectSerializer();

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

    private static final IMapSerializer getSerializer(Class<?> fieldType, Class<?> valueType) {
        // é primitivo?
        IMapSerializer serializer = PRIMITIVE_SERIALIZERS.get(fieldType);
        if (serializer != null) {
            return serializer;
        }

        serializer = PRIMITIVE_SERIALIZERS.get(valueType);
        if (serializer != null) {
            return PRIMITIVE_IN_OBJECT_SERIALIZER;
        }
        if (valueType.isArray()) {
            return OBJECT_ARRAY_SERIALIZER;
        }

        return OBJECT_SERIALIZER;
    }

    private static void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
        OBJECT_SERIALIZER.serializeTo(object, data, ctx, ctxBuilder, null);
    }

    private static void serializeTo(Object object, Map<String, String> data, ObjCtxBuilder ctxBuilder) {
        serializeTo(object, data, new StringBuilder(), ctxBuilder);
    }

    public static Map<String, String> serialize(Object object) {
        Map<String, String> ret = new LinkedHashMap<>();
        ObjCtxBuilder objCtxBuilder = new ObjCtxBuilder();
        serializeTo(object, ret, objCtxBuilder);
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

        Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType);
    }

    private static class BooleanUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return BOOL_TRUE.equals(str);
        }
    }

    private static class ByteUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Byte.valueOf(str);
        }
    }

    private static class PrimitiveByteArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            byte[] ret = new byte[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Byte.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class ByteArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Byte[] ret = new Byte[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Byte.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class ShortUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Short.valueOf(str);
        }
    }

    private static class PrimitiveShortArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            short[] ret = new short[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Short.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class ShortArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Short[] ret = new Short[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Short.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class IntegerUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Integer.valueOf(str);
        }
    }

    private static class PrimitiveIntArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            int[] ret = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Integer.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class IntegerArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Integer[] ret = new Integer[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Integer.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class LongUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Long.valueOf(str);
        }
    }

    private static class PrimitiveLongArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            long[] ret = new long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Long.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class LongArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Long[] ret = new Long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Long.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class FloatUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Float.valueOf(str);
        }
    }

    private static class PrimitiveFloatArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            float[] ret = new float[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Float.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class FloatArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Float[] ret = new Float[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Float.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class DoubleUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            return Double.valueOf(str);
        }
    }

    private static class PrimitiveDoubleArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            double[] ret = new double[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = Double.valueOf(strs[i]);
            }
            return ret;
        }
    }

    private static class DoubleArrayUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String str = data.get(ctx.toString());
            if (str == null) {
                return null;
            }
            str = data.get(str);
            String[] strs = str.split(",");
            Double[] ret = new Double[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String innerStr = strs[i];
                if (!ARRAY_NULL_FLAG.equals(innerStr)) {
                    ret[i] = Double.valueOf(innerStr);
                }
            }
            return ret;
        }
    }

    private static class StringPrimitiveUnserializer implements IMapUnserializer {

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            return data.get(ctx.toString());
        }
    }

    private static class StringArrayUnserializer implements IMapUnserializer {

        private String[] buildStringArrayFromStr(String string) {
            return string.split(ARRAY_VALUE_SEPARATOR);
        }

        private String[] buildStringArrayFromB64Str(String string) {
            String[] ret = string.split(ARRAY_VALUE_SEPARATOR);
            for (int i = 0; i < ret.length; i++) {
                String value = ret[i];
                if (value.equals(ARRAY_NULL_FLAG)) {
                    ret[i] = null;
                    continue;
                }
                ret[i] = Base64Utils.decode(ret[i]);

            }
            return ret;
        }

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String value = data.get(ctx.toString());
            if (value != null) {
                return buildStringArrayFromStr(value);
            }
            ctx.append(BASE_64_STRING_CTX);
            value = data.get(ctx.toString());
            if (value != null) {
                return buildStringArrayFromB64Str(value);
            }
            return null;
        }

    }

    private static class ObjectUnserializer implements IMapUnserializer {

        private static Class<?> loadClass(String clazzName) {
            try {
                return ToMapSerializer.class.getClassLoader().loadClass(clazzName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private Object unserialize(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> clazz) {
            try {
                Object ret = clazz.newInstance();
                int len = ctx.length();
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    ctx.setLength(len);
                    ctx.append(field.getName());
                    Class<?> type = field.getType();
                    IMapUnserializer unserializer = getUnserializer(type, data, ctx);
                    Object value = unserializer.unserializeFrom(data, ctx, objectMap, type);
                    if (value == null) {
                        continue;
                    }
                    field.set(ret, value);
                }
                return ret;
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> fieldType) {
            String currentCtx = ctx.toString();
            String refId = data.get(currentCtx);
            Object ret = objectMap.get(refId);
            if (ret != null) {
                return ret;
            }
            boolean isRootObject = "".equals(currentCtx);

            String innerObjectCtx = isRootObject ? "" : refId;
            if (innerObjectCtx != null) {
                StringBuilder newCtx = new StringBuilder(innerObjectCtx);
                if (!isRootObject) {
                    ctx.append('.');
                    newCtx.append('.');
                }
                Class<?> type = fieldType;
                ctx.append(CLASS_CONTEXT_ID);
                String className = data.get(ctx.toString());
                if (className != null) {
                    type = loadClass(className);
                }
                ret = unserialize(data, newCtx, objectMap, type);
                objectMap.put(innerObjectCtx, ret);
            }
            return ret;
        }
    }

    private static class ObjectArrayUnserializer implements IMapUnserializer {

        private static Class<?> loadClass(String clazzName) {
            try {
                return ToMapSerializer.class.getClassLoader().loadClass(clazzName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Object unserializeFrom(Map<String, String> data, StringBuilder ctx, Map<String, Object> objectMap, Class<?> clazz) {
            try {
                Object ret = clazz.newInstance();
                int len = ctx.length();
                Field[] fields = clazz.getFields();
                for (Field field : fields) {
                    ctx.setLength(len);
                    ctx.append(field.getName());
                    Class<?> type = field.getType();
                    IMapUnserializer unserializer = getUnserializer(type, data, ctx);
                    Object value = unserializer.unserializeFrom(data, ctx, objectMap, type);
                    if (value == null) {
                        continue;
                    }
                    field.set(ret, value);
                }
                return ret;
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final IMapUnserializer OBJECT_UNSERIALIZER = new ObjectUnserializer();

    private static final IMapUnserializer OBJECT_ARRAY_UNSERIALIZER = new ObjectArrayUnserializer();

    private static final Map<Class<?>, IMapUnserializer> PRIMITIVE_UNSERIALIZERS = buildPrimitiveUnserilizers();

    private static Map<Class<?>, IMapUnserializer> buildPrimitiveUnserilizers() {
        Map<Class<?>, IMapUnserializer> ret = new HashMap<>();

        ret.put(boolean.class, new BooleanUnserializer());
        ret.put(Boolean.class, new BooleanUnserializer());

        ret.put(byte.class, new ByteUnserializer());
        ret.put(Byte.class, new ByteUnserializer());
        ret.put(byte[].class, new PrimitiveByteArrayUnserializer());
        ret.put(Byte[].class, new ByteArrayUnserializer());

        ret.put(short.class, new ShortUnserializer());
        ret.put(Short.class, new ShortUnserializer());
        ret.put(short[].class, new PrimitiveShortArrayUnserializer());
        ret.put(Short[].class, new ShortArrayUnserializer());

        ret.put(int.class, new IntegerUnserializer());
        ret.put(Integer.class, new IntegerUnserializer());
        ret.put(int[].class, new PrimitiveIntArrayUnserializer());
        ret.put(Integer[].class, new IntegerArrayUnserializer());

        ret.put(long.class, new LongUnserializer());
        ret.put(Long.class, new LongUnserializer());
        ret.put(long[].class, new PrimitiveLongArrayUnserializer());
        ret.put(Long[].class, new LongArrayUnserializer());

        ret.put(float.class, new FloatUnserializer());
        ret.put(Float.class, new FloatUnserializer());
        ret.put(float[].class, new PrimitiveFloatArrayUnserializer());
        ret.put(Float[].class, new FloatArrayUnserializer());

        ret.put(double.class, new DoubleUnserializer());
        ret.put(Double.class, new DoubleUnserializer());
        ret.put(double[].class, new PrimitiveDoubleArrayUnserializer());
        ret.put(Double[].class, new DoubleArrayUnserializer());

        ret.put(String.class, new StringPrimitiveUnserializer());
        ret.put(String[].class, new StringArrayUnserializer());
        //
        return ret;
    }

    private static IMapUnserializer getUnserializer(Class<?> fieldType, Map<String, String> data, StringBuilder ctx) {
        // é primitivo?
        IMapUnserializer unserializer = PRIMITIVE_UNSERIALIZERS.get(fieldType);
        if (unserializer != null) {
            return unserializer;
        }
        if (fieldType.isArray()) {
            return OBJECT_ARRAY_UNSERIALIZER;
        }
        return OBJECT_UNSERIALIZER;
    }

    private static Object unserialize(Map<String, String> data) {
        return OBJECT_UNSERIALIZER.unserializeFrom(data, new StringBuilder(), new HashMap<String, Object>(), null);
    }

    public static Object fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        @SuppressWarnings("unchecked")
        Map<String, String> map = gson.fromJson(json, LinkedHashMap.class);
        return unserialize(map);
    }

}
