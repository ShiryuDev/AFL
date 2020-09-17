package io.github.shiryu.afl.bukkit;

import io.github.shiryu.afl.api.CommandBase;
import io.github.shiryu.afl.api.CommandManager;
import io.github.shiryu.afl.api.annotations.Command;
import io.github.shiryu.afl.api.annotations.Optional;
import io.github.shiryu.afl.api.annotations.Sender;
import io.github.shiryu.afl.api.completer.impl.BooleanCompleter;
import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import io.github.shiryu.afl.api.immutable.ImmutableParameter;
import io.github.shiryu.afl.api.transformer.impl.*;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BukkitCommandManager extends CommandManager<Plugin> {

    private Plugin plugin;
    private CommandMap commandMap;
    private final List<org.bukkit.command.Command> bukkitCommands = new ArrayList<>();

    @Override
    public void registerCommand(@NotNull CommandBase base) {
        final Class<?> commandClazz = base.getClass();

        for (Method method : commandClazz.getMethods()){
            final Command commandAnnotation = method.getAnnotation(Command.class);

            if (commandAnnotation == null) continue;
            final List<ImmutableParameter> parameters = new ArrayList<>();


            for (int i = 1; i < method.getParameterTypes().length; i++){
                final Class<?> elementType = method.getParameterTypes()[i];

                parameters.add(
                        new ImmutableParameter(
                                elementType.getSimpleName() + "-" + i,
                                method.getAnnotation(Optional.class) != null,
                                method.getAnnotation(Sender.class) != null,
                                elementType
                        )
                );
            }

            final BukkitImmutableCommand immutable = new BukkitImmutableCommand(
                    base,
                    parameters,
                    method,
                    commandAnnotation.aliases(),
                    commandAnnotation.permissions(),
                    commandAnnotation.description(),
                    commandAnnotation.usage(),
                    commandAnnotation.target()
            );

            commands.add(
                    immutable
            );

            final BukkitCommand command = new BukkitCommand(
                    this.plugin,
                    new BukkitExecutor(
                            this
                    ),
                    immutable
            );

            for (String alias : immutable.getAliases())
                commandMap.register(alias, command);

            bukkitCommands.add(
                    command
            );

            commands.sort(Comparator.comparingInt(o -> o.getName().length()));

        }
    }

    @Override
    public void handle(@NotNull final Plugin plugin) {
        this.plugin = plugin;

        this.commandMap = getCommandMap();

        registerTransformer(Boolean.class, new BooleanTransformer());
        registerTransformer(Double.class, new DoubleTransformer());
        registerTransformer(Float.class, new FloatTransformer());
        registerTransformer(Integer.class, new IntegerTransformer());
        registerTransformer(Long.class, new LongTransformer());

        registerCompleter(Boolean.class, new BooleanCompleter());

    }

    @NotNull
    public CommandMap getCommandMap() {
        try {
            PluginManager manager = plugin.getServer().getPluginManager();
            Field field = manager.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return CommandMap.class.cast(field.get(manager));
        } catch (NoSuchFieldException | IllegalAccessException error) {
            throw new IllegalStateException("could not find CommandMap from server", error);
        }
    }

}
