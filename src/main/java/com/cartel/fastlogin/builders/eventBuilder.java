package com.cartel.fastlogin.builders;

import com.cartel.fastlogin.config.stringConfig;
import com.cartel.fastlogin.core;
import org.bukkit.event.Listener;

public abstract class eventBuilder implements Listener {
    public abstract void setData(core plugin, stringConfig stringConfig);
}
