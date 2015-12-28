package com.updg.daemon.models;

/**
 * Created by Alex
 * Date: 09.11.13  18:44
 */
public class ServerConfig {
    private boolean restartOnKill = false;
    private String id;
    private int startRam;
    private int maxRam;
    private boolean autostart = false;
    private boolean stopedManualy = false;

    public ServerConfig(String id, int startRam, int maxRam, boolean autoStart, boolean restartOnKill) {
        this.id = id;
        this.startRam = startRam;
        this.maxRam = maxRam;
        this.autostart = autoStart;
        this.restartOnKill = restartOnKill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStartRam() {
        return startRam;
    }

    public void setStartRam(int startRam) {
        this.startRam = startRam;
    }

    public int getMaxRam() {
        return maxRam;
    }

    public void setMaxRam(int maxRam) {
        this.maxRam = maxRam;
    }

    public boolean isAutostart() {
        return autostart;
    }

    public boolean isRestartOnKill() {
        if (stopedManualy)
            return false;
        return restartOnKill;
    }

    public void setStopedManualy(boolean stopedManualy) {
        this.stopedManualy = stopedManualy;
    }
}
