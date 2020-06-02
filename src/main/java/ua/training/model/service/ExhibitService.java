package ua.training.model.service;

import ua.training.model.dao.ExhibitDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.ExhibitDTO;
import ua.training.model.entity.Exhibit;
import ua.training.util.CheckUtils;
import ua.training.util.FinancialUtil;
import ua.training.util.MessageUtil;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.training.Constants.EXHIBIT_NAME;

public class ExhibitService {

    public void saveExhibit(ExhibitDTO exhibitDTO) throws Exception {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            exhibitDao.save(new Exhibit(exhibitDTO));
        }
    }

    public Optional<ExhibitDTO> findById(Long id) {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            Optional<ExhibitDTO> result = exhibitDao.findById(id).map(ExhibitDTO::new);
            result.ifPresent(exhibitDTO -> exhibitDTO.setTicketCost(FinancialUtil.calcCost(exhibitDTO.getTicketCost())));
            return result;
        }
    }

    public List<ExhibitDTO> findAllExhibit() {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            List<ExhibitDTO> result = exhibitDao.findAll().stream().map(ExhibitDTO::new).collect(Collectors.toList());
            result.forEach(e -> e.setTicketCost(FinancialUtil.calcCost(e.getTicketCost())));
            return result;
        }
    }

    public List<ExhibitDTO> findCurrentExhibits() {
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            return exhibitDao.findCurrentExhibits().stream().map(ExhibitDTO::new).collect(Collectors.toList());
        }
    }

    public List<ExhibitDTO> getExhibitListByDates(Long exhibitId) throws CloneNotSupportedException {
        Optional<ExhibitDTO> exhibitDTOFromDB;
        try (ExhibitDao exhibitDao = JDBCDaoFactory.getInstance().createExhibitDao()) {
            exhibitDTOFromDB = exhibitDao.findByIdWithHall(exhibitId);
            if (exhibitDTOFromDB.isPresent()) {
                return buildExhibitListByDates(exhibitDTOFromDB.get());
            }
        }

        throw new RuntimeException(MessageUtil.getObjectByIdNotFoundMessage(exhibitId, EXHIBIT_NAME));
    }

    private List<ExhibitDTO> buildExhibitListByDates(ExhibitDTO exhibitDTO) throws CloneNotSupportedException {
        List<ExhibitDTO> result = new ArrayList<>();
        LocalDate startDate =
                CheckUtils.getMaxDate(LocalDate.now(), exhibitDTO.getStartDateTime().toLocalDate());
        int restDays = Period.between(startDate, exhibitDTO.getEndDateTime().toLocalDate()).getDays();
        ExhibitDTO newExhibitDTO;
        LocalDate exhibitDate;
        for (int i = 0; i <= restDays; i++) {
            newExhibitDTO = exhibitDTO.clone();
            exhibitDate = startDate.plusDays(i);
            newExhibitDTO.setExhibitDate(exhibitDate);
            if (CheckUtils.isExhibitDateTimeActual(exhibitDate, newExhibitDTO)) {
                result.add(newExhibitDTO);
            }
        }

        return result;
    }
}
