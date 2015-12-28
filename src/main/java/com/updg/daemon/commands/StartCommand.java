package com.updg.daemon.commands;

import com.updg.daemon.Daemon;
import com.updg.daemon.Server;

/**
 * Created by Alex
 * Date: 09.11.13  19:21
 */
public class StartCommand implements Command {
    @Override
    public void exec(String[] args) {
        if (args.length > 1)
            if (Daemon.serversConfigs.containsKey(args[1])) {
                Server.start(Daemon.serversConfigs.get(args[1]));
            } else {
                System.out.println("Instance not found");
            }
    }
}
