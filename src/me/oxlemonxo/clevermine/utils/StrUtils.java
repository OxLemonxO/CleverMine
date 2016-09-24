package me.oxlemonxo.clevermine.utils;

import org.bukkit.ChatColor;


public class StrUtils 
{
    public static String colorize(String str, char chr)
    {
        return ChatColor.translateAlternateColorCodes(chr, str);
    }
    public static String colorize(String str)
    {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    
    public static String stripColorCodes(String str, char chr)
    {
        String translated = colorize(str, chr);
        return ChatColor.stripColor(translated);
    }
    public static String stripColorCodes(String str)
    {
        String translated = colorize(str);
        return ChatColor.stripColor(translated);
    }
}
