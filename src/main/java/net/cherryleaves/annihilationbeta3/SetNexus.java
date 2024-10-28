package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetNexus implements Listener {

    ItemStack RedNexus = ItemMeta(Material.RED_WOOL, ChatColor.RED + "RedTeamNexus");
    ItemStack BlueNexus = ItemMeta(Material.BLUE_WOOL, ChatColor.BLUE + "BlueTeamNexus");
    ItemStack YellowNexus = ItemMeta(Material.YELLOW_WOOL, ChatColor.YELLOW + "YellowTeamNexus");
    ItemStack GreenNexus = ItemMeta(Material.GREEN_WOOL, ChatColor.GREEN + "GreenTeamNexus");

    List<ItemStack> SetNexusItem = new ArrayList<>();

    public List<ItemStack> addList() {
        SetNexusItem.add(RedNexus);
        SetNexusItem.add(BlueNexus);
        SetNexusItem.add(YellowNexus);
        SetNexusItem.add(GreenNexus);
        return SetNexusItem;
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void SetNexusLocate(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Material m = e.getBlock().getType();
        Location l = e.getBlock().getLocation();
        ItemStack i = p.getInventory().getItemInMainHand();
        List<ItemStack> t = new SetNexus().addList();
        if(t.contains(i) && m.equals(Material.END_STONE)){
            e.setCancelled(true);
            String c = "";
            File File = new File("NexusLocation/main.yml");
            if(i.equals(RedNexus)){
                c = ChatColor.RED + "赤";
                SaveNexusLocation.saveLocationToYaml(l, File, "RedNexus");
            }
            else if(i.equals(BlueNexus)){
                c = ChatColor.BLUE + "青";
                SaveNexusLocation.saveLocationToYaml(l, File, "BlueNexus");
            }
            else if(i.equals(YellowNexus)){
                c = ChatColor.YELLOW + "黄";
                SaveNexusLocation.saveLocationToYaml(l, File, "YellowNexus");
            }
            else if(i.equals(GreenNexus)){
                c = ChatColor.GREEN + "緑";
                SaveNexusLocation.saveLocationToYaml(l, File, "GreenNexus");
            }
            p.sendMessage(c + " チームのネクサスの場所を設定しました");
        }
    }
}