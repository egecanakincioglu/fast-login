package com.cartel.fastlogin.database;

import com.cartel.fastlogin.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class fastLoginData {
    private final String url;
    private final String user;
    private final String password;

    private core plugin;

    public fastLoginData(core plugin) {
        ConfigurationSection currentConfig = core.mainConfig.getConfig().getConfigurationSection("database-connection");
        this.plugin = plugin;
        this.url = currentConfig.getString("url");
        this.user = currentConfig.getString("user");
        this.password = currentConfig.getString("password");
    }

    public boolean initializeDatabase() {
        if (url.equals("url")) {
            plugin.getLogger().severe("Pluginin çalışabilmesi için MySQL verilerini Config.yml üzerinden doldurmalısınız.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "CREATE TABLE IF NOT EXISTS FastLoginData (id INT AUTO_INCREMENT PRIMARY KEY, playerUUID VARCHAR(36), ipAddress VARCHAR(255), isEnabled BOOLEAN DEFAULT true)";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            core.getInstance().getLogger().log(Level.SEVERE, "Database initialization error", ex);
            return false;
        }
    }

    public void savePlayerData(Player player, Optional<Boolean> autoLogin) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO FastLoginData (playerUUID, ipAddress, isEnabled)\n" +
                    "VALUES (?, ?, ?)\n" + 
                    "ON DUPLICATE KEY UPDATE ipAddress = VALUES(ipAddress), isEnabled = ?;"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getAddress().getHostName());

            if (autoLogin.isPresent()) {
                preparedStatement.setBoolean(3, autoLogin.get()); 
                preparedStatement.setBoolean(4, autoLogin.get()); 
            } else {
                preparedStatement.setBoolean(3, true); 
                preparedStatement.setBoolean(4, true); 
            }

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            core.getInstance().getLogger().log(Level.SEVERE, "Error saving player data", ex);
        }
    }

    public boolean isPlayerRegistered(Player player) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM FastLoginData WHERE playerUUID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            core.getInstance().getLogger().log(Level.SEVERE, "Error checking player registration", ex);
            return false;
        }
    }

    public String getPlayerIP(Player player) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT ipAddress FROM FastLoginData WHERE playerUUID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("ipAddress");
            }
        } catch (SQLException ex) {
            core.getInstance().getLogger().log(Level.SEVERE, "Error getting player IP", ex);
        }
        return null;
    }

    public boolean isAutoLoginEnabled(Player player) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT isEnabled FROM FastLoginData WHERE playerUUID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("isEnabled");
            }
        } catch (SQLException ex) {
            core.getInstance().getLogger().log(Level.SEVERE, "Error checking player auto login", ex);
        }
        return false;
    }
}
