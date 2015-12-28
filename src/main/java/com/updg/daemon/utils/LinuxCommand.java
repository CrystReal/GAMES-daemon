package com.updg.daemon.utils;

import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Bond
 * Date: 19.03.13
 * Time: 1:14
 */
public class LinuxCommand extends Thread implements Runnable {
    private String folder = null;
    private boolean debug = false;
    private String[] command;
    private BufferedWriter stdin = null;
    private InputStream stderr = null;
    private InputStream stdout = null;
    private Process proc = null;
    private boolean running = false;

    public LinuxCommand(String[] command, String folder, boolean debug) {
        this.command = command;
        this.folder = folder;
        this.debug = debug;
    }

    public void run() {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] cmd = {"/bin/sh", "-c"};
            cmd = ArrayUtils.join(cmd, command);
            if (this.folder != null)
                proc = rt.exec(cmd, null, new File(this.folder));
            else
                proc = rt.exec(cmd);
            this.running = true;
            stderr = proc.getErrorStream();
            stdout = proc.getInputStream();
            stdin = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                this.running = false;
                System.out.println(e.getMessage());
            }
            this.running = false;
        } catch (IOException e) {
            this.running = false;
            System.out.println(e.getMessage());
        }
    }

    public void exec(String cmd) {
        try {
            stdin.write(cmd);
            stdin.newLine();
            stdin.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return this.running;
    }
}
