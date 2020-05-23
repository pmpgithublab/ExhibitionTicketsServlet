package ua.training.model.service;

import org.apache.log4j.Logger;
import ua.training.model.dao.ExhibitDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.entity.Exhibit;
import ua.training.util.CheckUtils;
import ua.training.util.MessageUtil;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.training.Constants.*;

public class ExhibitService {
    private static final Logger log = Logger.getLogger(ExhibitService.class);

    private static final String CREATING_EXHIBIT_LIST_BY_DATES_ERROR = "Creating exhibit list by dates error";


    public void saveExhibit(ExhibitDTO exhibitDTO) throws Exception {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            exhibitDao.save(new Exhibit(exhibitDTO));
        }
    }

    public Optional<ExhibitDTO> findById(Long id) {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            return exhibitDao.findById(id).map(ExhibitDTO::new);
        }
    }

    public List<ExhibitDTO> findAllExhibit() {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            return exhibitDao.findAll().stream()
                    .map(ExhibitDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public List<ExhibitDTO> getCurrentExhibits() {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            return exhibitDao.findCurrentExhibits().stream().map(ExhibitDTO::new).collect(Collectors.toList());
        }
    }

    public List<ExhibitDTO> getExhibitListByDates(Long exhibitId) {
        Optional<ExhibitDTO> exhibitDTOFromDB;
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            exhibitDTOFromDB = exhibitDao.findByIdWithHall(exhibitId);
            if (exhibitDTOFromDB.isPresent()) {
                return buildExhibitListByDates(exhibitDTOFromDB.get());
            }
        }

        throw new RuntimeException(MessageUtil.getObjectByIdNotFoundMessage(exhibitId, EXHIBIT_NAME));
    }

    private List<ExhibitDTO> buildExhibitListByDates(ExhibitDTO exhibitDTO) {
        List<ExhibitDTO> result = new ArrayList<>();
        LocalDate startDate =
                CheckUtils.getMaxDate(LocalDate.now(), exhibitDTO.getStartDateTime().toLocalDate());
        int restDays = Period.between(startDate, exhibitDTO.getEndDateTime().toLocalDate()).getDays();
        ExhibitDTO newExhibitDTO;
        LocalDate exhibitDate;
        for (int i = 0; i <= restDays; i++) {
            try {
                newExhibitDTO = exhibitDTO.clone();
            } catch (CloneNotSupportedException e) {
                log.error(CREATING_EXHIBIT_LIST_BY_DATES_ERROR + SLASH_SYMBOL + e.getMessage());
//                    log.error(MessageUtil.getRuntimeExceptionMessage(e));
                throw new RuntimeException(e);
            }
            exhibitDate = startDate.plusDays(i);
            newExhibitDTO.setExhibitDate(exhibitDate);
            if (CheckUtils.isExhibitDateTimeActual(exhibitDate, newExhibitDTO)) {
                result.add(newExhibitDTO);
            }
        }

        return result;
    }
}
