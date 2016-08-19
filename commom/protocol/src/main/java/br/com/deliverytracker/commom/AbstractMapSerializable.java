package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractMapSerializable {

    private interface IMapSerializer {
    }

    private static final IMapSerializer SIMPLE_CLASS_SERIALIZER = new IMapSerializer() {};

    private static final IMapSerializer MULTI_CLASS_SERIALIZER = new IMapSerializer() {};

    private static final Map<Field, IMapSerializer> SERIALIZER_MAP = new HashMap<>();

    synchronized private static final IMapSerializer getSerializer(Field field, Object currentValue) {
        IMapSerializer serializer = SERIALIZER_MAP.get(field);
        if (serializer == null) {
            Class<?> type = field.getType();
            if (type.isAssignableFrom(AbstractMapSerializable.class)) {
                serializer = field.getType() != currentValue.getClass() ? MULTI_CLASS_SERIALIZER : SIMPLE_CLASS_SERIALIZER;
            } else if (1 == 1) {
                serializer = null;
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

    void serializeTo(Map<String, String> data, String parentContext) {
        StringBuilder ctx = new StringBuilder(parentContext);
        int len = ctx.length();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            ctx.setLength(len);
            ctx.append(field.getName());
            try {
                Object value = field.get(this);
                if (value != null) {
                    IMapSerializer serializer = getSerializer(field, value);
                    if (value instanceof AbstractMapSerializable) {
                        ctx.append(".class");
                        Class<? extends Object> clazz = value.getClass();
                        // É uma derivação ou implementação
                        if (field.getType() != clazz) {
                            // Salve a classe em questão
                            // Se o package da classe é o mesmo do objeto corrente, salva o simpleName, senão o canonicalName
                            String className = clazz.getPackage().equals(getClass().getPackage()) ? clazz.getSimpleName() : clazz.getCanonicalName();
                            data.put(ctx.toString(), className);
                        }
                        AbstractMapSerializable serializable = (AbstractMapSerializable) value;
                        ctx.setLength(len + 1);
                        serializable.serializeTo(data, ctx.toString());
                    } else {
                        data.put(ctx.toString(), value.toString());
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
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
        serializeTo(map, "");
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
