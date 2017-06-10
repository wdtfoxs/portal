package com.code405.service;

import com.code405.entity.enumeration.ReferenceType;
import com.code405.entity.enumeration.Status;
import com.code405.entity.model.Reference;
import com.code405.entity.model.Student;
import com.code405.repository.models.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdtfoxs on 17.05.2017.
 */
@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Reference> getListForCollaborator() {
        return referenceRepository.findByStatusOrderByCreatedDesc(Status.CREATED);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Reference> getListByStudent(Student student) {
        return referenceRepository.getAllByStudentOrderByCreatedDesc(student);
    }

    @Transactional
    public void save(Reference reference) {
        referenceRepository.save(reference);
    }

    public List<Reference> orderDocument(Integer count, Integer type, Student student) {
        List<Reference> list = new ArrayList<>();
        Reference reference;
        for (int i = 0; i < count; i++) {
            reference = new Reference();
            reference.setStatus(Status.CREATED);
            reference.setStudent(student);
            reference.setType(defineTypeOfDocument(type));
            reference.setCreated(Timestamp.valueOf(LocalDateTime.now(Clock.system(ZoneId.of("Europe/Moscow")))));
            this.save(reference);
//            reference.setStudent(null);
            list.add(reference);
        }
        return list;
    }

    @Transactional
    public void updateStatus(Long id, Integer status) {
        referenceRepository.updateStatusOfDocument(id, defineStatusOfDocument(status));
    }

    private ReferenceType defineTypeOfDocument(Integer type) {
        ReferenceType temp;
        switch (type) {
            case 1:
                temp = ReferenceType.ABOUT_STUDING;
                break;
            case 2:
                temp = ReferenceType.TO_WORK;
                break;
            default:
                temp = ReferenceType.ABOUT_STUDING;
                break;
        }
        return temp;
    }

    private Status defineStatusOfDocument(Integer status) {
        Status temp;
        switch (status) {
            case 1:
                temp = Status.READY;
                break;
            case 2:
                temp = Status.CREATED;
                break;
            default:
                temp = Status.CREATED;
                break;
        }
        return temp;
    }
}
