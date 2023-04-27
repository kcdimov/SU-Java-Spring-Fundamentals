package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerXmlSeedDto {

    @XmlElement(name = "id")
    private int id;

    public SellerXmlSeedDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
