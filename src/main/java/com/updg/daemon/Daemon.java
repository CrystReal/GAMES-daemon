package com.updg.daemon;

import com.updg.daemon.commands.Command;
import com.updg.daemon.commands.RestartCommand;
import com.updg.daemon.commands.StartCommand;
import com.updg.daemon.commands.StopCommand;
import com.updg.daemon.models.ServerConfig;
import com.updg.daemon.utils.LinuxCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Alex Bond
 * Date: 19.03.13
 * Time: 1:12
 */
public class Daemon {
    public static HashMap<String, ServerInstance> servers = new HashMap<String, ServerInstance>();
    public static HashMap<String, ServerConfig> serversConfigs = new HashMap<String, ServerConfig>();
    public static Starter main;

    public static void main(String[] args) {
        main = new Starter();
    }
}
