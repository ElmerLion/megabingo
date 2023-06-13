package com.elmer.megabingo.listeners;

import com.elmer.megabingo.managers.BingoManager;
import com.elmer.megabingo.MegaBingo;
import com.elmer.megabingo.tools.MaterialList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BingoCraftListener implements Listener {

    MegaBingo megaBingo;
    public BingoCraftListener(MegaBingo megaBingo){
        this.megaBingo = megaBingo;
    }

    @EventHandler
    public void onCraft (CraftItemEvent e){
        if (e.getWhoClicked() instanceof Player && megaBingo.getBingoManager().isStarted()){
            BingoManager bingoManager = megaBingo.getBingoManager();
            Material craftedItem = e.getRecipe().getResult().getType();
            Player player = (Player) e.getWhoClicked();


            UUID  uuid = player.getUniqueId();

            Map<UUID, Inventory> bingoGUIs = bingoManager.getBingoGUIs();

            MaterialList materialListObject = megaBingo.getMaterialList();

            List<Material> allMaterials = new ArrayList<>();
            allMaterials.addAll(materialListObject.easy);
            allMaterials.addAll(materialListObject.normal);
            allMaterials.addAll(materialListObject.hard);
            allMaterials.addAll(materialListObject.extreme);
            allMaterials.addAll(materialListObject.impossible);


            if (allMaterials.contains(craftedItem) && bingoGUIs.containsKey(uuid)){
                for (int i : bingoManager.getSlots()){
                    if (bingoGUIs.get(uuid).getItem(i).getType().equals(craftedItem)){
                        bingoManager.markItemAsComplete(player, craftedItem);
                    }
                }
            }
        }

    }
}
