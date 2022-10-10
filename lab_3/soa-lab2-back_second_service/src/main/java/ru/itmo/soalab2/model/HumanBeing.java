package ru.itmo.soalab2.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
public class HumanBeing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Coordinates coordinates; //Поле не может быть null

    @Column
    private LocalDateTime creationDate; //Поле не может быть null

    @Column
    private Boolean realHero; //Поле не может быть null

    @Column
    private boolean hasToothpick;

    @Column
    private Long impactSpeed; //Поле может быть null

    @Column
    private Integer minutesOfWaiting; //Поле может быть null

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType; //Поле может быть null

    @Column
    @Enumerated(EnumType.STRING)
    private Mood mood; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Car car; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Team team; // имя героя


}
