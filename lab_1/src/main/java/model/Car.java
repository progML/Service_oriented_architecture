package model;



import model.dataForXml.JaxbCar;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private int id; // в модели отсутствует
    @XmlElement
    private boolean cool;



    public Car(int id, boolean cool) {
        this.id = id;
        this.cool = cool;
    }

    public void update(JaxbCar carData) {
        this.cool = carData.isCool();
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCool() {
        return cool;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }
}
