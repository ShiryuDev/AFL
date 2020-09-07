package io.github.shiryu.afl.api;

import io.github.shiryu.afl.api.immutable.ImmutableCommand;
import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class CommandManager<T> {

    protected final List<ImmutableCommand> commands = new ArrayList<>();

    protected final Map<Class<?>, Transformer> transformers = new HashMap<>();

    public <P> void registerTransformer(@NotNull final Class<P> elementType, @NotNull final Transformer<P> transformer){
        if (this.transformers.containsKey(elementType))
            return;

        this.transformers.put(elementType, transformer);
    }

    @NotNull
    public Object transform(@NotNull final CommandSender sender, @NotNull final String value, @NotNull final Class<?> elementType){
        return elementType.equals(String.class) ? value : this.transformers.get(elementType).transform(sender, value);
    }

    public abstract void registerCommand(@NotNull final CommandBase base);

    public abstract void handle(@NotNull final T plugin);
}
