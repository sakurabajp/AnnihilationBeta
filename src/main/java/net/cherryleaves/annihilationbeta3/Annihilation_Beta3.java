package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
        Bukkit.getServer().getPluginManager().registerEvents(new GUI(), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    String FC = ChatColor.LIGHT_PURPLE + "Click to join a game";
    String FB = ChatColor.GRAY + "Click to toggle player visibility";
    String FSB = ChatColor.AQUA + "Shotbow Swag";
    String FCA = ChatColor.AQUA + "Social menu";
    String FF = ChatColor.WHITE + "Fly hack item";
    String FS = ChatColor.YELLOW + "Click to join an event";


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.teleport(new Location(p.getWorld(), 5000.5, 4.0, 5000.5, 90, 0));
        e.setJoinMessage(null);
        p.sendMessage(ChatColor.GREEN + "Welcome back " + ChatColor.YELLOW + p.getDisplayName() + ChatColor.GREEN + ", You are currently the rank of...");
        Inventory pe = p.getInventory();
        pe.clear();
        pe.setItem(0, new GUI().ItemMeta(Material.COMPASS,  FC));
        pe.setItem(1, new GUI().ItemMeta(Material.BLAZE_ROD,  FB));
        pe.setItem(4, new GUI().ItemMeta(Material.BLACK_SHULKER_BOX,  FSB));
        pe.setItem(6, new GUI().ItemMeta(Material.CAKE,  FCA));
        pe.setItem(7, new GUI().ItemMeta(Material.FEATHER,  FF));
        pe.setItem(8, new GUI().ItemMeta(Material.SAND,  FS));
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action action = e.getAction();
        Block b = e.getClickedBlock();
        if (action != Action.PHYSICAL) {
            ItemStack itemInHand = p.getInventory().getItemInMainHand(); // プレイヤーの手に持っているアイテムを取得
            if (itemInHand.getType().isAir()) {
                return;
            }
            if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FC) && e.getItem().getType().equals(Material.COMPASS)){
                new GUI().JoinGame(p);
                e.setCancelled(true);
            }
            else if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FB) && e.getItem().getType().equals(Material.BLAZE_ROD)){
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            }
            else if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FSB) && e.getItem().getType().equals(Material.BLACK_SHULKER_BOX)){
                new GUI().Swag(p);
                e.setCancelled(true);
            }
            else if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FCA) && e.getItem().getType().equals(Material.CAKE)){
                p.sendMessage(ChatColor.GRAY + "実装する気ないよ(笑)");
                e.setCancelled(true);
            }
            else if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FF) && e.getItem().getType().equals(Material.FEATHER)){
                if(p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.sendMessage(ChatColor.RED + "Disallow Flight");
                }
                else if(!p.getAllowFlight()){
                    p.setAllowFlight(true);
                    p.sendMessage(ChatColor.GREEN + "Allow Flight");
                }
            }
            else if(Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta()).getDisplayName().equals(FS) && e.getItem().getType().equals(Material.SAND)){
                p.sendMessage(ChatColor.GRAY + "要らんやろこれ");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        ItemStack i = e.getItemDrop().getItemStack();
        ItemMeta im = i.getItemMeta();
        String imd = Objects.requireNonNull(im).getDisplayName();
        if(imd.equals(FB) || imd.equals(FC) || imd.equals(FF) || imd.equals(FCA) || imd.equals(FSB) || imd.equals(FS)){
            e.setCancelled(true);
        }
    }
}
