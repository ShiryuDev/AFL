package io.github.shiryu.afl.transformer;

import io.github.shiryu.afl.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Transformer<T> {

    @NotNull
    T transform(@NotNull final CommandSender sender, @NotNull final String value);

}
