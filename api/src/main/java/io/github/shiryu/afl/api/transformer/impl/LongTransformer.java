package io.github.shiryu.afl.api.transformer.impl;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LongTransformer implements Transformer<Long> {

    @Override
    public @NotNull Long transform(@NotNull final CommandSender sender, @NotNull final String value) {
        if (value.toLowerCase(Locale.ENGLISH).contains("e"))
            return 0l;

        try {
            long parsed = Long.parseLong(value);

            if (Double.isNaN(parsed) || !Double.isFinite(parsed))
                return 0l;


            return parsed;
        } catch (NumberFormatException exception) {
            return 0l;
        }
    }
}
