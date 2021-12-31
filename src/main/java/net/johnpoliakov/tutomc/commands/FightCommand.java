package net.johnpoliakov.tutomc.commands;

import net.johnpoliakov.tutomc.TutoMC;
import net.johnpoliakov.tutomc.utils.GameSettings;
import net.johnpoliakov.tutomc.utils.VFX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FightCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        // On traite si la commande vient d'un joueur et si ce dernier est OP
        if(sender instanceof Player p && p.isOp()){

            if(TutoMC.getInstance().getGameManager().isLobby()){

                // Si la partie n'est pas déjà commencé et si le nombre de joueurs minimum est atteint alors on lance la partie
                if(Bukkit.getOnlinePlayers().size() >= GameSettings.MINIMUM_PLAYERS)
                    TutoMC.getInstance().getGameManager().startGame();
                else
                    VFX.notifyPlayer(p, "§cIl n'y a pas assez de joueurs", Sound.ENTITY_VILLAGER_NO);

            }else{

                VFX.notifyPlayer(p, "§cLa partie est déjà en cours", Sound.ENTITY_VILLAGER_NO);
            }
            return true;

        }


        return false;
    }

}
