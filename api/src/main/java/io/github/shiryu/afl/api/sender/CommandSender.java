package io.github.shiryu.afl.api.sender;

import org.jetbrains.annotations.NotNull;

public interface CommandSender {

    void sendMessage(@NotNull final String message);

    boolean hasPermission(@NotNull final String permission);


}
