package com.cartel.fastlogin;

import com.cartel.fastlogin.config.mainConfig;
import com.cartel.fastlogin.database.fastLoginData;
import com.cartel.fastlogin.handlers.commandHandler;
import com.cartel.fastlogin.handlers.eventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import com.cartel.fastlogin.config.stringConfig;

public class core extends JavaPlugin {

    private static core instance;
    public stringConfig stringConfig;
    public static mainConfig mainConfig;
    public static fastLoginData loginData;

    @Override
    public void onEnable() {
        instance = this;

        mainConfig = new mainConfig(this);
        mainConfig.setup();

        stringConfig = new stringConfig(this);
        stringConfig.setup();

        loginData = new fastLoginData(this);
        boolean result = loginData.initializeDatabase();

        if (!result) {
            return;
        }

        new commandHandler(this, stringConfig).loadCommands();
        getLogger().info("Komutlar yüklendi!");

        new eventHandler(this, stringConfig).loadEvents();
        getLogger().info("Eventler yüklendi!");

        getLogger().info("Otomatik Giris plugini aktif!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Otomatik Giris plugini devre dışı!");
    }

    public static core getInstance() {
        return instance;
    }
}
