package softuni.exam.models.entities;


import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
//    id – integer number, primary identification field.
//    make – a char sequence (between 2 to 20 exclusive).
//    model – a char sequence (between 2 to 20 exclusive).
//    kilometers – a number (must be positive).
//    registeredOn – a date.
//    The combination of make, model and kilometers makes a car unique.

    private String make;
    private String model;
    private int kilometers;
    private LocalDate registeredOn;

    private List<Picture> pictures;


    public Car() {
    }

  //  @Length(min = 2, max = 19)
    @Column(name = "make", nullable = false)
    public String getMake() {
        return make;
    }


    public void setMake(String make) {
        this.make = make;
    }

   // @Length(min = 2, max = 19)
    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Min(value = 0)
    @Column(name = "kilometers")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @Column(name = "registered_on")
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
