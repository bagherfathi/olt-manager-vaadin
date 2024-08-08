package com.gohardani.oltmanager.SSH.drafts;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class SSHUtility3 {

    public static void main(String[] args) {
        SSHUtility3 ssh=new SSHUtility3();
        String s= ssh.runCommand("192.168.1.112",22,"bagher","tbontb","netstat -apn | grep 3000");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(s);
    }
    public String runCommand(String host, int port, String user, String password,String command) {
        String result = null;
        try {
            result = "";
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            result = "Connected to " + host + ":" + port + "\n";
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command+ "\n" + "ls -l\n" + "telnet\n" + "help\n");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    String s = new String(tmp, 0, i);
                    System.out.print(s);
                    result += s + "\n";
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    result += "exit-status: " + channel.getExitStatus() + "\n";
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
            result += "DONE\n";
        } catch (Exception e) {
            e.printStackTrace();
            result+= "ERROR: " + e.getMessage() + "\n";
        }
        finally {
            return result;
        }
//        return result;
    }

}
