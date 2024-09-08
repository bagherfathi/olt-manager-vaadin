package com.gohardani.oltmanager;

import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
public class Application  {

    public static final int DEMO_USERS_COUNT = UserService.USERS_COUNT_LIMIT / 2;

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Application started");
        SpringApplication.run(Application.class, args);
        log.info("Application started");
//        try {
//            System.out.println(telnetConnection("help","admin@localhost","admin","192.168.154.129"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> initDatabase(OntService ontService, PortService portService, SlotService slotService, FrameService frameService, GroupService groupService, UserService userService,
                                                                   CategoryService categoryService, OltTypeService oltTypeService, OltService oltService, SshService sshService, LineProfileService lineProfileService, ServiceProfileService serviceProfileService) {
        return event -> {
            if (oltTypeService.count() == 0) {
                createDemoData(ontService, portService, slotService, frameService, groupService, userService, categoryService, oltTypeService, oltService, sshService, lineProfileService, serviceProfileService);
            }
        };
    }

    private void createDemoData(OntService ontService, PortService portService, SlotService slotService, FrameService frameService, GroupService groupService, UserService userService, CategoryService categoryService, OltTypeService oltTypeService, OltService oltService, SshService sshService
            , LineProfileService lineProfileService, ServiceProfileService serviceProfileService) {
        log.info("Creating demo data...");

        Stream.of("Services,IT,HR,Management,Marketing,Sales,Operations,Finance".split(","))
                .map(Group::new)
                .forEach(groupService::save);

        List<Group> allGroups = groupService.findAll();

        groupService.findAll();

        String[] firstNames = "Maria,Nicole,Sandra,Brenda,Clare,Cathy,Elizabeth,Tom,John,Daniel,Edward,Hank,Arthur,Bill"
                .split(",");
        String[] lastNames = "Smith,Johnson,Williams,Jones,Brown,Miller,Wilson,Wright,Thompson,Lee".split(",");

        Random rand = new Random();

        IntStream.rangeClosed(1, DEMO_USERS_COUNT)
                .mapToObj(i -> {
                    String name = firstNames[rand.nextInt(firstNames.length)] + " "
                            + lastNames[rand.nextInt(lastNames.length)];
                    ArrayList<Group> groups = IntStream.rangeClosed(1, 1 + rand.nextInt(2))
                            .mapToObj(j -> allGroups.get(rand.nextInt(allGroups.size())))
                            .collect(Collectors.toCollection(ArrayList::new));

                    return new User(
                            name,
                            LocalDate.now().minusDays(365 * 10),
                            rand.nextInt(9000000) + 1000000,
                            name.replace(" ", "").toLowerCase() + i + "@test.com",
                            BigDecimal.valueOf(5000),
                            UUID.randomUUID().toString(),
                            rand.nextInt(10) > 0,
                            groups.get(rand.nextInt(groups.size())),
                            new HashSet<>(groups),
                            MaritalStatus.values()[rand.nextInt(MaritalStatus.values().length)]);
                })
                .forEach(userService::save);

        String[] languages = new String[]{"Java", "Javascript", "Dart"};
        String[][] frameworks = new String[][]{
                {"Vaadin", "Spring", "Guice"},
                {"Hilla", "React", "Svelte"},
                {"Flutter"}
        };

        for (int i = 0; i < languages.length; i++) {
            Category language = categoryService.save(new Category(languages[i], languages[i], null));
            for (int j = 0; j < frameworks[i].length; j++) {
                categoryService.save(new Category(frameworks[i][j], frameworks[i][j], language));
            }
        }
        //olttype test data
        ArrayList<OltType> oltTypes = fillOltType();
        for(OltType oltType:oltTypes)
            oltTypeService.save(oltType);
        ArrayList<Olt> olts = fillOlt(oltService, oltTypes);
        ArrayList<SshCommand> sshCommands=fillSshCommand(oltTypes,sshService);
        ArrayList<Frame> frames = fillFrame(frameService, oltService, olts);
        ArrayList<Slot> slots = fillSlot(frames, slotService);
        ArrayList<Port> ports = fillPort(slots, portService);
        ArrayList<Ont> onts = fillOnt(ports, ontService);
        fillLineProfile(lineProfileService);
        fillServiceProfile(serviceProfileService);
        SshCommand sshCommand = null;
        log.info("OLT types created");
        log.info("OLTs created");
        //sshcommand test data

        log.info("SshCommands created");

        log.info("Demo data created.");
    }

    private ArrayList<OltType> fillOltType() {
        ArrayList<OltType> oltTypes=new ArrayList<>();
        OltType oltType = new OltType();
        oltType.setName("huawei");
        oltType.setCompany("huawei");
        oltType.setModel("ax2000");
        oltTypes.add(oltType);
        oltType = new OltType();
        oltType.setName("Eltex");
        oltType.setCompany("Eltex");
        oltType.setModel("el2000");
        oltTypes.add(oltType);
        return oltTypes;
    }

    private ArrayList<Olt> fillOlt(OltService oltService, ArrayList<OltType> oltTypes) {
        //olt test data
        ArrayList<Olt> olts = new ArrayList<>();
        Olt olt = new Olt();
        olt.setName("farda asadi 1");
        olt.setIp("1.1.1.1");
        olt.setPort(22);
        olt.setSerialNumber("111111111111111111111111111111");
        olt.setUsername("bagher");
        olt.setPassword("bagher");
        olt.setOltType(oltTypes.get(0));
        oltService.save(olt);
        olts.add(olt);
        olt = new Olt();
        olt.setName("farda asadi 2");
        olt.setIp("1.1.1.2");
        olt.setPort(22);
        olt.setSerialNumber("22222222222222222222222222222222");
        olt.setUsername("bagher");
        olt.setPassword("bagher");
        olt.setOltType(oltTypes.get(0));
        oltService.save(olt);
        olts.add(olt);
        olt = new Olt();
        olt.setName("simulator test");
        olt.setIp("127.0.0.1");
        olt.setPort(6001);
        olt.setSerialNumber("3333333333333333333333333333333333");
        olt.setUsername("admin");
        olt.setPassword("admin");
        olt.setOltType(oltTypes.get(0));
        oltService.save(olt);
        olts.add(olt);
        return olts;
    }

    private ArrayList<Frame> fillFrame(FrameService frameService, OltService oltService, ArrayList<Olt> olts) {
        ArrayList<Frame> frames = new ArrayList<>();
        Frame frame = new Frame();
        frame.setFrameNumber(5);
        frame.setOlt(olts.get(0));
        frameService.save(frame);
        frames.add(frame);
        frame = new Frame();
        frame.setFrameNumber(2);
        frame.setOlt(olts.get(1));
        frameService.save(frame);
        frames.add(frame);
        frame = new Frame();
        frame.setFrameNumber(3);
        frame.setOlt(olts.get(2));
        frameService.save(frame);
        frames.add(frame);
        return frames;
    }

    private ArrayList<Slot> fillSlot(ArrayList<Frame> frames, SlotService slotService) {
        ArrayList<Slot> slots = new ArrayList<>();
        Slot slot = new Slot();
        slot.setSlotid(8);
        slot.setFrame(frames.get(2));
        slot.setBoardName("noName");

        slots.add(slot);
        slotService.save(slot);
        slot = new Slot();
        slot.setSlotid(0);
        slot.setBoardName("H805GPFD");
        slot.setStatus("normal");
        slot.setFrame(frames.get(0));
        slots.add(slot);
        slotService.save(slot);
        slot = new Slot();
        slot.setSlotid(1);
        slot.setBoardName("H805GPFD");
        slot.setStatus("normal");
        slot.setFrame(frames.get(1));
        slots.add(slot);
        slotService.save(slot);
        return slots;
    }

    private ArrayList<Port> fillPort(ArrayList<Slot> slots, PortService portService) {
        ArrayList<Port> ports = new ArrayList<>();
        Port port = new Port();
        port.setFsp("0/2/0");
        port.setOpticalModuleStatus("online");
        port.setLaserState("normal");
        port.setSlot(slots.get(0));
        ports.add(port);
        portService.save(port);
        port = new Port();
        port.setFsp("0/2/1");
        port.setOpticalModuleStatus("online");
        port.setLaserState("normal");
        port.setSlot(slots.get(1));
        ports.add(port);
        portService.save(port);
        return ports;
    }

    private ArrayList<Ont> fillOnt(ArrayList<Port> ports, OntService ontService) {
        ArrayList<Ont> onts = new ArrayList<>();
        Ont ont = new Ont();
        ont.setOntID(0);
        ont.setRunState("online");
        ont.setConfigState("normal");
        ont.setProtectSide("no");
        ont.setControlFlag("active");
        ont.setPortNo(0);
        ont.setSlotNo(0);
        ont.setFrameNo(0);
        ont.setSerialNumber("0000000000000000000000000000");
        ont.setPort(ports.get(0));
        onts.add(ont);
        ontService.save(ont);
        ont = new Ont();
        ont.setOntID(1);
        ont.setRunState("online");
        ont.setConfigState("normal");
        ont.setProtectSide("no");
        ont.setControlFlag("active");
        ont.setPortNo(1);
        ont.setSlotNo(1);
        ont.setFrameNo(1);
        ont.setSerialNumber("11111111111111111111111111111111");
        ont.setPort(ports.get(1));
        onts.add(ont);
        ontService.save(ont);
        return onts;
    }
    private ArrayList<SshCommand> fillSshCommand(ArrayList<OltType> oltTypes,SshService sshService) {
        ArrayList<SshCommand> sshCommands = new ArrayList<>();
        //ont add
        SshCommand sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(0));
        sshCommand.setName("ont add");
        sshCommand.setFixPart("ont add");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(1));
        sshCommand.setName("ont add");
        sshCommand.setFixPart("ont add");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        //service-port
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(0));
        sshCommand.setName("vlan");
        sshCommand.setFixPart("service-port");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(1));
        sshCommand.setName("vlan");
        sshCommand.setFixPart("service-port");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        //ipconfig
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(0));
        sshCommand.setName("ipconfig");
        sshCommand.setFixPart("ont ipconfig");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(1));
        sshCommand.setName("ipconfig");
        sshCommand.setFixPart("ont ipconfig");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        //tr069
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(0));
        sshCommand.setName("tr069");
        sshCommand.setFixPart("ont tr069-server-config");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        sshCommand = new SshCommand();
        sshCommand.setOltType(oltTypes.get(1));
        sshCommand.setName("tr069");
        sshCommand.setFixPart("ont tr069-server-config");
        sshCommands.add(sshCommand);
        sshService.save(sshCommand);
        return sshCommands;
    }
    private void fillLineProfile(LineProfileService lineProfileService) {
        LineProfile lineProfile = new LineProfile();
        lineProfile.setProfileID("0");
        lineProfile.setProfileName("line-profile_default_0");
        lineProfile.setBindingTimes("0");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("1");
        lineProfile.setProfileName("HG-8240");
        lineProfile.setBindingTimes("781");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("2");
        lineProfile.setProfileName("LineProfile-M6_01");
        lineProfile.setBindingTimes("83");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("3");
        lineProfile.setProfileName("DATA-MPLS");
        lineProfile.setBindingTimes("16");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("4");
        lineProfile.setProfileName("MPLS-SIP-VLAN2000");
        lineProfile.setBindingTimes("27");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("100");
        lineProfile.setProfileName("Residential-In-700-800_17313010");
        lineProfile.setBindingTimes("0");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("101");
        lineProfile.setProfileName("Residential-Out-700-800_17313010");
        lineProfile.setBindingTimes("1");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("102");
        lineProfile.setProfileName("Commercial-700-800_17313010");
        lineProfile.setBindingTimes("1");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("900");
        lineProfile.setProfileName("ACS");
        lineProfile.setBindingTimes("114");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("5603");
        lineProfile.setProfileName("MA5603T");
        lineProfile.setBindingTimes("0");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("5612");
        lineProfile.setProfileName("MA-5612");
        lineProfile.setBindingTimes("0");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("5616");
        lineProfile.setProfileName("MA-5616");
        lineProfile.setBindingTimes("0");
        lineProfileService.save(lineProfile);
        lineProfile = new LineProfile();
        lineProfile.setProfileID("5618");
        lineProfile.setProfileName("MA-5618");
        lineProfile.setBindingTimes("1");
        lineProfileService.save(lineProfile);
    }
    private void fillServiceProfile(ServiceProfileService serviceProfileService) {
        ServiceProfile serviceProfile = new ServiceProfile();
        serviceProfile.setProfileID("0");
        serviceProfile.setProfileName("srv-profile_default_0");
        serviceProfile.setBindingTimes("1");
        serviceProfileService.save(serviceProfile);
        serviceProfile = new ServiceProfile();
        serviceProfile.setProfileID("1");
        serviceProfile.setProfileName("HG-8240");
        serviceProfile.setBindingTimes("909");
        serviceProfileService.save(serviceProfile);
        serviceProfile = new ServiceProfile();
        serviceProfile.setProfileID("2");
        serviceProfile.setProfileName("FTTH");
        serviceProfile.setBindingTimes("1");
        serviceProfileService.save(serviceProfile);
        serviceProfile = new ServiceProfile();
        serviceProfile.setProfileID("930");
        serviceProfile.setProfileName("ACS");
        serviceProfile.setBindingTimes("112");
        serviceProfileService.save(serviceProfile);

    }
}
