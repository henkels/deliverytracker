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
						ctx.append('.');
						// É uma derivação ou implementação
						if (field.getType() != obj.getClass()) {
							// Salve a classe em questão
							int newLen = ctx.length();
							ctx.append("class");
							data.put(ctx.toString(), obj.getClass().getSimpleName());
							ctx.setLength(newLen);
						}
						AbstractMapSerializable serializable = (AbstractMapSerializable) obj;
						ctx.append('.');
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
	
	
	void unserializeFrom(LinkedHashMap<String, String> data, String parentContext);
	
	

}
