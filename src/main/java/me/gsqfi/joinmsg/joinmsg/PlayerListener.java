package me.gsqfi.joinmsg.joinmsg;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        BaseComponent[] compose = compose(e.getPlayer(), Main.joinMsg);
        Bukkit.getServer().spigot().broadcast(compose);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        System.out.println("test");
        e.setQuitMessage(null);
        BaseComponent[] compose = compose(e.getPlayer(), Main.quitMsg);
        Bukkit.getServer().spigot().broadcast(compose);
    }

    public static BaseComponent[] compose(OfflinePlayer player, String content) {
        ComponentBuilder builder = new ComponentBuilder("");
        while (content.contains("{") && content.contains("}")) {
            int s = content.indexOf('{');
            int e = content.indexOf('}');
            if (s > e) {
                continue;
            }
            BaseComponent[] compose = Main.conComMap.get(content.substring(s + 1, e)).compose(player);
            if (s != 0) {
                builder.append(new ComponentBuilder(content.substring(0, s)).create(), ComponentBuilder.FormatRetention.NONE);
            }
            builder.append(compose);
            content = content.substring(e+1);
        }
        builder.append(new ComponentBuilder(content).create(), ComponentBuilder.FormatRetention.NONE);
        return builder.create();
    }
}
