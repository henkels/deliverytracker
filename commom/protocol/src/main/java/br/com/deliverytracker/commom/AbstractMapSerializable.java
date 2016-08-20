package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractMapSerializable {

    private interface IMapSerializer {

        void serializeTo(Map<String, String> data, StringBuilder ctx, Object value);
    }

    private static class SimpleClassSerializer implements IMapSerializer {

        protected void serializeTo(Map<String, String> data, StringBuilder ctx, AbstractMapSerializable value) {
            value.serializeTo(data, ctx);
        }

        @Override
        public void serializeTo(Map<String, String> data, StringBuilder ctx, Object value) {
            serializeTo(data, ctx, (AbstractMapSerializable) value);
        }

    }

    private static class MultiClassSerializer extends SimpleClassSerializer {

        @Override
        protected void serializeTo(Map<String, String> data, StringBuilder ctx, AbstractMapSerializable value) {
            int len = ctx.length();
            ctx.append(".class");
            Class<? extends Object> clazz = value.getClass();
            // Salve a classe em questão
            // Se o package da classe é o mesmo do objeto corrente, salva o simpleName, senão o canonicalName
            String className = clazz.getPackage().equals(getClass().getPackage()) ? clazz.getSimpleName() : clazz.getCanonicalName();
            data.put(ctx.toString(), className);
            ctx.setLength(len);
            super.serializeTo(data, ctx, value);
        }

    }

    private static abstract class AbstractPrimitiveSerializer implements IMapSerializer {

        protected void serializeTo(Map<String, String> data, StringBuilder ctx, String value) {
            data.put(ctx.toString(), value);
        }
    }

    private static class BooleanPrimitiveSerializer extends AbstractPrimitiveSerializer {

        @Override
        public void serializeTo(Map<String, String> data, StringBuilder ctx, Object value) {
            Boolean b = (Boolean) value;
            if (b) {
                serializeTo(data, ctx, value.toString());
            }
        }
    }

    private static class StringPrimitiveSerializer extends AbstractPrimitiveSerializer {

        @Override
        public void serializeTo(Map<String, String> data, StringBuilder ctx, Object value) {
            String val = value.toString();
            val = val.replaceAll("\"", "\"\"");
            serializeTo(data, ctx, val);
        }
    }

    private static final IMapSerializer SIMPLE_CLASS_SERIALIZER = new SimpleClassSerializer();

    private static final IMapSerializer MULTI_CLASS_SERIALIZER = new MultiClassSerializer();

    private static final Map<Class<?>, IMapSerializer> PRIMITIVE_SERIALIZERS = buildPrimitiveSerilizers();

    private static final Map<Field, IMapSerializer> SERIALIZER_MAP = new HashMap<>();

    private static Map<Class<?>, IMapSerializer> buildPrimitiveSerilizers() {
        Map<Class<?>, IMapSerializer> ret = new HashMap<>();
        ret.put(boolean.class, new BooleanPrimitiveSerializer());
        //ret.put(String.class, new StringPrimitiveSerializer());
        return ret;
    }

    synchronized private static final IMapSerializer getSerializer(Field field, Object currentValue) {
        IMapSerializer serializer = SERIALIZER_MAP.get(field);
        if (serializer == null) {
            Class<?> type = field.getType();
            if (type.isAssignableFrom(AbstractMapSerializable.class)) {
                serializer = field.getType() != currentValue.getClass() ? MULTI_CLASS_SERIALIZER : SIMPLE_CLASS_SERIALIZER;
            } else {
                serializer = PRIMITIVE_SERIALIZERS.get(type);
                if (serializer == null) {
                    throw new RuntimeException(String.format("A serialização para mapa do tipo <%s> não é suportada", type.getCanonicalName()));
                }
            }
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

    protected void serializeTo(Map<String, String> data, StringBuilder ctx) {
        int len = ctx.length();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            ctx.setLength(len);
            ctx.append(field.getName());
            try {
                Object value = field.get(this);
                if (value != null) {
                    IMapSerializer serializer = getSerializer(field, value);
                    serializer.serializeTo(data, ctx, value);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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
                if (fieldClazz.isAssignableFrom(AbstractMapSerializable.class)) {
                    Class<? extends AbstractMapSerializable> objectFieldClass = (Class<? extends AbstractMapSerializable>) fieldClazz;
                    ctx.append(".class");
                    String clazzName = data.get(ctx.toString());
                    if (clazzName != null) {
                        // É uma derivação ou implementação
                        if (clazzName.indexOf('.') != -1) {
                            // A classe é do mesmo package
                            clazzName = String.format("%s.$s", getClass().getPackage().getName(), clazzName);
                        }
                        objectFieldClass = (Class<? extends AbstractMapSerializable>) getClass().getClassLoader().loadClass(clazzName);
                    }
                    AbstractMapSerializable newInstance = objectFieldClass.newInstance();
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

    @Override
    final public String toString() {
        Map<String, String> map = new LinkedHashMap<>();
        serializeTo(map, new StringBuilder());
        StringBuilder sb = new StringBuilder("{");
        for (Entry<String, String> entry : map.entrySet()) {
            sb.append("\r\"");
            sb.append(entry.getKey());
            sb.append("\":\"");
            sb.append(entry.getValue());
            sb.append("\",");
        }
        int len = sb.length();
        if (len > 1) {
            // tem objetos
            sb.setLength(len - 1);
        }
        sb.append("}");
        return sb.toString();
    }

}
