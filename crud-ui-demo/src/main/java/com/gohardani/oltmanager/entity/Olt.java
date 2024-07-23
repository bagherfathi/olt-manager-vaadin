package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Olt.
 */
@Entity
@Table(name = "olt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Olt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @NotNull
    @Size(min = 7, max = 15)
    @Column(name = "ip", length = 15)
    private String ip;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "username", length = 20)
    private String username;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "password", length = 20)
    private String password;

    @Size(max = 100)
    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "olts", "sshCommands", "commandHistories" }, allowSetters = true)
    private OltType oltType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "olt")
    @JsonIgnoreProperties(value = { "sshCommand", "olt", "oltType" }, allowSetters = true)
    private Set<CommandHistory> commandHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Olt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Olt name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return this.ip;
    }

    public Olt ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return this.port;
    }

    public Olt port(Integer port) {
        this.setPort(port);
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public Olt username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Olt password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Olt serialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public OltType getOltType() {
        return this.oltType;
    }

    public void setOltType(OltType oltType) {
        this.oltType = oltType;
    }

    public Olt oltType(OltType oltType) {
        this.setOltType(oltType);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Olt user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<CommandHistory> getCommandHistories() {
        return this.commandHistories;
    }

    public void setCommandHistories(Set<CommandHistory> commandHistories) {
        if (this.commandHistories != null) {
            this.commandHistories.forEach(i -> i.setOlt(null));
        }
        if (commandHistories != null) {
            commandHistories.forEach(i -> i.setOlt(this));
        }
        this.commandHistories = commandHistories;
    }

    public Olt commandHistories(Set<CommandHistory> commandHistories) {
        this.setCommandHistories(commandHistories);
        return this;
    }

    public Olt addCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.add(commandHistory);
        commandHistory.setOlt(this);
        return this;
    }

    public Olt removeCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.remove(commandHistory);
        commandHistory.setOlt(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Olt)) {
            return false;
        }
        return getId() != null && getId().equals(((Olt) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return getName() + ":" + getIp();
    }
}
