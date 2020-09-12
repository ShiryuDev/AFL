package io.github.shiryu.afl.api.immutable;

import io.github.shiryu.afl.api.CommandBase;
import io.github.shiryu.afl.api.CommandManager;
import io.github.shiryu.afl.api.annotations.Command;
import io.github.shiryu.afl.api.sender.CommandSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public abstract class ImmutableCommand {

    protected  final CommandBase base;
    protected  final List<ImmutableParameter> parameters;
    protected  final Method method;
    protected  final String[] aliases;
    protected  final String[] permission;
    protected  final String description;
    protected  final String usage;
    protected  final Command.Target target;

    public boolean canAccess(@NotNull final CommandSender sender){
        return sender.hasPermission(permission[0]);
    }

    @NotNull
    public String getName(){
        return aliases[0];
    }

    @NotNull
    public String getUsageString() {
        return getUsageString(getName());
    }

    @NotNull
    public String getUsageString(@NotNull final String aliasUsed) {
        StringBuilder stringBuilder = new StringBuilder();

        getParameters().forEach(parameter ->{
                    stringBuilder.append(parameter.isOptional() ? "<" : "[").append(parameter.getName());
                    stringBuilder.append(parameter.isOptional() ? ">" : "]").append(" ");
                });

        return "/" + aliasUsed.toLowerCase(Locale.ENGLISH) + " " + stringBuilder.toString().trim().toLowerCase(Locale.ENGLISH);
    }

    public abstract void execute(@NotNull final CommandManager commandManager, @NotNull final CommandSender sender, @NotNull final String command, @NotNull final String[] args);

}
