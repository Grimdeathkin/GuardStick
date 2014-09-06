package com.gurimu;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harrison on 03/05/14.
 */
public class GuardKit {

    FileConfiguration config = GuardStick.plugin.getConfig();
    public int protection;

    public GuardKit (){

    }

    public GuardKit(Player player, Color helm, Color colour){

        guardArmour(Material.LEATHER_HELMET, player, helm);
        guardArmour(Material.LEATHER_CHESTPLATE, player, colour);
        guardArmour(Material.LEATHER_LEGGINGS, player, colour);
        guardArmour(Material.LEATHER_BOOTS, player, colour);
        guardWeapon(player);
        guardBow(player);
        giveItem(player, Material.ARROW, 1);
        giveConfiscator(player);
    }

    public void guardArmour(Material mat, Player player, Color colour){
        ItemStack item = new ItemStack(mat);
        LeatherArmorMeta im = (LeatherArmorMeta) item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + player.getName());
        im.setLore(lore);
        im.setColor(colour);
        if(player.hasPermission("guardstick.guard")){
            im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, config.getInt("Guard.Protection"), true);
        } else if(player.hasPermission("guardstick.moderator")){
            im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, config.getInt("Moderator.Protection"), true);
        } else if(player.hasPermission("guardstick.admin")){
            im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, config.getInt("Admin.Protection"), true);
        }
        im.addEnchant(Enchantment.DURABILITY, 10, true);

        item.setItemMeta(im);
        player.getInventory().addItem(item);

    }
    public void guardWeapon(Player player){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta im = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + player.getName());
        if(player.hasPermission("guardstick.guard")){
            im.addEnchant(Enchantment.DAMAGE_ALL, config.getInt("Guard.Sharpness"), true);
        } else if(player.hasPermission("guardstick.moderator")){
            im.addEnchant(Enchantment.DAMAGE_ALL, config.getInt("Moderator.Sharpness"), true);
        } else if(player.hasPermission("guardstick.admin")){
            im.addEnchant(Enchantment.DAMAGE_ALL, config.getInt("Admin.Sharpness"), true);
        }
        im.addEnchant(Enchantment.DURABILITY, 2, true);
        item.setItemMeta(im);
        player.getInventory().addItem(item);
    }

    public void guardBow(Player player){
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta im = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + player.getName());
        im.setLore(lore);
        im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        im.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
        im.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
        im.addEnchant(Enchantment.DURABILITY, 2, true);
        item.setItemMeta(im);
        player.getInventory().addItem(item);
    }

    public void giveItem(Player player, Material mat, int Amount ){
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GREEN + player.getName());
        im.setLore(lore);
        item.setItemMeta(im);
        player.getInventory().addItem(item);

    }
    public void giveConfiscator(Player player){
         ItemStack Confiscator = new ItemStack(Material.getMaterial(config.getString("Guard.Confiscator")),1);;
        if(!(config.getString(player.getName() + ".confiscator") == null)){
            Confiscator = new ItemStack(Material.valueOf(config.getString(player.getName() + ".Confiscator")),1);
        } else if(player.hasPermission("guardstick.guard")){
            Confiscator = new ItemStack(Material.valueOf(config.getString("Guard.Confiscator")),1);
        } else if(player.hasPermission("guardstick.moderator")){
            Confiscator = new ItemStack(Material.valueOf(config.getString("Moderator.Confiscator")), 1);
        } else if(player.hasPermission("guardstick.admin")){
            Confiscator = new ItemStack(Material.valueOf(config.getString("Admin.Confiscator")),1);
        }
        ItemMeta im = Confiscator.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "Confiscator");
        im.addEnchant(Enchantment.KNOCKBACK, 5, true);
        Confiscator.setItemMeta(im);
        player.getInventory().addItem(Confiscator);
    }

}
