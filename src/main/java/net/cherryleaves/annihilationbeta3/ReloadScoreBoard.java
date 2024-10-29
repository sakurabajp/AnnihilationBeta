package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

import static net.cherryleaves.annihilationbeta3.Annihilation_Beta3.*;

public class ReloadScoreBoard {
    public void StartAnniScoreBoard(){
        ScoreboardManager managerTime = Bukkit.getScoreboardManager();
        Scoreboard boardTime = Objects.requireNonNull(managerTime).getNewScoreboard();
        Objective objectiveT = boardTime.registerNewObjective("NexusHP", "dummy", ChatColor.RED + " ANNI" + ChatColor.YELLOW + "HI" + ChatColor.BLUE + "LATI" + ChatColor.GREEN + "ON ");
        Objects.requireNonNull(objectiveT).setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score8 = Objects.requireNonNull(objectiveT).getScore(none);
        score8.setScore(8);
        Score score7 = Objects.requireNonNull(objectiveT).getScore(ChatColor.GOLD + "Map: " + ChatColor.BOLD + "Korustal");
        score7.setScore(7);
        Score score6 = Objects.requireNonNull(objectiveT).getScore(ChatColor.DARK_GREEN + none);
        score6.setScore(6);
        Score score5 = Objects.requireNonNull(objectiveT).getScore(ChatColor.RED + "Red Nexus: " + ChatColor.AQUA + RedNexus);
        score5.setScore(5);
        Score score4 = Objects.requireNonNull(objectiveT).getScore(ChatColor.BLUE + "Blue Nexus: " + ChatColor.AQUA + BlueNexus);
        score4.setScore(4);
        Score score3 = Objects.requireNonNull(objectiveT).getScore(ChatColor.YELLOW + "Yellow Nexus: " + ChatColor.AQUA + YellowNexus);
        score3.setScore(3);
        Score score2 = Objects.requireNonNull(objectiveT).getScore(ChatColor.GREEN + "Green Nexus: " + ChatColor.AQUA + GreenNexus);
        score2.setScore(2);
        Score score1 = Objects.requireNonNull(objectiveT).getScore(ChatColor.RED + none);
        score1.setScore(1);
        Score score0 = Objects.requireNonNull(objectiveT).getScore(ChatColor.GOLD + "mc.cherry-leaves.net");
        score0.setScore(0);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(boardTime);
        }
    }
}
