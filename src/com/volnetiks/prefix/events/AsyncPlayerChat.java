package com.volnetiks.prefix.events;

import com.volnetiks.prefix.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Volnetiks on 16/05/2018 for Minecraft
 */
public class AsyncPlayerChat implements Listener {

    private PlayerManager playerManager;

    public AsyncPlayerChat(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(playerManager.havePrefix(player)) {
            event.setFormat(playerManager.getPrefix(player) + " " + player.getName() + " >> " + ChatColor.GRAY + event.getMessage());
            return;
        }
        event.setFormat(ChatColor.GRAY + player.getName() + " >> " + event.getMessage());
    }

}
