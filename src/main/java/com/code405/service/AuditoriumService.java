package com.code405.service;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.Week;
import com.code405.entity.enumeration.WeekDay;
import com.code405.entity.model.Auditorium;
import com.code405.entity.util.Select;
import com.code405.repository.models.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Kozlovsky on 28.05.17.
 */
@Service
public class AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public List<Select> getAuditoriumsByBuilding(Long building) {
        List<Auditorium> byBuildingId = auditoriumRepository.findByBuildingId(building);
        List<Select> auditoriumList = new ArrayList<>();
        for (Auditorium auditorium : byBuildingId) {
            auditoriumList.add(new Select(false, auditorium.getId().toString(), auditorium.getNumber().toString(), true));
        }
        return auditoriumList;
    }

    public List<Auditorium> getFreeAuditoriums(WeekDay day, Class time) {
        return auditoriumRepository.findAllFreeAuditories(time.name(), day.name(), Week.getWeek().getType());
    }

}
