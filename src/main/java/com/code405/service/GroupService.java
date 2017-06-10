package com.code405.service;

import com.code405.entity.model.Group;
import com.code405.repository.models.GroupRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Peter Kozlovsky on 27.05.17.
 */
@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @SneakyThrows
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> getGroupsByCourse(int course) {
        LocalDate localDate = LocalDate.now();
        int currentYear = localDate.getMonthValue() < 9 ? localDate.getYear() - 1 : localDate.getYear();
        int yearOfGroupCreated = currentYear - course + 1;
        List<Group> byCreated = groupRepository.findByCreated(format.parse("01.09." + yearOfGroupCreated), new Sort(Sort.Direction.ASC, "groupCode"));
        byCreated.forEach((x)->x.setStudents(null));
        return byCreated;
    }
}
