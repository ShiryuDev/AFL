package io.github.shiryu.afl.api.immutable;

import io.github.shiryu.afl.api.CommandBase;
import io.github.shiryu.afl.api.annotations.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public class ImmutableCommand {

    private final CommandBase base;
    private final List<ImmutableParameter> parameters;
    private final String[] aliases;
    private final String description;
    private final String permission;
    private final String usage;
    private final Command.Target target;

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
}
