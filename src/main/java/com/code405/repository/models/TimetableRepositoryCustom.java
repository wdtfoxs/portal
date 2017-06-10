package com.code405.repository.models;

import com.code405.entity.model.Timetable;

import java.util.List;

/**
 * Created by Peter Kozlovsky on 28.05.17.
 */
public interface TimetableRepositoryCustom {
    List<Timetable> findByAVeryComplicatedQuery();
}
