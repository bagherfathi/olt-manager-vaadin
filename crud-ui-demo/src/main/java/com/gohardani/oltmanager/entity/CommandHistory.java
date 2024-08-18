package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CommandHistory.
 */
@Entity
@Table(name = "command_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "excecution_time", nullable = false)
    private ZonedDateTime excecutionTime;

    @Size(min = 3, max = 5000)
    @Column(name = "command_text", length = 5000)
    private String commandText;

    @Size(max = 5000)
    @Column(name = "result", length = 5000)
    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "user", "oltType", "commandHistories" }, allowSetters = true)
    private SshCommand sshCommand;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "oltType", "user", "commandHistories" }, allowSetters = true)
    private Olt olt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "olts", "sshCommands", "commandHistories" }, allowSetters = true)
    private OltType oltType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommandHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getExcectionTime() {
        return this.excecutionTime;
    }

    public CommandHistory excectionTime(ZonedDateTime excectionTime) {
        this.setExcectionTime(excectionTime);
        return this;
    }

    public void setExcectionTime(ZonedDateTime excecutionTime) {
        this.excecutionTime = excecutionTime;
    }

    public String getCommandText() {
        return this.commandText;
    }

    public CommandHistory commandText(String commandText) {
        this.setCommandText(commandText);
        return this;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getResult() {
        return this.result;
    }

    public CommandHistory result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SshCommand getSshCommand() {
        return this.sshCommand;
    }

    public void setSshCommand(SshCommand sshCommand) {
        this.sshCommand = sshCommand;
    }

    public CommandHistory sshCommand(SshCommand sshCommand) {
        this.setSshCommand(sshCommand);
        return this;
    }

    public Olt getOlt() {
        return this.olt;
    }

    public void setOlt(Olt olt) {
        this.olt = olt;
        this.setOltType(olt.getOltType());
    }

    public CommandHistory olt(Olt olt) {
        this.setOlt(olt);
        this.setOltType(olt.getOltType());
        return this;
    }

    public OltType getOltType() {
        return this.oltType;
    }

    public void setOltType(OltType oltType) {
        this.oltType = oltType;
    }

    public CommandHistory oltType(OltType oltType) {
        this.setOltType(oltType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((CommandHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandHistory{" +
            "id=" + getId() +
            ", excecutionTime='" + getExcectionTime() + "'" +
            ", commandText='" + getCommandText() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }
}
