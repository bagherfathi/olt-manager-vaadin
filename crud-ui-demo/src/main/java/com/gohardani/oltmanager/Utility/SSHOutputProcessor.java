package com.gohardani.oltmanager.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSHOutputProcessor {
    private static final Logger log = LoggerFactory.getLogger(SSHOutputProcessor.class);

    public static void main(String[] args) {
        getSlotList("test");
    }
    public static void getSlotList(String command) {
        command=" -------------------------------------------------------------------------\n" +
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
        String[] commands=command.split("\n");
         for(String s:commands)
         {
             if(s.contains("-------------------")) {
                 log.debug("dashes bypassed");
                 continue;
             }
             if(s.trim().startsWith("SlotID"))
             {
                 log.debug("header bypassed");
                 continue;
             }

            System.out.println(s);
         }
    }
}
