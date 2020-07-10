package com.pyropoops.ventusshops.inventories;

import com.pyropoops.ventusshops.ShopItem;
import com.pyropoops.ventusshops.Transaction;
import com.pyropoops.ventusshops.VentusShops;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PurchaseInventory implements Listener {
    private static List<PurchaseInventory> purchaseInventories = new ArrayList<>();
    private Inventory inventory;
    private ShopItem shopItem;
    private Inventory originInventory;

    public PurchaseInventory(ShopItem shopItem, Inventory originInventory) {
        this.shopItem = shopItem;
        this.originInventory = originInventory;

        VentusShops.getInstance().registerEvents(this);

        inventory = Bukkit.createInventory(null, 45, "§cPurchase");
        purchaseInventories.add(this);
        this.construct();
    }

    private void construct() {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            ItemStack itemStack = null;
            if (i == 3) {
                itemStack = new ItemStack(Material.GREEN_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§aBuy 1");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 4) {
                itemStack = this.shopItem.getDisplayItem(1);
            } else if (i == 5) {
                itemStack = new ItemStack(Material.RED_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§cSell 1");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 12) {
                itemStack = new ItemStack(Material.GREEN_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§aBuy 8");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 13) {
                itemStack = this.shopItem.getDisplayItem(8);
            } else if (i == 14) {
                itemStack = new ItemStack(Material.RED_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§cSell 8");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 21) {
                itemStack = new ItemStack(Material.GREEN_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§aBuy 64");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 22) {
                itemStack = this.shopItem.getDisplayItem(64);
            } else if (i == 23) {
                itemStack = new ItemStack(Material.RED_WOOL, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§cSell 64");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 36) {
                itemStack = new ItemStack(Material.ARROW, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§8§lGO BACK");
                itemStack.setItemMeta(itemMeta);
            } else if (i == 44) {
                itemStack = new ItemStack(Material.BARRIER, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName("§4§L" + "CLOSE");
                itemStack.setItemMeta(itemMeta);
            }
            if (itemStack == null) continue;
            inventory.setItem(i, itemStack);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(this.inventory)) return;
        e.setCancelled(true);
        switch (e.getSlot()) {
            case 36:
                e.getWhoClicked().openInventory(this.originInventory);
                break;
            case 44:
                e.getWhoClicked().closeInventory();
                break;
            case 3:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 1, true);
                e.getWhoClicked().closeInventory();
                break;
            case 12:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 8, true);
                e.getWhoClicked().closeInventory();
                break;
            case 21:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 64, true);
                e.getWhoClicked().closeInventory();
                break;
            case 5:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 1, false);
                e.getWhoClicked().closeInventory();
                break;
            case 14:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 8, false);
                e.getWhoClicked().closeInventory();
                break;
            case 23:
                new Transaction((Player) e.getWhoClicked(), this.shopItem, 64, false);
                e.getWhoClicked().closeInventory();
                break;
            default:
                break;
        }
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
        PurchaseInventory instance = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (inventory.getViewers().size() <= 0) {
                    purchaseInventories.remove(instance);
                    this.cancel();
                }
            }
        }.runTaskTimer(VentusShops.getInstance(), 0L, 0L);
    }

}
