package com.volnetiks.prefix.command;

import com.volnetiks.prefix.Prefix;
import com.volnetiks.prefix.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Volnetiks on 16/05/2018 for Minecraft
 */
public class PrefixCommand implements CommandExecutor {

    private Prefix main;
    private PlayerManager playerManager;

    public PrefixCommand(Prefix prefix) {
        main = prefix;
    }

    public PrefixCommand(Prefix prefix, PlayerManager playerManager) {
        main = prefix;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("prefix.command")) {
                if(args.length >= 1) {

                    if(args[0].equalsIgnoreCase("list")) {
                        for(String p : main.getConfig().getConfigurationSection("prefix").getKeys(true)) {
                            Player playerP = Bukkit.getPlayer(p);
                            if(playerP == null) {
                                return false;
                            }
                            String prefix = main.getConfig().getString("prefix." + playerP.getName().replace("&", "§"));
                            if(!prefix.equals("&7[Joueur]")) {
                                player.sendMessage(ChatColor.GREEN + "Prefix of " + ChatColor.GOLD + playerP.getName() + ChatColor.GREEN + ": " + ChatColor.GOLD + prefix);
                            }
                        }
                        return true;
                    }

                    if(args.length >= 2) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(args[1].equalsIgnoreCase("reset")) {
                            main.getConfig().getConfigurationSection("prefix").set(target.getName(), "&7[Joueur]");
                            main.saveConfig();
                            player.sendMessage(ChatColor.GREEN + "The prefix of " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " has been reset!");
                            return true;
                        } else {
                            String prefix = args[1];
                            prefix = prefix.replace("&", "§");
                            if(target != null) {
                                player.sendMessage(ChatColor.GREEN + "You have set to " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " the prefix " + ChatColor.GOLD + prefix + ChatColor.GREEN + "!");
                                main.getConfig().getConfigurationSection("prefix").set(target.getName(), prefix);
                                main.saveConfig();
                                if(!playerManager.getPlayers().contains(target)) playerManager.getPlayers().add(target);
                            } else {
                                player.sendMessage(ChatColor.RED + "This player don't exist!");
                            }
                            return true;
                        }
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null) {
                            if(playerManager.havePrefix(target)) {
                                player.sendMessage(ChatColor.GREEN + "The prefix of " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " is " + ChatColor.GOLD + playerManager.getPrefix(target, player));
                            } else {
                                player.sendMessage(ChatColor.GREEN + "The player " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " don't have prefix!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "This player don't exist!");
                        }
                        return true;
                    }
                } else {
                    if(playerManager.havePrefix(player)) {
                        player.sendMessage(ChatColor.GREEN + "Your prefix is " + playerManager.getPrefix(player));
                    } else {
                        player.sendMessage(ChatColor.GREEN + "You don't have prefix!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You dont have permission to do that!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You are not allowed to do this!");
        }
        return false;
    }
}
