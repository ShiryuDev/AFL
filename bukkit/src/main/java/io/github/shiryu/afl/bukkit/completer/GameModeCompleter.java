package io.github.shiryu.afl.bukkit.completer;

import com.google.common.collect.ImmutableMap;
import io.github.shiryu.afl.api.completer.Completer;
import io.github.shiryu.afl.api.sender.CommandSender;
import org.bukkit.GameMode;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameModeCompleter implements Completer {

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
    public @NotNull List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String value) {
        return TYPINGS.keySet().stream()
                .filter(s -> StringUtil.startsWithIgnoreCase(s, value))
                .collect(Collectors.toList());
    }
}
