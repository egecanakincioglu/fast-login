package com.cartel.fastlogin.builders;

import com.cartel.fastlogin.config.stringConfig;
import com.cartel.fastlogin.core;
import org.bukkit.command.CommandExecutor;

public abstract class commandBuilder implements CommandExecutor {
    public abstract String getName();
    public abstract void setData(core plugin, stringConfig stringConfig);
}
