package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        ListAddClass(Classes);
        Inventory cg = Bukkit.createInventory(null, 45, sc);
        p.openInventory(cg);
    }

    public void ListAddClass(List<ItemStack> Classes) {
        Classes.add(new GUI().ItemMeta(Material.FEATHER, "Acrobat"));
        Classes.add(new GUI().ItemMeta(Material.BREWING_STAND, "Alchemist"));
        Classes.add(new GUI().ItemMeta(Material.BOW, "Archer"));
        Classes.add(new GUI().ItemMeta(Material.POTION, "Assassin"));
        Classes.add(new GUI().ItemMeta(Material.JUKEBOX, "Bard"));
        Classes.add(new GUI().ItemMeta(Material.CHAINMAIL_CHESTPLATE, "Berserker"));
        Classes.add(new GUI().ItemMeta(Material.FERMENTED_SPIDER_EYE, "Bloodmage"));
        Classes.add(new GUI().ItemMeta(Material.BRICK, "Builder"));
        Classes.add(new GUI().ItemMeta(Material.CRAFTING_TABLE, "Civilian"));
        Classes.add(new GUI().ItemMeta(Material.ENDER_PEARL, "Dasher"));
        Classes.add(new GUI().ItemMeta(Material.PRISMARINE, "Defender"));
        Classes.add(new GUI().ItemMeta(Material.ENCHANTING_TABLE, "Enchanter"));
        Classes.add(new GUI().ItemMeta(Material.TNT, "Engineer"));
        Classes.add(new GUI().ItemMeta(Material.WHEAT_SEEDS, "Farmer"));
        Classes.add(new GUI().ItemMeta(Material.ANVIL, "HandyMan"));
        Classes.add(new GUI().ItemMeta(Material.CHORUS_FRUIT, "Healer"));
        // はぁ...
    }
}
