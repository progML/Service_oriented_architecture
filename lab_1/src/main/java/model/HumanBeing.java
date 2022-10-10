package model;

import model.dataForXml.JaxbHumanBeing;
import model.dataForXml.LocalDateTimeAdapter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Root
@Entity
@Table
public class HumanBeing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Element
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Element
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @Element
    private Coordinates coordinates; //Поле не может быть null
    @Element
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate; //Поле не может быть null
    @Element
    private Boolean realHero; //Поле не может быть null
    @Element
    private boolean hasToothpick;
    @Element
    private Long impactSpeed; //Поле может быть null
    @Element
    private Integer minutesOfWaiting; //Поле может быть null
    @Element
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType; //Поле может быть null

    @Element
    @Enumerated(EnumType.STRING)
    private Mood mood; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @Element
    private Car car; //Поле не может быть null


    public HumanBeing(String name, Coordinates coordinates, LocalDateTime creationDate, Boolean realHero, boolean hasToothpick, Long impactSpeed, Integer minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
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
    }

    public HumanBeing(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, Boolean realHero, boolean hasToothpick, Long impactSpeed, Integer minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
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
    }


    public HumanBeing() {

    }

    public void update(JaxbHumanBeing data) {
        this.name = data.getName();
        this.coordinates.update(data.getCoordinates());
        this.creationDate = data.getCreationDate();
        this.realHero = data.getRealHero();
        this.hasToothpick = data.isHasToothpick();
        this.impactSpeed = data.getImpactSpeed();
        this.minutesOfWaiting = data.getMinutesOfWaiting();
        this.weaponType = data.getWeaponType();
        this.mood = data.getMood();
        this.car.update(data.getCar());
    }

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

    public String getCustomFormatter() {
        return creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getCustomFormatterDate() {
        return creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getCustomFormatterTime() {
        return creationDate.format(DateTimeFormatter.ofPattern("HH:mm"));
    }


}
