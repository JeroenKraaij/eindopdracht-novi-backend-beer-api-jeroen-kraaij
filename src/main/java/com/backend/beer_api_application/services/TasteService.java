package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.mapper.TasteMapper;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.models.Taste;
import com.backend.beer_api_application.repositories.TasteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Taste> getTasteById(Long id) {
        return tasteRepository.findById(id);
    }

    @Transactional
    public Optional<Taste> findByName(String name) {
        return tasteRepository.findByName(name);
    }

    @Transactional
    public TasteOutputDto addTaste(TasteInputDto tasteInputDto) {
        if (findByName(tasteInputDto.getName()).isPresent()) {
            return null;
        }

        Taste taste = TasteMapper.transferToTasteEntity(tasteInputDto);
        Taste savedTaste = tasteRepository.save(taste);
        return TasteMapper.transferToTasteOutputDto(savedTaste);
    }

    @Transactional
    public TasteOutputDto updateTaste(Long id, TasteInputDto tasteInputDto) {
        Taste taste = tasteRepository.findById(id).orElse(null);
        if (taste == null) return null;
        Optional<Taste> existingTaste = findByName(tasteInputDto.getName());
        if (existingTaste.isPresent() && !existingTaste.get().getId().equals(id)) {
            return null;
        }

        TasteMapper.updateTasteEntity(taste, tasteInputDto);
        Taste updatedTaste = tasteRepository.save(taste);
        return TasteMapper.transferToTasteOutputDto(updatedTaste);
    }

    @Transactional
    public boolean deleteTaste(Long id) {
        if (!tasteRepository.existsById(id)) {
            return false;
        }
        tasteRepository.deleteById(id);
        return true;
    }
}
