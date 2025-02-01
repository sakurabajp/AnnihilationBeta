package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class NetherGate implements Listener{

    private final JavaPlugin plugin;

    public NetherGate(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        // ネザーゲートが原因の場合のみ処理
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            Location currentLocation = player.getLocation();
            // X 方向に 3 ブロック移動
            currentLocation.add(3, 0, 0);
            // 新しい座標にテレポート
            player.teleport(currentLocation);
            // 1 秒 (20 tick) 後にクラス選択処理を実行
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(plugin, () -> {
                new SelectClass().ClassList(player);
            }, 1L);
        }
    }
}


