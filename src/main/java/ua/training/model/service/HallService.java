package ua.training.model.service;

import ua.training.model.dao.HallDao;
import ua.training.model.dao.implementation.JDBCDaoFactory;
import ua.training.model.dto.HallDTO;
import ua.training.model.entity.Hall;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HallService {
    public void saveHall(HallDTO hallDTO) throws Exception {
        try(HallDao hallDao = JDBCDaoFactory.getInstance().createHallDao()){
            hallDao.save(new Hall(hallDTO));
        }
    }

    public Optional<HallDTO> findById(Long id) {
        try(HallDao hallDao = JDBCDaoFactory.getInstance().createHallDao()) {
            return hallDao.findById(id).map(HallDTO::new);
        }
    }

    public List<HallDTO> findAllHall() {
        try(HallDao hallDao = JDBCDaoFactory.getInstance().createHallDao()){
            return hallDao.findAll().stream()
                    .map(HallDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
