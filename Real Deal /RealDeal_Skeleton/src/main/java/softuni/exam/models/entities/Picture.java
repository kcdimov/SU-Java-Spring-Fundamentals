package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

//    id – integer number, primary identification field.
//    name – a char sequence (between 2 to 20 exclusive). The name of a picture is unique.
//            dateAndTime – The date and time of a picture.

    private String name;
    private LocalDateTime dateAndTime;
    private Car car;

    public Picture() {
    }

//    @Length(min = 2, max = 19)
    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date_and_time")
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
