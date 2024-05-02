package net.karashokleo.l2hostility.content.screen.tab;

import dev.xkmc.l2tabs.tabs.content.BaseTextScreen;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import net.karashokleo.l2hostility.client.L2HostilityClient;
import net.karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.karashokleo.l2hostility.content.component.chunk.SectionDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import net.karashokleo.l2hostility.content.logic.TraitManager;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DifficultyScreen extends BaseTextScreen
{
    protected DifficultyScreen(Text title)
    {
        super(title, new Identifier("l2tabs:textures/gui/empty.png"));
    }

    public void init()
    {
        super.init();
        new TabManager<>(this, new InvTabData()).init(this::addDrawableChild, L2HostilityClient.TAB_DIFFICULTY);
    }

    public void render(DrawContext context, int mx, int my, float ptick)
    {
        super.render(context, mx, my, ptick);
        int x = this.leftPos + 8;
        int y = this.topPos + 6;
        List<Pair<Text, Supplier<List<Text>>>> list = new ArrayList<>();
        addDifficultyInfo(list, Formatting.DARK_RED, Formatting.DARK_GREEN, Formatting.DARK_PURPLE);
        addRewardInfo(list);
        List<Text> tooltip = null;
        for (var c : list)
        {
            if (mx >= x && mx <= x + textRenderer.getWidth(c.getLeft()) && my >= y && my <= y + 10)
                tooltip = c.getRight() == null ? null : c.getRight().get();
            context.drawText(this.textRenderer, c.getLeft(), x, y, 0, false);
            y += 10;
        }
        if (tooltip != null && !tooltip.isEmpty())
            context.drawTooltip(this.textRenderer, tooltip, mx, my);
    }

    public static void addRewardInfo(List<Pair<Text, Supplier<List<Text>>>> list)
    {
        PlayerEntity player = L2HostilityClient.getClientPlayer();
        assert player != null;
        PlayerDifficulty cap = PlayerDifficulty.get(player);
        list.add(new Pair<>(LHTexts.INFO_REWARD.get(cap.getRewardCount()).formatted(Formatting.DARK_GREEN), List::of));
    }

    public static void addDifficultyInfo(List<Pair<Text, Supplier<List<Text>>>> list, Formatting... formats)
    {
        // red, green, gold
        PlayerEntity player = L2HostilityClient.getClientPlayer();
        assert player != null;
        PlayerDifficulty cap = PlayerDifficulty.get(player);
        list.add(new Pair<>(LHTexts.INFO_PLAYER_LEVEL.get(cap.getLevel().getStr()), cap::getPlayerDifficultyDetail));
        int perc = Math.round(100f * cap.getLevel().getExp() / cap.getLevel().getMaxExp());
        list.add(new Pair<>(LHTexts.INFO_PLAYER_EXP.get(perc), List::of));
        int maxCap = cap.getRankCap();
        list.add(new Pair<>(LHTexts.INFO_PLAYER_CAP.get(
                maxCap > TraitManager.getMaxLevel() ?
                        LHTexts.TOOLTIP_LEGENDARY.get().formatted(formats[2]) : maxCap), List::of));
        var opt = ChunkDifficulty.at(player.getWorld(), player.getBlockPos());
        if (opt.isPresent())
        {
            ChunkDifficulty chunk = opt.get();
            SectionDifficulty sec = chunk.getSection(player.getBlockPos().getY());
            if (sec.isCleared())
                list.add(new Pair<>(LHTexts.INFO_CHUNK_CLEAR.get().formatted(formats[1]), List::of));
            else
            {
                MobDifficultyCollector ins = new MobDifficultyCollector();
                chunk.modifyInstance(player.getBlockPos(), ins);
                list.add(new Pair<>(LHTexts.INFO_CHUNK_LEVEL.get(ins.getBase()).formatted(formats[0]),
                        () -> sec.getSectionDifficultyDetail(player)));
                list.add(new Pair<>(LHTexts.INFO_CHUNK_SCALE.get(ins.scale).formatted(formats[0]), List::of));
            }
        }
    }
}
