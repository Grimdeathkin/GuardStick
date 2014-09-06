package com.gurimu;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Harrison on 03/05/14.
 */
public class GuardCommands implements CommandExecutor {
    private GuardStick plugin = GuardStick.plugin;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(cmd.getName().equalsIgnoreCase("countdown")){
            if(sender.hasPermission("guardstick.countdown")){
                if(!(sender instanceof Player)) return false;
                Player player = (Player) sender;
                if(!(args.length == 1)){
                    player.sendMessage(ChatColor.RED + "Correct usage: /countdown <player>");
                }
                if(args.length == 1){
                    Player target = player.getServer().getPlayer(args[0]);
                    if(target != null && target.isOnline()){
                        BukkitTask task = new Countdown(this.plugin, 5, player, target).runTaskTimer(this.plugin, 0L, 20L);
                        }
                    }return true;
                }

        }if (cmd.getName().equalsIgnoreCase("GuardEdit")){
            if(!(args.length == 2)){
                sender.sendMessage("/guardedit <path> <value>");
                return false;}
            String path = args[0];
            if(args[1] == null) return false;
            int newValue = Integer.parseInt(args[1]);
            plugin.getConfig().set(path, newValue);
            plugin.saveConfig();
            plugin.reloadConfig();
            sender.sendMessage("Config edited");
        } return false;

    }
}
