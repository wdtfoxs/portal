package com.code405.service;

import com.code405.entity.embeddable.Time;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Exam;
import com.code405.entity.model.Professor;
import com.code405.entity.model.Student;
import com.code405.repository.models.ExamRepository;
import com.code405.repository.models.ExamResultRepository;
import com.code405.repository.models.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * Created by hajrullinbulat on 30.05.2017.
 */
@Service
public class ExamsService {
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final ExamResultRepository examResultRepository;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    public ExamsService(ExamRepository examRepository, StudentRepository studentRepository, ExamResultRepository examResultRepository) {
        this.examRepository = examRepository;

        this.studentRepository = studentRepository;

        this.examResultRepository = examResultRepository;
    }

    public List<Exam> getExams(Student student) {
        Time timeForCurrentSemester = Semester.getTimeForCurrentSemester();
        return examRepository.findExamsByGroupAndTime_FromBetweenOrderByTime_fromAsc(student.getGroup(), timeForCurrentSemester.getFrom(), timeForCurrentSemester.getTo());
    }

    public List<Exam> getExams(Professor professor) {
        Time timeForCurrentSemester = Semester.getTimeForCurrentSemester();
        return examRepository.findExamsByProfessorAndTime_FromBetweenOrderByTime_fromAsc(professor, timeForCurrentSemester.getFrom(), timeForCurrentSemester.getTo());
    }

    public List<Exam> getExams(String groupCode) {
        Time timeForCurrentSemester = Semester.getTimeForCurrentSemester();
        return examRepository.findExamsByGroupGroupCodeAndTime_FromBetweenOrderByTime_fromAsc(groupCode, timeForCurrentSemester.getFrom(), timeForCurrentSemester.getTo());
    }

    @SneakyThrows
    @Transactional
    public String saveExam(String createDate,
                           String beginningTime,
                           String endTime,
                           String type,
                           Long auditorium,
                           Long discipline,
                           Long professor,
                           String groupCode) {
        Timestamp begin = new Timestamp(format.parse(createDate + " " + beginningTime).getTime());
        Timestamp end = new Timestamp(format.parse(createDate + " " + endTime).getTime());
        Time timeForCurrentSemester = Semester.getTimeForCurrentSemester();
        if (!(begin.after(timeForCurrentSemester.getFrom()) && begin.before(timeForCurrentSemester.getTo())
                && end.after(timeForCurrentSemester.getFrom()) && end.before(timeForCurrentSemester.getTo()))) {
            return "Укажите валидное время для текущего семестра.\n" +
                    "Первый: 01.09 - 31.01. Второй: 01.02 - 31.08";
        }
        List<Exam> examsAndAuditoriumId = examRepository.findExamsByAuditoriumIdOrProfessorIdOrDisciplineId(auditorium, professor, discipline);

        for (Exam exam : examsAndAuditoriumId) {
            if (Objects.equals(discipline, exam.getDiscipline().getId()) && exam.getGroup().getGroupCode().equals(groupCode)) {
                return "У данной группы уже есть экзамен по данному предмету.";
            }
            Time time = exam.getTime();
            if (!exam.getDiscipline().isPhysicalCulture() &&
                    ((begin.compareTo(time.getFrom()) >= 0 && begin.compareTo(time.getTo()) <= 0) || (end.compareTo(time.getFrom()) >= 0 && end.compareTo(time.getTo()) <= 0))) {
                if (Objects.equals(exam.getAuditorium().getId(), auditorium)) {
                    return "В данное время в данной аудитории проводится экзамен у другой группы.";
                } else {
                    return "В данное время у преподавателя экзамен у другой группы.";
                }
            }
        }
        Long sequence = examRepository.getSequence();
        if (professor != -1) {
            examRepository.insert(sequence, type, begin, end, auditorium, discipline, groupCode, professor);
        } else {
            examRepository.insertPhysical(sequence, type, begin, end, discipline, groupCode);
        }

        List<BigInteger> allIds = studentRepository.findAllIds(groupCode);
        for (BigInteger id : allIds) {
            Long examResId = examRepository.getSequence();
            examResultRepository.insert(examResId, 0, sequence, id.longValueExact());
        }
        return "Добавлено";
    }

    @SneakyThrows
    public String updateExam(String createDate, String beginningTime, String endTime, Long id) {
        Timestamp begin = new Timestamp(format.parse(createDate + " " + beginningTime).getTime());
        Timestamp end = new Timestamp(format.parse(createDate + " " + endTime).getTime());
        Time timeForCurrentSemester = Semester.getTimeForCurrentSemester();
        if (!(begin.after(timeForCurrentSemester.getFrom()) && begin.before(timeForCurrentSemester.getTo())
                && end.after(timeForCurrentSemester.getFrom()) && end.before(timeForCurrentSemester.getTo()))) {
            return "Укажите валидное время для текущего семестра.\n" +
                    "Первый: 01.09 - 31.01. Второй: 01.02 - 31.08";
        }
        Exam one = examRepository.findOne(id);
        List<Exam> examsByTimeFromBetween = examRepository.findExamsByTimeFromBetween(timeForCurrentSemester.getFrom(), timeForCurrentSemester.getTo());
        for(Exam exam : examsByTimeFromBetween){
            if(!Objects.equals(one.getId(), exam.getId())){
                if (!exam.getDiscipline().isPhysicalCulture() &&
                        ((begin.compareTo(exam.getTime().getFrom()) >= 0 && begin.compareTo(exam.getTime().getTo()) <= 0) || (end.compareTo(exam.getTime().getFrom()) >= 0 && end.compareTo(exam.getTime().getTo()) <= 0))) {
                    if (Objects.equals(exam.getAuditorium().getId(), one.getAuditorium().getId())) {
                        return "В данное время в данной аудитории проводится экзамен у другой группы.";
                    }
                    if (Objects.equals(exam.getProfessor().getId(), one.getProfessor().getId())){
                        return "В данное время у преподавателя экзамен у другой группы.";
                    }
                }
            }
        }
        Time time = one.getTime();
        time.setFrom(begin);
        time.setTo(end);
        examRepository.save(one);
        return "Изменено";
    }
}
