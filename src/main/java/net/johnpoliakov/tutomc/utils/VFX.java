package net.johnpoliakov.tutomc.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.Duration;

public class VFX {


    // Joue un son à un joueur donné
    public static void playSound(Player p, Sound sound){

        p.playSound(p.getLocation(), sound, 0.5f, 1.f);

    }

    // Joue un son à tout les joueurs du serveur
    public static void playSoundToAll(Sound sound){

        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(p.getLocation(), sound, 0.5f, 1.f);

    }


    // Envoie un message à un joueur donné
    public static void notifyPlayer(Player p, String message, Sound sound){
        p.sendMessage(Component.text(message));
        if(sound != null)
            playSound(p, sound);
    }


    // Envoie un message à tout les joueurs du serveur
    public static void notifyAll(String message, Sound sound){
        Bukkit.broadcast(Component.text(message));
        if(sound != null)
            playSoundToAll(sound);
    }


    // Envoie un title à un joueur donné
    public static void sendTitle(Player p, String title, String subtitle, long fadeIn, long stay, long fadeOut){

        p.showTitle(Title.title(Component.text(title), Component.text(subtitle), Title.Times.of(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut))));

    }


}
