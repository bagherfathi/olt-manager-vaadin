package com.gohardani.oltmanager.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name="center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Area area;

    @NotNull
    @Size(min = 4, max = 150)
    @Column(name = "name", length = 150)
    private String name;



    @Override
    public String toString() {
        return "Center{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return Objects.equals(id, center.id) && Objects.equals(name, center.name) && Objects.equals(area, center.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, area);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(min = 4, max = 150) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 4, max = 150) String name) {
        this.name = name;
    }

    public @NotNull Area getArea() {
        return area;
    }

    public void setArea(@NotNull Area area) {
        this.area = area;
    }

    public Center(Long id, String name, Area area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public Center() {
    }

    public Center(Long id) {
        this.id = id;
    }
}
