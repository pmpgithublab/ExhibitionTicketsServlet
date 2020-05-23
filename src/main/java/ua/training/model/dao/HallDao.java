package ua.training.model.dao;

import ua.training.model.entity.Hall;

import java.util.List;
import java.util.Optional;

public interface HallDao extends GenericDao<Hall> {
    void save(Hall hall) throws Exception;

    Optional<Hall> findById(Long id);

    List<Hall> findAll();
}
