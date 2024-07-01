package karashokleo.l2hostility.content.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.minecraft.util.JsonSerializer;

import java.util.Objects;

public record TraitSerializer<T>(Class<T> cls) implements JsonSerializer<T>
{
    @Override
    public void toJson(JsonObject json, T object, JsonSerializationContext context)
    {
        JsonCodec.toJsonObject(object, json);
    }

    @Override
    public T fromJson(JsonObject json, JsonDeserializationContext context)
    {
        return Objects.requireNonNull(JsonCodec.from(json, cls, null));
    }
}
