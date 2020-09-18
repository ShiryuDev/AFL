package io.github.shiryu.afl.bukkit;

import io.github.shiryu.afl.api.CommandBase;
import io.github.shiryu.afl.api.CommandManager;
import io.github.shiryu.afl.api.annotations.Command;
import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import io.github.shiryu.afl.api.immutable.ImmutableParameter;
import io.github.shiryu.afl.api.sender.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BukkitImmutableCommand extends ImmutableCommand {

    public BukkitImmutableCommand(@NotNull final CommandBase base,
                                  @NotNull final List<ImmutableParameter> parameters,
                                  @NotNull final Method method,
                                  @NotNull final String[] aliases,
                                  @NotNull final String[] permission,
                                  @NotNull final String description,
                                  @NotNull final String usage,
                                  @NotNull final Command.Target target) {
        super(base, parameters, method, aliases, permission, description, usage, target);
    }

    @Override
    public void execute(@NotNull final CommandManager commandManager, @NotNull final CommandSender sender, @NotNull final String command, @NotNull final String[] args) {
        final List<Object> transformedParameters = new ArrayList<>();

        if (sender instanceof BukkitCommandSender) transformedParameters.add(((BukkitCommandSender)sender).getSender());

        for (int i = 0; i < getParameters().size(); i++){
            final ImmutableParameter parameter = getParameters().get(i);

            if (parameter.getElementType().equals(String[].class)){
                transformedParameters.add(args);

                continue;
            }

            String passedParameter = (i < args.length ? args[i] : "").trim();

            if (parameter.isOptional() && passedParameter.isEmpty())
                return;

            if (i >= args.length && !parameter.isOptional() && passedParameter.isEmpty())
                return;

            final Object result = commandManager.transform(sender, passedParameter, parameter.getElementType());

            if (result == null)
                return;

            transformedParameters.add(result);
        }

        try {
            method.invoke(base, transformedParameters.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
