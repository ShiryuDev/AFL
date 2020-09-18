package io.github.shiryu.afl.api;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import io.github.shiryu.afl.api.completer.Completer;
import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import io.github.shiryu.afl.api.immutable.ImmutableParameter;
import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public abstract class CommandManager<T> {

    protected final List<ImmutableCommand> commands = new ArrayList<>();

    protected final Map<Class<?>, Transformer> transformers = new HashMap<>();
    protected final Map<Class<?>, Completer> completers = new HashMap<>();

    public <P> void registerCompleter(@NotNull final Class<P> elementType, @NotNull final Completer completer){
        if (this.completers.containsKey(elementType))
            return;

        this.completers.put(elementType, completer);
    }

    public <P> void registerTransformer(@NotNull final Class<P> elementType, @NotNull final Transformer<P> transformer){
        if (this.transformers.containsKey(elementType))
            return;

        this.transformers.put(elementType, transformer);
    }

    @NotNull
    public Object transform(@NotNull final CommandSender sender, @NotNull final String value, @NotNull final Class<?> elementType){
        final Transformer transformer = this.transformers.get(elementType);

        if (transformer == null)
            return null;

        return elementType.equals(String.class) ? value : transformer.transform(sender, value);
    }

    @NotNull
    public List<String> getSuggestions(@NotNull final CommandSender sender, @NotNull final String cmdLine){
        final String[] split = cmdLine.split(" ", -1);

        final ImmutableCommand command = findCommand(cmdLine);

        if (command == null)
            return Lists.newArrayList();

        if (!command.canAccess(sender))
            return Lists.newArrayList();

        if (split.length <= 1){
            final List<String> suggestions = Lists.newArrayList();

            for (String alias : command.getAliases())
                suggestions.add(alias);

            return suggestions;
        }else{
            final List<String> suggestions = Lists.newArrayList();

            for (String alias : command.getAliases()){
                int index = (cmdLine.split(" ").length - alias.split(" ").length);

                if (index == command.getParameters().size() || !cmdLine.endsWith(" "))
                    index = index - 1 < 0 ? 0 : index - 1;

                final ImmutableParameter parameter = command.getParameters().get(index);

                for (String suggestion : getCompleter(sender, cmdLine.endsWith(" ") ? "" : split[split.length - 1], parameter.getElementType()))
                    suggestions.add(suggestion);

                return suggestions;
            }
        }

        return Collections.emptyList();
    }

    @NotNull
    public List<String> getCompleter(@NotNull final CommandSender sender, @NotNull final String value,
                                       @NotNull final Class<?> elementType){
        if (!completers.containsKey(elementType)) return Lists.newArrayList();

        return completers.get(elementType).getSuggestions(sender, value);
    }

    @NotNull
    public ImmutableCommand findCommand(@NotNull final String name){
        return commands.parallelStream()
                .filter(command -> command.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public abstract void registerCommand(@NotNull final CommandBase base);

    public abstract void handle(@NotNull final T plugin);
}
