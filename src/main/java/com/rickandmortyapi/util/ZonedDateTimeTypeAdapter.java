package com.rickandmortyapi.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class ZonedDateTimeTypeAdapter implements JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(final JsonElement json, final Type typeOfT,
									 final JsonDeserializationContext context) throws JsonParseException {
		JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
		try {
			if (jsonPrimitive.isString()) {
				return ZonedDateTime.parse(jsonPrimitive.getAsString(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz"));
			}
			if (jsonPrimitive.isNumber()) {
				return ZonedDateTime.ofInstant(Instant.ofEpochMilli(jsonPrimitive.getAsLong()),
						ZoneId.systemDefault());
			}
		} catch (RuntimeException e) {
			throw new JsonParseException("Unable to parse ZonedDateTime", e);
		}
		throw new JsonParseException("Unable to parse ZonedDateTime");
	}
}
