package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectClass implements Listener {

    String sc = ChatColor.DARK_GRAY + "Select a Class:";

    public void ClassList(Player p){
        List<ItemStack> Classes = new ArrayList<>();
        addList(Classes);
        Inventory cg = Bukkit.createInventory(null, 45, sc);
        p.openInventory(cg);
        for(ItemStack i : Classes){
            cg.addItem(i);
        }
    }

    public void addList(List<ItemStack> Classes){
        Classes.add(ItemMeta(Material.FEATHER, "Acrobat"));
        Classes.add(ItemMeta(Material.BREWING_STAND, "Alchemist"));
        Classes.add(ItemMeta(Material.BOW, "Archer"));
        Classes.add(ItemMeta(Material.POTION, "Assassin"));
        Classes.add(ItemMeta(Material.JUKEBOX, "Bard"));
        Classes.add(ItemMeta(Material.CHAINMAIL_CHESTPLATE, "Berserker"));
        Classes.add(ItemMeta(Material.FERMENTED_SPIDER_EYE, "Bloodmage"));
        Classes.add(ItemMeta(Material.BRICK, "Builder"));
        Classes.add(ItemMeta(Material.CRAFTING_TABLE, "Civilian"));
        Classes.add(ItemMeta(Material.ENDER_PEARL, "Dasher"));
        Classes.add(ItemMeta(Material.PRISMARINE, "Defender"));
        Classes.add(ItemMeta(Material.ENCHANTING_TABLE, "Enchanter"));
        Classes.add(ItemMeta(Material.TNT, "Engineer"));
        Classes.add(ItemMeta(Material.WHEAT_SEEDS, "Farmer"));
        Classes.add(ItemMeta(Material.ANVIL, "HandyMan"));
        Classes.add(ItemMeta(Material.CHORUS_FRUIT, "Healer"));
        Classes.add(ItemMeta(Material.LEAD, "Hunter"));
        Classes.add(ItemMeta(Material.PACKED_ICE, "Iceman"));
        Classes.add(ItemMeta(Material.SLIME_BALL, "Immobilizer"));
        Classes.add(ItemMeta(Material.STONE_AXE, "Lumberjack"));
        Classes.add(ItemMeta(Material.SKELETON_SKULL, "Mercenary"));
        Classes.add(ItemMeta(Material.IRON_PICKAXE, "Miner"));
        Classes.add(ItemMeta(Material.FIREWORK_STAR, "Ninja"));
        Classes.add(ItemMeta(Material.FLINT_AND_STEEL, "Pyro"));
        Classes.add(ItemMeta(Material.BLAZE_ROD, "Rift Walker"));
        Classes.add(ItemMeta(Material.NETHER_STAR, "Scorpio"));
        Classes.add(ItemMeta(Material.ARROW, "Sniper"));
        Classes.add(ItemMeta(Material.COBWEB, "Spider"));
        Classes.add(ItemMeta(Material.POTION, "Spy"));
        Classes.add(ItemMeta(Material.RED_DYE, "Succubus"));
        Classes.add(ItemMeta(Material.MUSIC_DISC_CAT, "Swapper"));
        Classes.add(ItemMeta(Material.SHIELD, "Tank"));
        Classes.add(ItemMeta(Material.GOLDEN_AXE, "Thor"));
        Classes.add(ItemMeta(Material.REDSTONE_BLOCK, "Tinkerer"));
        Classes.add(ItemMeta(Material.QUARTZ, "Transporter"));
        Classes.add(ItemMeta(Material.REDSTONE, "Vampire"));
        Classes.add(ItemMeta(Material.STONE_SWORD, "Warrior"));
        Classes.add(ItemMeta(Material.STICK, "Wizard"));

        // はぁ...
        // https://discord.com/channels/1161695897860702218/1166370520611160104/1318477433317363712
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }
}
