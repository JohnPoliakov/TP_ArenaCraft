package net.johnpoliakov.tutomc.managers;

import net.johnpoliakov.tutomc.utils.GameSettings;
import net.johnpoliakov.tutomc.utils.GameState;
import net.johnpoliakov.tutomc.utils.VFX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    private GameState state = GameState.LOBBY;
    private final HashMap<UUID, Integer> score = new HashMap<>();
    private final HashMap<UUID, Long> lastKill = new HashMap<>();
    private final ScoreboardManager scoreboardManager = new ScoreboardManager();


    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isRunning(){
        return state == GameState.RUNNING;
    }

    public boolean isLobby(){
        return state == GameState.LOBBY;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public HashMap<UUID, Integer> getScore() {
        return score;
    }

    public int getPlayerScore(UUID uuid){

        return score.getOrDefault(uuid, 0);

    }

    // On compatbilise le kill pour le joueur précisé, si la limite est atteinte, on stop la partie
    public void incrementScore(UUID uuid){
        if(score.containsKey(uuid)) {
            score.replace(uuid, score.get(uuid) + 1);
            updateLastKill(uuid);
        }

        if(score.get(uuid) == GameSettings.MAXIMUM_SCORE){

            stop();

        }
    }

    // On enregistre le dernier kill du joueur afin de savoir qui a fait le plus vieux kill en cas d'égalité
    private void updateLastKill(UUID uuid){

        if(lastKill.containsKey(uuid)){
            lastKill.replace(uuid, System.currentTimeMillis());
        }else
            lastKill.put(uuid, System.currentTimeMillis());

    }


    public void startGame(){

        setState(GameState.PREGAME);
        TimeManager.start();

    }

    public void stop(){

        Long tmp = System.currentTimeMillis();
        UUID winner = null;
        int score = 0;

        setState(GameState.LOBBY);

        // On reset les joueurs
        for(Player p : Bukkit.getOnlinePlayers()) {

            p.setInvulnerable(true);
            p.getInventory().clear();
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            p.teleport(GameSettings.LOBBY_SPAWN);

        }

        // On calcule le vainqueur
        for(Map.Entry<UUID, Long> map : lastKill.entrySet()){

            if(this.score.getOrDefault(map.getKey(), 0) > score){

                score = getPlayerScore(map.getKey());
                tmp = map.getValue();
                winner = map.getKey();

            }else if(this.score.getOrDefault(map.getKey(), 0) == score && map.getValue() < tmp) {
                score = getPlayerScore(map.getKey());
                tmp = map.getValue();
                winner = map.getKey();
            }

        }

        VFX.notifyAll("§aLe joueur §e"+Bukkit.getPlayer(winner).getName()+" §aa gagné la partie avec §6"+score+ " §akills !", Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST);

        TimeManager.reset();
        this.score.clear();
        scoreboardManager.reset();

    }

}
