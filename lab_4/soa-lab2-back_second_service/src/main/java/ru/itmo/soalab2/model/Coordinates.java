package ru.itmo.soalab2.model;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Coordinates implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double x;

    @Column
    private float y; //Значение поля должно быть больше -854

    public Coordinates() {
    }
}

