package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ToMapSerializer {

    private static final String OBJECT_CONTEXT_PREFIX = "REF_";

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

    private static class SimpleClassSerializer implements IMapSerializer {

        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder objCtx = ctxBuilder.getExistingObjCtx(object);
            boolean notSerializedYet = objCtx == null;
            if (notSerializedYet) {
                objCtx = ctxBuilder.getNewObjCtx(object);
            }
            data.put(ctx.toString(), objCtx.toString());
            if (notSerializedYet) {
                objCtx.append('.');
                ToMapSerializer.serializeTo(object, data, objCtx, ctxBuilder);
            }
        }
    }

    private static class MultiClassSerializer extends SimpleClassSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            int len = ctx.length();
            ctx.append(".class");
            Class<? extends Object> clazz = object.getClass();
            // Salve a classe em questão
            // Se o package da classe é o mesmo do objeto corrente, salva o simpleName, senão o canonicalName
            String className = clazz.getPackage().equals(getClass().getPackage()) ? clazz.getSimpleName() : clazz.getCanonicalName();
            data.put(ctx.toString(), className);
            ctx.setLength(len);
            super.serializeTo(object, data, ctx, ctxBuilder);
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

    private static final String FLAG_NULL = "N";

    private static class StringPrimitiveSerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            String val = object.toString();
            val = val.replaceAll("\"", "\"\"");
            serializeTo(val, data, ctx);
        }
    }

    private static class PrimitiveByteArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (byte currValue : ((byte[]) object)) {
                val.append(Byte.toString(currValue));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class ByteArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Byte currValue : ((Byte[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(Byte.toString(currValue));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class PrimitiveShortArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (short currValue : ((short[]) object)) {
                val.append(Short.toString(currValue));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class ShortArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Short currValue : ((Short[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(Short.toString(currValue));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class PrimitiveIntArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (int currValue : ((int[]) object)) {
                val.append(Integer.toString(currValue));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class IntegerArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Integer currValue : ((Integer[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(Integer.toString(currValue));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class PrimitiveLongArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (long currValue : ((long[]) object)) {
                val.append(Long.toString(currValue));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class LongArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Long currValue : ((Long[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(Long.toString(currValue));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class PrimitiveFloatArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (float currValue : ((float[]) object)) {
                val.append(prepareFloat(Float.toString(currValue)));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class FloatArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Float currValue : ((Float[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(prepareFloat(Float.toString(currValue)));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class PrimitiveDoubleArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (double currValue : ((double[]) object)) {
                val.append(prepareFloat(Double.toString(currValue)));
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class DoubleArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (Double currValue : ((Double[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append(prepareFloat(Double.toString(currValue)));
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static class StringArraySerializer extends AbstractBasicSerializer {

        @Override
        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder val = new StringBuilder();
            for (String currValue : ((String[]) object)) {
                if (currValue == null) {
                    val.append(FLAG_NULL);
                } else {
                    val.append("\"");
                    val.append((currValue.replaceAll("\"", "\"\"")));
                    val.append("\"");
                }
                val.append(' ');
            }
            val.setLength(val.length() - 1);
            serializeTo(val.toString(), data, ctx);
        }
    }

    private static final IMapSerializer SIMPLE_CLASS_SERIALIZER = new SimpleClassSerializer();

    private static final IMapSerializer MULTI_CLASS_SERIALIZER = new MultiClassSerializer();

    //private static final IMapSerializer ARRAY_SERIALIZER = new ObjectArraySerializer();

    private static final Map<Class<?>, IMapSerializer> PRIMITIVE_SERIALIZERS = buildPrimitiveSerilizers();

    private static final Map<Field, IMapSerializer> SERIALIZER_MAP = new HashMap<>();

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

    synchronized private static final IMapSerializer getSerializer(Field field, Object currentValue) {
        IMapSerializer serializer = SERIALIZER_MAP.get(field);
        if (serializer == null) {
            Class<?> type = field.getType();
            // é primitivo?
            serializer = PRIMITIVE_SERIALIZERS.get(type);
            if (serializer == null) {
                //                if (type.isArray()) {
                //                    Class<?> arrayType = type.getComponentType();
                //                    if ()
                //                    serializer = ARRAY_SERIALIZER;
                //                } else {
                serializer = field.getType() != currentValue.getClass() ? MULTI_CLASS_SERIALIZER : SIMPLE_CLASS_SERIALIZER;
                //                }
            }
            //              } else {
            //                throw new RuntimeException(String.format("A serialização para mapa do tipo <%s> não é suportada", type.getCanonicalName()));
            //            }
            SERIALIZER_MAP.put(field, serializer);
        } else {
            if (serializer == SIMPLE_CLASS_SERIALIZER) {
                if (field.getType() != currentValue.getClass()) {
                    SERIALIZER_MAP.put(field, MULTI_CLASS_SERIALIZER);
                    serializer = MULTI_CLASS_SERIALIZER;
                }
            }
        }
        return serializer;
    }

    private static void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
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

    private static void serializeTo(Object object, Map<String, String> data, ObjCtxBuilder ctxBuilder) {
        serializeTo(object, data, ctxBuilder.getNewObjCtx(object), ctxBuilder);
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
            sb.append(entry.getValue());
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

    @SuppressWarnings("unchecked")
    void unserializeFrom(Map<String, String> data, StringBuilder ctx) {
        int len = ctx.length();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            ctx.setLength(len);
            ctx.append(field.getName());
            try {
                Class<?> fieldClazz = field.getType();
                if (fieldClazz.isAssignableFrom(ToMapSerializer.class)) {
                    Class<? extends ToMapSerializer> objectFieldClass = (Class<? extends ToMapSerializer>) fieldClazz;
                    ctx.append(".class");
                    String clazzName = data.get(ctx.toString());
                    if (clazzName != null) {
                        // É uma derivação ou implementação
                        if (clazzName.indexOf('.') != -1) {
                            // A classe é do mesmo package
                            clazzName = String.format("%s.$s", getClass().getPackage().getName(), clazzName);
                        }
                        objectFieldClass = (Class<? extends ToMapSerializer>) getClass().getClassLoader().loadClass(clazzName);
                    }
                    ToMapSerializer newInstance = objectFieldClass.newInstance();
                    field.set(this, newInstance);
                    ctx.setLength(len + 1);
                    newInstance.unserializeFrom(data, ctx);
                } else {
                    String value = data.get(ctx.toString());
                    if (value != null) {
                        field.set(this, value);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
