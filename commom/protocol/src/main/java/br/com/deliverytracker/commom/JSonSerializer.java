package br.com.deliverytracker.commom;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A generic POJO-JSON converter.
 * 
 * This converter assumes the conventions implemented by the SDL generator.
 * Namely:
 * <ul>
 * <li>it does not use final in fields
 * <li>arrays are encoded in Java using java.util.List
 * <li>byte arrays are used for blobs
 * <li>other kinds of arrays are ignored
 * </ul>
 */
public class JSonSerializer {

	// private static DateTimeFormatter DATE_TIME_FORMAT =
	// DateTimeFormatter.ISO_DATE_TIME;
	// private static DateTimeFormatter DATE_FORMAT =
	// DateTimeFormatter.ISO_LOCAL_DATE;
	// private static DateTimeFormatter TIME_FORMAT =
	// DateTimeFormatter.ISO_LOCAL_TIME;

	/**
	 * The name of the property that is used as discriminator for polymorphic
	 * types.
	 */
	public static final String DISCRIMINATOR = "_discriminator";

	/**
	 * The gson instance we use for objects.
	 */
	private static Gson objectGson = buildGson(true);
	/**
	 * The gson instance we use for everything else.
	 */
	private static Gson primitiveGson = buildGson(false);

	// /**
	// * Type adapter between JSON strings and Java Date objects.
	// */
	// private static class DateTimeAdapter implements JsonSerializer<Date>,
	// JsonDeserializer<Date> {
	//
	// @Override
	// public JsonElement serialize(Date src, Type typeOfSrc,
	// JsonSerializationContext context) {
	// if (src == null) {
	// return null;
	// }
	// return new JsonPrimitive(
	// DATE_TIME_FORMAT.format(OffsetDateTime.ofInstant(src.toInstant(),
	// ZoneOffset.UTC)));
	// }
	//
	// @Override
	// public Date deserialize(JsonElement json, Type typeOfT,
	// JsonDeserializationContext context)
	// throws JsonParseException {
	// if (json == null || json instanceof JsonNull) {
	// return null;
	// }
	// try {
	// return new Date(OffsetDateTime.parse(json.getAsString(),
	// DATE_TIME_FORMAT).toInstant().toEpochMilli());
	// } catch (DateTimeParseException e) {
	// throw new JsonParseException(e);
	// }
	// }
	// }

	// /**
	// * Type adapter between JSON strings and Java LocalDate objects.
	// */
	// private static class DateAdapter implements JsonSerializer<LocalDate>,
	// JsonDeserializer<LocalDate> {
	//
	// @Override
	// public JsonElement serialize(LocalDate src, Type typeOfSrc,
	// JsonSerializationContext context) {
	// if (src == null) {
	// return null;
	// }
	// return new JsonPrimitive(DATE_FORMAT.format(src));
	// }
	//
	// @Override
	// public LocalDate deserialize(JsonElement json, Type typeOfT,
	// JsonDeserializationContext context)
	// throws JsonParseException {
	// if (json == null || json instanceof JsonNull) {
	// return null;
	// }
	// try {
	// return LocalDate.parse(json.getAsString(), DATE_FORMAT);
	// } catch (DateTimeParseException e) {
	// throw new JsonParseException(e);
	// }
	// }
	// }

	// /**
	// * Type adapter between JSON strings and Java LocalTime objects.
	// */
	// private static class TimeAdapter implements JsonSerializer<LocalTime>,
	// JsonDeserializer<LocalTime> {
	//
	// @Override
	// public JsonElement serialize(LocalTime src, Type typeOfSrc,
	// JsonSerializationContext context) {
	// if (src == null) {
	// return null;
	// }
	// return new JsonPrimitive(TIME_FORMAT.format(src));
	// }
	//
	// @Override
	// public LocalTime deserialize(JsonElement json, Type typeOfT,
	// JsonDeserializationContext context)
	// throws JsonParseException {
	// if (json == null || json instanceof JsonNull) {
	// return null;
	// }
	// try {
	// return LocalTime.parse(json.getAsString(), TIME_FORMAT);
	// } catch (DateTimeParseException e) {
	// throw new JsonParseException(e);
	// }
	// }
	// }

	// /**
	// * Type adapter between (Base64-encoded) JSON strings and Java byte[]
	// * objects.
	// */
	// private static class BlobAdapter implements JsonSerializer<byte[]>,
	// JsonDeserializer<byte[]> {
	//
	// @Override
	// public JsonElement serialize(byte[] src, Type typeOfSrc,
	// JsonSerializationContext context) {
	// if (src == null) {
	// return null;
	// }
	// return new JsonPrimitive(Base64.getEncoder().encodeToString(src));
	// }
	//
	// @Override
	// public byte[] deserialize(JsonElement json, Type typeOfT,
	// JsonDeserializationContext context)
	// throws JsonParseException {
	// if (json == null || json instanceof JsonNull) {
	// return null;
	// }
	// try {
	// return Base64.getDecoder().decode(json.getAsString());
	// } catch (DateTimeParseException e) {
	// throw new JsonParseException(e);
	// }
	// }
	// }

	/**
	 * Type adapter between JSON and Java objects.
	 */
	private static class ObjectAdapter implements JsonDeserializer<Object>, JsonSerializer<Object> {

		@Override
		public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
			if (src == null) {
				return null;
			}
			// TODO
			// if (isPrimitive(typeOfSrc)) {
			// return primitiveGson.toJsonTree(src);
			// }
			if (isCollection(typeOfSrc)) {
				JsonArray array = new JsonArray();
				for (Object current : (Collection<?>) src) {
					array.add(current == null ? null : serialize(current, current.getClass(), context));
				}
				return array;
			}
			Class<?> objectClass = src.getClass();
			List<Field> fields = new LinkedList<>();
			collectFields(objectClass, fields);
			JsonObject serialized = new JsonObject();
			for (Field field : fields) {
				int modifiers = field.getModifiers();
				if (!Modifier.isFinal(modifiers) && isSerializable(field)) {
					field.setAccessible(true);
					try {
						serialized.add(field.getName(), context.serialize(field.get(src), field.getType()));
					} catch (ReflectiveOperationException e) {
						throw new RuntimeException(e);
					}
				}
			}
			if (objectClass.getSuperclass() != Object.class) {
				serialized.addProperty(DISCRIMINATOR, toFirstLower(objectClass.getSimpleName()));
			}
			return serialized;
		}

		@Override
		public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			if (json instanceof JsonArray) {
				JsonArray asArray = json.getAsJsonArray();
				Collection<Object> collection = new ArrayList<>();
				Type elementType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
				for (JsonElement item : asArray) {
					collection.add(objectGson.fromJson(item, elementType));
				}
				return collection;
			}
			// TODO
			// if (!(json instanceof JsonObject) || isPrimitive(typeOfT)) {
			// return primitiveGson.fromJson(json, typeOfT);
			// }
			JsonObject jsonObject = (JsonObject) json;
			JsonElement discriminatorValue = jsonObject.get(DISCRIMINATOR);
			try {
				Class<?> dtoClass = (Class<?>) typeOfT;
				if (discriminatorValue instanceof JsonPrimitive) {
					// figure out the actual DTO class to instantiate
					String discriminator = discriminatorValue.getAsString();
					char separator = dtoClass.isMemberClass() ? '$' : '.';
					String prefix = dtoClass.getName().substring(0, dtoClass.getName().lastIndexOf(separator) + 1);
					String typeName = toFirstUpper(discriminator);
					dtoClass = Class.forName(prefix + typeName);
				}
				Object dto = dtoClass.newInstance();
				for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
					String fieldName = entry.getKey();
					JsonElement fieldValue = entry.getValue();
					if (!DISCRIMINATOR.equals(fieldName)) {
						Field dtoField = getField(dtoClass, fieldName);
						if (dtoField != null && isSerializable(dtoField)) {
							dtoField.setAccessible(true);
							Object dtoValue = context.deserialize(fieldValue, dtoField.getGenericType());
							dtoField.set(dto, dtoValue);
						}
					}
				}
				;
				return dto;
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}

		private void collectFields(Class<?> clazz, List<Field> fields) {
			if (clazz != Object.class) {
				for (Field f : clazz.getDeclaredFields()) {
					fields.add(f);
				}
				collectFields(clazz.getSuperclass(), fields);
			}
		}

		private Field getField(Class<?> clazz, String fieldName) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				Class<?> superclass = clazz.getSuperclass();
				if (superclass != null && superclass != Object.class) {
					return getField(clazz.getSuperclass(), fieldName);
				}
				return null;
			}
		}

		private static String toFirstUpper(String string) {
			return string.substring(0, 1).toUpperCase() + (string.length() > 1 ? string.substring(1) : "");
		}

		private static String toFirstLower(String string) {
			return string.substring(0, 1).toLowerCase() + (string.length() > 1 ? string.substring(1) : "");
		}
	}

	/**
	 * Is this field actually serializable by this class? Non-serializable
	 * fields are silently ignored.
	 * 
	 * @param toCheck
	 * @return true if a field this class can serialize, false otherwise
	 */
	private static boolean isSerializable(Field toCheck) {
		if (toCheck.getType().isArray() && toCheck.getType().getComponentType() != byte.class) {
			// we only support Java arrays for blobs (for now)
			return false;
		}
		return true;
	}

	/**
	 * Converts the given JSON representation into a instance of the given POJO
	 * type.
	 * 
	 * @param <T>
	 *            type of the object being converted
	 * @param json
	 *            input json markup
	 * @param targetType
	 *            the target POJO class
	 * @return an instance of the specified POJO class with the values loaded
	 *         from the JSON representation
	 */
	public static <T> T toObject(String json, Class<T> targetType) {
		return getGson().fromJson(json, targetType);
	}

	/**
	 * Converts the given POJO to its corresponding JSON representation.
	 * 
	 * @param <T>
	 *            type of the object being converted
	 * @param source
	 *            the POJO to convert
	 * @return the resulting JSON representation
	 */
	public static <T> String toJSON(T source) {
		return getGson().toJson(source);
	}

	private static Gson getGson() {
		return objectGson;
	}

	private static Gson buildGson(boolean supportObjects) {
		GsonBuilder builder = new GsonBuilder();
		// we don't want Gson's default handling of date/time/date time/byte
		// array
		// representations
		// TODO
		// builder.registerTypeAdapter(Date.class, new DateTimeAdapter());
		// builder.registerTypeAdapter(LocalDate.class, new DateAdapter());
		// builder.registerTypeAdapter(LocalTime.class, new TimeAdapter());
		// builder.registerTypeAdapter(byte[].class, new BlobAdapter());

		if (supportObjects) {
			builder.registerTypeHierarchyAdapter(Object.class, new ObjectAdapter());
		}
		return builder.create();
	}

	// private static List<?> PRIMITIVES_CLASSES = Arrays.asList(String.class,
	// Date.class, LocalDate.class, LocalTime.class,
	// Double.class, Long.class, Boolean.class, BigDecimal.class);

	// /**
	// * Is this a type of primitive value?
	// */
	// private static boolean isPrimitive(Type valueType) {
	// if (!(valueType instanceof Class)) {
	// // don't know what this is
	// return false;
	// }
	// Class<?> clazz = (Class<?>) valueType;
	// return clazz.isPrimitive() || clazz.isEnum() ||
	// PRIMITIVES_CLASSES.contains(valueType);
	// }

	/**
	 * Is this a type of value that we should not handle as an object?
	 */
	private static boolean isCollection(Type valueType) {
		if (!(valueType instanceof Class)) {
			return false;
		}
		Class<?> clazz = (Class<?>) valueType;
		return Collection.class.isAssignableFrom(clazz);
	}
}
