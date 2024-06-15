package com.james.simplerename;

import co.aikar.commands.PaperCommandManager;
import com.james.simplerename.commands.LoreCommand;
import com.james.simplerename.commands.RenameCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This Project is property of Xadia © 2024
 * Redistribution of this Project is not allowed
 *
 * @author Xadia
 * Created: 15/06/2024
 * Project: SimpleRename
 */

public class Main extends JavaPlugin {

    @Getter private static Main instance;
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("SimpleRename has been enabled!");
        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleRename has been disabled!");
    }

    public void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new RenameCommand());
        commandManager.registerCommand(new LoreCommand());
    }
}
