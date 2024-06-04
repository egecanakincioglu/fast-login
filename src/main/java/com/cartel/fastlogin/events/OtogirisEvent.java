package com.cartel.fastlogin.events;

import com.cartel.fastlogin.builders.eventBuilder;
import com.cartel.fastlogin.config.stringConfig;
import com.cartel.fastlogin.core;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class OtogirisEvent extends eventBuilder {
    public OtogirisEvent() {}
    private core plugin;
    private stringConfig stringConfig;
    public void setData(core plugin, stringConfig stringConfig) {
        this.plugin = plugin;
        this.stringConfig = stringConfig;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!core.loginData.isAutoLoginEnabled(player)) {
            if (!AuthMeApi.getInstance().isAuthenticated(player)) {
                player.sendMessage(stringConfig.getConfig().getString("otomatik_giris_kapali"));
            }
        }

        if (core.loginData.isAutoLoginEnabled(player)) {
            String ipAddress = core.loginData.getPlayerIP(player);

            if (!ipAddress.equals(player.getAddress())) return;

            AuthMeApi.getInstance().forceLogin(player);
            player.sendMessage(stringConfig.getConfig().getString("otomatik_giris_basarili"));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (isCustomInventory(event)) {
            event.setCancelled(true);

            Player player = plugin.getServer().getPlayer(event.getView().getPlayer().getUniqueId());
            ItemStack item = event.getView().getItem(event.getSlot());

            System.out.println(player.getName());
            System.out.println(item.getType());

            if (item != null && item.getType().equals(Material.CHEST) && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasDisplayName() && meta.hasLore()) {
                    if (meta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bOtomatik Giriş Sistemi"))) {
                        if (core.loginData.isAutoLoginEnabled(player)) {
                            core.loginData.savePlayerData(player, Optional.of(false));
                            player.sendMessage(stringConfig.getConfig().getString("otomatik_giris_kapatildi"));
                        } else {
                            core.loginData.savePlayerData(player, Optional.of(true));
                            player.sendMessage(stringConfig.getConfig().getString("otomatik_giris_acik"));
                        }
                    }
                }
            }
        }
    }

    private boolean isCustomInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return false;
        String title = event.getView().getTitle();
        return "Oto Giriş Ayarları".equals(title);
    }
}


