package com.gurimu;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Harrison on 14/06/14.
 */
public class DisabledBlocks implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block == null) return;
        ItemStack item = event.getPlayer().getItemInHand();
        if(item == null) return;
        boolean blocks = disabledBlocks(block, event.getPlayer());
        boolean items = disabledItems(item, event.getPlayer());
        boolean trade = tradingBlocks(block, event.getPlayer());

        if((blocks == true) || (items == true) || (trade == true)){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can not do that");
        }
    }

    @EventHandler
    public void villagerTrade(PlayerInteractEntityEvent event){
        if(event.getRightClicked().getType().equals(EntityType.VILLAGER)){
            if(!(event.getPlayer().hasPermission("guardstick.villager"))){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You can not trade with villagers");
            }
        }
    }

    public boolean disabledBlocks(Block block, Player player){
        FileConfiguration config = GuardStick.plugin.getConfig();
        List<String> disabledBlocks = config.getStringList("DisabledBlocks");
        if(!(player.hasPermission("guardstick.disabledblocks"))){
            for(String name : disabledBlocks){
                if(block.getType().toString().equalsIgnoreCase(name)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean disabledItems(ItemStack item, Player player){
        FileConfiguration config = GuardStick.plugin.getConfig();
        List<String> disabledItems = config.getStringList("DisabledItems");
        if(!(player.hasPermission("guardstick.disableditems"))){
            for(String name : disabledItems){
                if(item.getType().toString().equalsIgnoreCase(name)){
                    return true;
                }
            }
        } return false;
    }
    public boolean tradingBlocks(Block block,Player player){
        if(player.hasPermission("guardstick.trade")){
            if(block.getType() == Material.CHEST || block.getType() == Material.FURNACE || block.getType() == Material.BREWING_STAND || block.getType() == Material.DISPENSER ||
                    block.getType() == Material.DROPPER || block.getType() == Material.HOPPER || block.getType() == Material.BURNING_FURNACE){
                player.sendMessage(ChatColor.RED + "No trading");
                return true;
            }
        } return false;
    }

}
