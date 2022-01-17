package network.darkhelmet.tileowner.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;

import java.util.Optional;

import network.darkhelmet.tileowner.TileOwner;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@CommandAlias("tileowner")
public class TileOwnerCommand extends BaseCommand {
    /**
     * Check who owns a block.
     *
     * @param player The player
     */
    @Subcommand("check")
    @CommandPermission("tileowner.check")
    public void onCheck(Player player) {
        Block target = player.getTargetBlock(null, 5);

        Optional<OfflinePlayer> owner = TileOwner.getInstance().getOwner(target);

        if (owner.isPresent()) {
            player.sendMessage(String.format("Block is owned by %s", owner.get().getName()));
        } else {
            player.sendMessage("No one owns that block.");
        }
    }

    /**
     * Clear the current owner of a block.
     *
     * @param player The player
     */
    @Subcommand("clear")
    @CommandPermission("tileowner.clear")
    public void onClear(Player player) {
        Block target = player.getTargetBlock(null, 5);

        if (!TileOwner.getInstance().hasOwner(target)) {
            player.sendMessage(ChatColor.RED + "No one owns this block.");
        } else {
            TileOwner.getInstance().clearOwner(target);

            player.sendMessage(ChatColor.GREEN + "Cleared the owner of that block.");
        }
    }

    /**
     * Set yourself as the owner for a block.
     *
     * @param player The player
     */
    @Subcommand("set")
    @CommandCompletion("@players")
    @CommandPermission("tileowner.set")
    public void onSet(Player player) {
        onSet(player, player);
    }

    /**
     * Set an owner to the block.
     *
     * @param player The player
     * @param owner The player who will own this block
     */
    @Subcommand("set")
    @CommandCompletion("@players")
    @CommandPermission("tileowner.set")
    public void onSet(Player player, OfflinePlayer owner) {
        Block target = player.getTargetBlock(null, 5);

        if (TileOwner.getInstance().hasOwner(target)) {
            player.sendMessage(ChatColor.RED + "Someone already owns that block.");
        } else {
            TileOwner.getInstance().setOwner(target, owner.getUniqueId());

            player.sendMessage(ChatColor.GREEN + "Registered " + owner.getName() + " as the owner of that block.");
        }
    }
}
