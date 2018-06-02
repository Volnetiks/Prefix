package com.volnetiks.prefix.utils;

import com.volnetiks.prefix.Prefix;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Volnetiks on 18/05/2018 for Minecraft
 */
public class PlayerManager {

    private Prefix prefix;

    ArrayList<Player> players = new ArrayList<>();

    public PlayerManager(Player player, Prefix prefix) {
        players.add(player);
        this.prefix = prefix;
    }

    public PlayerManager(Prefix prefix) {
        this.prefix = prefix;
    }

    public String getPrefix(Player player, Player sender) {
        if(havePrefix(player)) {
            return prefix.getConfig().getString("prefix." + player.getName()).replace("&", "§");
        }
        return null;
    }

    public boolean havePrefix(Player player) {
        return players.contains(player);
    }

    public String getPrefix(Player player) {
        prefix.reloadConfig();
        if(havePrefix(player)) {
            return prefix.getConfig().getConfigurationSection("prefix").getString(player.getName()).replace("&", "§");
        }
        return "&7[Player]";
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
