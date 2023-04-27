package softuni.exam.models.dtos;

import org.hibernate.validator.constraints.Length;
import softuni.exam.config.LocalDateTimeAdapter;

import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    @XmlElement
    private BigDecimal price;
    @XmlElement
    private String description;
    @XmlElement(name = "has-gold-status")
    private Boolean hasGoldStatus;
    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime addedOn;
    @XmlElement(name = "car")
    private CarXmlSeedDto carXmlSeedDto;
    @XmlElement(name = "seller")
    private SellerXmlSeedDto sellerXmlSeedDto;

    public OfferSeedDto() {
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(Boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public CarXmlSeedDto getCarXmlSeedDto() {
        return carXmlSeedDto;
    }

    public void setCarXmlSeedDto(CarXmlSeedDto carXmlSeedDto) {
        this.carXmlSeedDto = carXmlSeedDto;
    }

    public SellerXmlSeedDto getSellerXmlSeedDto() {
        return sellerXmlSeedDto;
    }

    public void setSellerXmlSeedDto(SellerXmlSeedDto sellerXmlSeedDto) {
        this.sellerXmlSeedDto = sellerXmlSeedDto;
    }
}
