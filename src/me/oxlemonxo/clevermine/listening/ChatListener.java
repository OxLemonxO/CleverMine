package me.oxlemonxo.clevermine.listening;

import me.oxlemonxo.clevermine.CleverMine;
import me.oxlemonxo.clevermine.Log;
import me.oxlemonxo.clevermine.PlayerData;
import me.oxlemonxo.clevermine.cleverbot.ChatterBot;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotFactory;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotSession;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatListener implements Listener
{

    private CleverMine plugin = CleverMine.plugin;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith(plugin.config.getString("bot.trigger").replace("%botname%", ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name"))))))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        PlayerData data = PlayerData.getPlayerData(player);
                        if (data.isEnabled())
                        {
                            String msg = message.replace(plugin.config.getString("bot.trigger").replace("%botname%", ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name")))).toLowerCase(), "");
                            ChatterBotFactory factory = new ChatterBotFactory();
                            ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
                            ChatterBotSession session = bot.createSession();
                            if (data.isPersonal())
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.formatting").replace("%botname%", ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name"))).replace("%message%", session.think(msg))));
                            } 
                            else
                            {

                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.formatting").replace("%botname%", ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name"))).replace("%message%", session.think(msg))));
                            }
                        }
                    } 
                    catch (Exception ex)
                    {
                        Log.severe(ex);
                    }
                }
            }.runTaskAsynchronously(CleverMine.plugin);
        }

    }
}

