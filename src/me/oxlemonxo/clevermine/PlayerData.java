package me.oxlemonxo.clevermine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData
{

    public static final Map<String, PlayerData> PLAYER_DATA = new HashMap<String, PlayerData>(); // ip,data
    public static final long AUTO_PURGE = 20L * 60L * 5L;

    public static String getIp(Player player)
    {
        return player.getPlayer().getAddress().getAddress().getHostAddress().trim();
    }

    public static boolean hasPlayerData(Player player)
    {
        return PLAYER_DATA.containsKey(getIp(player));
    }

    public static PlayerData getPlayerDataSync(Player player)
    {
        synchronized (PLAYER_DATA)
        {
            return getPlayerData(player);
        }
    }

    public static PlayerData getPlayerData(Player player)
    {
        final String ip = getIp(player);

        PlayerData data = PlayerData.PLAYER_DATA.get(ip);

        if (data != null)
        {
            return data;
        }

        if (Bukkit.getOnlineMode())
        {
            for (PlayerData dataTest : PLAYER_DATA.values())
            {
                if (dataTest.player.getName().equalsIgnoreCase(player.getName()))
                {
                    data = dataTest;
                    break;
                }
            }
        }

        if (data != null)
        {
            return data;
        }

        data = new PlayerData(player, player.getUniqueId(), ip);
        PlayerData.PLAYER_DATA.put(ip, data);

        return data;
    }
    //
    private final Player player;
    private final String ip;
    private final UUID uuid;
    //
    private boolean isEnabled = true;
    private boolean isPersonal = true;

    private PlayerData(Player player, UUID uuid, String ip)
    {
        this.player = player;
        this.uuid = uuid;
        this.ip = ip;
    }

    public String getIpAddress()
    {
        return this.ip;
    }

    public UUID getUniqueId()
    {
        return uuid;
    }

    public void enable()
    {
        this.isEnabled = true;
    }

    public void disable()
    {
        this.isEnabled = false;
    }

    public boolean isEnabled()
    {
        return this.isEnabled;
    }

    public void setGlobal()
    {
        this.isPersonal = false;
    }

    public void setPersonal()
    {
        this.isPersonal = true;
    }
    
    public boolean isPersonal()
    {
        return this.isPersonal;
    }
}
