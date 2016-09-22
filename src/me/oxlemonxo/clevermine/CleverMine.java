package me.oxlemonxo.clevermine;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import me.oxlemonxo.clevermine.listening.ChatListener;
import me.oxlemonxo.clevermine.commands.CommandLoader;
import me.oxlemonxo.clevermine.commands.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

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
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                CommandLoader.scan();

            }
        }.runTaskLater(plugin, 20L);
    }

    @Override
    public void onDisable()
    {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return CommandHandler.handleCommand(sender, cmd, commandLabel, args);
    }
    
}
