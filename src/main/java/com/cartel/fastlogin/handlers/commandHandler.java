package com.cartel.fastlogin.handlers;

import com.cartel.fastlogin.builders.commandBuilder;
import com.cartel.fastlogin.config.stringConfig;
import com.cartel.fastlogin.core;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class commandHandler {
    private final core plugin;
    private final stringConfig stringConfig;
    public commandHandler(core plugin, stringConfig stringConfig) {
        this.plugin = plugin;
        this.stringConfig = stringConfig;
    }
    public void loadCommands() {
        Reflections reflections = new Reflections("com.cartel.fastlogin.commands");
        Set<Class<? extends commandBuilder>> subTypes = reflections.getSubTypesOf(commandBuilder.class);
        for (Class<? extends commandBuilder> Command : subTypes) {
            try {
                commandBuilder command = Command.getConstructor().newInstance();
                command.setData(plugin, stringConfig);
                plugin.getCommand(command.getName()).setExecutor(command);
                plugin.getLogger().info(command.getName() + " komutu yüklendi.");
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}