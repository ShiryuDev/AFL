package io.github.shiryu.afl.bukkit;

import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BukkitExecutor implements CommandExecutor, TabCompleter {

    private final BukkitCommandManager commandManager;
    private final BukkitImmutableCommand command;

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String string, @NotNull final String[] args) {
        switch(this.command.getTarget()){
            case PLAYER:
                if (!(sender instanceof Player))
                    return false;

                break;
            case CONSOLE:
                if (!(sender instanceof ConsoleCommandSender))
                    return false;

                break;
        }


        this.command.execute(
                commandManager,
                new BukkitCommandSender(sender),
                string,
                args
        );

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
