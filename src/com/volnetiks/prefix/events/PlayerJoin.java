package com.volnetiks.prefix.events;

import com.volnetiks.prefix.Prefix;
import com.volnetiks.prefix.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Volnetiks on 24/05/2018 for Minecraft
 */
public class PlayerJoin implements Listener {

    private PlayerManager playerManager;
    private Prefix main;

    public PlayerJoin(PlayerManager playerManager, Prefix prefix) {
        this.playerManager = playerManager;
        this.main = prefix;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerManager.getPlayers().add(player);

        Scoreboard scoreboard = main.getServer().getScoreboardManager().getMainScoreboard();

        Team team = scoreboard.getTeam(player.getName());
        if(team == null) {
            team = scoreboard.registerNewTeam(player.getName());
            team.setPrefix(playerManager.getPrefix(player).replace("&", "§") + " ");
            team.addEntry(player.getName());
        }

        event.setJoinMessage(playerManager.getPrefix(player) + " " + player.getName() + ChatColor.GREEN + " has join the server!");
    }

}
