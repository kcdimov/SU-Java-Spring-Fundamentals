package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarService carService;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper,
                              ValidationUtil validationUtil, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureSeedDto[] dtos = this.gson.fromJson(new FileReader(GlobalConstants.PICTURES_FILE_PATH), PictureSeedDto[].class);

        Arrays.stream(dtos).forEach(pictureSeedDto -> {

            if (validationUtil.isValid(pictureSeedDto) &&
                    this.pictureRepository.findPictureByName(pictureSeedDto.getName()) == null)  {

                Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);
                LocalDateTime localDate = LocalDateTime.parse(pictureSeedDto.getDateAndTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                picture.setDateAndTime(localDate);
                Car carId = this.carService.getCarById(pictureSeedDto.getCar());

                picture.setCar(carId);

                this.pictureRepository.saveAndFlush(picture);
                sb.append(String.format("Successfully import picture - %s", pictureSeedDto.getName()));

            } else {
                sb.append("Invalid picture");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
