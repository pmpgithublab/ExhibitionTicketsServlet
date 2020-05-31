package ua.training.model.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.dto.HallDTO;
import ua.training.model.entity.Hall;
import ua.training.model.exception.NoDuplicationAllowedException;
import ua.training.util.LocaleUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ExhibitServiceTest {
    private static final String TEST_STRING = "Test";
    private static final int ONE_ELEMENT = 1;

    private final Random random = new Random(System.currentTimeMillis());

    private final ExhibitService exhibitService = new ExhibitService();
    private final HallService hallService = new HallService();


    @Test
    public void successfulSaveExhibit() throws Exception {
        ExhibitDTO exhibitDTO = buildDefaultTestExhibitDTO();

        exhibitService.saveExhibit(exhibitDTO);
    }

    private ExhibitDTO buildDefaultTestExhibitDTO() throws Exception {
        HallDTO hallDTO = buildDefaultTestHallDTO();
        hallService.saveHall(hallDTO);

        ExhibitDTO exhibitDTO = new ExhibitDTO();
        exhibitDTO.setName(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        exhibitDTO.setNameUK(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        exhibitDTO.setStartDateTime(LocalDateTime.now());
        exhibitDTO.setEndDateTime(LocalDateTime.now().plusDays(10));
        exhibitDTO.setMaxVisitorsPerDay(99);
        exhibitDTO.setTicketCost(44);
        exhibitDTO.setHallId(hallService.findAllHall().get(0).getId());
        return exhibitDTO;
    }

    private HallDTO buildDefaultTestHallDTO() {
        Hall hall = new Hall();
        hall.setName(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        hall.setNameUK(TEST_STRING + LocalDateTime.now() + random.nextInt(10_000));
        return new HallDTO(hall);
    }

    @Test(expected = NoDuplicationAllowedException.class)
    public void failSaveExhibitionDuplicate() throws Exception {
        ExhibitDTO exhibitDTO = buildDefaultTestExhibitDTO();

        exhibitService.saveExhibit(exhibitDTO);

        exhibitService.saveExhibit(exhibitDTO);
    }

    @Test
    public void failSaveExhibitWithEmptyFields() throws Exception {
        ExhibitDTO exhibitDTO = buildDefaultTestExhibitDTO();

        exhibitDTO.setName(null);
        try {
            exhibitService.saveExhibit(exhibitDTO);
            fail();
        } catch (Exception ignored) {
        }

        exhibitDTO.setName(TEST_STRING);
        exhibitDTO.setNameUK(null);
        try {
            exhibitService.saveExhibit(exhibitDTO);
            fail();
        } catch (Exception ignored) {
        }

        exhibitDTO.setName(null);
        exhibitDTO.setNameUK(null);
        try {
            exhibitService.saveExhibit(exhibitDTO);
            fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void successfulSaveAndFindAllExhibits() throws Exception {
        List<ExhibitDTO> exhibitDTOS = buildDefaultTestExhibitListDTO(3);
        for (ExhibitDTO exhibitDTO : exhibitDTOS) {
            exhibitService.saveExhibit(exhibitDTO);
        }

        List<ExhibitDTO> exhibitDTOSFromDB = exhibitService.findAllExhibit();
        List<ExhibitDTO> result = exhibitDTOSFromDB.stream()
                .filter(e -> exhibitDTOS.stream().filter(d -> d.getName().equals(e.getName())).count() == 1)
                .collect(Collectors.toList());

        if (result.size() != exhibitDTOS.size()) {
            fail();
        }
    }

    private List<ExhibitDTO> buildDefaultTestExhibitListDTO(int quantity) throws Exception {
        List<ExhibitDTO> result = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            result.add(buildDefaultTestExhibitDTO());
        }
        return result;
    }

    @Test
    public void successfulFindAllAndFindById() {
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
    public void successfulFindCurrentExhibits() throws Exception {
        ExhibitDTO exhibitDTOPast = buildDefaultTestExhibitDTO();
        exhibitDTOPast.setStartDateTime(LocalDateTime.now().minusDays(10));
        exhibitDTOPast.setEndDateTime(LocalDateTime.now().minusDays(1));
        exhibitService.saveExhibit(exhibitDTOPast);

        ExhibitDTO exhibitDTOFuture = buildDefaultTestExhibitDTO();
        exhibitDTOFuture.setStartDateTime(LocalDateTime.now().minusDays(10));
        exhibitDTOFuture.setEndDateTime(LocalDateTime.now().plusDays(10));
        exhibitService.saveExhibit(exhibitDTOFuture);

        List<ExhibitDTO> exhibitDTOS = exhibitService.findCurrentExhibits();

        if (exhibitDTOS.stream().filter(e -> e.getName().equals(exhibitDTOPast.getName())).count() == ONE_ELEMENT) {
            fail();
        }

        if (exhibitDTOS.stream().filter(e -> e.getName().equals(exhibitDTOFuture.getName())).count() != ONE_ELEMENT) {
            fail();
        }
    }

    @Test
    public void successfulGetExhibitListByDates() throws Exception {
        int tenDays = 10;
        ExhibitDTO exhibitDTO = buildDefaultTestExhibitDTO();
        exhibitDTO.setStartDateTime(LocalDateTime.now().minusDays(tenDays).minusSeconds(5));
        exhibitDTO.setEndDateTime(LocalDateTime.now().plusDays(tenDays).plusMinutes(10));
        exhibitService.saveExhibit(exhibitDTO);

        List<ExhibitDTO> exhibitDTOS = exhibitService.findCurrentExhibits();
        Long exhibitId = exhibitDTOS.stream()
                .filter(e -> e.getName().equals(exhibitDTO.getName()))
                .collect(Collectors.toList()).get(0)
                .getId();
        List<ExhibitDTO> result = exhibitService.getExhibitListByDates(exhibitId);
        if (result.size() != tenDays + 1) {
            fail();
        }
    }
}