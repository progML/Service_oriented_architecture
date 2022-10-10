package ru.itmo.soalab2.model;

import javax.persistence.*;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // в модели отсутствует

    @Column
    private boolean cool;
}


