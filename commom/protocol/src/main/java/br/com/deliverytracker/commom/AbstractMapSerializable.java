package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public abstract class AbstractMapSerializable {

    void serializeTo(LinkedHashMap<String, String> data, String parentContext) {
        StringBuilder ctx = new StringBuilder(parentContext);
        int len = ctx.length();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            ctx.setLength(len);
            ctx.append(field.getName());
            try {
                Object obj = field.get(this);
                if (obj != null) {
                    if (obj instanceof AbstractMapSerializable) {
                        ctx.append(".class");
                        Class<? extends Object> clazz = obj.getClass();
                        // É uma derivação ou implementação
                        if (field.getType() != clazz) {
                            // Salve a classe em questão
                            // Se o package da classe é o mesmo do objeto corrente, salva o simpleName, senão o canonicalName
                            String className = clazz.getPackage().equals(getClass().getPackage()) ? clazz.getSimpleName() : clazz.getCanonicalName();
                            data.put(ctx.toString(), className);
                        }
                        AbstractMapSerializable serializable = (AbstractMapSerializable) obj;
                        ctx.setLength(len + 1);
                        serializable.serializeTo(data, ctx.toString());
                    } else {
                        data.put(ctx.toString(), obj.toString());
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @SuppressWarnings("unchecked")
    void unserializeFrom(LinkedHashMap<String, String> data, StringBuilder ctx) {
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

}
