package io.github.shiryu.afl.bukkit;

import io.github.shiryu.afl.api.sender.CommandSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class BukkitCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    @Override
    public void sendMessage(@NotNull final String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(@NotNull final String permission) {
        return sender.hasPermission(permission);
    }
}
