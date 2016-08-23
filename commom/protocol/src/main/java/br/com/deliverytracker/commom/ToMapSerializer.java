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

        private int currentId = -1;

        private StringBuilder getNextObjCtx() {
            currentId++;
            if (currentId == 0) {
                return new StringBuilder();
            }
            StringBuilder ret = new StringBuilder(OBJECT_CONTEXT_PREFIX);
            ret.append(Integer.toString(currentId));
            return ret;
        }
    }

    private interface IMapSerializer {

        void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder);
    }

    private static class SimpleClassSerializer implements IMapSerializer {

        public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
            StringBuilder nextObjCtx = ctxBuilder.getNextObjCtx();
            data.put(ctx.toString(), nextObjCtx.toString());
            nextObjCtx.append('.');
            ToMapSerializer.serializeTo(object, data, nextObjCtx, ctxBuilder);
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

        private static class StringPrimitiveSerializer extends AbstractBasicSerializer {

            @Override
            public void serializeTo(Object object, Map<String, String> data, StringBuilder ctx, ObjCtxBuilder ctxBuilder) {
                String val = object.toString();
                val = val.replaceAll("\"", "\"\"");
                serializeTo(val, data, ctx);
            }
        }

        private static final IMapSerializer SIMPLE_CLASS_SERIALIZER = new SimpleClassSerializer();

        private static final IMapSerializer MULTI_CLASS_SERIALIZER = new MultiClassSerializer();

        private static final Map<Class<?>, IMapSerializer> PRIMITIVE_SERIALIZERS = buildPrimitiveSerilizers();

        private static final Map<Field, IMapSerializer> SERIALIZER_MAP = new HashMap<>();

        private static Map<Class<?>, IMapSerializer> buildPrimitiveSerilizers() {
            Map<Class<?>, IMapSerializer> ret = new HashMap<>();

            ret.put(boolean.class, new BooleanPrimitiveSerializer());
            ret.put(Boolean.class, new BooleanSerializer());

            ret.put(byte.class, new BytePrimitiveSerializer());
            ret.put(Byte.class, new ByteSerializer());

            ret.put(short.class, new ShortPrimitiveSerializer());
            ret.put(Short.class, new ShortSerializer());

            ret.put(int.class, new IntPrimitiveSerializer());
            ret.put(Integer.class, new IntegerSerializer());

            ret.put(long.class, new LongPrimitiveSerializer());
            ret.put(Long.class, new LongSerializer());

            ret.put(float.class, new FloatPrimitiveSerializer());
            ret.put(Float.class, new FloatSerializer());

            ret.put(double.class, new DoublePrimitiveSerializer());
            ret.put(Double.class, new DoubleSerializer());

            ret.put(String.class, new StringPrimitiveSerializer());
            return ret;
        }

        synchronized private static final IMapSerializer getSerializer(Field field, Object currentValue) {
            IMapSerializer serializer = SERIALIZER_MAP.get(field);
            if (serializer == null) {
                Class<?> type = field.getType();
                // é primitivo?
                serializer = PRIMITIVE_SERIALIZERS.get(type);
                if (serializer == null) {
                    serializer = field.getType() != currentValue.getClass() ? MULTI_CLASS_SERIALIZER : SIMPLE_CLASS_SERIALIZER;
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
            serializeTo(object, data, ctxBuilder.getNextObjCtx(), ctxBuilder);
        }

        public static Map<String, String> serialize(Object object) {
            Map<String, String> ret = new HashMap<>();
            serializeTo(object, ret, new ObjCtxBuilder());
            return ret;
        }

        static public String toJson(Object object) {
            Map<String, String> map = new LinkedHashMap<>();
            serializeTo(object, map, new ObjCtxBuilder());
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
