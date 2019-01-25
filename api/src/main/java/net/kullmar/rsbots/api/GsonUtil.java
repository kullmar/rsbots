package net.kullmar.rsbots.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import net.kullmar.rsbots.api.agility.courses.data.RectangleData;
import net.kullmar.rsbots.api.agility.courses.data.pyramid.CompositeRectangleData;
import net.kullmar.rsbots.api.agility.courses.data.pyramid.ShapeData;

public class GsonUtil {
    public static Gson getGson() {
        final RuntimeTypeAdapterFactory<ShapeData> typeFactory = RuntimeTypeAdapterFactory
                .of(ShapeData.class, "type")
                .registerSubtype(RectangleData.class, "rectangle")
                .registerSubtype(CompositeRectangleData.class, "compositeRectangle");
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create();
    }
}
