package com.gurimu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harrison on 09/06/14.
 */
public class GuardMenu implements Listener {

    public static Inventory menu = Bukkit.createInventory(null, 9, "Guard Menu");


    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getPlayer().getItemInHand().getType().equals(Material.BOOK)){
            if(event.getPlayer().hasPermission("guardstick.menu")){
                Player player = event.getPlayer();
                event.setCancelled(true);
                CreateMenu(player);
                player.openInventory(menu);
            }
        }
    }

    public static Inventory CreateMenu(Player player){
        menu.setItem(0, createItem(Material.WOOD_SWORD, "Guard kit(M)", "Guards Only"));
        menu.setItem(1, createItem(Material.GOLD_SWORD, "Guard kit(F)", "Guards Only"));
        menu.setItem(2, createItem(Material.STONE_SWORD, "Mod kit", "Moderators Only"));
        menu.setItem(3, createItem(Material.DIAMOND_SWORD, "Admin kit", "Admins Only"));
        menu.setItem(5, createItem(Material.POTION, "Reapply effects", ""));
        menu.setItem(7, createItem(Material.BOOK, "Rules", ""));
        menu.setItem(8, createItem(Material.PAPER, "Guidelines", "If in doubt read them"));

        return menu;
    }

    public static ItemStack createItem(Material mat, String name, String lore){
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta im = item.getItemMeta();
        List<String> listLore = new ArrayList<String>();
        listLore.add(ChatColor.RED + lore);
        im.setLore(listLore);
        im.setDisplayName(ChatColor.GREEN + name);
        item.setItemMeta(im);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        FileConfiguration config = GuardStick.plugin.getConfig();
        if(inventory.getName().equals(menu.getName())){
            if(clicked.getType() == Material.WOOD_SWORD){
                event.setCancelled(true);
                player.closeInventory();
                new GuardKit(player, Color.WHITE, Color.BLUE);

            }else if(clicked.getType() == Material.GOLD_SWORD){
                event.setCancelled(true);
                player.closeInventory();
                new GuardKit(player, Color.WHITE, Color.PURPLE);

            }else if(clicked.getType() == Material.STONE_SWORD){
                event.setCancelled(true);
                player.closeInventory();
                new GuardKit(player, Color.WHITE, Color.GREEN);

            }else if(clicked.getType() == Material.DIAMOND_SWORD){
                event.setCancelled(true);
                player.closeInventory();
                new GuardKit(player, Color.BLACK, Color.RED);

            }else if(clicked.getType() == Material.POTION){
                event.setCancelled(true);
                player.closeInventory();
                applyPotions(player);

            }else if(clicked.getType() == Material.BOOK){
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(config.getString("Rules"));

            }else if(clicked.getType() == Material.PAPER){
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(config.getString("Guidelines"));

            }
        }
    }
    public void applyPotions(Player player){
        FileConfiguration config = GuardStick.plugin.getConfig();
        int speed = getConfigInt(".Potions.Speed", player);
        int resistance = getConfigInt(".Potions.Resistance", player);
        int regeneration = getConfigInt(".Potions.Regeneration", player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, speed));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, resistance));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, regeneration));
    }

    public int getConfigInt(String path, Player player){
        FileConfiguration config = GuardStick.plugin.getConfig();
        String name = player.getName();
        if(!(config.getString(name + path) == null)){
            return config.getInt(name + path);
        } else if(player.hasPermission("guardstick.guard")){
            return config.getInt("Guard" + path);
        } else if(player.hasPermission("guardstick.moderator")){
            return config.getInt("Moderator" + path);
        } else if(player.hasPermission("guardstick.admin")){
            return config.getInt("Admin" + path);
        } else return 0;
    }
}
