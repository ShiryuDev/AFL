package io.github.shiryu.afl.completer;

import io.github.shiryu.afl.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandCompleter {

    @NotNull
    List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String command);
}
