package model.dataForXml;

import model.Coordinates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
public class JaxbCoordinates {
    @XmlElement
    private double x;
    @XmlElement
    private float y; //Значение поля должно быть больше -854


    public JaxbCoordinates() {

    }

    public JaxbCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates toCoordinates() {
        return new Coordinates(0, x, y);
    }

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
