package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;

import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;


    public CarServiceImpl(Gson gson, ModelMapper modelMapper,
                          CarRepository carRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;

    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarSeedDto[] dtos = this.gson.fromJson(this.readCarsFileContent(), CarSeedDto[].class);

        Arrays.stream(dtos).forEach(carSeedDto -> {
            if (validationUtil.isValid(carSeedDto)) {
                if (this.carRepository.findCarByMakeAndModelAndKilometers(carSeedDto.getMake(),
                        carSeedDto.getModel(), carSeedDto.getKilometers()) == null) {
                    Car car = this.modelMapper.map(carSeedDto, Car.class);
                    LocalDate localDate = LocalDate.parse(carSeedDto.getRegisteredOn(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                            car.setRegisteredOn(localDate);
                    this.carRepository.saveAndFlush(car);
                    sb.append(String.format("Successfully imported car - %s - %s",
                            carSeedDto.getMake(), carSeedDto.getModel()));
                } else {
                    sb.append("Already in DB");
                }
            } else {
                sb.append("Invalid car");

            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        //TODO

        StringBuilder result = new StringBuilder();


        this.carRepository.findCarByPicturesAndMakeOrderByPicturesDescMakeAsc().forEach(car -> {
            result.append(String.format("Car make - %s, model - %s\n" +
                    "\tKilometers - %d\n" +
                    "\tRegistered on - %S\n" +
                    "\tNumber of pictures - %d",
                    car.getMake(), car.getModel(), car.getKilometers(), car.getRegisteredOn(),
                    car.getPictures().size())).append(System.lineSeparator());
        });


        return result.toString();
    }

    @Override
    public Car getCarById(long id) {
        return carRepository.findCarById(id);
    }
}
