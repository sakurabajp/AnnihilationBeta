package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SelectClass implements Listener {

    String sc = ChatColor.DARK_GRAY + "Select a Class:";

    public void ClassList(Player p){
        List<ItemStack> Classes = new ArrayList<>();
        Inventory cg = Bukkit.createInventory(null, 45, sc);
        p.openInventory(cg);
    }
}
