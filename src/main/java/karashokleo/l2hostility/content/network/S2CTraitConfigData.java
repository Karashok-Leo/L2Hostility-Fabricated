package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHData;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SerialClass
public class S2CTraitConfigData implements SerialPacketS2C
{
    @SerialClass.SerialField
    public Map<Identifier, TraitConfig.Config> configs = new HashMap<>();

    @Deprecated
    public S2CTraitConfigData()
    {
    }

    public S2CTraitConfigData(Map<Identifier, TraitConfig.Config> configs)
    {
        this.configs = configs;
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        TraitConfig traitConfig = new TraitConfig();
        configs.forEach(traitConfig::put);
        LHData.traits = traitConfig;
    }

    public static S2CTraitConfigData create()
    {
        return new S2CTraitConfigData(LHData.traits.getAll());
    }
}
