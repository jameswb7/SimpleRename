package com.james.simplerename.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.base.Joiner;
import com.james.simplerename.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This Project is property of James © 2024
 * Redistribution of this Project is not allowed
 *
 * @author James
 * Created: 15/06/2024
 * Project: SimpleRename
 */

@CommandAlias("rename")
@CommandPermission("command.rename")
public class RenameCommand extends BaseCommand {

    @HelpCommand
    public void onHelp(Player player) {
        player.sendMessage(StringUtil.color("&cUsage: /rename <name;remove>"));
    }

    @Default
    public void onRename(Player player, String[] args) {
        if(args.length < 1) {
            onHelp(player);
            return;
        }

        ItemStack item = player.getItemInHand();
        if(item == null || item.getType() == Material.AIR) {
            player.sendMessage(StringUtil.color("&cYou must be holding an item to rename."));
            return;
        }

        String name = Joiner.on(' ').join(args);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(StringUtil.color(name));
        item.setItemMeta(meta);

        player.setItemInHand(item);
        player.updateInventory();
    }

    @Subcommand("remove")
    public void onRemove(Player player) {
        ItemStack item = player.getItemInHand();
        if(item == null || item.getType() == Material.AIR) {
            player.sendMessage(StringUtil.color("&cYou must be holding an item to rename."));
            return;
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(null);
        item.setItemMeta(meta);

        player.setItemInHand(item);
        player.updateInventory();

        player.sendMessage(StringUtil.color("&eRemoved the name of your item."));
    }
}
