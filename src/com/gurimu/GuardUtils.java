package com.gurimu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Harrison on 14/06/14.
 */
public class GuardUtils implements Listener {


    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.hasPermission("guardstick.nohunger")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.hasPermission("guardstick.nodrop")){
            event.getDrops().clear();
            applyPotions(player);
        }
        if(player.hasPermission("guardstick.drop")){
            player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHER_STAR));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("guardstick.speed")){
            applyPotions(player);
        }
        if(player.hasPermission("guardstick.menu")){
            if(!(player.getInventory().contains(new ItemStack(Material.BOOK, 1)))){
                ItemStack item = new ItemStack(Material.BOOK, 1);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(ChatColor.BLUE + "Guard Menu");
                item.setItemMeta(im);
                player.getInventory().addItem(item);
            }
        }
    }
    public void applyPotions(Player player){
        int speed = getConfigInt(".potions.speed", player);
        int resistance = getConfigInt(".potions.resistance", player);
        int regeneration = getConfigInt(".potions.resistance", player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, speed));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, resistance));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, regeneration));
    }

    public int getConfigInt(String path, Player player){
        FileConfiguration config = GuardStick.plugin.getConfig();
        String name = player.getName();
        if(player.hasPermission("guardstick.guard")){
            String string = "admin" + path;
            return config.getInt(string);
        } else if(player.hasPermission("guardstick.moderator")){
            String string = "admin" + path;
            return config.getInt(string);
        } else if(player.hasPermission("guardstick.admin")){
            String string = "admin" + path;
            return config.getInt(string);
        } else return 0;
    }
}
