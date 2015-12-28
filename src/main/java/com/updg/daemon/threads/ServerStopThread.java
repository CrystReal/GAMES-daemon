package com.updg.daemon.threads;

import com.updg.daemon.Daemon;
import com.updg.daemon.models.ServerConfig;

/**
 * Created by Alex
 * Date: 09.11.13  18:52
 */
public class ServerStopThread extends Thread implements Runnable {
    private ServerConfig config;

    public ServerStopThread(ServerConfig config) {
        this.config = config;
    }

    public void run() {
        Daemon.servers.get(config.getId()).setStoping(true);
        Daemon.servers.get(config.getId()).exec("say СЕРВЕР ВЫКЛЮЧИТСЯ ЧЕРЕЗ 15 СЕКУНД!");
        int i = 15;
        while (i > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            i--;
            if (i == 10)
                Daemon.servers.get(config.getId()).exec("say СЕРВЕР ВЫКЛЮЧИТСЯ ЧЕРЕЗ 10 СЕКУНД!");
            if (i == 5)
                Daemon.servers.get(config.getId()).exec("say СЕРВЕР ВЫКЛЮЧИТСЯ ЧЕРЕЗ 5 СЕКУНД!");
        }
        Daemon.servers.get(config.getId()).exec("stop");
        boolean running = true;
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (!Daemon.servers.get(config.getId()).isRunning())
                running = false;
        }
        Daemon.servers.remove(config.getId());
        System.out.println("Instance '" + config.getId() + "' turned off");
    }
}
