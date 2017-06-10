package com.code405.repository.custom;

import com.code405.entity.model.Timetable;
import com.code405.repository.models.TimetableRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Peter Kozlovsky on 28.05.17.
 */
@Repository
public class TimetableRepositoryImpl implements TimetableRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Timetable> findByAVeryComplicatedQuery() {
      return  entityManager.createNativeQuery("SELECT t.id, t.day from timetable as t", Timetable.class).getResultList();
    }
}
