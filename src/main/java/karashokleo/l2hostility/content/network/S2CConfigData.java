package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.data.config.DifficultyConfig;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHData;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SerialClass
public class S2CConfigData implements SerialPacketS2C
{
    @SerialClass.SerialField
    public Map<Identifier, TraitConfig.Config> configs = new HashMap<>();
    @SerialClass.SerialField
    public DifficultyConfig difficultyConfig;

    @Deprecated
    public S2CConfigData()
    {
    }

    public S2CConfigData(Map<Identifier, TraitConfig.Config> configs, DifficultyConfig difficultyConfig)
    {
        this.configs = configs;
        this.difficultyConfig = difficultyConfig;
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        TraitConfig traitConfig = new TraitConfig();
        this.configs.forEach(traitConfig::put);
        LHData.traits = traitConfig;
        DifficultyConfig difficultyConfig = new DifficultyConfig();
        difficultyConfig.merge(this.difficultyConfig);
        LHData.difficulties = difficultyConfig;
    }

    public static S2CConfigData create()
    {
        return new S2CConfigData(LHData.traits.getAll(), LHData.difficulties);
    }
}
