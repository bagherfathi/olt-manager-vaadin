package com.gohardani.oltmanager.Utility.sshOutputProcessor;

import com.gohardani.oltmanager.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.gohardani.oltmanager.Utility.dialog.DialogModal.confirmDialog;

public class SSHOutputProcessor {
    private static final Logger log = LoggerFactory.getLogger(SSHOutputProcessor.class);

    public static void main(String[] args) {
        String displayPort = "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/0\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1094254\n" +
                "  Temperature(C)               46\n" +
                "  TX Bias current(mA)          28\n" +
                "  Supply Voltage(V)            3.21\n" +
                "  TX power(dBm)                3.47\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             20\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             20.0\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/1\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1068608\n" +
                "  Temperature(C)               47\n" +
                "  TX Bias current(mA)          26\n" +
                "  Supply Voltage(V)            3.17\n" +
                "  TX power(dBm)                3.64\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             20\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             20.0\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/2\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1105568\n" +
                "  Temperature(C)               45\n" +
                "  TX Bias current(mA)          17\n" +
                "  Supply Voltage(V)            3.24\n" +
                "  TX power(dBm)                3.77\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             40\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             -\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/3\n" +
                "  Optical Module status        Offline\n" +
                "  Port state                   Offline";

        String displayont = "-----------------------------------------------------------------------------\n" +
                "  F/S/P   ONT         SN         Control     Run      Config   Match    Protect\n" +
                "          ID                     flag        state    state    state    side\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  0/ 2/1    0  485754433B9FB99C  active      online   normal   mismatch no\n" +
                "  0/ 2/1    1  48575443E6A0432A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    2  48575443B763EB2A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    3  48575443CAFD1A41  active      offline  initial  initial  no\n" +
                "  0/ 2/1    4  48575443AEE058A7  active      online   normal   mismatch no\n" +
                "  0/ 2/1    5  48575443DBAF6F8D  active      online   normal   match    no\n" +
                "  0/ 2/1    6  48575443B797DA2A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    7  485754434624B89B  active      offline  initial  initial  no\n" +
                "  0/ 2/1    8  485754437386328D  active      online   normal   match    no\n" +
                "  0/ 2/1    9  485754438797D78A  active      offline  initial  initial  no\n" +
                "  0/ 2/1   10  485754431F010D9B  active      online   normal   mismatch no\n" +
                "  0/ 2/1   11  48575443015AF29A  active      offline  initial  initial  no\n" +
                "  0/ 2/1   12  485754438B1A952A  active      online   normal   match    no\n" +
                "  0/ 2/1   13  485754437F3BBC75  active      offline  initial  initial  no\n" +
                "  0/ 2/1   14  485754431D21548A  active      online   normal   match    no\n" +
                "  0/ 2/1   15  485754431E8C959B  active      online   normal   mismatch no\n" +
                "  0/ 2/1   16  48575443AB1AE32A  active      online   normal   match    no\n" +
                "  0/ 2/1   17  48575443B00F6796  active      online   normal   match    no\n" +
                "  0/ 2/1   18  48575443B00EB196  active      online   normal   match    no";

        String displayBoardI = " -------------------------------------------------------------------------\n" +
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
        ArrayList<Port> ports = new ArrayList<>();
        ports=getPortList(displayPort);
        for(Port p:ports)
            System.out.println(p);
//        getOntList(displayont);
//        getSlotList(displayBoardI);
    }

    public static ArrayList<Ont> testGetOntList() {
        String displayont = "-----------------------------------------------------------------------------\n" +
                "  F/S/P   ONT         SN         Control     Run      Config   Match    Protect\n" +
                "          ID                     flag        state    state    state    side\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  0/ 2/1    0  485754433B9FB99C  active      online   normal   mismatch no\n" +
                "  0/ 2/1    1  48575443E6A0432A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    2  48575443B763EB2A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    3  48575443CAFD1A41  active      offline  initial  initial  no\n" +
                "  0/ 2/1    4  48575443AEE058A7  active      online   normal   mismatch no\n" +
                "  0/ 2/1    5  48575443DBAF6F8D  active      online   normal   match    no\n" +
                "  0/ 2/1    6  48575443B797DA2A  active      offline  initial  initial  no\n" +
                "  0/ 2/1    7  485754434624B89B  active      offline  initial  initial  no\n" +
                "  0/ 2/1    8  485754437386328D  active      online   normal   match    no\n" +
                "  0/ 2/1    9  485754438797D78A  active      offline  initial  initial  no\n" +
                "  0/ 2/1   10  485754431F010D9B  active      online   normal   mismatch no\n" +
                "  0/ 2/1   11  48575443015AF29A  active      offline  initial  initial  no\n" +
                "  0/ 2/1   12  485754438B1A952A  active      online   normal   match    no\n" +
                "  0/ 2/1   13  485754437F3BBC75  active      offline  initial  initial  no\n" +
                "  0/ 2/1   14  485754431D21548A  active      online   normal   match    no\n" +
                "  0/ 2/1   15  485754431E8C959B  active      online   normal   mismatch no\n" +
                "  0/ 2/1   16  48575443AB1AE32A  active      online   normal   match    no\n" +
                "  0/ 2/1   17  48575443B00F6796  active      online   normal   match    no\n" +
                "  0/ 2/1   18  48575443B00EB196  active      online   normal   match    no";
                return getOntList(displayont);
    }

        public static ArrayList<Port> testGetPortList() {
        String displayPort = "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/0\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1094254\n" +
                "  Temperature(C)               46\n" +
                "  TX Bias current(mA)          28\n" +
                "  Supply Voltage(V)            3.21\n" +
                "  TX power(dBm)                3.47\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             20\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             20.0\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/1\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1068608\n" +
                "  Temperature(C)               47\n" +
                "  TX Bias current(mA)          26\n" +
                "  Supply Voltage(V)            3.17\n" +
                "  TX power(dBm)                3.64\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             20\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             20.0\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/2\n" +
                "  Optical Module status        Online\n" +
                "  Port state                   Online\n" +
                "  Laser state                  Normal\n" +
                "  Available bandwidth(Kbps)    1105568\n" +
                "  Temperature(C)               45\n" +
                "  TX Bias current(mA)          17\n" +
                "  Supply Voltage(V)            3.24\n" +
                "  TX power(dBm)                3.77\n" +
                "  Illegal rogue ONT            Inexistent\n" +
                "  Max Distance(Km)             40\n" +
                "  Wave length(nm)              1490\n" +
                "  Fiber type                   Single Mode\n" +
                "  Length(9▒▒m)(km)             -\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  F/S/P                        0/2/3\n" +
                "  Optical Module status        Offline\n" +
                "  Port state                   Offline";
                return getPortList(displayPort);
    }
        public static ArrayList<Slot> testGetSlotList(){
        String displayBoardI = " -------------------------------------------------------------------------\n" +
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
        return getSlotList(displayBoardI);
    }
    //process command: FARDEASADI-OLT# display board 0
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity

    public static ArrayList<Slot> getSlotList(String commandOutput) {
        ArrayList<Slot> slots = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        for (String s : commands) {
            if(s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            if(s.trim().startsWith("---- More")){
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.trim();
            }
            if (s.contains("-------------------")) {
                System.out.println("dashes bypassed");
                continue;
            }
            if (s.trim().startsWith("SlotID")) {
                System.out.println("header bypassed");
                continue;
            }
            if(s.trim().startsWith("---- More")){
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.trim();
            }
            if (s.trim().matches("[0-9].*")) {
                Slot sl = new Slot();
                System.out.println(s);
                String[] fields = s.trim().split("\\s+");
                if (fields.length == 1) {
//                    for(String f:fields)
//                        System.out.println("field---- " + f);
//                    System.out.println("value of field:" +fields[0]);
                    int j=Integer.parseInt(fields[0]);
//                    System.out.println("value of j:" +j);

                    sl.setSlotid(j);// a problem is here, when we just set slot id, repo cant save slot
                    sl.setBoardName("NoName");// temporiraly resolve
                } else if (fields.length == 2) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                } else if (fields.length == 3) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                } else if (fields.length == 4) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                } else if (fields.length == 5) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                    sl.setSubType1(fields[4]);
                } else if (fields.length == 6) {
                    sl.setSlotid(Integer.parseInt(fields[0]));
                    sl.setBoardName(fields[1]);
                    sl.setStatus(fields[2]);
                    sl.setSubType0(fields[3]);
                    sl.setSubType1(fields[4]);
                    sl.setOnOff(fields[5]);
                } else
                    System.out.println("error in parsing slot command output fiels len is:" + fields.length);
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
    public static ArrayList<Ont> getOntList(String commandOutput) {
        ArrayList<Ont> onts = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        for (String s : commands) {
            if(s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            else if(s.trim().startsWith("---- More")){
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.trim();
            }
            if (s.contains("-------------------")) {
                System.out.println("line of dashes bypassed");
                continue;
            }
            if (s.trim().startsWith("F/S/P")) {
                System.out.println("header line 1, bypassed");
                continue;
            }
            if (s.trim().startsWith("ID")) {
                System.out.println("header line 2, bypassed");
                continue;
            }

            String[] fields = s.trim().split("\\s+");
//            System.out.println("field no:"+fields.length);

            if (s.trim().matches("[0-9].*")) {
//                System.out.println("line start with digit");
                Ont o = new Ont();
//                System.out.println(s);
//                String[] fields = s.trim().split("\\s+");
                if (fields.length == 9) {
                    o.setFrameNo(Integer.parseInt(fields[0].replaceAll("/", " ").trim()));
                    String[] ts = fields[1].trim().split("/");
                    o.setSlotNo(Integer.parseInt(ts[0].trim()));
                    o.setPortNo(Integer.parseInt(ts[1].trim()));
                    o.setOntID(Integer.parseInt(fields[2].trim()));
                    o.setSerialNumber(fields[3].trim());
                    o.setControlFlag(fields[4].trim());
                    o.setRunState(fields[5].trim());
                    o.setConfigState(fields[6].trim());
                    o.setMatchState(fields[7].trim());
                    o.setProtectSide(fields[8].trim());
                    onts.add(o);
                } else
                if (fields.length == 8) {
                    String[] ts = fields[0].trim().split("/");
                    o.setFrameNo(Integer.parseInt(ts[0].trim()));
                    o.setSlotNo(Integer.parseInt(ts[1].trim()));
                    o.setPortNo(Integer.parseInt(ts[2].trim()));
                    o.setOntID(Integer.parseInt(fields[1].trim()));
                    o.setSerialNumber(fields[2].trim());
                    o.setControlFlag(fields[3].trim());
                    o.setRunState(fields[4].trim());
                    o.setConfigState(fields[5].trim());
                    o.setMatchState(fields[6].trim());
                    o.setProtectSide(fields[7].trim());
                    onts.add(o);
                }
                    System.out.println("error in parsing ont command output");
            }
        }
        return onts;
    }

    //process command: FARDEASADI-OLT(config-if-gpon-0/5)# display ont info 1 all
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity
    public static ArrayList<Port> getPortList(String commandOutput) {
        ArrayList<Port> ports = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        Port p=null;
        int s1=0;
        for (String s : commands) {
            if(s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            if(s.trim().startsWith("---- More")){
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.trim();
            }
            if (s.contains("-------------------")) {
                s1=1;
                p = new Port();
                continue;
            }

            if(s1==1) {
                //port section
                if (s.trim().startsWith("F/S/P")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setFsp(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Optical")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setOpticalModuleStatus(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Port")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setPortState(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Laser")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setLaserState(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Available")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setAvailableBandwidth(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Temperature")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setTemperature(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("TX Bias")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setTXBiasCurrent(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Supply")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setSupplyVoltage(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("TX power")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setTXPower(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Illegal")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setIllegalRogueONT(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Max")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setMaxDistance(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Wave")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setWaveLength(lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Fiber")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setFiberType(lineparts[lineparts.length - 2] + " " + lineparts[lineparts.length - 1]);
                } else if (s.trim().startsWith("Length")) {
                    String[] lineparts = s.trim().split("\\s+");
                    p.setLength(lineparts[lineparts.length - 1]);
                    if(p.getPortNumberAsString()!=null && !p.getPortNumberAsString().isEmpty())
                        ports.add(p);
                    s1 = 0;
                }
            }
        }

        return ports;
    }
    //enable
    //config
    //process command: FARDEASADI-OLT(config-if-gpon-0/5)# display ont autofind all
    // or 1 or 2 or ...
    //return array of onts can be saved in ontunregistered entity
    public static ArrayList<OntUnregistered> getUnregisterdONTList(String commandOutput) {
        ArrayList<OntUnregistered> ontus = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        OntUnregistered ount=null;
        int s1=0;
        for (String s : commands) {
            if(s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            if(s.trim().startsWith("---- More")){
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("----")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.substring(s.indexOf("[37D")+5);
                s=s.trim();
            }
            if (s.contains("-------------------")) {
                s1=1;
                ount = new OntUnregistered();
                continue;
            }

            if(s1==1) {
                //port section
                if (s.trim().startsWith("Number")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setNo(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("F/S/P")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setFsp(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Ont SN")) {
                    String[] lineparts = s.trim().split(":");
                    String[] sa=lineparts[lineparts.length - 1].split("\\(");
                    ount.setSerialNumber(sa[0].trim());
                } else if (s.trim().startsWith("Password")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setPassword(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Loid")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setLoid(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Checkcode")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setCheckcode(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("VendorID")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setVendorID(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Ont Version")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setOntVersion(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Ont SoftwareVersion")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setOntSoftwareVersion(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Ont EquipmentID")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setOntEquipmentID(lineparts[lineparts.length - 1].trim());
                } else if (s.trim().startsWith("Ont autofind time")) {
                    String[] lineparts = s.trim().split(":");
                    ount.setOntAutofindTime(lineparts[lineparts.length - 1].trim());
                    if(ount.getFsp()!=null && !ount.getFsp().isEmpty())
                        ontus.add(ount);
                    s1 = 0;
                }
            }
        }
        return ontus;
    }

    //display ont-lineprofile gpon all
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity
    public static ArrayList<LineProfile> getLineProfileList(String commandOutput) {
        ArrayList<LineProfile> lineProfiles = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        LineProfile lp = null;
        for (String s : commands) {
            if (s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            if (s.trim().startsWith("---- More")) {
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.trim();
            }
            if (s.contains("-------------------")) {
                System.out.println("dashes bypassed");
                continue;
            }
            if (s.trim().startsWith("Profile-ID")) {
                System.out.println("header bypassed");
                continue;
            }
            if (s.trim().startsWith("---- More")) {
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.trim();
            }
            if (s.trim().matches("[0-9].*")) {
                String[] fields = s.trim().split("\\s+");
                lp=new LineProfile();
                lp.setProfileID(fields[0].trim());
                lp.setProfileName(fields[1].trim());
                lp.setBindingTimes(fields[2].trim());
                lineProfiles.add(lp);
            }
        }
        return lineProfiles;
    }
    public static ArrayList<LineProfile> testGetLineProfileList(){
        String commandOutput = "FARDEASADI-OLT#display ont-lineprofile gpon all\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  Profile-ID  Profile-name                                Binding times\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  0           line-profile_default_0                      0\n" +
                "  1           HG-8240                                     781\n" +
                "  2           LineProfile-M6_01                           83\n" +
                "  3           DATA-MPLS                                   16\n" +
                "  4           MPLS-SIP-VLAN2000                           27\n" +
                "  100         Residential-In-700-800_17313010             0\n" +
                "  101         Residential-Out-700-800_17313010            1\n" +
                "  102         Commercial-700-800_17313010                 1\n" +
                "  900         ACS                                         114\n" +
                "  5603        MA5603T                                     0\n" +
                "  5612        MA-5612                                     0\n" +
                "  5616        MA5616                                      0\n" +
                "  5818        MA5818                                      1\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  Total: 13\n" +
                "\n" +
                "FARDEASADI-OLT#";
        return getLineProfileList(commandOutput);
    }
    //display ont-lineprofile gpon all
    // or 1 or 2 or ...
    //return array of slots can be saved in slot entity
    public static ArrayList<ServiceProfile> getServiceProfileList(String commandOutput) {
        ArrayList<ServiceProfile> serviceProfiles = new ArrayList<>();
        String[] commands = commandOutput.split("\n");
        ServiceProfile sp = null;
        for (String s : commands) {
            if (s.trim().contains("Failure: System is busy, please retry after a while"))
                throw new RuntimeException("OLT is busy, Try Later, received message:" + "Failure: System is busy, please retry after a while");
            if (s.trim().startsWith("---- More")) {
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.trim();
            }
            if (s.contains("-------------------")) {
                System.out.println("dashes bypassed");
                continue;
            }
            if (s.trim().startsWith("Profile-ID")) {
                System.out.println("header bypassed");
                continue;
            }
            if (s.trim().startsWith("---- More")) {
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("----") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.substring(s.indexOf("[37D") + 5);
                s = s.trim();
            }
            if (s.trim().matches("[0-9].*")) {
                String[] fields = s.trim().split("\\s+");
                sp=new ServiceProfile();
                sp.setProfileID(fields[0].trim());
                sp.setProfileName(fields[1].trim());
                sp.setBindingTimes(fields[2].trim());
                serviceProfiles.add(sp);
            }
        }
        return serviceProfiles;
    }
    public static ArrayList<ServiceProfile> testGetServiceProfileList(){
        String commandOutput = "FARDEASADI-OLT(config)#display ont-srvprofile gpon all\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  Profile-ID  Profile-name                                Binding times\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  0           srv-profile_default_0                       1\n" +
                "  1           HG-8240                                     909\n" +
                "  2           FTTH                                        1\n" +
                "  930         ACS                                         112\n" +
                "  -----------------------------------------------------------------------------\n" +
                "  Total: 4";
        return getServiceProfileList(commandOutput);
    }
}


