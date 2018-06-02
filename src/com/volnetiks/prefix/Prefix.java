package com.volnetiks.prefix;

import com.volnetiks.prefix.command.PrefixCommand;
import com.volnetiks.prefix.events.AsyncPlayerChat;
import com.volnetiks.prefix.events.PlayerJoin;
import com.volnetiks.prefix.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Volnetiks on 16/05/2018 for Minecraft
 */
public class Prefix extends JavaPlugin {

    PluginManager pm = Bukkit.getPluginManager();

    private PlayerManager playerManager = new PlayerManager(this);

    @Override
    public void onEnable() {
        config();
        registerCommands();
        registerEvent();
        tabChange();
    }

    public void tabChange() {
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();

                for(Player player : getServer().getOnlinePlayers()) {
                    reloadConfig();
                    Team team = scoreboard.getTeam(player.getName());
                    if(team == null) {
                        team = scoreboard.registerNewTeam(player.getName());
                    }
                    team.setPrefix(playerManager.getPrefix(player).replace("&", "§") + " ");
                    team.addEntry(player.getName());
                }
            }
        }, 0, 40);
    }

    private void config() {
        reloadConfig();
        getConfig().options().copyDefaults(true);
    }

    private void registerEvent() {
        pm.registerEvents(new AsyncPlayerChat(playerManager), this);
        pm.registerEvents(new PlayerJoin(playerManager, this), this);
    }

    private void registerCommands() {
        getCommand("prefix").setExecutor(new PrefixCommand(this, playerManager));
    }

}
