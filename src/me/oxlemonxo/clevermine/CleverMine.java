package me.oxlemonxo.clevermine;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import me.oxlemonxo.clevermine.listening.ChatListener;
import org.bukkit.configuration.file.FileConfiguration;

public class CleverMine extends JavaPlugin
{

    public static CleverMine plugin;
    public static Server server;
    public FileConfiguration config;

    @Override
    public void onLoad()
    {
        CleverMine.plugin = this;
        CleverMine.server = plugin.getServer();
        config = plugin.getConfig();
        Log.setServerLogger(server.getLogger());
        Log.setServerLogger(server.getLogger());
    }

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        server.getPluginManager().registerEvents(new ChatListener(), CleverMine.plugin);
    }

    @Override
    public void onDisable()
    {
     
    }

}
