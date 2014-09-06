package com.gurimu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;

/**
 * Created by Harrison on 14/06/14.
 */
public class PvP implements Listener {

    public HashMap<String, String> lastDamager = new HashMap<String, String>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        if(!(event.getEntity() instanceof Player)) return;
        Entity entDmger = event.getDamager();
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        boolean melee = meleeAttack(target, damager);
        boolean range = rangedAttack(target, entDmger);
        setLastDamager(target, damager);
        durabillity(target);
        if(melee == true || range == true){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(!(entity instanceof Player)) return;
        Player player = (Player) entity;
        durabillity(player);
    }

    public boolean meleeAttack(Player target, Player damager){
        if((damager.hasPermission("guardstick.nodamage") && (target.hasPermission("guardstick.nodamage")))){
            return true;
        }
        return false;
    }

    public boolean rangedAttack(Player target, Entity damager){
        if(damager instanceof Arrow){
            Arrow arrow = (Arrow) damager;
            if(!(arrow.getShooter() instanceof Player)) return false;
            Player shooter = (Player) arrow.getShooter();
            if(shooter.hasPermission("guardstick.nodamage") && target.hasPermission("guardstick.nodamage")) return true;
        }return  false;
    }

    @EventHandler
    public void onFishEvent(PlayerFishEvent event){
        if(!(event.getCaught() instanceof Player)) return;
        Player player = event.getPlayer();
        Player target = (Player) event.getCaught();

        if(player.hasPermission("guardstick.nodamage") && target.hasPermission("guardstick.nodamage")){
            event.setCancelled(true);
        }
        return;
    }

    public void setLastDamager(Player target, Player damager){
        if(!(target.hasPermission("guardstick.listen"))) return;
        if(lastDamager.get(target.getName()) == damager.getName()) return;
        lastDamager.put(target.getName(), damager.getName());
        target.sendMessage((ChatColor.GREEN + "You were hit by " + ChatColor.RED + damager.getName() + "."));
    }

    public void durabillity(Player player){
        if(player.hasPermission("guardstick.durabillity")){
            short d = 0;
            if(!(player.getInventory().getBoots() == null)){
                player.getInventory().getBoots().setDurability(d);
            }
            if(!(player.getInventory().getChestplate() == null)){
                player.getInventory().getChestplate().setDurability(d);
            }
            if(!(player.getInventory().getLeggings() == null)){
                player.getInventory().getLeggings().setDurability(d);
            }
            if(!(player.getInventory().getHelmet() == null)){
                player.getInventory().getHelmet().setDurability(d);
            }
        }
    }
}
