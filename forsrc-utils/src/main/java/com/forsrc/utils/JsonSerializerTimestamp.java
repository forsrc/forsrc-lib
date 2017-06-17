package com.forsrc.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * The type Json serializer timestamp.
 */
public class JsonSerializerTimestamp extends JsonSerializer<Timestamp> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Timestamp date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String value = dateFormat.format(date);
        gen.writeString(value);
    }
}