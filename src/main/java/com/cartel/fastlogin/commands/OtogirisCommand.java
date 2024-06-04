package com.cartel.fastlogin.commands;

import com.cartel.fastlogin.builders.commandBuilder;
import com.cartel.fastlogin.core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cartel.fastlogin.config.stringConfig;

public class OtogirisCommand extends commandBuilder {
    public OtogirisCommand() {}
    private core plugin;
    private stringConfig stringConfig;
    public void setData(core plugin, stringConfig stringConfig) {
        this.plugin = plugin;
        this.stringConfig = stringConfig;
    }
    public String getName() {
        return "otogiris";
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut yalnızca oyuncular tarafından kullanılabilir.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage("Doğru kullanım: /otogiris");
            return true;
        }

        addAutoLoginChest(player);

        return true;
    }

    private void addAutoLoginChest(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Oto Giriş Ayarları");
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta meta = chest.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bOtomatik Giriş Sistemi"));
            String startMessage = core.loginData.isAutoLoginEnabled(player) ? (ChatColor.DARK_RED + "Kapatmak ") : (ChatColor.DARK_GREEN + "Açmak ");
            meta.setLore(java.util.Arrays.asList(
                    ChatColor.GREEN + "Bunun sayesinde girdiğiniz",
                    ChatColor.GREEN + "cihazdan hesabınıza şifresiz",
                    ChatColor.GREEN + "giriş yapabilirsiniz",
                    "",
                    startMessage + ChatColor.GREEN + "için tıklayın!"
            ));
            chest.setItemMeta(meta);
            inventory.setItem(4, chest);
            player.openInventory(inventory);
        }
    }
}
