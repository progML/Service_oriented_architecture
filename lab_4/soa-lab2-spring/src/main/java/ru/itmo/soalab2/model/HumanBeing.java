package ru.itmo.soalab2.model;

//import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table
//@Data
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public Long getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(Long impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public Integer getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public void setMinutesOfWaiting(Integer minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
