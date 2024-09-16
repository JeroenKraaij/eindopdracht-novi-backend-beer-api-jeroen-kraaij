package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.mapper.TasteMapper;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Taste;
import com.backend.beer_api_application.repositories.TasteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasteService {

    private final TasteRepository tasteRepository;

    public TasteService(TasteRepository tasteRepository) {
        this.tasteRepository = tasteRepository;
    }

    @Transactional
    public List<TasteOutputDto> getAllTastes() {
        return tasteRepository.findAll().stream()
                .map(TasteMapper::transferToTasteOutputDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TasteOutputDto getTasteById(Long id) {
        Taste taste = tasteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Taste not found with ID: " + id));
        return TasteMapper.transferToTasteOutputDto(taste);
    }

    @Transactional
    public TasteOutputDto addTaste(TasteInputDto tasteInputDto) {
        Taste taste = TasteMapper.transferToTasteEntity(tasteInputDto);
        Taste savedTaste = tasteRepository.save(taste);
        return TasteMapper.transferToTasteOutputDto(savedTaste);
    }

    @Transactional
    public TasteOutputDto updateTaste(Long id, TasteInputDto tasteInputDto) {
        Taste taste = tasteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Taste not found with ID: " + id));

        TasteMapper.updateTasteEntity(taste, tasteInputDto);
        Taste updatedTaste = tasteRepository.save(taste);
        return TasteMapper.transferToTasteOutputDto(updatedTaste);
    }

    @Transactional
    public void deleteTaste(Long id) {
        if (!tasteRepository.existsById(id)) {
            throw new RecordNotFoundException("Taste not found with ID: " + id);
        }
        tasteRepository.deleteById(id);
    }
}
