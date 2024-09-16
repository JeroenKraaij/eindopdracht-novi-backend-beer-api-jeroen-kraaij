package com.backend.beer_api_application.dto.mapper;
import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.models.Taste;
import org.springframework.stereotype.Service;

@Service
public class TasteMapper {

    // Convert from TasteInputDto to Taste entity
    public static Taste transferToTasteEntity(TasteInputDto tasteInputDto) {
        return new Taste(tasteInputDto.getName());
    }

    // Convert from Taste entity to TasteOutputDto
    public static TasteOutputDto transferToTasteOutputDto(Taste taste) {
        TasteOutputDto dto = new TasteOutputDto();
        dto.setId(taste.getId());
        dto.setName(taste.getName());
        return dto;
    }

    // Update Taste entity with data from TasteInputDto
    public static void updateTasteEntity(Taste taste, TasteInputDto tasteInputDto) {
        taste.setName(tasteInputDto.getName());
    }
}
