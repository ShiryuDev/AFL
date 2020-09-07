package io.github.shiryu.afl.api.transformer.impl;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class DoubleTransformer implements Transformer<Double> {

    @Override
    public @NotNull Double transform(@NotNull final CommandSender sender, @NotNull final String value) {
        if (value.toLowerCase(Locale.ENGLISH).contains("e"))
            return null;

        try {
            double parsed = Double.parseDouble(value);

            if (Double.isNaN(parsed) || !Double.isFinite(parsed))
                return 0.0d;

            return parsed;
        } catch (NumberFormatException exception) {
            return 0.0d;
        }
    }
}
