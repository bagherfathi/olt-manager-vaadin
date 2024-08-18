package com.gohardani.oltmanager.Utility.SSH;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class JavaTelnetsimulator {
    private static int cnt=0;
    public static void main(String[] arg) {
        try {
            ArrayList<String> c=new ArrayList<>();
            c.add("enable");
            c.add("display board");
            c.add("quit");
            c.add("exit");
            System.out.println(telnetConnection(c,"admin","admin","192.168.154.129",6001));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String telnetConnection(ArrayList<String> commands, String user, String password, String host, int port) throws JSchException, Exception {
        JSch jsch=new JSch();
        Session session=jsch.getSession(user, host, port);
        session.setPassword(password);
        // It must not be recommended, but if you want to skip host-key check,
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect(3000);
        //session.connect(30000);   // making a connection with timeout.

        Channel channel=session.openChannel("shell");

        channel.connect(3000);

        DataInputStream dataIn = new DataInputStream(channel.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
        DataOutputStream dataOut = new DataOutputStream(channel.getOutputStream());

        System.out.println("Starting telnet connection...");
        //run commands
        for(String command:commands){
            dataOut.writeBytes(command+" \r\n");
            dataOut.flush();
            sleep(1000);
        }
        //end run commands
//        dataOut.writeBytes("help\r\n");
//        dataOut.flush();
//        sleep(1000);
//        String result=read(reader);
//      dataOut.writeBytes("enable\r\n");
//        //       dataOut.writeBytes(command+"\r\n");
//        dataOut.writeBytes("display ont summary ont\r\n"); //exit from telnet
//        dataOut.flush();
//        sleep(1000);
//        result+=read(reader);
//        dataOut.writeBytes("quit\r\n"); //exit from shell
//        dataOut.flush();
//        sleep(1000);
////        result+=read(reader);
//        dataOut.writeBytes("exit\r\n"); //exit from shell
//        dataOut.flush();
//        sleep(1000);
//        result+=read(reader);
        System.out.println("Flushing telnet...");
        String result=read(reader);
//        String line = reader.readLine();
//        String result = line +"\n";
//        System.out.println(line);
//        int i=0;
//        while (!(line= reader.readLine()).equals("Connection closed by foreign host")){
//            result += line +"\n";
//            System.out.println(line);
//            if(i++>20)
//                break;
//        }

        dataIn.close();
        dataOut.close();
        channel.disconnect();
        session.disconnect();
        System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTT::::::::::::::\n" + result);
        System.out.println("END OF RESULTTTTTTTTTTTTTTTTTTTT::::::::::::::\n");
        return result;

    }
    private static String read(BufferedReader reader) throws IOException {
        String result="",line="";
        int cnt2=0;
        while (reader.ready()){
            line=reader.readLine();
            System.out.println("cnt:" + cnt +" line:" + line);
            result +=line+"\r\n";
            cnt++;cnt2++;
            if(cnt2>100 || cnt>100)
                break;
        }
        return result;
    }
}