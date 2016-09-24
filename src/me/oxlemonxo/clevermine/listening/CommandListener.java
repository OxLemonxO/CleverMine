package me.oxlemonxo.clevermine.listening;

import static me.oxlemonxo.clevermine.CleverMine.plugin;
import me.oxlemonxo.clevermine.Log;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e)
    {
        Player player = e.getPlayer();
        String command = e.getMessage();

        if(plugin.debugmode)
        {
            Log.debug("Event " + e.getEventName() + " triggered.");
        }
        
        if (plugin.config.getStringList("commandaliases").contains(command.replaceFirst("/", "").split(" ")[0]))
        {
            plugin.getServer().dispatchCommand((CommandSender) player, "clevermine " + command.replaceFirst(command.split(" ")[0], ""));
            e.setCancelled(true);
        }
        else
        {
            e.setCancelled(false);
            return;
        }
    }
}
