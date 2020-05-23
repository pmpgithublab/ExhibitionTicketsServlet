package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.dto.HallDTO;
import ua.training.model.entity.Hall;
import ua.training.model.exception.NoDuplicationAllowedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ExhibitServiceTest {
    private final String TEST_STRING = "Test";
    private final ExhibitService exhibitService = new ExhibitService();
    private final HallService hallService = new HallService();

    @Test
    public void saveExhibit() {
        Hall hall = new Hall();
        hall.setName(TEST_STRING + LocalDateTime.now());
        hall.setNameUK(TEST_STRING + LocalDateTime.now());
        HallDTO hallDTO = new HallDTO(hall);
        try {
            hallService.saveHall(hallDTO);
        } catch (Exception exception) {
            fail();
        }

        ExhibitDTO exhibitDTO = new ExhibitDTO();
//        exhibitDTO.setId(-1L);
        exhibitDTO.setName(TEST_STRING + LocalDateTime.now());
        exhibitDTO.setNameUK(TEST_STRING + LocalDateTime.now());
        exhibitDTO.setStartDateTime(LocalDateTime.now());
        exhibitDTO.setEndDateTime(LocalDateTime.now().plusDays(10));
        exhibitDTO.setMaxVisitorsPerDay(99);
        exhibitDTO.setTicketCost(44);
        exhibitDTO.setHallId(hallService.findAllHall().get(0).getId());

        try{
            exhibitService.saveExhibit(exhibitDTO);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void findById() {
        List<ExhibitDTO> ExhibitDTOSFromDB = exhibitService.findAllExhibit();
        Optional<ExhibitDTO> exhibitDTO;
        for (ExhibitDTO exhibitDTOFromDB : ExhibitDTOSFromDB) {
            exhibitDTO = exhibitService.findById(exhibitDTOFromDB.getId());
            if (!exhibitDTO.isPresent() || !exhibitDTO.get().getName().equals(exhibitDTOFromDB.getName())) {
                fail();
            }
        }
    }

    @Test
    public void getAllExhibit() {
        List<ExhibitDTO> exhibitDTOS = buildDefaultTestExhibitListDTO(3);
        for (ExhibitDTO exhibitDTO : exhibitDTOS) {
            try {
                exhibitService.saveExhibit(exhibitDTO);
            } catch (NoDuplicationAllowedException e) {
            } catch (Exception e) {
                fail();
            }
        }

        List<ExhibitDTO> exhibitDTOSFromDB = exhibitService.findAllExhibit();
        List<ExhibitDTO> result = exhibitDTOSFromDB.stream()
                .filter(e -> exhibitDTOS.stream().filter(d -> d.getName().equals(e.getName())).count() == 1)
                .collect(Collectors.toList());

        if (result.size() != exhibitDTOS.size()) {
            fail();
        }
    }

    private List<ExhibitDTO> buildDefaultTestExhibitListDTO(int quantity) {
        Hall hall = new Hall();
        hall.setName(TEST_STRING + LocalDateTime.now());
        hall.setNameUK(TEST_STRING + LocalDateTime.now());
        HallDTO hallDTO = new HallDTO(hall);
        try {
            hallService.saveHall(hallDTO);
        } catch (Exception exception) {
            fail();
        }

        List<ExhibitDTO> result = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            ExhibitDTO exhibitDTO = new ExhibitDTO();
            exhibitDTO.setName(TEST_STRING + LocalDateTime.now() + i);
            exhibitDTO.setNameUK(TEST_STRING + LocalDateTime.now() + i);
            exhibitDTO.setStartDateTime(LocalDateTime.now());
            exhibitDTO.setEndDateTime(LocalDateTime.now().plusDays(10));
            exhibitDTO.setMaxVisitorsPerDay(99);
            exhibitDTO.setTicketCost(44);
            exhibitDTO.setHallId(hallService.findAllHall().get(0).getId());
            result.add(exhibitDTO);
        }
        return result;
    }
}