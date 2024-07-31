package com.gohardani.oltmanager.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Ont")
public class Ont implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Port port;

    @NotNull
    @Column(name="frame")
    private int frameNo;

    @NotNull
    @Column(name="slotNo")
    private int slotNo;

    @NotNull
    @Column(name="port")
    private int portNo;

    @NotNull
    @Column(name="ontID")
    private int ontID;

    @NotNull
    @Column(name="serialNumber")
    @Size(max = 100)
    private String serialNumber;

    @Column(name="controlFlag")
    @Size(max = 100)
    private String controlFlag;

    @Column(name="runState")
    @Size(max = 100)
    private String runState;

    @Column(name="configState")
    @Size(max = 100)
    private String configState;

    @Column(name="matchState")
    @Size(max = 100)
    private String matchState;

    @Column(name="protectSide")
    @Size(max = 100)
    private String protectSide;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @NotNull
    public int getOntID() {
        return ontID;
    }

    public void setOntID(@NotNull int ontID) {
        this.ontID = ontID;
    }

    public @Size(max = 100) String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(@Size(max = 100) String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public @Size(max = 100) String getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(@Size(max = 100) String controlFlag) {
        this.controlFlag = controlFlag;
    }

    public @Size(max = 100) String getRunState() {
        return runState;
    }

    public void setRunState(@Size(max = 100) String runState) {
        this.runState = runState;
    }

    public @Size(max = 100) String getConfigState() {
        return configState;
    }

    public void setConfigState(@Size(max = 100) String configState) {
        this.configState = configState;
    }

    public @Size(max = 100) String getMatchState() {
        return matchState;
    }

    public void setMatchState(@Size(max = 100) String matchState) {
        this.matchState = matchState;
    }

    public @Size(max = 100) String getProtectSide() {
        return protectSide;
    }

    public void setProtectSide(@Size(max = 100) String protectSide) {
        this.protectSide = protectSide;
    }

    public Ont() {}

    @NotNull
    public int getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(@NotNull int frameNo) {
        this.frameNo = frameNo;
    }

    @NotNull
    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(@NotNull int slotNo) {
        this.slotNo = slotNo;
    }

    @NotNull
    public int getPortNo() {
        return portNo;
    }

    public void setPortNo(@NotNull int portNo) {
        this.portNo = portNo;
    }

    public @NotNull Port getPort() {
        return port;
    }

    public void setPort(@NotNull Port port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ont ont = (Ont) o;
        return frameNo == ont.frameNo && slotNo == ont.slotNo && portNo == ont.portNo && ontID == ont.ontID && Objects.equals(id, ont.id) && Objects.equals(port, ont.port) && Objects.equals(serialNumber, ont.serialNumber) && Objects.equals(controlFlag, ont.controlFlag) && Objects.equals(runState, ont.runState) && Objects.equals(configState, ont.configState) && Objects.equals(matchState, ont.matchState) && Objects.equals(protectSide, ont.protectSide) && Objects.equals(user, ont.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, port, frameNo, slotNo, portNo, ontID, serialNumber, controlFlag, runState, configState, matchState, protectSide, user);
    }

    @Override
    public String toString() {
        return "Ont{" +
                "id=" + id +
                ", port=" + port +
                ", frameNo=" + frameNo +
                ", slotNo=" + slotNo +
                ", portNo=" + portNo +
                ", ontID=" + ontID +
                ", serialNumber='" + serialNumber + '\'' +
                ", controlFlag='" + controlFlag + '\'' +
                ", runState='" + runState + '\'' +
                ", configState='" + configState + '\'' +
                ", matchState='" + matchState + '\'' +
                ", protectSide='" + protectSide + '\'' +
                ", user=" + user +
                '}';
    }
}

