package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GUI implements Listener {

    public void JoinGame(Player p){
        Inventory jg = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Select a game server <You can join the match directly>");/*メインクラスにもでてくる*/
        jg.clear();
        p.openInventory(jg);
        jg.setItem(0, ItemMeta(Material.LIME_DYE, ChatColor.GOLD + "Korustal"));
    }

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

    public void Swag(Player p){
        Inventory st = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Shotbow Swag");/*メインクラスにもでてくる*/
        st.clear();
        p.openInventory(st);
        String CB = ChatColor.YELLOW + "\n \nClick to Browse!";
        st.setItem(11, ItemMeta(Material.FEATHER, ChatColor.AQUA + "Annihilation Classes" + CB));
        st.setItem(13, ItemMeta(Material.EGG, ChatColor.AQUA + "Pets" + CB));
        st.setItem(15, ItemMeta(Material.GLOWSTONE_DUST, ChatColor.AQUA + "Trails" + CB));
        st.setItem(31, ItemMeta(Material.BLACK_SHULKER_BOX, "Welcome to Shotbow Swag!"));
        st.setItem(34, ItemMeta(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "ここにXPを表示" + " XP" + CB));
    }

    public ItemStack ItemMeta(Material Item, String ItemName){
        ItemStack item = new ItemStack(Item);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ItemName);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        HumanEntity h = e.getWhoClicked();
        Player p = (Player) h;
        if (e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Select a team <You can join the match directly>")) {
            e.setCancelled(true);
            if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.RED_WOOL)) {
                h.sendMessage(ChatColor.RED + "You joined the Red team");
            } else if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.BLUE_WOOL)) {
                h.sendMessage(ChatColor.BLUE + "You joined the Blue team");
            } else if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.GREEN_WOOL)) {
                h.sendMessage(ChatColor.GREEN + "You joined the Green team");
            } else if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.YELLOW_WOOL)) {
                h.sendMessage(ChatColor.YELLOW + "You joined the Yellow team");
            } else if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(Material.WHITE_WOOL)) {
                // 後で書く
                h.sendMessage(ChatColor.GRAY + "new RandomTeam(Player) は未実装な仕様です");
            }
        } else if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "Shotbow Swag")) {
            {
                e.setCancelled(true);
            }
        }
    }
}
