package com.updg.daemon.threads;

import com.updg.daemon.Daemon;
import com.updg.daemon.Server;
import com.updg.daemon.models.ServerConfig;

/**
 * Created by Alex
 * Date: 09.11.13  18:52
 */
public class CheckServerDownThread extends Thread implements Runnable {

    public static boolean work = true;

    public void run() {

        while (work) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ServerConfig item : Daemon.serversConfigs.values()) {
                if (Daemon.servers.containsKey(item.getId())) {
                    if (!Daemon.servers.get(item.getId()).isRunning()) {
                        if (item.isRestartOnKill()) {
                            Server.start(item);
                        }
                    }
                } else {
                    if (item.isRestartOnKill()) {
                        Server.start(item);
                    }
                }
            }

        }
    }
}
