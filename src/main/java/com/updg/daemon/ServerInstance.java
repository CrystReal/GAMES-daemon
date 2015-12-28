package com.updg.daemon;

import com.updg.daemon.utils.LinuxCommand;

/**
 * Created by Alex Bond
 * Date: 19.03.13
 * Time: 2:03
 */
public class ServerInstance extends Thread implements Runnable {
    private String id;
    private LinuxCommand proc = null;
    private int startRAM = 512;
    private int maxRAM = 1024;
    private boolean stoping = false;

    public ServerInstance(String id, int startRAM, int maxRAM) {
        this.id = id;
        this.startRAM = startRAM;
        this.maxRAM = maxRAM;
    }

    @Override
    public void run() {
        System.out.println("Try to start instance " + this.id);
        ///bin/su mc-" + id + "
        String[] cmd = {"/usr/bin/java -Xms" + this.startRAM + "M -Xmx" + this.maxRAM + "M -jar spigot.jar nogui"};
        proc = new LinuxCommand(cmd, "/home/servers/" + id + "/", true);
        proc.start();
    }

    public void exec(String cmd) {
        this.proc.exec(cmd);
    }

    public boolean isRunning() {
        return this.proc.isRunning();
    }

    public boolean isStoping() {
        return stoping;
    }

    public void setStoping(boolean stoping) {
        this.stoping = stoping;
    }
}
