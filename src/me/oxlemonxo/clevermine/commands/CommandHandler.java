package me.oxlemonxo.clevermine.commands;   

import me.oxlemonxo.clevermine.Log;
import me.oxlemonxo.clevermine.CleverMine;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Credit to TF

public class CommandHandler
{
    public static final String COMMAND_PATH = BaseCommand.class.getPackage().getName();
    public static final String COMMAND_PREFIX = "Command_";

    public static boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        final Player playerSender;
        final boolean senderIsConsole;

        if (sender instanceof Player)
        {
            senderIsConsole = false;
            playerSender = (Player) sender;

            Log.info(String.format("[PLAYER_COMMAND] %s (%s): /%s %s",
                    playerSender.getName(),
                    ChatColor.stripColor(playerSender.getDisplayName()),
                    commandLabel,
                    StringUtils.join(args, " ")), true);
        }
        else
        {
            senderIsConsole = true;
            playerSender = null;

            Log.info(String.format("[CONSOLE_COMMAND] %s: /%s %s",
                    sender.getName(),
                    commandLabel,
                    StringUtils.join(args, " ")), true);
        }

        final BaseCommand dispatcher;
        try
        {
            final ClassLoader classLoader = CleverMine.class.getClassLoader();
            dispatcher = (BaseCommand) classLoader.loadClass(String.format("%s.%s%s",
                    COMMAND_PATH,
                    COMMAND_PREFIX,
                    cmd.getName().toLowerCase())).newInstance();
            dispatcher.setup(CleverMine.plugin, sender, dispatcher.getClass());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Log.severe("Could not load command: " + cmd.getName());
            Log.severe(ex);

            sender.sendMessage(ChatColor.RED + "Command Error! Could not load command: " + cmd.getName());
            return true;
        }

        try
        {
            return dispatcher.run(sender, playerSender, cmd, commandLabel, args, senderIsConsole);
        }
        catch (Exception ex)
        {
            Log.severe("Command Error: " + commandLabel);
            Log.severe(ex);
            sender.sendMessage(ChatColor.RED + "Command Error: " + ex.getMessage());
        }

        return true;
    }
}