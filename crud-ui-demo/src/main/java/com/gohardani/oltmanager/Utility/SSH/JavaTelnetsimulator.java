package com.gohardani.oltmanager.Utility.SSH;

import com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor;
import com.gohardani.oltmanager.entity.OntUnregistered;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class JavaTelnetsimulator {
    private static int cnt=0;
    public static void main(String[] arg) {
        try {
            ArrayList<String> c=new ArrayList<>();
            c.add("enable");
            c.add("config");
//            c.add("interface gpon " +frame.getFrameNumberAsText() + " " + slot.getSlotidAsText());
//            c.add("display ont info " + port.getPortNumberAsString()  + " all");
//            c.add("interface gpon " + "0" + "/" + "1");
            c.add("display ont autofind all");
            for(int i=0;i<30;i++)
                c.add("\t");
            c.add("\n\n\n");
//            c.add("quit");
            c.add("quit");
            c.add("quit");
            c.add("y");
//            System.out.println(telnetConnection(c,"acstest","acstest@123","10.61.8.2",22));
            String result=telnetConnection(c,"acstest","acstest@123","10.61.8.2",22);
            List<OntUnregistered> ontu= SSHOutputProcessor.getUnregisterdONTList(result);
            for(OntUnregistered o:ontu)
                System.out.println(o);

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

        session.connect(5000);
        //session.connect(30000);   // making a connection with timeout.

        Channel channel=session.openChannel("shell");

        channel.connect(5000);

        DataInputStream dataIn = new DataInputStream(channel.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
        DataOutputStream dataOut = new DataOutputStream(channel.getOutputStream());
        sleep(5000);
        System.out.println("Starting SSH connection...");
        //run commands
        for(String command:commands){
            dataOut.writeBytes(command +" \n");
            dataOut.flush();
            sleep(1000);
            if(command.startsWith("ont add"))
                sleep(10000);
        }
        System.out.println("Flushing SSH...");
        String result=read(reader);

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
            if(cnt2>1000 || cnt>1000)
                break;
        }
        return result;
    }
}