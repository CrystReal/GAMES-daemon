package com.updg.daemon;

import com.updg.daemon.models.ServerConfig;
import com.updg.daemon.threads.ServerRestartThread;
import com.updg.daemon.threads.ServerStopThread;
import com.updg.daemon.utils.LinuxCommand;

/**
 * Created by Alex Bond
 * Date: 19.03.13
 * Time: 1:50
 */
public class Server {
    public static void createUser(String id) {
        LinuxCommand cmd;
        cmd = new LinuxCommand(new String[]{"/usr/sbin/groupadd  \"mc-" + id + "\""}, null, false);
        cmd.run();
        cmd = new LinuxCommand(new String[]{"/usr/sbin/useradd -c \"SC Server " + id + "\" -d \"/home/servers/" + id + "\" -g \"mc-" + id + "\" -s /bin/false \"mc-" + id + "\""}, null, false);
        cmd.run();
    }

    public static void removeUser(String id) {
        LinuxCommand cmd;
        cmd = new LinuxCommand(new String[]{"/usr/sbin/groupdel  mc-" + id}, null, false);
        cmd.run();
        cmd = new LinuxCommand(new String[]{"/usr/sbin/userdel mc-" + id}, null, false);
        cmd.run();
    }

    public static void start(ServerConfig config) {
        if (Daemon.servers.containsKey(config.getId())) {
            if (Daemon.servers.get(config.getId()).isRunning()) {
                System.out.println("Server already running");
                return;
            } else {
                Daemon.servers.remove(config.getId());
            }
        }
        config.setStopedManualy(false);
        Daemon.servers.put(config.getId(), new ServerInstance(config.getId(), config.getStartRam(), config.getMaxRam()));
        Daemon.servers.get(config.getId()).start();
    }

    public static void stop(ServerConfig config) {
        if (Daemon.servers.containsKey(config.getId())) {
            if (Daemon.servers.get(config.getId()).isRunning()) {
                if (!Daemon.servers.get(config.getId()).isStoping()) {
                    config.setStopedManualy(true);
                    new ServerStopThread(config).start();
                }
            } else {
                System.out.println("Server died");
                Daemon.servers.remove(config.getId());
            }
        } else {
            System.out.println("Server not started");
        }
    }

    public static void restart(ServerConfig config) {
        if (Daemon.servers.containsKey(config.getId())) {
            if (Daemon.servers.get(config.getId()).isRunning()) {
                if (!Daemon.servers.get(config.getId()).isStoping()) {
                    config.setStopedManualy(false);
                    new ServerRestartThread(config).start();
                }
            } else {
                System.out.println("Server died");
                Daemon.servers.remove(config.getId());
            }
        } else {
            System.out.println("Server not started");
        }
    }

    public static void kill(ServerConfig config) {
        if (Daemon.servers.containsKey(config.getId())) {
            if (Daemon.servers.get(config.getId()).isRunning()) {
                Daemon.servers.remove(config.getId());
            } else {
                System.out.println("Server died");
                Daemon.servers.remove(config.getId());
            }
        } else {
            System.out.println("Server not started");
        }
    }

    public static boolean isRunning(ServerConfig config) {
        if (Daemon.servers.containsKey(config.getId())) {
            if (Daemon.servers.get(config.getId()).isRunning()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
