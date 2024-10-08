package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.mapper.TasteMapper;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Taste;
import com.backend.beer_api_application.repositories.TasteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TasteServiceTest {

    @Mock
    private TasteRepository tasteRepository;

    @InjectMocks
    private TasteService tasteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTastes_shouldReturnTasteOutputDtoList_whenTastesExist() {
        List<Taste> tasteList = new ArrayList<>();
        tasteList.add(new Taste());
        tasteList.add(new Taste());

        when(tasteRepository.findAll()).thenReturn(tasteList);

        List<TasteOutputDto> result = tasteService.getAllTastes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tasteRepository, times(1)).findAll();
    }

    @Test
    void getTasteById_shouldReturnTasteOutputDto_whenTasteExists() {
        Taste mockTaste = new Taste();
        mockTaste.setId(1L);

        when(tasteRepository.findById(1L)).thenReturn(Optional.of(mockTaste));

        TasteOutputDto result = tasteService.getTasteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tasteRepository, times(1)).findById(1L);
    }

    @Test
    void getTasteById_shouldThrowRecordNotFoundException_whenTasteDoesNotExist() {
        when(tasteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> tasteService.getTasteById(1L));
        verify(tasteRepository, times(1)).findById(1L);
    }

    @Test
    void updateTaste_shouldUpdateAndReturnTaste_whenTasteExists() {
        TasteInputDto inputDto = new TasteInputDto();
        Taste existingTaste = new Taste();
        existingTaste.setId(1L);

        when(tasteRepository.findById(1L)).thenReturn(Optional.of(existingTaste));
        when(tasteRepository.save(any(Taste.class))).thenReturn(existingTaste);

        TasteOutputDto result = tasteService.updateTaste(1L, inputDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tasteRepository, times(1)).findById(1L);
        verify(tasteRepository, times(1)).save(existingTaste);
    }

    @Test
    void updateTaste_shouldThrowRecordNotFoundException_whenTasteDoesNotExist() {
        TasteInputDto inputDto = new TasteInputDto();

        when(tasteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> tasteService.updateTaste(1L, inputDto));
        verify(tasteRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTaste_shouldDeleteTaste_whenTasteExists() {
        when(tasteRepository.existsById(1L)).thenReturn(true);

        tasteService.deleteTaste(1L);

        verify(tasteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTaste_shouldThrowRecordNotFoundException_whenTasteDoesNotExist() {
        when(tasteRepository.existsById(1L)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> tasteService.deleteTaste(1L));
        verify(tasteRepository, never()).deleteById(anyLong());
    }
}
