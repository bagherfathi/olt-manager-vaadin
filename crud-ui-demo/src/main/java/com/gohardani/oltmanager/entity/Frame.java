package com.gohardani.oltmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Frame.
 */
@Entity
@Table(name = "frame")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Frame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    public void setFrameNumber(@NotNull int frameNumber) {
        this.frameNumber = frameNumber;
    }

    @NotNull
    @Column(name = "frameNumber")
    private int frameNumber;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    public void setOlt(@NotNull Olt olt) {
        this.olt = olt;
    }

    public void setDescription(@Size(max = 500) String description) {
        this.description = description;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Olt olt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;



    @NotNull
    public int getFrameNumber() {
        return frameNumber;
    }
    @NotNull
    public String getFrameNumberAsText() {
        return String.valueOf(frameNumber);
    }

    public  @Size(max = 500) String getDescription() {
        return description;
    }

    public @NotNull Olt getOlt() {
        return olt;
    }

    public Long getId() {
        return this.id;
    }

    public Frame id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }




    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Frame)) {
            return false;
        }
        return getId() != null && getId().equals(((Frame) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return getOlt() + ":" + getFrameNumber();
    }

}
