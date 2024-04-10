package net.karashokleo.l2hostility.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class DataLoader implements SimpleSynchronousResourceReloadListener
{
    @Override
    public Identifier getFabricId()
    {
        return null;
    }

    public static void error(Identifier id, Exception e)
    {
        L2Hostility.LOGGER.error("Error loading resource {}. {}", id.toString(), e.toString());
    }

    @Override
    public void reload(ResourceManager manager)
    {
        LHData.clear();
        manager.findResources(Constants.PARENT_CONFIG_PATH + "/" + Constants.ENTITY_CONFIG_PATH, id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) ->
        {
            try
            {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                LHData.entities.merge(Objects.requireNonNull(JsonCodec.from(data, EntityConfig.class, null)));
            } catch (Exception e)
            {
                error(id, e);
            }
        });
        manager.findResources(Constants.PARENT_CONFIG_PATH + "/" + Constants.WEAPON_CONFIG_PATH, id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) ->
        {
            try
            {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                LHData.weapons.merge(Objects.requireNonNull(JsonCodec.from(data, WeaponConfig.class, null)));
            } catch (Exception e)
            {
                error(id, e);
            }
        });
        manager.findResources(Constants.PARENT_CONFIG_PATH + "/" + Constants.DIFFICULTY_CONFIG_PATH, id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) ->
        {
            try
            {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                LHData.difficulties.merge(Objects.requireNonNull(JsonCodec.from(data, WorldDifficultyConfig.class, null)));
            } catch (Exception e)
            {
                error(id, e);
            }
        });
        manager.findResources(Constants.PARENT_CONFIG_PATH + "/" + Constants.TRAIT_CONFIG_PATH, id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) ->
        {
            try
            {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                LHData.traits.put(id.withPath(s ->
                {
                    int li = s.lastIndexOf('/');
                    int ri = s.lastIndexOf(".json");
                    if (li != -1 && ri != -1)
                    {
                        return s.substring(li + 1, ri);
                    }
                    return s;
                }), Objects.requireNonNull(JsonCodec.from(data, TraitConfig.Config.class, null)));
            } catch (Exception e)
            {
                error(id, e);
            }
        });
    }
}
