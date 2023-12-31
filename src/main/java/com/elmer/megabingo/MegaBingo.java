package com.elmer.megabingo;

import com.elmer.megabingo.listeners.*;
import com.elmer.megabingo.managers.BingoManager;
import com.elmer.megabingo.managers.SettingsManager;
import com.elmer.megabingo.tools.MaterialList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaBingo extends JavaPlugin {
    private BingoManager bingoManager;
    private MaterialList materialList;

    @Override
    public void onEnable() {

        bingoManager = new BingoManager(this);
        materialList = new MaterialList(this);
        SettingsManager settingsManager = new SettingsManager(this);

        BingoCraftListener bingoCraftListener = new BingoCraftListener(this);
        BingoPickupListener bingoPickupListener = new BingoPickupListener(this);
        BingoInventoryOpenListener bingoInventoryOpenListener = new BingoInventoryOpenListener(this);
        SettingsListener settingsListener = new SettingsListener(materialList, settingsManager);


        getCommand("bingo").setExecutor(new BingoCommand(this, settingsManager, bingoManager));
        getCommand("bingo").setTabCompleter(new BingoCompleter());

        Bukkit.getPluginManager().registerEvents(bingoCraftListener, this);
        Bukkit.getPluginManager().registerEvents(bingoPickupListener, this);
        Bukkit.getPluginManager().registerEvents(new BingoGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(bingoInventoryOpenListener, this);
        Bukkit.getPluginManager().registerEvents(settingsListener, this);

        materialList.createMaterials();

    }

    public BingoManager getBingoManager() {
        return bingoManager;
    }
    public MaterialList getMaterialList(){
        return materialList;
    }

    @Override
    public void onDisable() {
        bingoManager.clearData();
        bingoManager.started = false;
    }
}
