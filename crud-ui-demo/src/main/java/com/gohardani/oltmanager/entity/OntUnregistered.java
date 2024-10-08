package com.gohardani.oltmanager.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "OntUnregistered")
public class OntUnregistered implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name="no")
    @Size(max = 100)
    private String no;

    @NotNull
    @Column(name="fsp")
    @Size(max = 100)
    private String fsp;

    @NotNull
    @Column(name="SerialNumber")
    @Size(max = 100)
    private String serialNumber;

    @Column(name="Password")
    @Size(max = 100)
    private String Password;

    @Column(name="Loid")
    @Size(max = 100)
    private String Loid;

    @Column(name="Checkcode")
    @Size(max = 100)
    private String Checkcode;

    @Column(name="VendorID")
    @Size(max = 100)
    private String VendorID;

    @Column(name="OntVersion")
    @Size(max = 100)
    private String OntVersion;

    @Column(name="OntSoftwareVersion")
    @Size(max = 100)
    private String OntSoftwareVersion;

    @Column(name="OntEquipmentID")
    @Size(max = 100)
    private String OntEquipmentID;

    @Column(name="OntAutofindTime")
    @Size(max = 100)
    private String OntAutofindTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Olt olt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public OntUnregistered(Long id, String no, String fsp, String serialNumber, String password, String loid, String checkcode, String vendorID, String ontVersion, String ontSoftwareVersion, String ontEquipmentID, String ontAutofindTime, User user) {
        this.id = id;
        this.no = no;
        this.fsp = fsp;
        this.serialNumber = serialNumber;
        Password = password;
        Loid = loid;
        Checkcode = checkcode;
        VendorID = vendorID;
        OntVersion = ontVersion;
        OntSoftwareVersion = ontSoftwareVersion;
        OntEquipmentID = ontEquipmentID;
        OntAutofindTime = ontAutofindTime;
        this.user = user;
    }
    public Olt getOlt() {
        return olt;
    }

    public void setOlt(Olt olt) {
        this.olt = olt;
    }
    public OntUnregistered() {

    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OntUnregistered{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", fsp='" + fsp + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", Password='" + Password + '\'' +
                ", Loid='" + Loid + '\'' +
                ", Checkcode='" + Checkcode + '\'' +
                ", VendorID='" + VendorID + '\'' +
                ", OntVersion='" + OntVersion + '\'' +
                ", OntSoftwareVersion='" + OntSoftwareVersion + '\'' +
                ", OntEquipmentID='" + OntEquipmentID + '\'' +
                ", OntAutofindTime='" + OntAutofindTime + '\'' +
                ", olt=" + olt +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OntUnregistered that = (OntUnregistered) o;
        return Objects.equals(id, that.id) && Objects.equals(no, that.no) && Objects.equals(fsp, that.fsp) && Objects.equals(serialNumber, that.serialNumber) && Objects.equals(Password, that.Password) && Objects.equals(Loid, that.Loid) && Objects.equals(Checkcode, that.Checkcode) && Objects.equals(VendorID, that.VendorID) && Objects.equals(OntVersion, that.OntVersion) && Objects.equals(OntSoftwareVersion, that.OntSoftwareVersion) && Objects.equals(OntEquipmentID, that.OntEquipmentID) && Objects.equals(OntAutofindTime, that.OntAutofindTime) && Objects.equals(olt, that.olt) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, no, fsp, serialNumber, Password, Loid, Checkcode, VendorID, OntVersion, OntSoftwareVersion, OntEquipmentID, OntAutofindTime, olt, user);
    }

    public OntUnregistered(String ontSoftwareVersion, Long id, String no, String fsp, String serialNumber, String password, String loid, String checkcode, String vendorID, String ontVersion, String ontEquipmentID, String ontAutofindTime, Olt olt, User user) {
        OntSoftwareVersion = ontSoftwareVersion;
        this.id = id;
        this.no = no;
        this.fsp = fsp;
        this.serialNumber = serialNumber;
        Password = password;
        Loid = loid;
        Checkcode = checkcode;
        VendorID = vendorID;
        OntVersion = ontVersion;
        OntEquipmentID = ontEquipmentID;
        OntAutofindTime = ontAutofindTime;
        this.olt = olt;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 100) String getNo() {
        return no;
    }

    public void setNo(@Size(max = 100) String no) {
        this.no = no;
    }

    public @NotNull @Size(max = 100) String getFsp() {
        return fsp;
    }

    public void setFsp(@NotNull @Size(max = 100) String fsp) {
        this.fsp = fsp;
    }

    public @NotNull @Size(max = 100) String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(@NotNull @Size(max = 100) String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public @Size(max = 100) String getPassword() {
        return Password;
    }

    public void setPassword(@Size(max = 100) String password) {
        Password = password;
    }

    public @Size(max = 100) String getLoid() {
        return Loid;
    }

    public void setLoid(@Size(max = 100) String loid) {
        Loid = loid;
    }

    public @Size(max = 100) String getCheckcode() {
        return Checkcode;
    }

    public void setCheckcode(@Size(max = 100) String checkcode) {
        Checkcode = checkcode;
    }

    public @Size(max = 100) String getVendorID() {
        return VendorID;
    }

    public void setVendorID(@Size(max = 100) String vendorID) {
        VendorID = vendorID;
    }

    public @Size(max = 100) String getOntVersion() {
        return OntVersion;
    }

    public void setOntVersion(@Size(max = 100) String ontVersion) {
        OntVersion = ontVersion;
    }

    public @Size(max = 100) String getOntSoftwareVersion() {
        return OntSoftwareVersion;
    }

    public void setOntSoftwareVersion(@Size(max = 100) String ontSoftwareVersion) {
        OntSoftwareVersion = ontSoftwareVersion;
    }

    public @Size(max = 100) String getOntEquipmentID() {
        return OntEquipmentID;
    }

    public void setOntEquipmentID(@Size(max = 100) String ontEquipmentID) {
        OntEquipmentID = ontEquipmentID;
    }

    public @Size(max = 100) String getOntAutofindTime() {
        return OntAutofindTime;
    }

    public void setOntAutofindTime(@Size(max = 100) String ontAutofindTime) {
        OntAutofindTime = ontAutofindTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

