package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XMLParser {
    <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException;
    <T> void marshalToFile(String filePath, T rootDto) throws JAXBException;

    <T> T parseXML(Class<T> objectClass, String filePath) throws JAXBException;
    <T> void exportXML(T object, Class<T> objectClass, String filePath) throws JAXBException;

}
