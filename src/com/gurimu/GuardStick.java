package com.gurimu;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


/**
 * Created by Harrison on 05/03/14.
 */
public final class GuardStick extends JavaPlugin implements Listener{

    public static GuardStick plugin;
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new GuardMenu(), this);
        this.getServer().getPluginManager().registerEvents(new GuardUtils(), this);
        this.getServer().getPluginManager().registerEvents(new PvP(), this);
        this.getServer().getPluginManager().registerEvents(new Confiscate(), this);
        this.getServer().getPluginManager().registerEvents(new DisabledBlocks(), this);
        this.plugin = this;
        saveDefaultConfig();
        this.getCommand("GuardEdit").setExecutor(new GuardCommands());
        this.getCommand("countdown").setExecutor(new GuardCommands());
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {

    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEco(){
        return econ;
    }
}