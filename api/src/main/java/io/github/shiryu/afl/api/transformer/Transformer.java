package io.github.shiryu.afl.api.transformer;

import io.github.shiryu.afl.api.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Transformer<T> {

    @NotNull
    T transform(@NotNull final CommandSender sender, @NotNull final String value);

}
