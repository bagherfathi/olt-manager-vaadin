package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OltType.
 */
@Entity
@Table(name = "olt_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OltType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 4, max = 150)
    @Column(name = "name", length = 150)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "company", length = 100, nullable = false)
    private String company;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "model", length = 200)
    private String model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oltType")
    @JsonIgnoreProperties(value = { "oltType", "user", "commandHistories" }, allowSetters = true)
    private Set<Olt> olts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oltType")
    @JsonIgnoreProperties(value = { "user", "oltType", "commandHistories" }, allowSetters = true)
    private Set<SshCommand> sshCommands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oltType")
    @JsonIgnoreProperties(value = { "sshCommand", "olt", "oltType" }, allowSetters = true)
    private Set<CommandHistory> commandHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OltType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public OltType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return this.company;
    }

    public OltType company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return this.model;
    }

    public OltType model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<Olt> getOlts() {
        return this.olts;
    }

    public void setOlts(Set<Olt> olts) {
        if (this.olts != null) {
            this.olts.forEach(i -> i.setOltType(null));
        }
        if (olts != null) {
            olts.forEach(i -> i.setOltType(this));
        }
        this.olts = olts;
    }

    public OltType olts(Set<Olt> olts) {
        this.setOlts(olts);
        return this;
    }

    public OltType addOlt(Olt olt) {
        this.olts.add(olt);
        olt.setOltType(this);
        return this;
    }

    public OltType removeOlt(Olt olt) {
        this.olts.remove(olt);
        olt.setOltType(null);
        return this;
    }

    public Set<SshCommand> getSshCommands() {
        return this.sshCommands;
    }

    public void setSshCommands(Set<SshCommand> sshCommands) {
        if (this.sshCommands != null) {
            this.sshCommands.forEach(i -> i.setOltType(null));
        }
        if (sshCommands != null) {
            sshCommands.forEach(i -> i.setOltType(this));
        }
        this.sshCommands = sshCommands;
    }

    public OltType sshCommands(Set<SshCommand> sshCommands) {
        this.setSshCommands(sshCommands);
        return this;
    }

    public OltType addSshCommand(SshCommand sshCommand) {
        this.sshCommands.add(sshCommand);
        sshCommand.setOltType(this);
        return this;
    }

    public OltType removeSshCommand(SshCommand sshCommand) {
        this.sshCommands.remove(sshCommand);
        sshCommand.setOltType(null);
        return this;
    }

    public Set<CommandHistory> getCommandHistories() {
        return this.commandHistories;
    }

    public void setCommandHistories(Set<CommandHistory> commandHistories) {
        if (this.commandHistories != null) {
            this.commandHistories.forEach(i -> i.setOltType(null));
        }
        if (commandHistories != null) {
            commandHistories.forEach(i -> i.setOltType(this));
        }
        this.commandHistories = commandHistories;
    }

    public OltType commandHistories(Set<CommandHistory> commandHistories) {
        this.setCommandHistories(commandHistories);
        return this;
    }

    public OltType addCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.add(commandHistory);
        commandHistory.setOltType(this);
        return this;
    }

    public OltType removeCommandHistory(CommandHistory commandHistory) {
        this.commandHistories.remove(commandHistory);
        commandHistory.setOltType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OltType)) {
            return false;
        }
        return getId() != null && getId().equals(((OltType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
//    @Override
//    public String toString() {
//        return "OltType{" +
//            "id=" + getId() +
//            ", name='" + getName() + "'" +
//            ", company='" + getCompany() + "'" +
//            ", model='" + getModel() + "'" +
//            "}";
//    }
    @Override
    public String toString() {
        return getName();
    }
}
