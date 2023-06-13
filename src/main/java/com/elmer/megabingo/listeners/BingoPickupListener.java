package com.elmer.megabingo.listeners;

import com.elmer.megabingo.managers.BingoManager;
import com.elmer.megabingo.MegaBingo;
import com.elmer.megabingo.tools.MaterialList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BingoPickupListener implements Listener {
    MegaBingo megaBingo;
    public BingoPickupListener(MegaBingo megaBingo){
        this.megaBingo = megaBingo;
    }

    @EventHandler
    public void onPickup (PlayerPickupItemEvent e){
        if (megaBingo.getBingoManager().isStarted()){
            BingoManager bingoManager = megaBingo.getBingoManager();
            Material pickedItem = e.getItem().getItemStack().getType();
            Player player = (Player) e.getPlayer();


            UUID uuid = player.getUniqueId();

            Map<UUID, Inventory> bingoGUIs = bingoManager.getBingoGUIs();

            MaterialList materialListObject = megaBingo.getMaterialList();

            List<Material> allMaterials = new ArrayList<>();
            allMaterials.addAll(materialListObject.easy);
            allMaterials.addAll(materialListObject.normal);
            allMaterials.addAll(materialListObject.hard);
            allMaterials.addAll(materialListObject.extreme);
            allMaterials.addAll(materialListObject.impossible);

            if (allMaterials.contains(pickedItem)){
                for (int i : bingoManager.getSlots()){
                    if (bingoGUIs.get(uuid).getItem(i).getType().equals(pickedItem)){
                        bingoManager.markItemAsComplete(player, pickedItem);
                    }
                }
            }
        }


    }
}
