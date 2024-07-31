package com.gohardani.oltmanager.Utility;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SSHOutputProcessor {
    private static final Logger log = LoggerFactory.getLogger(SSHOutputProcessor.class);

    public static void main(String[] args) {
        String displayBoardI=" -------------------------------------------------------------------------\n" +
                "  SlotID  BoardName  Status          SubType0 SubType1    Online/Offline\n" +
                "  -------------------------------------------------------------------------\n" +
                "  0\n" +
                "  1       H805GPFD   Normal\n" +
                "  2       H805GPFD   Normal\n" +
                "  3       H805GPFD   Normal\n" +
                "  4       H805GPFD   Normal\n" +
                "  5       H805GPFD   Normal\n" +
                "  6\n" +
                "  7\n" +
                "  8\n" +
                "  9       H802SCUN   Active_normal   FLBA\n" +
                "  10      H802SCUN   Standby_normal  FLBA\n" +
                "  11\n";
        getSlotList(displayBoardI);
    }
    //process command: FARDEASADI-OLT# display board 0
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity
    public static ArrayList<Slot> getSlotList(String command) {
        ArrayList<Slot> slots=new ArrayList<>();
        String[] commands=command.split("\n");
         for(String s:commands)
         {
             if(s.contains("-------------------")) {
                 System.out.println("dashes bypassed");
                 continue;
             }
             if(s.trim().startsWith("SlotID"))
             {
                 System.out.println("header bypassed");
                 continue;
             }
            if(s.trim().matches("[0-9].*")) {
                Slot sl=new Slot();
                System.out.println(s);
                String[] fields=s.trim().split("\\s+");
                if(fields.length==1){
                    sl.setSlotid(Integer.parseInt(fields[0]));
                }
                else if(fields.length==2){
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                }
                if(fields.length==3) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                }
                else if(fields.length==4) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                }
                else if(fields.length==5) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                    sl.setSubType1(fields[4]);
                }
                else if(fields.length==6) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                    sl.setSubType1(fields[4]);
                    sl.setOnOff(fields[5]);
                }
                else
                    System.out.println("error in parsing slots input command");
                slots.add(sl);
//                for(String f:fields) {
////                    System.out.println(f);
//
//                }
            }
//            System.out.println(s);
         }
         return slots;
    }
    //process command: FARDEASADI-OLT(config-if-gpon-0/5)# display ont info 1 all
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity
    public static ArrayList<Ont> getOntList(String command) {
        ArrayList<Ont> onts=new ArrayList<>();
        String[] commands=command.split("\n");
        for(String s:commands)
        {
            if(s.contains("-------------------")) {
                System.out.println("dashes bypassed");
                continue;
            }
            if(s.trim().startsWith("SlotID"))
            {
                System.out.println("header bypassed");
                continue;
            }
            if(s.trim().matches("[0-9].*")) {
                Ont o=new Ont();
                System.out.println(s);
                String[] fields=s.trim().split("\\s+");
                if(fields.length==1){
//                    o.setSlotid(Integer.parseInt(fields[0]));
                }
                else if(fields.length==2){
//                    sl.setSlotid(Integer.parseInt(fields[0]));
//                    sl.setBoardName(fields[1]);
                }
                if(fields.length==3) {
//                    sl.setSlotid(Integer.parseInt(fields[0]));
//                    sl.setBoardName(fields[1]);
//                    sl.setStatus(fields[2]);
                }
                else if(fields.length==4) {
//                    sl.setSlotid(Integer.parseInt(fields[0]));
//                    sl.setBoardName(fields[1]);
//                    sl.setStatus(fields[2]);
//                    sl.setSubType0(fields[3]);
                }
                else if(fields.length==5) {
//                    sl.setSlotid(Integer.parseInt(fields[0]));
//                    sl.setBoardName(fields[1]);
//                    sl.setStatus(fields[2]);
//                    sl.setSubType0(fields[3]);
//                    sl.setSubType1(fields[4]);
                }
                else if(fields.length==6) {
//                    sl.setSlotid(Integer.parseInt(fields[0]));
//                    sl.setBoardName(fields[1]);
//                    sl.setStatus(fields[2]);
//                    sl.setSubType0(fields[3]);
//                    sl.setSubType1(fields[4]);
//                    sl.setOnOff(fields[5]);
                }
                else
                    System.out.println("error in parsing onts input command");
                onts.add(o);
//                for(String f:fields) {
////                    System.out.println(f);
//
//                }
            }
//            System.out.println(s);
        }
        return onts;
    }
}
