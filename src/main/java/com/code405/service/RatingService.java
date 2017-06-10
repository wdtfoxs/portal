package com.code405.service;

import com.code405.entity.embeddable.Time;
import com.code405.entity.model.*;
import com.code405.repository.models.DayBookRepository;
import com.code405.repository.models.ExamRepository;
import com.code405.repository.models.ExamResultRepository;
import com.code405.repository.models.TimetableRepository;
import com.code405.web.dto.RatingDTO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final TimetableRepository timetableRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamRepository examRepository;
    private final DayBookRepository dayBookRepository;

    @Autowired
    public RatingService(
            TimetableRepository timetableRepository,
            ExamResultRepository examResultRepository,
            ExamRepository examRepository,
            DayBookRepository dayBookRepository
    ) {
        this.timetableRepository = timetableRepository;
        this.examResultRepository = examResultRepository;
        this.examRepository = examRepository;
        this.dayBookRepository = dayBookRepository;
    }

    public List<RatingDTO> getRatingForStudent(Student student, Time timeForCourse) {
        List<Exam> courseExams = examRepository.
                findExamsByGroupAndTime_FromBetweenOrderByTime_fromAsc(student.getGroup(), timeForCourse.getFrom(), timeForCourse.getTo());

        return courseExams.stream()
                .map(exam -> {
                    Discipline discipline = exam.getDiscipline();

                    Integer examPoints = Optional.ofNullable(examResultRepository.findExamResultByExamAndStudent(exam, student))
                            .map(ExamResult::getPoints)
                            .orElse(0);

                    Integer semesterPoints =
                            Optional.ofNullable(
                                    dayBookRepository.findDayBooksByStudentAndDisciplineAndDateBetween(
                                            student, discipline, timeForCourse.getFrom(), timeForCourse.getTo())
                            )
                                    .orElseGet(Lists::newArrayList)
                                    .stream()
                                    .mapToInt(DayBook::getPoints)
                                    .sum();

                    Double avgPointsGroup = dayBookRepository.selectAvgPointsForGroup(
                            discipline.getId(), student.getGroup().getGroupCode(), timeForCourse.getFrom(), timeForCourse.getTo()
                    );
                    Double avgPointsForCourse = dayBookRepository.selectAvgPointsForCourse(
                            discipline.getId(), timeForCourse.getFrom(), timeForCourse.getTo()
                    );

                    return RatingDTO.builder()
                            .examPoints(examPoints)
                            .discipline(discipline)
                            .passTime(exam.getTime())
                            .semesterPoints(semesterPoints)
                            .totalPoints(examPoints + semesterPoints)
                            .averageGroupPoints(Optional.ofNullable(avgPointsGroup).orElse(0.0))
                            .averageCoursePoints(Optional.ofNullable(avgPointsForCourse).orElse(0.0))
                            .build();

                }).collect(Collectors.toList());
    }

    public Double getStudentPlaceInGroup(Student student, Time timeForCourse) {
        return dayBookRepository.getStudentPlaceInGroup(
                student.getId(), student.getGroup().getGroupCode(), timeForCourse.getFrom(), timeForCourse.getTo()
        );
    }

    public Double getStudentPlaceInCourse(Student student, Time timeForCourse) {
        return dayBookRepository.getStudentPlaceInCourse(student.getId(), timeForCourse.getFrom(), timeForCourse.getTo());
    }

}
