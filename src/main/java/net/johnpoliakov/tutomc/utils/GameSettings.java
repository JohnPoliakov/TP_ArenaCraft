package net.johnpoliakov.tutomc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

// Ensemble de constante servant de configuration à notre jeu
public class GameSettings {

    public final static Location LOBBY_SPAWN = new Location(Bukkit.getWorld("world"), 156.5, 35.5, -7.5);
    public final static Location ARENA_SPAWN = new Location(Bukkit.getWorld("world"), 16.5, 55, -10.5);
    public final static Location ARENA_SAFE_ZONE_CORNER_1 = new Location(Bukkit.getWorld("world"), 25, 60, -20);
    public final static Location ARENA_SAFE_ZONE_CORNER_2 = new Location(Bukkit.getWorld("world"), 10, 50, -5);
    public final static int MINIMUM_PLAYERS = 2;
    public final static int MAXIMUM_SCORE = 5;

}
