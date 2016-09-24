package me.oxlemonxo.clevermine.listening;

import static me.oxlemonxo.clevermine.CleverMine.plugin;
import me.oxlemonxo.clevermine.Log;
import me.oxlemonxo.clevermine.PlayerData;
import me.oxlemonxo.clevermine.cleverbot.ChatterBot;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotFactory;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotSession;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotType;
import me.oxlemonxo.clevermine.utils.StrUtils;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatListener implements Listener
{

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String trigger = plugin.config.getString("bot.trigger").replace("%botname%", StrUtils.stripColorCodes(plugin.config.getString("bot.name")));
        String msg = event.getMessage().toLowerCase();
        String message = event.getMessage().replace(trigger, "");

        Log.debug("Event " + event.getEventName() + " triggered.");
        
        if (msg.startsWith(trigger.toLowerCase()))
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

                            ChatterBotFactory factory = new ChatterBotFactory();
                            ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
                            ChatterBotSession session = bot.createSession();
                            if (data.isPersonal())
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.formatting").replace("%botname%", ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name"))).replace("%message%", session.think(message))));
                            } else
                            {

                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.formatting").replace("%botname%", ChatColor.translateAlternateColorCodes('&', plugin.config.getString("bot.name"))).replace("%message%", session.think(message))));
                            }
                        }
                    } catch (Exception ex)
                    {
                        Log.severe(ex);
                    }
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
