package com.cartel.fastlogin.handlers;

import com.cartel.fastlogin.builders.eventBuilder;
import com.cartel.fastlogin.core;
import com.cartel.fastlogin.config.stringConfig;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class eventHandler {
    private core plugin;
    private stringConfig stringConfig;
    public eventHandler(core plugin, stringConfig stringConfig) {
        this.plugin = plugin;
        this.stringConfig = stringConfig;
    }
    public void loadEvents() {
        Reflections reflections = new Reflections("com.cartel.fastlogin.events");
        Set<Class<? extends eventBuilder>> subTypes = reflections.getSubTypesOf(eventBuilder.class);
        for (Class<? extends eventBuilder> Event : subTypes) {
            try {
                eventBuilder event = Event.getConstructor().newInstance();
                event.setData(plugin, stringConfig);
                plugin.getServer().getPluginManager().registerEvents(event, plugin);
                plugin.getLogger().info(Event.getSimpleName() + " etkinliği yüklendi.");

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
