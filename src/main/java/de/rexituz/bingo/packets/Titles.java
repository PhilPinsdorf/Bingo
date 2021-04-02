package de.rexituz.bingo.packets;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Titles {
    public static void sendTitle(Player p, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut){
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;

        PacketPlayOutTitle PacketPlayOutTime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(PacketPlayOutTime);
        if(subtitle != null){
            IChatBaseComponent TitleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle PacketPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, TitleSub);
            connection.sendPacket(PacketPlayOutSubTitle);
        }
        if(title != null) {
            IChatBaseComponent Title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle PacketPlayOutTitleMain = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, Title);
            connection.sendPacket(PacketPlayOutTitleMain);
        }
    }

    public static void sendActionBar(Player p, String actionbar){
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;

        IChatBaseComponent Actionbar = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + actionbar + "\"}");
        PacketPlayOutChat PacketPlayOutActionbar = new PacketPlayOutChat(Actionbar, (byte)2);
        connection.sendPacket(PacketPlayOutActionbar);
    }
}
