package net.cherryleaves.annihilationbeta3;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.awt.Color.red;

public final class Annihilation_Beta3 extends JavaPlugin implements Listener {

    public BossBar bossBar;
    public int time;
    public int time2;
    public int timeS;
    public int timeM;
    public static int phase;

    static int RedNexus = 75;
    static int BlueNexus = 75;
    static int YellowNexus = 75;
    static int GreenNexus = 75;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new GUI(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SetNexus(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new NexusBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new NetherGate(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Respawn(), this);
        CreateTeam();
        Objects.requireNonNull(getCommand("anni")).setExecutor(this);
        Objects.requireNonNull(getCommand("nexus")).setExecutor(this);
        Objects.requireNonNull(getCommand("NexusLocation")).setExecutor(this);
        setupBossBar();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (bossBar != null) {
            bossBar.removeAll();
        }
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
            if (!(time <= 0)) {
                return true;
            }
            new ReloadScoreBoard().StartAnniScoreBoard();
            startTimer();
            RedNexus = 75;
            BlueNexus = 75;
            YellowNexus = 75;
            GreenNexus = 75;
        } else if (command.getName().equalsIgnoreCase("nexus")) {
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage("数値を入力してください");
                return false;
            }
            if (args.length >= 2) {
                String TeamNexus = args[0];  // 1番目の引数
                try {
                    Integer.parseInt(args[1]);
                    {
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("２つ目の引数に整数以外を打たないでください");
                }
                int score = Integer.parseInt(args[1]);  // 2番目の引数
                if (Objects.equals(TeamNexus, "Red")) {
                    RedNexus = score;
                } else if (Objects.equals(TeamNexus, "Blue")) {
                    BlueNexus = score;
                } else if (Objects.equals(TeamNexus, "Yellow")) {
                    YellowNexus = score;
                } else if (Objects.equals(TeamNexus, "Green")) {
                    GreenNexus = score;
                } else {
                    sender.sendMessage("1つめの引数にRed, Blue, Yellow, Green, 以外の文字列を打たないでください");
                    return false;
                }
                sender.sendMessage(TeamNexus + "のスコアを" + score + "に設定します。");
                StartAnni();
                new ReloadScoreBoard().StartAnniScoreBoard();
            }
        } else if (command.getName().equalsIgnoreCase("NexusLocation")) {
            List<ItemStack> b = new SetNexus().addList();
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            } else {
                for (int i = 0; i < b.size(); i++) {
                    ((Player) sender).getInventory().addItem(b.get(i));
                    sender.sendMessage(b.get(i).toString());
                }
            }
        } else if (command.getName().equalsIgnoreCase("RespawnLocation")) {
            List<ItemStack> b = new Respawn().addList();
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            } else {
                for (int i = 0; i < b.size(); i++) {
                    ((Player) sender).getInventory().addItem(b.get(i));
                    sender.sendMessage(b.get(i).toString());
                }
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("nexus")) {
            List<String> completions = new ArrayList<>();
            // コマンド名をチェック
            // 最初の引数の候補を提供
            if (args.length == 1) {
                completions.add("Red");
                completions.add("Blue");
                completions.add("Yellow");
                completions.add("Green");
            }
            return completions;
        }
        return null;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (time > 0) {
            new ReloadScoreBoard().StartAnniScoreBoard();
            bossBar.addPlayer(p);
        }
        p.teleport(new Location(p.getWorld(), 5000.5, 4.0, 5000.5, 90, 0));
        e.setJoinMessage(null);
        p.sendMessage(ChatColor.GREEN + "Welcome back " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.GREEN + ", You are currently the rank of...");
        Inventory pe = p.getInventory();
        pe.clear();
        pe.setItem(0, new GUI().ItemMeta(Material.COMPASS, FC));
        pe.setItem(1, new GUI().ItemMeta(Material.BLAZE_ROD, FB));
        pe.setItem(4, new GUI().ItemMeta(Material.BLACK_SHULKER_BOX, FSB));
        pe.setItem(6, new GUI().ItemMeta(Material.CAKE, FCA));
        pe.setItem(7, new GUI().ItemMeta(Material.FEATHER, FF));
        pe.setItem(8, new GUI().ItemMeta(Material.SAND, FS));
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        Block b = e.getClickedBlock();
        if (e.getItem() == null || e.getItem().getItemMeta() == null) {
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
            if (j.equals(FC) && t.equals(Material.COMPASS)) {
                new GUI().JoinGame(p);
                e.setCancelled(true);
            } else if (j.equals(FB) && t.equals(Material.BLAZE_ROD)) {
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            } else if (j.equals(FSB) && t.equals(Material.BLACK_SHULKER_BOX)) {
                new GUI().Swag(p);
                e.setCancelled(true);
            } else if (j.equals(FCA) && t.equals(Material.CAKE)) {
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            } else if (j.equals(FF) && t.equals(Material.FEATHER)) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.sendMessage(ChatColor.RED + "Disallow Flight");
                } else if (!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    p.sendMessage(ChatColor.GREEN + "Allow Flight");
                }
            } else if (j.equals(FS) && t.equals(Material.SAND)) {
                p.sendMessage(ChatColor.GRAY + "要らんやろこれ");
                e.setCancelled(true);
            } else if (j.equals(ChatColor.GOLD + "Select Team") && t.equals(Material.NETHER_STAR)) {
                new GUI().SelectTeam(p);
                e.setCancelled(true);
            } else if (j.equals(ChatColor.AQUA + "Select Map") && t.equals(Material.MAP)) {
                new GUI().SelectMap(p);
                e.setCancelled(true);
            } else if (j.equals(ChatColor.AQUA + "Select Class") && t.equals(Material.FEATHER)) {
                new SelectClass().ClassList(p);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        ItemStack i = e.getItemDrop().getItemStack();
        ItemMeta im = i.getItemMeta();
        String imd = Objects.requireNonNull(im).getDisplayName();
        if (imd.equals(FB) || imd.equals(FC) || imd.equals(FF) || imd.equals(FCA) || imd.equals(FSB) || imd.equals(FS)) {
            e.setCancelled(true);
        }
        if (imd.equals(new GUI().FF) || imd.equals(new GUI().FB) || imd.equals(new GUI().FN) || imd.equals(new GUI().FM)) {
            e.setCancelled(true);
        }
    }

    public void CreateTeam() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = Objects.requireNonNull(manager).getMainScoreboard();

        Team red = scoreboard.getTeam("red");
        if (red == null) {
            // チームが存在しない場合、新しく作成
            red = scoreboard.registerNewTeam("red");
        }
        red.setPrefix(ChatColor.RED.toString());
        red.setColor(ChatColor.RED);
        red.setAllowFriendlyFire(false);
        Team blue = scoreboard.getTeam("blue");
        if (blue == null) {
            // チームが存在しない場合、新しく作成
            blue = scoreboard.registerNewTeam("blue");
        }
        blue.setPrefix(ChatColor.BLUE.toString());
        blue.setColor(ChatColor.BLUE);
        blue.setAllowFriendlyFire(false);
        Team green = scoreboard.getTeam("green");
        if (green == null) {
            // チームが存在しない場合、新しく作成
            green = scoreboard.registerNewTeam("green");
        }
        green.setPrefix(ChatColor.GREEN.toString());
        green.setColor(ChatColor.GREEN);
        green.setAllowFriendlyFire(false);
        Team yellow = scoreboard.getTeam("yellow");
        if (yellow == null) {
            // チームが存在しない場合、新しく作成
            yellow = scoreboard.registerNewTeam("yellow");
        }
        yellow.setPrefix(ChatColor.YELLOW.toString());
        yellow.setColor(ChatColor.YELLOW);
        yellow.setAllowFriendlyFire(false);
    }

    static String none = "";

    public void StartAnni() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = Objects.requireNonNull(manager).getMainScoreboard();
        Team red = scoreboard.getTeam("red");
        Team blue = scoreboard.getTeam("blue");
        Team green = scoreboard.getTeam("green");
        Team yellow = scoreboard.getTeam("yellow");
        CreateTeam();
    }

    public void setupBossBar() {
        // ボスバーを作成（タイトル、色、スタイルを設定）
        bossBar = Bukkit.createBossBar(ChatColor.GREEN + "Please wait a start command in admin", BarColor.BLUE, BarStyle.SOLID);
        // 全プレイヤーにボスバーを表示
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
    }

    public void startTimer() {
        // 初期時間を0に設定
        time = 0;
        phase = 2;
        timeM = 0;
        double progress = Math.min(1.0, 600.0);  // 最大60秒でフルバーにする
        bossBar.setProgress(progress);
        // 1秒ごとに実行されるタスク
        new BukkitRunnable() {
            @Override
            public void run() {
                // 全プレイヤーにボスバーを表示（サーバーに新しいプレイヤーが参加した場合も更新）
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!bossBar.getPlayers().contains(player)) {
                        bossBar.addPlayer(player);
                    }
                }
                // 時間を1秒増加
                time++;
                if (phase <= 4) {
                    time2 = 600 - time;
                    timeM = (int) time2 / 60;
                    timeS = time2 - (timeM * 60);
                    // ボスバーのタイトルを更新（経過秒数を表示）
                    if (timeS >= 10) {
                        bossBar.setTitle(ChatColor.WHITE + "Phase: " + phase + " - " + timeM + ":" + timeS + "s");
                    }
                    if (timeS <= 9) {
                        bossBar.setTitle(ChatColor.WHITE + "Phase: " + phase + " - " + timeM + ":0" + timeS + "s");
                    }
                    if (time >= 600) {
                        time = 0;
                        phase++;
                        bossBar.setTitle(ChatColor.WHITE + "Phase: " + phase + 1 + " - " + "10:00");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.5F, 1);
                        }
                    }
                } else {
                    bossBar.setTitle(ChatColor.WHITE + "Phase: 5 - " + ChatColor.RED + "DOUBLE" + ChatColor.WHITE + " Nexus Damage");
                }
            }
        }.runTaskTimer(this, 0L, 20L); // 0L は初回の遅延、20L は20ティック(1秒)間隔
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        // プレイヤー名から所属チームを取得
        Team team = scoreboard.getEntryTeam(p.getName());
        File File = new File("RespawnLocation/main.yml");
        if (Objects.equals(Objects.requireNonNull(team).getName(), "red")) {
            Location l = Objects.requireNonNull(SaveNexusLocation.loadLocationFromYaml(File, "RedRespawn"));
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(this, () -> {
                p.teleport(l);
            }, 0L);
        }
        if (Objects.equals(Objects.requireNonNull(team).getName(), "blue")) {
            Location l = SaveNexusLocation.loadLocationFromYaml(File, "BlueRespawn");
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(this, () -> {
                p.teleport(l);
            }, 0L);
        }
        if (Objects.equals(Objects.requireNonNull(team).getName(), "yellow")) {
            Location l = SaveNexusLocation.loadLocationFromYaml(File, "YellowRespawn");
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(this, () -> {
                p.teleport(l);
            }, 0L);
        }
        if (Objects.equals(Objects.requireNonNull(team).getName(), "green")) {
            Location l = SaveNexusLocation.loadLocationFromYaml(File, "GreenRespawn");
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(this, () -> {
                p.teleport(l);
            }, 0L);
        }
    }
}
