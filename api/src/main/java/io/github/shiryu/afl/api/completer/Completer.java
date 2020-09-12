package io.github.shiryu.afl.api.completer;

import io.github.shiryu.afl.api.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Completer {

    @NotNull
    List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String value);
}
