package com.gohardani.oltmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="port")
public class Port implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Slot slot;

    @Column(name = "fsp")
    @Size(max=20)
    private String fsp;

    @Column(name = "OpticalModuleStatus")
    @Size(max=20)
    private String opticalModuleStatus;

    @Column(name = "PortState")
    @Size(max=20)
    private String portState;

    @Column(name = "LaserState")
    @Size(max=20)
    private String laserState;

    @Column(name = "AvailableBandwidth")
    @Size(max=20)
    private String availableBandwidth;


    @Column(name = "temperature")
    @Size(max=20)
    private String temperature;

    @Column(name = "TXBiasCurrent")
    @Size(max=20)
    private String TXBiasCurrent;

    @Column(name = "supplyVoltage")
    @Size(max=20)
    private String supplyVoltage;

    @Column(name = "TXPower")
    @Size(max=20)
    private String TXPower;

    @Column(name = "illegalRogueONT")
    @Size(max=20)
    private String illegalRogueONT;

    @Column(name = "maxDistance")
    @Size(max=20)
    private String maxDistance;

    @Column(name = "waveLength")
    @Size(max=20)
    private String waveLength;

    @Column(name = "fiberType")
    @Size(max=20)
    private String fiberType;

    @Column(name = "length")
    @Size(max=20)
    private String length;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Port() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public @Size(max = 20) String getFsp() {
        return fsp;
    }

    public void setFsp(@Size(max = 20) String fsp) {
        this.fsp = fsp;
    }

    public @Size(max = 20) String getOpticalModuleStatus() {
        return opticalModuleStatus;
    }

    public void setOpticalModuleStatus(@Size(max = 20) String opticalModuleStatus) {
        this.opticalModuleStatus = opticalModuleStatus;
    }

    public @Size(max = 20) String getPortState() {
        return portState;
    }

    public void setPortState(@Size(max = 20) String portState) {
        this.portState = portState;
    }

    public @Size(max = 20) String getLaserState() {
        return laserState;
    }

    public void setLaserState(@Size(max = 20) String laserState) {
        this.laserState = laserState;
    }

    public @Size(max = 20) String getAvailableBandwidth() {
        return availableBandwidth;
    }

    public void setAvailableBandwidth(@Size(max = 20) String availableBandwidth) {
        this.availableBandwidth = availableBandwidth;
    }

    public @Size(max = 20) String getTemperature() {
        return temperature;
    }

    public void setTemperature(@Size(max = 20) String temperature) {
        this.temperature = temperature;
    }

    public @Size(max = 20) String getTXBiasCurrent() {
        return TXBiasCurrent;
    }

    public void setTXBiasCurrent(@Size(max = 20) String TXBiasCurrent) {
        this.TXBiasCurrent = TXBiasCurrent;
    }

    public @Size(max = 20) String getSupplyVoltage() {
        return supplyVoltage;
    }

    public void setSupplyVoltage(@Size(max = 20) String supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }

    public @Size(max = 20) String getTXPower() {
        return TXPower;
    }

    public void setTXPower(@Size(max = 20) String TXPower) {
        this.TXPower = TXPower;
    }



    public @Size(max = 20) String getIllegalRogueONT() {
        return illegalRogueONT;
    }

    public void setIllegalRogueONT(@Size(max = 20) String illegalRogueONT) {
        this.illegalRogueONT = illegalRogueONT;
    }

    public @Size(max = 20) String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(@Size(max = 20) String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public @Size(max = 20) String getWaveLength() {
        return waveLength;
    }

    public void setWaveLength(@Size(max = 20) String waveLength) {
        this.waveLength = waveLength;
    }

    public @Size(max = 20) String getFiberType() {
        return fiberType;
    }

    public void setFiberType(@Size(max = 20) String fiberType) {
        this.fiberType = fiberType;
    }

    public @Size(max = 20) String getLength() {
        return length;
    }

    public void setLength(@Size(max = 20) String length) {
        this.length = length;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(id, port.id) && Objects.equals(slot, port.slot) && Objects.equals(fsp, port.fsp) && Objects.equals(opticalModuleStatus, port.opticalModuleStatus) && Objects.equals(portState, port.portState) && Objects.equals(laserState, port.laserState) && Objects.equals(availableBandwidth, port.availableBandwidth) && Objects.equals(temperature, port.temperature) && Objects.equals(TXBiasCurrent, port.TXBiasCurrent) && Objects.equals(supplyVoltage, port.supplyVoltage) && Objects.equals(TXPower, port.TXPower) && Objects.equals(illegalRogueONT, port.illegalRogueONT) && Objects.equals(maxDistance, port.maxDistance) && Objects.equals(waveLength, port.waveLength) && Objects.equals(fiberType, port.fiberType) && Objects.equals(length, port.length) && Objects.equals(user, port.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slot, fsp, opticalModuleStatus, portState, laserState, availableBandwidth, temperature, TXBiasCurrent, supplyVoltage, TXPower, illegalRogueONT, maxDistance, waveLength, fiberType, length, user);
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", slot=" + slot +
                ", fsp='" + fsp + '\'' +
                ", opticalModuleStatus='" + opticalModuleStatus + '\'' +
                ", portState='" + portState + '\'' +
                ", laserState='" + laserState + '\'' +
                ", availableBandwidth='" + availableBandwidth + '\'' +
                ", temperature='" + temperature + '\'' +
                ", TXBiasCurrent='" + TXBiasCurrent + '\'' +
                ", supplyVoltage='" + supplyVoltage + '\'' +
                ", TXPower='" + TXPower + '\'' +
                ", illegalRogueONT='" + illegalRogueONT + '\'' +
                ", maxDistance='" + maxDistance + '\'' +
                ", waveLength='" + waveLength + '\'' +
                ", fiberType='" + fiberType + '\'' +
                ", length='" + length + '\'' +
                ", user=" + user +
                '}';
    }
    public String getPortNumberAsString(){
        String[] fspparts=getFsp().trim().split("/");
        return fspparts[fspparts.length-1];
    }
}
