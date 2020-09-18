package io.github.shiryu.afl.bukkit.transformer;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import io.github.shiryu.afl.bukkit.BukkitCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerTransformer implements Transformer<Player> {

    @Override
    public @NotNull Player transform(@NotNull final CommandSender sender, @NotNull final String value) {
        if (sender instanceof BukkitCommandSender && (value.equalsIgnoreCase("self") || value.isEmpty())){
            final BukkitCommandSender bukkitSender = (BukkitCommandSender) sender;

            if (bukkitSender.getSender() instanceof Player)
                return (Player) bukkitSender.getSender();
        }

        final Player player = Bukkit.getPlayer(value);

        if (player == null){
            return null;
        }

        return player;
    }
}
