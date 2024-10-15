package net.cherryleaves.annihilationbeta3;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public final class Annihilation_Beta3 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new GUI(), this);
        CreateTeam();
        Objects.requireNonNull(getCommand("anni")).setExecutor(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    String FC = ChatColor.LIGHT_PURPLE + "Click to join a game";
    String FB = ChatColor.GRAY + "Click to toggle player visibility";
    String FSB = ChatColor.AQUA + "Shotbow Swag";
    String FCA = ChatColor.AQUA + "Social menu";
    String FF = ChatColor.WHITE + "Fly hack item";
    String FS = ChatColor.YELLOW + "Click to join an event";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("anni")) {
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            StartAnni();
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.teleport(new Location(p.getWorld(), 5000.5, 4.0, 5000.5, 90, 0));
        e.setJoinMessage(null);
        p.sendMessage(ChatColor.GREEN + "Welcome back " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.GREEN + ", You are currently the rank of...");
        Inventory pe = p.getInventory();
        pe.clear();
        pe.setItem(0, new GUI().ItemMeta(Material.COMPASS,  FC));
        pe.setItem(1, new GUI().ItemMeta(Material.BLAZE_ROD,  FB));
        pe.setItem(4, new GUI().ItemMeta(Material.BLACK_SHULKER_BOX,  FSB));
        pe.setItem(6, new GUI().ItemMeta(Material.CAKE,  FCA));
        pe.setItem(7, new GUI().ItemMeta(Material.FEATHER,  FF));
        pe.setItem(8, new GUI().ItemMeta(Material.SAND,  FS));
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action action = e.getAction();
        Block b = e.getClickedBlock();
        if(e.getItem() == null || e.getItem().getItemMeta() == null){
            return;
        } else {
            e.getItem().getItemMeta().getDisplayName();
        }
        String j = e.getItem().getItemMeta().getDisplayName();
        Material t = e.getItem().getType();
        if (action != Action.PHYSICAL) {
            ItemStack itemInHand = p.getInventory().getItemInMainHand(); // プレイヤーの手に持っているアイテムを取得
            if (itemInHand.getType().isAir()) {
                return;
            }
            if(j.equals(FC) && t.equals(Material.COMPASS)){
                new GUI().JoinGame(p);
                e.setCancelled(true);
            }
            else if(j.equals(FB) && t.equals(Material.BLAZE_ROD)){
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            }
            else if(j.equals(FSB) && t.equals(Material.BLACK_SHULKER_BOX)){
                new GUI().Swag(p);
                e.setCancelled(true);
            }
            else if(j.equals(FCA) && t.equals(Material.CAKE)){
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            }
            else if(j.equals(FF) && t.equals(Material.FEATHER)){
                if(p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.sendMessage(ChatColor.RED + "Disallow Flight");
                }
                else if(!p.getAllowFlight()){
                    p.setAllowFlight(true);
                    p.sendMessage(ChatColor.GREEN + "Allow Flight");
                }
            }
            else if(j.equals(FS) && t.equals(Material.SAND)){
                p.sendMessage(ChatColor.GRAY + "要らんやろこれ");
                e.setCancelled(true);
            }
            else if(j.equals(ChatColor.GOLD + "Select Team") && t.equals(Material.NETHER_STAR)){
                new GUI().SelectTeam(p);
                e.setCancelled(true);
            }
            else if(j.equals(ChatColor.AQUA + "Select Map") && t.equals(Material.MAP)){
                new GUI().SelectMap(p);
                e.setCancelled(true);
            }
            else if(j.equals(ChatColor.AQUA + "Select Class") && t.equals(Material.FEATHER)){
                new SelectClass().ClassList(p);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        ItemStack i = e.getItemDrop().getItemStack();
        ItemMeta im = i.getItemMeta();
        String imd = Objects.requireNonNull(im).getDisplayName();
        if(imd.equals(FB) || imd.equals(FC) || imd.equals(FF) || imd.equals(FCA) || imd.equals(FSB) || imd.equals(FS)){
            e.setCancelled(true);
        }
        if(imd.equals(new GUI().FF) || imd.equals(new GUI().FB) || imd.equals(new GUI().FN) || imd.equals(new GUI().FM)){
            e.setCancelled(true);
        }
    }

    public void CreateTeam(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = Objects.requireNonNull(manager).getMainScoreboard();

        Team red = scoreboard.getTeam("red");
        if (red == null) {
            // チームが存在しない場合、新しく作成
            red = scoreboard.registerNewTeam("red");
            red.setPrefix(ChatColor.RED.toString());
            red.setColor(ChatColor.RED);
            red.setAllowFriendlyFire(false);
        }
        Team blue = scoreboard.getTeam("blue");
        if (blue == null) {
            // チームが存在しない場合、新しく作成
            blue = scoreboard.registerNewTeam("blue");
            blue.setPrefix(ChatColor.BLUE.toString());
            blue.setColor(ChatColor.BLUE);
            blue.setAllowFriendlyFire(false);
        }
        Team green = scoreboard.getTeam("green");
        if (green == null) {
            // チームが存在しない場合、新しく作成
            green = scoreboard.registerNewTeam("green");
            green.setPrefix(ChatColor.GREEN.toString());
            green.setColor(ChatColor.GREEN);
            green.setAllowFriendlyFire(false);
        }
        Team yellow = scoreboard.getTeam("yellow");
        if (yellow == null) {
            // チームが存在しない場合、新しく作成
            yellow = scoreboard.registerNewTeam("yellow");
            yellow.setPrefix(ChatColor.YELLOW.toString());
            yellow.setColor(ChatColor.YELLOW);
            yellow.setAllowFriendlyFire(false);
        }
    }

    String none = "";

    public void StartAnni(){
        new Nexus().RedNexus = 75;
        new Nexus().BlueNexus = 75;
        new Nexus().YellowNexus = 75;
        new Nexus().GreenNexus = 75;
        ScoreboardManager managerTime = Bukkit.getScoreboardManager();
        Scoreboard boardTime = Objects.requireNonNull(managerTime).getNewScoreboard();
        Objective objectiveT = boardTime.registerNewObjective("NexusHP", "dummy", ChatColor.RED + " ANNI" + ChatColor.YELLOW + "HI" + ChatColor.BLUE + "LATI" + ChatColor.GREEN + "ON ");
        Objects.requireNonNull(objectiveT).setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score8 = Objects.requireNonNull(objectiveT).getScore(none);
        score8.setScore(8);
        Score score7 = Objects.requireNonNull(objectiveT).getScore(ChatColor.GOLD + "Map: Korustal");
        score7.setScore(7);
        Score score6 = Objects.requireNonNull(objectiveT).getScore(ChatColor.DARK_GREEN + none);
        score6.setScore(6);
        Score score5 = Objects.requireNonNull(objectiveT).getScore(ChatColor.RED + "RedNexus: " + ChatColor.AQUA + new Nexus().RedNexus);
        score5.setScore(5);
        Score score4 = Objects.requireNonNull(objectiveT).getScore(ChatColor.BLUE + "BlueNexus: " + ChatColor.AQUA + new Nexus().BlueNexus);
        score4.setScore(4);
        Score score3 = Objects.requireNonNull(objectiveT).getScore(ChatColor.YELLOW + "YellowNexus: " + ChatColor.AQUA + new Nexus().YellowNexus);
        score3.setScore(3);
        Score score2 = Objects.requireNonNull(objectiveT).getScore(ChatColor.GREEN + "GreenNexus: " + ChatColor.GREEN + new Nexus().GreenNexus);
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
