package model;


import model.dataForXml.JaxbCoordinates;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table
@XmlRootElement
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @XmlElement
    private double x;
    @XmlElement
    private float y; //Значение поля должно быть больше -854

    public Coordinates(int id, double x, Float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void update(JaxbCoordinates data) {
        this.x = data.getX();
        this.y = data.getY();
    }

    public Coordinates() {

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