package net.karashokleo.l2hostility.data.provider.cfg;

import com.google.gson.JsonElement;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.data.Constants;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ConfigDataProvider implements DataProvider
{
    protected final FabricDataOutput output;
    private final String name;
    private final List<ConfigEntry<?>> entryList = new ArrayList<>();

    public ConfigDataProvider(FabricDataOutput output, String name)
    {
        this.output = output;
        this.name = name;
    }

    public abstract void add(Collector collector);

    @Override
    public CompletableFuture<?> run(DataWriter writer)
    {
        Path folder = output.getPath();
        add(new Collector(name, entryList));
        List<CompletableFuture<?>> list = new ArrayList<>();
        entryList.forEach(entry ->
        {
            JsonElement elem = entry.serialize();
            if (elem != null)
                list.add(DataProvider.writeToPath(writer, elem, folder.resolve(entry.s + ".json")));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName()
    {
        return "LH Config";
    }

    public record Collector(String name, List<ConfigEntry<?>> list)
    {
        public <T> void add(Identifier id, T config)
        {
            list.add(
                    new ConfigEntry<>(
                            "data/" + id.getNamespace() + "/" + Constants.PARENT_CONFIG_PATH + "/" + name + "/" + id.getPath(),
                            config
                    )
            );
        }
    }

    public record ConfigEntry<T>(String s, T config)
    {
        @Nullable
        public JsonElement serialize()
        {
            return JsonCodec.toJson(config);
        }
    }
}
