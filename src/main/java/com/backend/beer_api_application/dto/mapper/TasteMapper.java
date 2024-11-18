package com.backend.beer_api_application.dto.mapper;
import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.models.Taste;
import org.springframework.stereotype.Service;

@Service
public class TasteMapper {

    public static Taste transferToTasteEntity(TasteInputDto tasteInputDto) {
        return new Taste(tasteInputDto.getName());
    }

    public static TasteOutputDto transferToTasteOutputDto(Taste taste) {
        TasteOutputDto dto = new TasteOutputDto();
        dto.setId(taste.getId());
        dto.setName(taste.getName());
        return dto;
    }

    public static void updateTasteEntity(Taste taste, TasteInputDto tasteInputDto) {
        taste.setName(tasteInputDto.getName());
    }
}
