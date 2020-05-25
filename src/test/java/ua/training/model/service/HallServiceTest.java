package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dto.HallDTO;
import ua.training.model.entity.Hall;
import ua.training.model.exception.NoDuplicationAllowedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HallServiceTest {
    private final String TEST_STRING = "Test";
    private final HallService hallService = new HallService();


    @Test
    public void getAllHall() {
        List<HallDTO> hallDTOS = buildDefaultTestHallListDTO(3);
        for (HallDTO hallDTO : hallDTOS) {
            try {
                hallService.saveHall(hallDTO);
            } catch (NoDuplicationAllowedException e) {
            } catch (Exception e) {
                fail();
            }
        }

        List<HallDTO> hallDTOSFromDB = hallService.findAllHall();
        List<HallDTO> result = hallDTOSFromDB.stream()
                .filter(e -> hallDTOS.stream().filter(d -> d.getName().equals(e.getName())).count() == 1)
                .collect(Collectors.toList());

        if (result.size() != hallDTOS.size()) {
            fail();
        }
    }

    private List<HallDTO> buildDefaultTestHallListDTO(int quantity) {
        List<HallDTO> result = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            Hall hall = new Hall();
            hall.setName(TEST_STRING + LocalDateTime.now());
            hall.setNameUK(TEST_STRING + LocalDateTime.now());
            result.add(new HallDTO(hall));
        }
        return result;
    }

    @Test
    public void saveHall() {
        Hall hall = new Hall();
        hall.setName(TEST_STRING + LocalDateTime.now());
        hall.setNameUK(TEST_STRING + LocalDateTime.now());
        HallDTO hallDTO = new HallDTO(hall);

        try {
            hallService.saveHall(hallDTO);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findById() {
        List<HallDTO> hallDTOSFromDB = hallService.findAllHall();
        Optional<HallDTO> hallDTO;
        for (HallDTO hallDTOFromDB : hallDTOSFromDB) {
            hallDTO = hallService.findById(hallDTOFromDB.getId());
            if (!hallDTO.isPresent() || !hallDTO.get().getName().equals(hallDTOFromDB.getName())) {
                fail();
            }
        }
    }
}