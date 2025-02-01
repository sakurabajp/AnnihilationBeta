package net.cherryleaves.annihilationbeta3;

import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import static net.cherryleaves.annihilationbeta3.Annihilation_Beta3.*;

public class NexusBreak implements Listener {

    @EventHandler
    public void EndStoneBreakEvent(BlockBreakEvent e){
        Player p = e.getPlayer();
        Material m = e.getBlock().getType();
        Location l = e.getBlock().getLocation();
        int a = phase;
        File File = new File("NexusLocation/main.yml");
        if(m.equals(Material.END_STONE)){
            ItemStack mainHandItem = p.getInventory().getItemInMainHand();
            if (mainHandItem.getType() != Material.AIR) {
                // メインハンドのアイテムを取得
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                // アイテムが存在し、AIRではない場合のみ処理
                if (item.getType() == Material.AIR) {
                    return;
                }
                // 耐久値を1増やす (実質的にはアイテムの耐久値を1減らす動作)
                short durability = item.getDurability();
                item.setDurability((short) (durability + 1));
            }
            if(l.equals(SaveNexusLocation.loadLocationFromYaml(File, "RedNexus"))){
                e.setCancelled(true);
                if(a >= 2 && a <=4){
                    RedNexus -= 1;
                    NexusEvent(l, "RedNexus");
                }
                else if(a == 5){
                    RedNexus -= 2;
                    NexusEvent(l, "RedNexus");
                }
                if(RedNexus <= 0){
                    NexusEnd(l,"RedNexus");
                }
            }
            if(l.equals(SaveNexusLocation.loadLocationFromYaml(File, "BlueNexus"))){
                e.setCancelled(true);
                if(a >= 2 && a <=4){
                    BlueNexus -= 1;
                    NexusEvent(l, "BlueNexus");
                }
                else if(a == 5){
                    BlueNexus -= 2;
                    NexusEvent(l, "BlueNexus");

                }
                if(BlueNexus <= 0){
                    NexusEnd(l,"BlueNexus");
                }
            }
            if(l.equals(SaveNexusLocation.loadLocationFromYaml(File, "YellowNexus"))){
                e.setCancelled(true);
                if(a >= 2 && a <=4){
                    YellowNexus -= 1;
                    NexusEvent(l,  "YellowNexus");
                }
                else if(a == 5){
                    YellowNexus -= 2;
                    NexusEvent(l, "YellowNexus");
                }
                if(YellowNexus <= 0){
                    NexusEnd(l,"YellowNexus");
                }
            }
            if(l.equals(SaveNexusLocation.loadLocationFromYaml(File, "GreenNexus"))){
                e.setCancelled(true);
                if(a >= 2 && a <=4){
                    GreenNexus -= 1;
                    NexusEvent(l, "GreenNexus");
                }
                else if(a == 5){
                    GreenNexus -= 2;
                    NexusEvent(l, "GreenNexus");
                }
                if(GreenNexus <= 0){
                    NexusEnd(l,"GreenNexus");
                }
            }
        }
    }

    public void NexusEvent(Location l,String Team){
        File File = new File("NexusLocation/main.yml");
        float pitch = generateRandomPitch();
        Objects.requireNonNull(l.getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, Objects.requireNonNull(SaveNexusLocation.loadLocationFromYaml(File, Team)), 100);
        Objects.requireNonNull(l.getWorld()).spawnParticle(Particle.VILLAGER_ANGRY, Objects.requireNonNull(SaveNexusLocation.loadLocationFromYaml(File, Team)), 50);
        Objects.requireNonNull(l.getWorld()).spawnParticle(Particle.LAVA, Objects.requireNonNull(SaveNexusLocation.loadLocationFromYaml(File, Team)), 25);
        for (Player PlayerALL : Bukkit.getOnlinePlayers()) {
            PlayerALL.playSound(l, Sound.BLOCK_ANVIL_PLACE, 1.0f, pitch);
        }
        new ReloadScoreBoard().StartAnniScoreBoard();
    }

    private float generateRandomPitch() {
        // ランダムなピッチを生成
        Random random = new Random();
        float minPitch = 0.1f;  // 最小ピッチ
        float maxPitch = 1.0f;  // 最大ピッチ
        return minPitch + random.nextFloat() * (maxPitch - minPitch);
    }

    public void NexusEnd(Location l, String Team){
        l.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, l, 25);
        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        for (Player pa : Bukkit.getOnlinePlayers()) {
            pa.sendMessage(Team + "の拠点が落ちたよ");
        }
        l.getBlock().setType(Material.BEDROCK);
    }
}
