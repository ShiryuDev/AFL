package io.github.shiryu.afl.api.completer.impl;

import com.google.common.collect.ImmutableMap;
import io.github.shiryu.afl.api.completer.Completer;
import io.github.shiryu.afl.api.sender.CommandSender;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BooleanCompleter implements Completer {

    private final Map<String, Boolean> TYPES = ImmutableMap.<String, Boolean>builder()
            .put("true", true)
            .put("on", true)
            .put("yes", true)
            .put("false", false)
            .put("off", false)
            .put("no", false)
            .build();

    @Override
    public @NotNull List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String value) {
        return TYPES.keySet()
                .parallelStream()
                .filter(key -> StringUtils.startsWithIgnoreCase(key, value))
                .collect(Collectors.toList());
    }
}
