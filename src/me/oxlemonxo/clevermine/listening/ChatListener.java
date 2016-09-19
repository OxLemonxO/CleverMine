package me.oxlemonxo.clevermine.listening;

import me.oxlemonxo.clevermine.CleverMine;
import me.oxlemonxo.clevermine.Log;
import me.oxlemonxo.clevermine.cleverbot.ChatterBot;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotFactory;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotSession;
import me.oxlemonxo.clevermine.cleverbot.ChatterBotType;
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
    public static void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith("Hey bot,"))
        {
            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    try
                    {
                        String msg = message.replace("Hey bot,", "");
                        ChatterBotFactory factory = new ChatterBotFactory();
                        ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
                        ChatterBotSession session = bot.createSession();
                        Bukkit.broadcastMessage("Bot > " + session.think(msg));
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
