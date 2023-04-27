package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.OfferSeedRoodDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final ModelMapper modelMapper;
    private final XMLParser xmlParser;
    private final ValidationUtil validationUtil;
    private final OfferRepository offerRepository;
    private final CarService carService;
    private final SellerService sellerService;

    public OfferServiceImpl(ModelMapper modelMapper, XMLParser xmlParser, ValidationUtil validationUtil,
                            OfferRepository offerRepository, CarService carService, SellerService sellerService) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferSeedRoodDto offerSeedRoodDto = this.xmlParser.parseXML(OfferSeedRoodDto.class,
                GlobalConstants.OFFERS_FILE_PATH);

        offerSeedRoodDto.getOffers().forEach(offerSeedDto -> {
            if (this.validationUtil.isValid(offerSeedDto)) {
                Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);

                Car car = carService.getCarById(offerSeedDto.getCarXmlSeedDto().getId());
                Seller seller = sellerService.getSellerById(offerSeedDto.getSellerXmlSeedDto().getId());

                offer.setCar(car);
                offer.setSeller(seller);
               offer.setPictures(new ArrayList<>(car.getPictures()));


                this.offerRepository.saveAndFlush(offer);
                sb.append(String.format("Successfully import seller %s - %s",
                        offerSeedDto.getAddedOn().toString(),
                        offerSeedDto.getHasGoldStatus()));

            } else {
                sb.append("Invalid offer");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
