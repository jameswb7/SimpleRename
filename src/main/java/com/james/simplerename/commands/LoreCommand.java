package com.james.simplerename.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.james.simplerename.util.RunnableBuilder;
import com.james.simplerename.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

/**
 * This Project is property of James © 2024
 * Redistribution of this Project is not allowed
 *
 * @author James
 * Created: 15/06/2024
 * Project: SimpleRename
 */

@CommandAlias("lore")
@CommandPermission("command.lore")
public class LoreCommand extends BaseCommand {

    @Default
    public void onLore(Player player, String[] args) {
        ItemStack item = player.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null && (meta = Bukkit.getItemFactory().getItemMeta(item.getType())) == null) {
            player.sendMessage(StringUtil.color("&cYou must be holding a valid item."));
            return;
        }

        if(args.length < 1) {
            player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
            return;
        }

        List<String> lore = meta.getLore();
        if(lore == null)
            lore = new LinkedList<>();

        LoreAction action;
        try {
            action = LoreAction.valueOf(args[0].toUpperCase());
        } catch(IllegalArgumentException notEnum) {
            player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
            return;
        }

        switch(action) {
            case ADD: {
                if(args.length < 2) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                lore.add(joinArgs(1, args));
                break;
            }
            case DELETE: {
                switch(args.length) {
                    case 1: {
                        if(lore.size() < 1) {
                            player.sendMessage(StringUtil.color("&cNothing to delete. :("));
                            return;
                        }

                        lore.remove(lore.size() - 1);
                        break;
                    }
                    case 2: {
                        int index;
                        try {
                            index = Integer.parseInt(args[1]) - 1;
                        } catch(Exception e) {
                            player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                            return;
                        }

                        if(lore.size() <= index || index < 0) {
                            player.sendMessage(StringUtil.color("&cInvalid line number."));
                            return;
                        }

                        lore.remove(index);
                        break;
                    }
                    default: {
                        return;
                    }
                }
                break;
            }
            case SET: {
                if(args.length < 3) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                int index;
                try {
                    index = Integer.parseInt(args[1]) - 1;
                } catch(Exception e) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                if(lore.size() <= index || index < 0) {
                    player.sendMessage(StringUtil.color("&cInvalid line number."));
                    return;
                }

                lore.set(index, joinArgs(2, args));
                break;
            }
            case INSERT: {
                if(args.length < 3) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                int index;
                try {
                    index = Integer.parseInt(args[1]) - 1;
                } catch(Exception e2) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                if(lore.size() <= index || index < 0) {
                    player.sendMessage(StringUtil.color("&cInvalid line number."));
                    return;
                }

                lore.add(index, joinArgs(2, args));
                break;
            }
            case CLEAR: {
                if(args.length != 1) {
                    player.sendMessage(StringUtil.color("&cUsage: /lore <add;set;delete;clear> (line;text) (text)"));
                    return;
                }

                lore.clear();
                break;
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        RunnableBuilder.bind(() -> {
            player.updateInventory();
            player.sendMessage(StringUtil.color("&aSuccessfully edit the lore of your current item."));
        }).runSync();
    }

    private static String joinArgs(int first, String... args) {
        if(first > args.length)
            return "";

        StringBuilder sb = new StringBuilder();
        for(int i = first; i <= args.length - 1; ++i)
            sb.append(" ").append(StringUtil.color(args[i]));

        return sb.substring(1);
    }

    private enum LoreAction {

        ADD,
        DELETE,
        SET,
        INSERT,
        CLEAR
    }
}

