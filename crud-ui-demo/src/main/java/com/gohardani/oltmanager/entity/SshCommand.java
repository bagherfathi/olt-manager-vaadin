package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SshCommand.
 */
@Entity
@Table(name = "ssh_command")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SshCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "name", length = 200)
    private String name;

    @NotNull
    @Size(min = 3, max = 5000)
    @Column(name = "fix_part", length = 5000)
    private String fixPart;

    @Size(max = 5000)
    @Column(name = "var_part", length = 5000)
    private String varPart;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "olts", "sshCommands", "commandHistories" }, allowSetters = true)
    private OltType oltType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sshCommand")
    @JsonIgnoreProperties(value = { "sshCommand", "olt", "oltType" }, allowSetters = true)
    private Set<CommandHistory> commandHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SshCommand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SshCommand name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFixPart() {
        return this.fixPart;
    }

    public SshCommand fixPart(String fixPart) {
        this.setFixPart(fixPart);
        return this;
    }

    public void setFixPart(String fixPart) {
        this.fixPart = fixPart;
    }

    public String getVarPart() {
        return this.varPart;
    }

    public SshCommand varPart(String varPart) {
        this.setVarPart(varPart);
        return this;
    }

    public void setVarPart(String varPart) {
        this.varPart = varPart;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SshCommand user(User user) {
        this.setUser(user);
        return this;
    }

    public OltType getOltType() {
        return this.oltType;
    }

    public void setOltType(OltType oltType) {
        this.oltType = oltType;
    }

    public SshCommand oltType(OltType oltType) {
        this.setOltType(oltType);
        return this;
    }

    public Set<CommandHistory> getCommandHistories() {
        return this.commandHistories;
    }

    public void setCommandHistories(Set<CommandHistory> commandHistories) {
        if (this.commandHistories != null) {
            this.commandHistories.forEach(i -> i.setSshCommand(null));
        }
        if (commandHistories != null) {
            commandHistories.forEach(i -> i.setSshCommand(this));
        }
        this.commandHistories = commandHistories;
    }

    public SshCommand commandHistories(Set<CommandHistory> commandHistories) {
        this.setCommandHistories(commandHistories);
        return this;
    }

    public SshCommand addCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.add(commandHistory);
        commandHistory.setSshCommand(this);
        return this;
    }

    public SshCommand removeCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.remove(commandHistory);
        commandHistory.setSshCommand(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SshCommand)) {
            return false;
        }
        return getId() != null && getId().equals(((SshCommand) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return getName();
    }
}
