package model.dataForXml;

import model.Car;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
public class JaxbCar {
    @XmlElement
    private boolean cool;

    public JaxbCar(){

    }

    public JaxbCar(boolean cool) {
        this.cool = cool;
    }

    public Car toCar(){
        return new Car(0, cool);
    }

    public boolean isCool() {
        return cool;
    }
}
