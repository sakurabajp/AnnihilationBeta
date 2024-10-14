package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.Optional;

public class GUI implements Listener {

    String MapName = ChatColor.GOLD + "Map: Korustal";
    String MapName2 = ChatColor.GOLD + "Korustal";

    String FF = ChatColor.AQUA + "Select Class";
    String FN = ChatColor.GOLD + "Select Team";
    String FM = ChatColor.AQUA + "Select Map";
    String FB = ChatColor.GREEN + "Click to manage your preferences";

    public void JoinGame(Player p){
        Inventory jg = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Select a game server <You can join the match directly>");/*メインクラスにもでてくる*/
        jg.clear();
        p.openInventory(jg);
        jg.setItem(0, ItemMeta(Material.LIME_DYE, MapName));
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

    public void SelectMap(Player p){
        Inventory jg = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Select a Map");
        jg.clear();
        p.openInventory(jg);
        jg.setItem(0, ItemMeta(Material.LIME_DYE, MapName2));
    }


    public void Swag(Player p){
        Inventory st = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Shotbow Swag");
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
        if (e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Select a game server <You can join the match directly>")) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null){
                return;
            }
            else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(MapName)) {
                p.closeInventory();
                h.getInventory().clear();
                p.getInventory().setItem(0, new GUI().ItemMeta(Material.FEATHER, FF));
                h.getInventory().setItem(1, new GUI().ItemMeta(Material.NETHER_STAR, FN));
                h.getInventory().setItem(2, new GUI().ItemMeta(Material.MAP, FM));
                h.getInventory().setItem(3, new GUI().ItemMeta(Material.BOOK, FB));
            }
        }
        if (e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Select a team <You can join the match directly>")) {
            e.setCancelled(true);
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = Objects.requireNonNull(manager).getMainScoreboard();
            if(e.getCurrentItem() == null){return;}
            Team team = scoreboard.getEntryTeam(p.getName());
            if (team == null) {
                if (e.getCurrentItem().getType().equals(Material.RED_WOOL)) {
                    h.sendMessage(ChatColor.RED + "You joined the Red team");
                    Objects.requireNonNull(scoreboard.getTeam("red")).addEntry(h.getName());
                } else if (e.getCurrentItem().getType().equals(Material.BLUE_WOOL)) {
                    h.sendMessage(ChatColor.BLUE + "You joined the Blue team");
                    Objects.requireNonNull(scoreboard.getTeam("blue")).addEntry(h.getName());
                } else if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)) {
                    h.sendMessage(ChatColor.GREEN + "You joined the Green team");
                    Objects.requireNonNull(scoreboard.getTeam("green")).addEntry(h.getName());
                } else if (e.getCurrentItem().getType().equals(Material.YELLOW_WOOL)) {
                    h.sendMessage(ChatColor.YELLOW + "You joined the Yellow team");
                    Objects.requireNonNull(scoreboard.getTeam("yellow")).addEntry(h.getName());
                } else if (e.getCurrentItem().getType().equals(Material.WHITE_WOOL)) {
                    // 後で書く
                    h.sendMessage(ChatColor.GRAY + "new RandomTeam(Player) は未実装な仕様です");
                }
            }
            else{
                h.sendMessage(ChatColor.DARK_RED + "You have already joined the team");
            }
        } else if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "Shotbow Swag")) {
            e.setCancelled(true);
        }
        else if (e.getView().getTitle().equals(ChatColor.DARK_AQUA + "Select a Map")) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null){
                return;
            }
            else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(MapName2)) {
                p.sendMessage(ChatColor.GREEN + "You Select the Korustal");
                p.closeInventory();
            }
        }
        else if (e.getView().getTitle().equals(new SelectClass().sc)) {
            e.setCancelled(true);
        }
    }
}
