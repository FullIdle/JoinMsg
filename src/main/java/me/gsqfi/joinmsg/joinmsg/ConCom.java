package me.gsqfi.joinmsg.joinmsg;

import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.OfflinePlayer;

@Getter
public class ConCom {
    private final String key;
    private final String display;
    private final ClickEvent.Action clickAc;
    private final String clickContent;
    private final String hoverContent;

    public ConCom(String key, String display, ClickEvent.Action clickAc, String clickContent, String hoverContent) {
        this.key = key;
        this.display = display;
        this.clickAc = clickAc;
        this.clickContent = clickContent;
        this.hoverContent = hoverContent;
    }

    public BaseComponent[] compose(OfflinePlayer player) {
        ComponentBuilder builder = new ComponentBuilder(Main.papi(player, display));
        builder.event(new ClickEvent(clickAc, Main.papi(player, clickContent)));
        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(Main.papi(player,hoverContent)).create()));
        return builder.create();
    }
}
