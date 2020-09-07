package io.github.shiryu.afl.api.completer;

import io.github.shiryu.afl.api.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandCompleter {

    @NotNull
    List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String command, @NotNull final String[] args);
}
