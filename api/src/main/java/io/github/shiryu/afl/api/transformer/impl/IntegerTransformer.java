package io.github.shiryu.afl.api.transformer.impl;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

public class IntegerTransformer implements Transformer<Integer> {

    @Override
    public @NotNull Integer transform(@NotNull final CommandSender sender, @NotNull final String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}
