package io.github.shiryu.afl.bukkit.transformer;

import com.google.common.collect.ImmutableMap;
import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GameModeTransformer implements Transformer<GameMode> {

    private final Map<String, GameMode> TYPINGS = ImmutableMap.<String, GameMode>builder()
            .put("a", GameMode.ADVENTURE)
            .put("adventure", GameMode.ADVENTURE)
            .put("2", GameMode.ADVENTURE)
            .put("c", GameMode.CREATIVE)
            .put("creative", GameMode.CREATIVE)
            .put("1", GameMode.CREATIVE)
            .put("s", GameMode.SURVIVAL)
            .put("survival", GameMode.SURVIVAL)
            .put("0", GameMode.SURVIVAL)
            .build();

    @Override
    public @NotNull GameMode transform(@NotNull CommandSender sender, @NotNull String value) {
        if (!TYPINGS.containsKey(value))
            return null;


        return TYPINGS.get(value);
    }
}
