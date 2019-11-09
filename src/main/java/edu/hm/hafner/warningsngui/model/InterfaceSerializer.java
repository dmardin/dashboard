package edu.hm.hafner.warningsngui.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public final class InterfaceSerializer<T>
        implements JsonSerializer<T>, JsonDeserializer<T> {

    private final Class<T> implementationClass;

    public InterfaceSerializer(final Class<T> implementationClass) {
        this.implementationClass = implementationClass;
    }

    public static <T> InterfaceSerializer<T> interfaceSerializer(final Class<T> implementationClass) {
        return new InterfaceSerializer<>(implementationClass);
    }

    @Override
    public JsonElement serialize(final T value, final Type type, final JsonSerializationContext context) {
        final Type targetType = value != null
                ? value.getClass() // `type` can be an interface so Gson would not even try to traverse the fields, just pick the implementation class
                : type;            // if not, then delegate further
        return context.serialize(value, targetType);
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
        return context.deserialize(jsonElement, implementationClass);
    }

}
