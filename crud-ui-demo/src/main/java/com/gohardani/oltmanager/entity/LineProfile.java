package com.gohardani.oltmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "lineprofile")
public class LineProfile implements Serializable {
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

    public LineProfile() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineProfile that = (LineProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(profileID, that.profileID) && Objects.equals(profileName, that.profileName) && Objects.equals(bindingTimes, that.bindingTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profileID, profileName, bindingTimes);
    }

    @Override
    public String toString() {
        return "LineProfile{" +
                "id=" + id +
                ", profileID='" + profileID + '\'' +
                ", profileName='" + profileName + '\'' +
                ", bindingTimes='" + bindingTimes + '\'' +
                '}';
    }
}
