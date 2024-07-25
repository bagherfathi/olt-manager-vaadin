package com.gohardani.oltmanager.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Port")
public class Port implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Slot slot;

    @NotNull
    @Column(name="F")
    private int F;

    @NotNull
    @Column(name="S")
    private int S;

    @NotNull
    @Column(name="P")
    private int P;

    @NotNull
    @Column(name="OntID")
    private int OntID;

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


}

