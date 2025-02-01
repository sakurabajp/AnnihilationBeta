package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Respawn implements Listener {

    ItemStack RedRespawn = ItemMeta(Material.RED_SHULKER_BOX, ChatColor.RED + "RedTeamRespawn");
    ItemStack BlueRespawn = ItemMeta(Material.BLUE_SHULKER_BOX, ChatColor.BLUE + "BlueTeamRespawn");
    ItemStack YellowRespawn = ItemMeta(Material.YELLOW_SHULKER_BOX, ChatColor.YELLOW + "YellowTeamRespawn");
    ItemStack GreenRespawn = ItemMeta(Material.GREEN_SHULKER_BOX, ChatColor.GREEN + "GreenTeamRespawn");

    List<ItemStack> SetRespawnItem = new ArrayList<>();

    public List<ItemStack> addList() {
        SetRespawnItem.add(RedRespawn);
        SetRespawnItem.add(BlueRespawn);
        SetRespawnItem.add(YellowRespawn);
        SetRespawnItem.add(GreenRespawn);
        return SetRespawnItem;
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void SetRespawnLocate(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Material m = e.getBlock().getType();
        Location l = e.getBlock().getLocation();
        ItemStack i = p.getInventory().getItemInMainHand();
        List<ItemStack> t = new Respawn().addList();
        if(t.contains(i) && !m.equals(Material.END_STONE)){
            e.setCancelled(true);
            String c = "";
            File File = new File("RespawnLocation/main.yml");
            if(i.equals(RedRespawn)){
                c = ChatColor.RED + "赤";
                SaveNexusLocation.saveLocationToYaml(l, File, "RedRespawn");
            }
            else if(i.equals(BlueRespawn)){
                c = ChatColor.BLUE + "青";
                SaveNexusLocation.saveLocationToYaml(l, File, "BlueRespawn");
            }
            else if(i.equals(YellowRespawn)){
                c = ChatColor.YELLOW + "黄";
                SaveNexusLocation.saveLocationToYaml(l, File, "YellowRespawn");
            }
            else if(i.equals(GreenRespawn)){
                c = ChatColor.GREEN + "緑";
                SaveNexusLocation.saveLocationToYaml(l, File, "GreenRespawn");
            }
            p.sendMessage(c + " チームのリスポーンの場所を設定しました");
        }
    }
}