package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.SellerSeedDto;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XMLParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final XMLParser xmlParser;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(XMLParser xmlParser, ModelMapper modelMapper,
                             SellerRepository sellerRepository, ValidationUtil validationUtil) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count()>0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.SELLERS_FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        SellerSeedRootDto sellerSeedRootDto = this.xmlParser.unmarshalFromFile(GlobalConstants.SELLERS_FILE_PATH,
                SellerSeedRootDto.class);

        sellerSeedRootDto.getSellers().forEach(sellerSeedDto -> {
            if (this.validationUtil.isValid(sellerSeedDto)) {
                if (this.sellerRepository.findByEmail(sellerSeedDto.getEmail()) == null) {
                    Seller seller = this.modelMapper.map(sellerSeedDto, Seller.class);

                    this.sellerRepository.saveAndFlush(seller);
                    sb.append(String.format("Successfully import seller %s - %s", sellerSeedDto.getLastName(),
                            sellerSeedDto.getEmail()));
                } else {
                    sb.append("Already in DB");
                }
            } else {
                sb.append("Invalid Seller");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Seller getSellerById(long id) {
        return this.sellerRepository.findById(id);
    }
}
