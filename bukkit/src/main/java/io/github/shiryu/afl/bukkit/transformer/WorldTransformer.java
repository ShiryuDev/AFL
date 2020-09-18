package io.github.shiryu.afl.bukkit.transformer;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class WorldTransformer implements Transformer<World> {

    @Override
    public @NotNull World transform(@NotNull final CommandSender sender, @NotNull final String value) {
        final World world = Bukkit.getWorld(value);

        if (world == null)
            return null;

        return world;
    }
}
