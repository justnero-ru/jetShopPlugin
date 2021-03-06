package ru.justnero.minecraft.bukkit.shop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.justnero.minecraft.bukkit.shop.Bootstrap;
import static ru.justnero.minecraft.bukkit.shop.Language.*;

import static ru.justnero.minecraft.bukkit.shop.UtilLog.*;

/**
 *
 * @author JustNero
 */
public abstract class BasicCommand implements CommandExecutor {
    
    protected static String[] requiredPermissions;
    protected static short requiredType = 0;
    protected CommandSender sender;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] arguments) {
        if(!(sender instanceof Player)){
            warning("Sorry, but console is not supported due to obvious reasons");
            return true;
        }
        if(!haveRights(sender)) {
            sender.sendMessage(langGet("notEnoughtRights"));
            return true;
        }
        this.sender = sender;
        boolean result = execute((Player) sender, arguments);
        return result;
    }
    
    public abstract boolean execute(final Player player, String[] args); 
    
    private boolean haveRights(CommandSender sender) {
        boolean result = requiredType == 0;
        for(String perm : requiredPermissions) {
            switch(requiredType) {
                default:
                case 0:
                    result |= Bootstrap.hasPerm(sender,perm);
                    break;
                case 1:
                    result &= Bootstrap.hasPerm(sender,perm);
                    break;
            }
        }
        return result;
    }

}
