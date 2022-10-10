package model.dataForXml;


import model.*;
import model.WeaponType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "humanBeing")
public class JaxbHumanBeing {
    @XmlElement
    private Long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private JaxbCoordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement
    private Boolean realHero; //Поле не может быть null
    @XmlElement
    private boolean hasToothpick;
    @XmlElement
    private Long impactSpeed; //Поле может быть null
    @XmlElement
    private Integer minutesOfWaiting; //Поле может быть null
    @XmlElement
    private WeaponType weaponType; //Поле может быть null
    @XmlElement
    private Mood mood; //Поле не может быть null
    @XmlElement
    private JaxbCar car; //Поле не может быть null

    public HumanBeing toHumanBeing() {
        return new HumanBeing(
                id,
                name,
                coordinates.toCoordinates(),
                creationDate,
                realHero,
                hasToothpick,
                impactSpeed,
                minutesOfWaiting,
                weaponType,
                mood,
                car.toCar()
        );
    }

    public JaxbHumanBeing(Long id, String name, JaxbCoordinates coordinates, LocalDateTime creationDate, Boolean realHero, boolean hasToothpick, Long impactSpeed, Integer minutesOfWaiting, WeaponType weaponType, Mood mood, JaxbCar car) {
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


    public JaxbHumanBeing() {

    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public JaxbCoordinates getCoordinates() {
        return coordinates;
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

    public void setWeaponTypeNull() {
        this.weaponType = WeaponType.NULL;
    }

    public void setImpactNull() {
        this.impactSpeed = 0L;
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

    public JaxbCar getCar() {
        return car;
    }
}
