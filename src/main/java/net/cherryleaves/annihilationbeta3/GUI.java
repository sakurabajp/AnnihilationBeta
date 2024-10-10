package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GUI {

    public void SelectTeam(Player p){
        Inventory st = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Select a team <You can join the match directly>");/*メインクラスにもでてくる*/
        st.clear();
        p.openInventory(st);
        st.setItem(0, ItemMeta(Material.RED_WOOL, ChatColor.RED + "Red Team"));
        st.setItem(1, ItemMeta(Material.BLUE_WOOL, ChatColor.BLUE + "Blue Team"));
        st.setItem(2, ItemMeta(Material.GREEN_WOOL, ChatColor.GREEN + "Green Team"));
        st.setItem(3, ItemMeta(Material.YELLOW_WOOL, ChatColor.YELLOW + "Yellow Team"));
        st.setItem(8, ItemMeta(Material.WHITE_WOOL, ChatColor.WHITE + "Random Team"));
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }
}
