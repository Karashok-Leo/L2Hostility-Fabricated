package karashokleo.l2hostility.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class HostilitySpawner extends Block
{
    public static final EnumProperty<State> STATE = EnumProperty.of("state", State.class);

    public HostilitySpawner()
    {
        super(
            FabricBlockSettings
                .copyOf(Blocks.SPAWNER)
                .strength(50F, 1200F)
                .luminance(state -> state.get(STATE).light())
                .nonOpaque()
        );
        this.setDefaultState(this.getDefaultState().with(STATE, State.IDLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(STATE);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(STATE, State.IDLE);
    }

    public enum State implements StringIdentifiable
    {
        IDLE(7),
        ACTIVATED(11),
        CLEAR(15),
        FAILED(11);

        private final int light;

        State(int light)
        {
            this.light = light;
        }

        public int light()
        {
            return light;
        }

        @Override
        public String asString()
        {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
