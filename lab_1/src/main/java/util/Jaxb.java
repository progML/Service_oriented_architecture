package util;

import model.dataForXml.HumanBeings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class Jaxb {

    public static String jaxbObjectToXML(HumanBeings humanBeings) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(HumanBeings.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(humanBeings, sw);

            //Verify XML Content
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T fromStr(String str, Class<T> tClass) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(tClass);

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new StringReader(str));
    }
}
