package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dto.HallDTO;
import ua.training.model.entity.Hall;
import ua.training.model.exception.NoDuplicationAllowedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.fail;

public class HallServiceTest {
    private static final String TEST_STRING = "Test";
    private static final int ONE_ELEMENT = 1;
    private static final int FIRST_ELEMENT = 0;

    private final HallService hallService = new HallService();

    private final Random random = new Random(System.currentTimeMillis());


    @Test
    public void successfulSaveHall() throws Exception {
        HallDTO hallDTO = buildDefaultTestHallDTO();

        hallService.saveHall(hallDTO);
    }

    private HallDTO buildDefaultTestHallDTO() {
        Hall hall = new Hall();
        hall.setName(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        hall.setNameUK(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        return new HallDTO(hall);
    }

    @Test(expected = NoDuplicationAllowedException.class)
    public void failSaveHallDuplicate() throws Exception {
        HallDTO hallDTO = buildDefaultTestHallDTO();

        hallService.saveHall(hallDTO);
        hallService.saveHall(hallDTO);
    }

    @Test
    public void failSaveHallWithEmptyFields() {
        HallDTO hallDTO = buildDefaultTestHallDTO();

        hallDTO.setName(null);
        try {
            hallService.saveHall(hallDTO);
            fail();
        } catch (Exception ignored) {
        }

        hallDTO.setName(TEST_STRING);
        hallDTO.setNameUK(null);
        try {
            hallService.saveHall(hallDTO);
            fail();
        } catch (Exception ignored) {
        }

        hallDTO.setName(null);
        hallDTO.setNameUK(null);
        try {
            hallService.saveHall(hallDTO);
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void successfulSaveAndFindAllHalls() throws Exception {
        List<HallDTO> hallDTOS = buildDefaultTestHallListDTO(3);
        for (HallDTO hallDTO : hallDTOS) {
            hallService.saveHall(hallDTO);
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
            result.add(buildDefaultTestHallDTO());
        }
        return result;
    }

    @Test
    public void successfulSaveHallAndFindHallAndFindById() throws Exception {
        HallDTO hallDTO = buildDefaultTestHallDTO();

        hallService.saveHall(hallDTO);

        List<HallDTO> hallDTOS = hallService.findAllHall();
        hallDTOS = hallDTOS.stream()
                .filter(e -> e.getName().equals(hallDTO.getName()))
                .collect(Collectors.toList());

        if (hallDTOS.size() != ONE_ELEMENT) {
            fail();
            return;
        }

        Optional<HallDTO> hallDTOFromDB = hallService.findById(hallDTOS.get(FIRST_ELEMENT).getId());
        if (!hallDTOFromDB.isPresent() || !hallDTOFromDB.get().getName().equals(hallDTO.getName())
                || !hallDTOFromDB.get().getId().equals(hallDTOS.get(FIRST_ELEMENT).getId())) {
            fail();
        }
    }

    @Test
    public void successfulFindAllAndFindById() {
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