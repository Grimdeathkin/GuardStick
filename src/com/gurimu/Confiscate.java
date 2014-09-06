package com.gurimu;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Harrison on 14/06/14.
 */
public class Confiscate implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof Player)) return;
        Player target = (Player) event.getRightClicked();
        Player guard = event.getPlayer();
        confiscateItem(guard, target);
    }

    public void confiscateItem(Player guard, Player target){
        FileConfiguration config = GuardStick.plugin.getConfig();
        List<String> items = config.getStringList("ConfiscatedItems");
        if(!(guard.hasPermission("guardstick.take"))) return;
        Material mat = getConfiscator(guard);
        if(mat == null) return;
        if(!(target.hasPermission("guardstick.take"))){
            for(int i = 0; i < items.size(); i++){
                if((target.getItemInHand().getType() == Material.getMaterial(items.get(i))) && guard.getItemInHand().getType() == mat){
                    guard.sendMessage(ChatColor.GREEN + "You destroyed " + target.getName() + "'s " + target.getItemInHand().getType());
                    int reward = 15 + (int)(Math.random() * 100);
                    EconomyResponse r = GuardStick.econ.depositPlayer(guard.getName(), reward);
                    target.sendMessage(ChatColor.RED + "Your " + target.getItemInHand().getType()  + " has been destroyed by " + guard.getName() + ".");
                    target.setItemInHand(new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }

    public Material getConfiscator(Player guard){
        FileConfiguration config = GuardStick.plugin.getConfig();
        if(guard.hasPermission("GuardStick.Guard")){
            return Material.getMaterial(config.getString("Guard.Confiscator"));
        } else if(guard.hasPermission("GuardStick.Moderator")){
            return Material.getMaterial(config.getString("Moderator.Confiscator"));
        } else if(guard.hasPermission("GuardStick.Admin")){
            return Material.getMaterial(config.getString("Admin.Confiscator"));
        }
        return null;
    }

}
