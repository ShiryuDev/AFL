package io.github.shiryu.afl.bukkit.transformer;

import io.github.shiryu.afl.api.sender.CommandSender;
import io.github.shiryu.afl.api.transformer.Transformer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OfflinePlayerTransformer implements Transformer<OfflinePlayer> {

    @Override
    public @NotNull OfflinePlayer transform(@NotNull CommandSender sender, @NotNull String value) {
        OfflinePlayer player;

        try{
            player = Bukkit.getOfflinePlayer(UUID.fromString(value));
        }catch (Exception e){
            player = Bukkit.getOfflinePlayer(value);
        }

        if (player == null){
            return null;
        }

        return player;
    }
}
