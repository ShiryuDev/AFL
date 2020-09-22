package io.github.shiryu.afl.bukkit;

import com.google.common.collect.Lists;
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

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String string, @NotNull final String[] args) {
        final ImmutableCommand command = commandManager.findCommand(cmd.getName());

        if (command == null)
            return false;

        switch(command.getTarget()){
            case PLAYER:
                if (!(sender instanceof Player))
                    return false;

                break;
            case CONSOLE:
                if (!(sender instanceof ConsoleCommandSender))
                    return false;

                break;
            default:
                break;
        }


        command.execute(
                commandManager,
                new BukkitCommandSender(sender),
                string,
                args
        );

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return commandManager.getSuggestions(new BukkitCommandSender(commandSender), s);
    }
}
