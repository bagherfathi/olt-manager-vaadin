package com.gohardani.oltmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Frame.
 */
@Entity
@Table(name = "slot")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Slot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "slotid")
    private int slotid;

    @Size(max = 500)
    @Column(name = "BoardName")
    private String boardName;

    @Column(name = "status")
    private String status;

    @Column(name = "subType0")
    private String subType0;

    @Column(name = "subType1")
    private String subType1;

    @Column(name = "onOff")
    private String onOff;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Frame frame;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;



    public Slot() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public int getSlotid() {
        return slotid;
    }
    public String getSlotidAsText() {
        return Integer.toString(slotid);
    }

    public void setSlotid(@NotNull int slotid) {
        this.slotid = slotid;
    }

    public @Size(max = 500) String getBoardName() {
        return boardName;
    }

    public void setBoardName(@Size(max = 500) String boardName) {
        this.boardName = boardName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubType0() {
        return subType0;
    }

    public void setSubType0(String subType0) {
        this.subType0 = subType0;
    }

    public String getSubType1() {
        return subType1;
    }

    public void setSubType1(String subType1) {
        this.subType1 = subType1;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }

    public @NotNull Frame getFrame() {
        return frame;
    }

    public void setFrame(@NotNull Frame frame) {
        this.frame = frame;
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
        Slot slot = (Slot) o;
        return slotid == slot.slotid && Objects.equals(id, slot.id) && Objects.equals(boardName, slot.boardName) && Objects.equals(status, slot.status) && Objects.equals(subType0, slot.subType0) && Objects.equals(subType1, slot.subType1) && Objects.equals(onOff, slot.onOff) && Objects.equals(frame, slot.frame) && Objects.equals(user, slot.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slotid, boardName, status, subType0, subType1, onOff, frame, user);
    }

    @Override
    public String toString() {
        return "slotid=" + slotid;
    }


}
