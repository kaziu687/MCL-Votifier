package pl.ibcgames.mclvotifier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ibcgames.mclvotifier.Modules.Configuration;

import java.util.logging.Logger;

public final class Votifier extends JavaPlugin {
    public static Logger log = Bukkit.getLogger();
    public static Votifier plugin;

    public static Configuration Config;

    @Override
    public void onEnable() {
        plugin = this;
        Config = new Configuration(this);
        this.saveDefaultConfig();

        String token = this.plugin.getConfiguration().get().getString("server_id");

        if (token == null || token.equalsIgnoreCase("paste_server_id_here")) {
            this.warning("No server id found in MCL-Votifier config");
            this.warning("How to use this plugin? See tutorial at:");
            this.warning("https://mc-list.org/mcl-votifier-plugin");
        }

        this.getCommand("mcl-vote").setExecutor(new Vote());
        this.getCommand("mcl-reward").setExecutor(new Reward());
        this.getCommand("mcl-test").setExecutor(new Test());
    }

    @Override
    public void onDisable() {
    }

    public Configuration getConfiguration() {
        return this.Config;
    }

    public void log(String log) {
        plugin.log.info("[MCL-Votifier] " + log);
    }

    public void warning(String log) {
        plugin.log.warning("[MCL-Votifier] " + log);
    }
}
