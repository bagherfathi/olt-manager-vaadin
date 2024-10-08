package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Olt.
 */
@Entity
@Table(name = "OltParameters")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OltParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "vlanid")
    private String vlanid="930";

    @Size(max = 10)
    @Column(name = "gemport")
    private String gemport="930";

    @NotNull
    @Size(max = 20)
    @Column(name = "userVlanID")
    private String userVlanID="930";

    @NotNull
    @Size(max = 20)
    @Column(name = "ipIndex", length = 20)
    private String ipIndex="0";

    @NotNull
    @Size(max = 5)
    @Column(name = "priority", length = 20)
    private String priority="0";

    @Size(max = 10)
    @Column(name = "tr069ProfileID", length = 100)
    private String tr069ProfileID="0";

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Olt olt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    public OltParameters(Long id, String vlanid, String gemport, String userVlanID, String ipIndex, String priority, String tr069ProfileID, Olt olt, User user) {
        this.id = id;
        this.vlanid = vlanid;
        this.gemport = gemport;
        this.userVlanID = userVlanID;
        this.ipIndex = ipIndex;
        this.priority = priority;
        this.tr069ProfileID = tr069ProfileID;
        this.olt = olt;
        this.user = user;
    }

    public OltParameters(Long id) {
        this.id = id;
    }

    public OltParameters() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OltParameters that = (OltParameters) o;
        return Objects.equals(id, that.id) && Objects.equals(vlanid, that.vlanid) && Objects.equals(gemport, that.gemport) && Objects.equals(userVlanID, that.userVlanID) && Objects.equals(ipIndex, that.ipIndex) && Objects.equals(priority, that.priority) && Objects.equals(tr069ProfileID, that.tr069ProfileID) && Objects.equals(olt, that.olt) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vlanid, gemport, userVlanID, ipIndex, priority, tr069ProfileID, olt, user);
    }

    @Override
    public String toString() {
        return "OltParameters{" +
                "id=" + id +
                ", vlanid='" + vlanid + '\'' +
                ", gemport='" + gemport + '\'' +
                ", userVlanID='" + userVlanID + '\'' +
                ", ipIndex='" + ipIndex + '\'' +
                ", priority='" + priority + '\'' +
                ", tr069ProfileID='" + tr069ProfileID + '\'' +
                ", olt=" + olt +
                ", user=" + user +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(max = 20) String getVlanid() {
        return vlanid;
    }

    public void setVlanid(@NotNull @Size(max = 20) String vlanid) {
        this.vlanid = vlanid;
    }

    public @Size(max = 10) String getGemport() {
        return gemport;
    }

    public void setGemport(@Size(max = 10) String gemport) {
        this.gemport = gemport;
    }

    public @NotNull @Size(max = 20) String getUserVlanID() {
        return userVlanID;
    }

    public void setUserVlanID(@NotNull @Size(max = 20) String userVlanID) {
        this.userVlanID = userVlanID;
    }

    public @NotNull @Size(max = 20) String getIpIndex() {
        return ipIndex;
    }

    public void setIpIndex(@NotNull @Size(max = 20) String ipIndex) {
        this.ipIndex = ipIndex;
    }

    public @NotNull @Size(max = 5) String getPriority() {
        return priority;
    }

    public void setPriority(@NotNull @Size(max = 5) String priority) {
        this.priority = priority;
    }

    public @Size(max = 10) String getTr069ProfileID() {
        return tr069ProfileID;
    }

    public void setTr069ProfileID(@Size(max = 10) String tr069ProfileID) {
        this.tr069ProfileID = tr069ProfileID;
    }

    public @NotNull Olt getOlt() {
        return olt;
    }

    public void setOlt(@NotNull Olt olt) {
        this.olt = olt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
