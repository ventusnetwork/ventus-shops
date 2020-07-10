package com.pyropoops.ventusshops;

import com.pyropoops.ventusshops.inventories.PurchaseInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements Listener {
    public static Map<String, Shop> shops = new HashMap<>();

    private String display;
    private int size;
    private List<ShopItem> shopItems;
    private List<Inventory> pages;

    public Shop(String id, String display, int size) {
        this.display = display;
        this.size = size;
        this.shopItems = new ArrayList<>();
        VentusShops.getInstance().registerEvents(this);

        shops.put(id, this);
    }

    public void createInventories() {
        this.pages = new ArrayList<>();
        for (int i = 0; i < getMaxPageSize(); i++) {
            this.pages.add(createPage(i + 1));
        }
    }

    public void open(Player player) {
        if (this.pages.size() > 0)
            player.openInventory(this.pages.get(0));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().startsWith(this.display)) return;

        e.setCancelled(true);

        int page = -1;
        for (int i = 0; i < this.pages.size(); i++) {
            Inventory inventory = this.pages.get(i);
            if (e.getClickedInventory() == null) continue;
            if (e.getClickedInventory().equals(inventory)) {
                page = i;
                break;
            }
        }

        if (page == -1) return;

        if (e.getSlot() < this.size) {
            ShopItem shopItem = this.shopItems.get(e.getSlot() + (page * this.size));
            PurchaseInventory purchaseInventory = new PurchaseInventory(shopItem, this.pages.get(page));
            purchaseInventory.open((Player) e.getWhoClicked());
        } else if (e.getSlot() == this.size) {
            if (page != 0) {
                e.getWhoClicked().openInventory(this.pages.get(page - 1));
            }
        } else if (e.getSlot() == this.size + 8) {
            if (page != this.getMaxPageSize() - 1) {
                e.getWhoClicked().openInventory(this.pages.get(page + 1));
            }
        }
    }

    private int getMaxPageSize() {
        return (int) Math.ceil((double) this.shopItems.size() / (double) this.size);
    }

    private Inventory createPage(int page) {
        Inventory inventory = Bukkit.createInventory(null, this.size + 9,
                this.display + " (" + page + "/" + this.getMaxPageSize() + ")");
        int index = this.size * (page - 1);
        for (int i = 0; i < this.size; i++) {
            if (i + index >= this.shopItems.size()) break;
            ShopItem shopItem = this.shopItems.get(i + index);
            inventory.setItem(i, shopItem.getDisplayItem());
        }
        ItemStack arrow = new ItemStack(Material.ARROW, 1);
        if (page != 1) {
            ItemMeta meta = arrow.getItemMeta();
            if(meta == null) return inventory;
            meta.setDisplayName("§c§lPREVIOUS PAGE");
            arrow.setItemMeta(meta);
            inventory.setItem(this.size, arrow);
        }
        if (page != this.getMaxPageSize()) {
            ItemMeta meta = arrow.getItemMeta();
            if(meta == null) return inventory;
            meta.setDisplayName("§a§lNEXT PAGE");
            arrow.setItemMeta(meta);
            inventory.setItem(this.size+8, arrow);
        }
        return inventory;
    }

    public void addItem(Material material, double buyPrice, double sellPrice) {
        this.shopItems.add(new ShopItem(material, buyPrice, sellPrice));
    }
}

