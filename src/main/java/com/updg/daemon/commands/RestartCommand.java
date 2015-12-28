package com.updg.daemon.commands;

import com.updg.daemon.Daemon;
import com.updg.daemon.Server;

/**
 * Created by Alex
 * Date: 09.11.13  19:21
 */
public class RestartCommand implements Command {
    @Override
    public void exec(String[] args) {
        if (Daemon.serversConfigs.containsKey(args[1])) {
            Server.restart(Daemon.serversConfigs.get(args[1]));
        } else {
            System.out.println("Instance not found");
        }
    }
}
