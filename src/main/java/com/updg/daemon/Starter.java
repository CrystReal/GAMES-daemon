package com.updg.daemon;

import com.updg.daemon.commands.Command;
import com.updg.daemon.commands.RestartCommand;
import com.updg.daemon.commands.StartCommand;
import com.updg.daemon.commands.StopCommand;
import com.updg.daemon.models.ServerConfig;
import com.updg.daemon.threads.CheckServerDownThread;
import com.updg.daemon.utils.bukkitapi.file.FileConfiguration;
import com.updg.daemon.utils.bukkitapi.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Alex
 * Date: 09.11.13  20:51
 */
public class Starter {
    private FileConfiguration config;

    public Starter() {

        config = YamlConfiguration.loadConfiguration(new File("config.yml"));
        for (String item : config.getConfigurationSection("servers").getKeys(false)) {
            Daemon.serversConfigs.put(
                    config.getString("servers." + item + ".name"),
                    new ServerConfig(config.getString("servers." + item + ".name"),
                            config.getInt("servers." + item + ".startRAM"),
                            config.getInt("servers." + item + ".maxRAM"),
                            config.getBoolean("servers." + item + ".autoStart"),
                            config.getBoolean("servers." + item + ".reloadOnKill")
                    )
            );
        }

        for (ServerConfig item : Daemon.serversConfigs.values()) {
            if (item.isAutostart())
                Server.start(item);
        }

        new CheckServerDownThread().start();

        HashMap<String, Command> commands = new HashMap<String, Command>();
        commands.put("start", new StartCommand());
        commands.put("stop", new StopCommand());
        commands.put("restart", new RestartCommand());

        try {
            Scanner in = new Scanner(System.in);
            String line;
            while ((line = in.nextLine()) != null) {
                String[] iArgs = line.split(" ");
                if (commands.containsKey(iArgs[0]))
                    commands.get(iArgs[0]).exec(iArgs);
                else
                    System.out.println("Command not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
