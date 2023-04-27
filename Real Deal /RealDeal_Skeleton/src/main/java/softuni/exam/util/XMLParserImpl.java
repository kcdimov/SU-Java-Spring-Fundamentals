package softuni.exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XMLParserImpl implements XMLParser{

    @Override
    public <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new FileReader(filePath));
    }

    @Override
    public <T> void marshalToFile(String filePath, T rootDto) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(rootDto.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rootDto, new File(filePath));
    }

    @Override
    public <T> T parseXML(Class<T> objectClass, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (T)unmarshaller.unmarshal(new File(filePath));
    }

    @Override
    public <T> void exportXML(T object, Class<T> objectClass, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(filePath));

    }
}
