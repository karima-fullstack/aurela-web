package web.aurela.aurelaweb.Mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import web.aurela.aurelaweb.Entities.Image;
import web.aurela.aurelaweb.Dtos.ImageDto;

@Service
@RequiredArgsConstructor
public class ImageMapper {

    private final ModelMapper modelMapper;

    public ImageDto toDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

    public Image toEntity(ImageDto imageDto) {
        return modelMapper.map(imageDto, Image.class);
    }
}

