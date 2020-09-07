package io.github.shiryu.afl.api.transformer.impl;

import com.google.common.collect.ImmutableMap;
import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BooleanTransformer implements Transformer<Boolean> {

    private final Map<String, Boolean> TYPES = ImmutableMap.<String, Boolean>builder()
            .put("true", true)
            .put("on", true)
            .put("yes", true)
            .put("false", false)
            .put("off", false)
            .put("no", false)
            .build();

    @Override
    public @NotNull Boolean transform(@NotNull final CommandSender sender, @NotNull final String value) {
        if (!TYPES.containsKey(value))
            return false;


        return TYPES.get(value);
    }
}
