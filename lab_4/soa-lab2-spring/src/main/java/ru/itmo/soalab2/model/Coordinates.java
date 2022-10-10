package ru.itmo.soalab2.model;


//import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
//@Data
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

    public Coordinates(int id, double x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

