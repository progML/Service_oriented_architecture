package ru.itmo.soalab2.model;

import javax.persistence.*;

@Entity
@Table
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String teamName;

}
