package io.github.shiryu.afl.bukkit;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class BukkitCommand extends Command implements PluginIdentifiableCommand {

    protected final Plugin plugin;
    protected final CommandExecutor executor;
    protected final TabCompleter completer;
    protected final List<String> permissions;

    public BukkitCommand(@NotNull final Plugin plugin, @NotNull final BukkitExecutor executor, @NotNull final ImmutableCommand command){
        this(plugin, executor, executor, command);
    }

    public BukkitCommand(@NotNull final Plugin plugin, @NotNull final CommandExecutor executor,
                         @NotNull final TabCompleter completer, @NotNull final ImmutableCommand command){
        super(
                command.getName(),
                command.getDescription(),
                command.getUsageString(),
                Lists.newArrayList(command.getAliases())
        );

        this.plugin = plugin;
        this.executor = executor;
        this.completer = completer;
        this.permissions = Arrays.asList(command.getPermission());

        super.setPermission(Joiner.on(";").join(permissions));
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(@NotNull final CommandSender sender, @NotNull final String label, @NotNull final String[] args) {
        return executor.onCommand(sender, this, label, args);
    }

    @Override
    public List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, @NotNull final String[] args)
            throws IllegalArgumentException {
        if (completer != null) {
            List<String> suggestions = completer.onTabComplete(sender, this, alias, args);
            if (suggestions != null) {
                return suggestions;
            }
        }
        return super.tabComplete(sender, alias, args);
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
