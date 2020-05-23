package ua.training.model.dao;

import ua.training.model.dto.ExhibitDTO;
import ua.training.model.entity.Exhibit;

import java.util.List;
import java.util.Optional;

public interface ExhibitDao extends GenericDao<Exhibit> {
    void save(Exhibit exhibit) throws Exception;

    Optional<Exhibit> findById(Long id);

    List<Exhibit> findAll();

    List<Exhibit> findCurrentExhibits();

    Optional<ExhibitDTO> findByIdWithHall(Long exhibitId);
}
