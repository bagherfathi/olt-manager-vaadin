package com.gohardani.oltmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "serviceprofile")
public class ServiceProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "profileID")
    @Size(max=20)
    private String profileID;

    @Column(name = "profileName")
    @Size(max = 50)
    private String profileName;

    @Column(name = "bindingTimes")
    @Size(max=50)
    private String bindingTimes;

    @ManyToOne(fetch = FetchType.EAGER)
    private Olt olt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Override
    public String toString() {
        return "ServiceProfile{" +
                "id=" + id +
                ", profileID='" + profileID + '\'' +
                ", profileName='" + profileName + '\'' +
                ", bindingTimes='" + bindingTimes + '\'' +
                ", olt=" + olt +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceProfile that = (ServiceProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(profileID, that.profileID) && Objects.equals(profileName, that.profileName) && Objects.equals(bindingTimes, that.bindingTimes) && Objects.equals(olt, that.olt) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profileID, profileName, bindingTimes, olt, user);
    }

    public ServiceProfile(Long id, String profileID, String profileName, String bindingTimes, Olt olt, User user) {
        this.id = id;
        this.profileID = profileID;
        this.profileName = profileName;
        this.bindingTimes = bindingTimes;
        this.olt = olt;
        this.user = user;
    }

    public Olt getOlt() {
        return olt;
    }

    public void setOlt(Olt olt) {
        this.olt = olt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 20) String getProfileID() {
        return profileID;
    }

    public void setProfileID(@Size(max = 20) String profileID) {
        this.profileID = profileID;
    }

    public @Size(max = 50) String getProfileName() {
        return profileName;
    }

    public void setProfileName(@Size(max = 50) String profileName) {
        this.profileName = profileName;
    }

    public @Size(max = 50) String getBindingTimes() {
        return bindingTimes;
    }

    public void setBindingTimes(@Size(max = 50) String bindingTimes) {
        this.bindingTimes = bindingTimes;
    }

}
