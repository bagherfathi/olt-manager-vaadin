package com.gohardani.oltmanager;

import com.gohardani.oltmanager.SSH.JavaTelnetsimulator;
import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.gohardani.oltmanager.SSH.JavaTelnetsimulator.telnetConnection;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
public class Application {

    public static final int DEMO_USERS_COUNT = UserService.USERS_COUNT_LIMIT / 2;

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            System.out.println(telnetConnection("help","admin@localhost","admin","192.168.154.129"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> initDatabase(GroupService groupService, UserService userService,
                                                                   CategoryService categoryService, OltTypeService oltTypeService, OltService oltService,SshService sshService) {
        return event -> {
            if (oltTypeService.count() == 0) {
                createDemoData(groupService, userService, categoryService,oltTypeService,oltService,sshService);
            }
        };
    }

    private void createDemoData(GroupService groupService, UserService userService, CategoryService categoryService,OltTypeService oltTypeService, OltService oltService, SshService sshService
    ) {
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

        String[] languages = new String[] { "Java", "Javascript", "Dart" };
        String[][] frameworks = new String[][] {
                { "Vaadin", "Spring", "Guice" },
                { "Hilla", "React", "Svelte" },
                { "Flutter" }
        };

        for (int i = 0; i < languages.length; i++) {
            Category language = categoryService.save(new Category(languages[i], languages[i], null));
            for (int j = 0; j < frameworks[i].length; j++) {
                categoryService.save(new Category(frameworks[i][j], frameworks[i][j], language));
            }
        }
        //olttype test data
        OltType oltType=null;
        Olt olt=null;
        SshCommand sshCommand=null;
        for(int i=1;i<10;i++){
            oltType = new OltType();
            oltType.setName("name" + i);
            oltType.setCompany("company" + i);
            oltType.setModel("model" + i);
            oltTypeService.save(oltType);
        }
        log.info("OLT types created");
        //olt test data
        for(int i=1;i<10;i++){
            olt = new Olt();
            olt.setName("name" + i);
            olt.setIp("192.168.1.112");
            olt.setPort(22);
            olt.setSerialNumber("111111111111111111" + i);
            olt.setUsername("bagher");
            olt.setPassword("tbontb");
            olt.setOltType(oltType);
            oltService.save(olt);
        }
        log.info("OLTs created");
        //sshcommand test data
        for(int i=1;i<10;i++){
            sshCommand= new SshCommand();
            sshCommand.setName("name" + i);
            sshCommand.setOltType(oltType);
            sshCommand.setFixPart("ls ");
            sshCommand.setVarPart(" -l");
            sshService.save(sshCommand);
        }
        log.info("SshCommands created");

        log.info("Demo data created.");
    }

}
