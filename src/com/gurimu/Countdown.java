package com.gurimu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Harrison on 26/04/14.
 */
public class Countdown extends BukkitRunnable {
    private final JavaPlugin plugin;
    private int i;
    private Player player;
    private Player target;

    public Countdown(JavaPlugin plugin, int i, Player player, Player target){
        this.plugin = plugin;
        this.i = i;
        this.player = player;
        this.target = target;
    }

    @Override
    public void run(){
        if(i >= 0){
            player.sendMessage(ChatColor.GREEN + Integer.toString(i));
            target.sendMessage(ChatColor.GREEN + Integer.toString(i));
            i--;
        }else{
            this.cancel();
        }
    }
}
