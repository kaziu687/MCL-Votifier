package pl.ibcgames.mclvotifier;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class Test implements CommandExecutor {
    String token = Votifier.plugin.getConfiguration().get().getString("server_id");
    boolean require_permission = Votifier.plugin.getConfiguration().get().getBoolean("require_permission");
    List<String> list = Votifier.plugin.getConfiguration().get().getStringList("commands_on_reward");
    String[] array = list.toArray(new String[0]);
    Map<String, Date> timeouts = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Runnable runnable = () -> {
            if (token == null || token.equalsIgnoreCase("paste_server_id_here")) {
                sender.sendMessage(Utils.message("&cNo server id found in MCL-Votifier config"));
                sender.sendMessage(Utils.message("&cHow to use this plugin? See tutorial at:"));
                sender.sendMessage(Utils.message("&ahttps://minecraft-servers.gg/mcl-votifier-plugin"));
                return;
            }

            if (!sender.isOp()) {
                sender.sendMessage(Utils.message("&cThis command can be used only by server operator"));
                return;
            }

            if (require_permission && !sender.hasPermission("mclvotifier.reward")) {

                sender.sendMessage(Utils.message("&cYou need &amclvotifier.reward &c permission to use this command"));
                return;
            }

            sender.sendMessage(Utils.message("&aThis command can be used to test a reward"));
            sender.sendMessage(Utils.message("&aIf you need to test connection with out website"));
            sender.sendMessage(Utils.message("&asimply claim your reward using &c/mcl-reward &acommand"));
            execute(sender);
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return true;
    }

    private void execute(CommandSender sender) {
        for (String cmd : list) {
            cmd = cmd.replace("{PLAYER}", sender.getName());
            String finalCmd = cmd;

            Bukkit.getScheduler().scheduleSyncDelayedTask(Votifier.plugin, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), finalCmd);
            }, 0);
        }
    }
}
