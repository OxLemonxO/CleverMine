package me.oxlemonxo.clevermine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.oxlemonxo.clevermine.listening.ChatListener;

public class CleverMine extends JavaPlugin
{

    public static CleverMine plugin;
    public static Server server;

    @Override
    public void onLoad()
    {
        CleverMine.plugin = this;
        CleverMine.server = plugin.getServer();
        Log.setServerLogger(server.getLogger());
        Log.setServerLogger(server.getLogger());
    }

    @Override
    public void onEnable()
    {
        server.getPluginManager().registerEvents(new ChatListener(), CleverMine.plugin);
    }

    @Override
    public void onDisable()
    {
     
    }

}
