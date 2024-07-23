package com.gohardani.oltmanager.SSH;

import com.jcraft.jsch.*;

import java.io.*;

import static org.apache.commons.codec.Resources.getInputStream;

public class SSHUtility2 {

    public static void main(String[] args) throws JSchException, IOException {
        SSHUtility2 ssh=new SSHUtility2();
        String s= ssh.runCommand("10.61.8.2",22,"acs123","acs@123","netstat -apn | grep 3000");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(s);
    }
    public String runCommand(String host, int port, String user, String password,String command) throws IOException, JSchException {
        String result = "";
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
//        ((ChannelExec) channel).setCommand(command);
//        channel.setInputStream(null);
//        ((ChannelExec) channel).setErrStream(System.err);

//        InputStream in = channel.getInputStream();
        OutputStream os = channel.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        InputStream is = channel.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        channel.connect();
        writer.println("enable");
        writer.flush();
        String response = reader.readLine();
        while(response != null) {
            // do something with response
            response = reader.readLine();
            System.out.println(response);
        }
        writer.println("display ont autofind all");
        writer.flush();
        response = reader.readLine();
        while(response != null) {
            // do something with response
            response = reader.readLine();
            System.out.println(response);
        }
        return result;
    }

}
