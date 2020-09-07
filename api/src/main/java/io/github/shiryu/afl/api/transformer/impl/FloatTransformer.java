package io.github.shiryu.afl.api.transformer.impl;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class FloatTransformer implements Transformer<Float> {

    @Override
    public @NotNull Float transform(@NotNull final CommandSender sender, @NotNull final String value) {
        if (value.toLowerCase(Locale.ENGLISH).contains("e"))
            return null;


        try {
            float parsed = Float.parseFloat(value);

            if (Float.isNaN(parsed) || !Float.isFinite(parsed))
                return 0.0f;


            return parsed;
        } catch (NumberFormatException exception) {
            return 0.0f;
        }
    }
}
