package ru.itmo.soalab2.model;

//import lombok.Data;

import javax.persistence.*;

@Entity
@Table
//@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // в модели отсутствует

    @Column
    private boolean cool;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCool() {
        return cool;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }
}


