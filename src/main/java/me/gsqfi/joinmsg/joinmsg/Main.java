package me.gsqfi.joinmsg.joinmsg;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin implements Listener {
    public static String joinMsg;
    public static String quitMsg;
    public static final Map<String, ConCom> conComMap = new HashMap<>();

    public static String papi(OfflinePlayer player, String content) {
        return PlaceholderAPI.setPlaceholders(player, content.replace('&','§'));
    }

    @Override
    public void onEnable() {
        reloadConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand(this.getDescription().getName().toLowerCase()).setExecutor(this);
        getLogger().info("§aPlugin loaded!");
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        conComMap.clear();

        FileConfiguration config = this.getConfig();
        ConfigurationSection cpt = config.getConfigurationSection("component");
        for (String key : cpt.getKeys(false)) {
            conComMap.put(key, new ConCom(
                    key,
                    cpt.getString(key + ".display"),
                    ClickEvent.Action.valueOf(cpt.getString(key + ".click.type")),
                    cpt.getString(key + ".click.content"),
                    cpt.getString(key + ".hover")
            ));
        }

        joinMsg = config.getString("joinMsg").replace('&','§');
        quitMsg = config.getString("quitMsg").replace('&','§');
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou do not have permission!");
            return false;
        }
        reloadConfig();
        sender.sendMessage("§aReloaded!");
        return false;
    }
}