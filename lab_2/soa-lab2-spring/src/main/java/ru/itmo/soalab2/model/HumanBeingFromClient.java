package ru.itmo.soalab2.model;

import java.time.LocalDateTime;

public class HumanBeingFromClient {
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private CoordinatesFromClient coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null
    private Boolean realHero; //Поле не может быть null
    private boolean hasToothpick;
    private Long impactSpeed; //Поле может быть null
    private Integer minutesOfWaiting; //Поле может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private CarFromClient car;
    private TeamFromClient team;

    public HumanBeingFromClient(Long id, String name, CoordinatesFromClient coordinates, LocalDateTime creationDate, Boolean realHero, boolean hasToothpick, Long impactSpeed, Integer minutesOfWaiting, WeaponType weaponType, Mood mood, CarFromClient car, TeamFromClient team) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordinatesFromClient getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public Long getImpactSpeed() {
        return impactSpeed;
    }

    public Integer getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(CoordinatesFromClient coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(Long impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setMinutesOfWaiting(Integer minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setCar(CarFromClient car) {
        this.car = car;
    }

    public TeamFromClient getTeam() {
        return team;
    }


    public void setTeam(TeamFromClient team) {
        this.team = team;
    }

    public CarFromClient getCar() {
        return car;
    }
}
