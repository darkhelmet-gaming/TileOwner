package network.darkhelmet.tileowner.listeners;

import network.darkhelmet.tileowner.TileOwner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(final BlockPlaceEvent event) {
        TileOwner.getInstance().setOwner(event.getBlock(), event.getPlayer().getUniqueId());
    }
}
