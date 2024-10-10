package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Annihilation_Beta3 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.teleport(new Location(p.getWorld(), 5000.5, 4.0, 5000.5, 90, 0));
        p.sendMessage(ChatColor.GREEN + "Welcome back " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.GREEN + ", You are currently the rank of...");
        Inventory pe = p.getInventory();
        pe.setItem(0, ItemMeta(Material.COMPASS,  ChatColor.LIGHT_PURPLE + "Click to join a game"));
        pe.setItem(1, ItemMeta(Material.BLAZE_ROD,  ChatColor.GRAY + "Click to toggle player visibility"));
        pe.setItem(4, ItemMeta(Material.BLACK_SHULKER_BOX,  ChatColor.AQUA + "Shotbow Swag"));
        pe.setItem(6, ItemMeta(Material.CAKE,  ChatColor.AQUA + "Social menu"));
        pe.setItem(7, ItemMeta(Material.FEATHER,  ChatColor.WHITE + "Fly hack item"));
        pe.setItem(8, ItemMeta(Material.SAND,  ChatColor.YELLOW + "Click to join an event"));
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (e.getAction().toString().contains("RIGHT_CLICK")) {
            if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Click to join a game") && e.getItem().getType().equals(Material.COMPASS)){
                new GUI().SelectTeam(p);
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        HumanEntity h = e.getWhoClicked();
        Player p = (Player) h;
        if(e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Select a team <You can join the match directly>")){
            e.setCancelled(true);
            if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.RED_WOOL)){
                h.sendMessage(ChatColor.RED + "You joined the Red team");
            }
            else if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.BLUE_WOOL)){
                h.sendMessage(ChatColor.BLUE + "You joined the Blue team");
            }
            else if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.GREEN_WOOL)){
                h.sendMessage(ChatColor.GREEN + "You joined the Green team");
            }
            else if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.YELLOW_WOOL)){
                h.sendMessage(ChatColor.YELLOW + "You joined the Yellow team");
            }
            else if(Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.WHITE_WOOL)){
                // 後で書く
                h.sendMessage(ChatColor.GRAY + "未実装です");
            }

        }
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }
}
