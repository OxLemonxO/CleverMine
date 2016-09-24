package me.oxlemonxo.clevermine.commands;

import me.oxlemonxo.clevermine.PlayerData;
import me.oxlemonxo.clevermine.utils.StrUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

@CommandPermissions(source = SourceType.ONLY_IN_GAME) 
@CommandParameters(description = "Main command", usage = "/<command> <on | off | personal | global>")
public class Command_clevermine extends BaseCommand
{

    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        PlayerData data = PlayerData.getPlayerData(sender_p);
        String botPrefix = StrUtils.colorize(plugin.config.getString("bot.prefix")).replace("%botname%",StrUtils.colorize(plugin.config.getString("bot.name")));

        if (args.length != 1)
        {
            return false;
        }
        switch (args[0].toLowerCase())
        {
            case "on":
            {
                if (data.isEnabled())
                {
                    sender.sendMessage(botPrefix + " Bot is already enabled for you.");
                    return true;
                }
                data.enable();
                sender.sendMessage(botPrefix + " Bot has been successfully enabled for you.");
                return true;
            }

            case "off":
            {
                if (!data.isEnabled())
                {
                    sender.sendMessage(botPrefix + " Bot is already disabled for you.");
                    return true;
                }
                data.disable();
                sender.sendMessage(botPrefix + " Bot has been successfully disabled for you.");
                return true;
            }

            case "personal":
            {
                if (data.isPersonal())
                {
                    sender.sendMessage(botPrefix + " Mode is already personal.");
                    return true;
                }
                data.setPersonal();
                sender.sendMessage(botPrefix + " Mode set to personal.");
                return true;
            }

            case "global":
            {
                if(plugin.config.getBoolean("disableglobal"))
                {
                    sender.sendMessage(botPrefix + " Global bot messages have been disabled.");
                    data.setPersonal();
                    return true;
                }
                if (!data.isPersonal())
                {
                    sender.sendMessage(botPrefix + " Mode is already global.");
                    return true;
                }
                data.setGlobal();
                sender.sendMessage(botPrefix + " Mode set to global.");
                return true;
            }

            default:
            {
                return false;
            }
        }
    }
}
