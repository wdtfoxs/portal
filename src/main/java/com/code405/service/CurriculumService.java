package com.code405.service;

import com.code405.entity.enumeration.Course;
import com.code405.entity.enumeration.PassType;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Curriculum;
import com.code405.entity.model.Discipline;
import com.code405.entity.model.Group;
import com.code405.repository.models.CurriculumRepository;
import com.code405.repository.models.DisciplineRepository;
import com.code405.repository.models.GroupRepository;
import com.code405.web.dto.CurriculumDTO;
import com.code405.web.dto.CurriculumView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final DisciplineRepository disciplineRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public CurriculumService(CurriculumRepository curriculumRepository, DisciplineRepository disciplineRepository, GroupRepository groupRepository) {
        this.curriculumRepository = curriculumRepository;
        this.disciplineRepository = disciplineRepository;
        this.groupRepository = groupRepository;

    }

    public List<CurriculumView> getCurriculum(Group group) {
        List<Curriculum> curriculum =
                curriculumRepository.findAllByGroupAndCourse(group, Course.getCourse(new Timestamp(group.getCreated().getTime())));
        return curriculum.stream()
                .collect(Collectors.groupingBy(c -> c.getDiscipline().getDiscipline()))
                .entrySet().stream()
                .map(this::convertToView)
                .collect(Collectors.toList());
    }

    private CurriculumView convertToView(Map.Entry<String, List<Curriculum>> entry) {
        List<Curriculum> curriculums = entry.getValue();
        if (curriculums.size() == 1) {
            Curriculum oneSemester = curriculums.get(0);
            Discipline discipline = oneSemester.getDiscipline();
            if (discipline.getSemester().equals(Semester.FIRST)) {
                return new CurriculumView(
                        discipline.getDiscipline(),
                        CurriculumDTO.convert(oneSemester),
                        null,
                        getTotalHours(oneSemester)
                );
            } else {
                return new CurriculumView(
                        discipline.getDiscipline(),
                        null,
                        CurriculumDTO.convert(oneSemester),
                        getTotalHours(oneSemester));
            }
        } else {
            Curriculum some1 = curriculums.get(0);
            Curriculum some2 = curriculums.get(1);
            Discipline discipline = some1.getDiscipline();
            if (discipline.getSemester().equals(Semester.FIRST)) {
                return new CurriculumView(
                        discipline.getDiscipline(),
                        CurriculumDTO.convert(some1),
                        CurriculumDTO.convert(some2),
                        getTotalHours(some1, some2));
            } else {
                return new CurriculumView(
                        discipline.getDiscipline(),
                        CurriculumDTO.convert(some2),
                        CurriculumDTO.convert(some1),
                        getTotalHours(some1, some2)
                );
            }
        }
    }

    private Integer getTotalHours(Curriculum curriculum1, Curriculum curriculum2) {
        return getTotalHours(curriculum1) + getTotalHours(curriculum2);
    }

    private Integer getTotalHours(Curriculum curriculum) {
        return curriculum.getLabAmount() + curriculum.getLectureAmount() + curriculum.getPracticeAmount();
    }

    @Transactional
    public void saveOrUpdate(Integer course, String groupCode, MultipartFile file) throws Exception {
        Course courseEnum = Course.getCourse(course);
        Group one = groupRepository.findOne(groupCode);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()), 32768)) {
            List<Curriculum> curriculumList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                Integer lectAmount = Math.abs(Integer.valueOf(split[0])) > 150 ? 150 : Math.abs(Integer.valueOf(split[0]));
                Integer practAmount =  Math.abs(Integer.valueOf(split[1])) > 150 ? 150 : Math.abs(Integer.valueOf(split[1]));
                Integer labAmount =  Math.abs(Integer.valueOf(split[2])) > 150 ? 150 : Math.abs(Integer.valueOf(split[2]));
                String disciplineName = split[3];
                PassType passType = PassType.valueOf(split[4]);
                Semester semester = Semester.valueOf(Integer.valueOf(split[5]));
                Discipline discipline = disciplineRepository.findByDisciplineAndSemester(disciplineName, semester);
                if (discipline != null) {
                    curriculumList.add(Curriculum.builder()
                            .course(courseEnum)
                            .labAmount(labAmount)
                            .lectureAmount(lectAmount)
                            .practiceAmount(practAmount)
                            .passType(passType)
                            .group(one)
                            .discipline(discipline).build());
                }
            }
            curriculumRepository.deleteByCourseAndGroup(course.toString(), groupCode);
            curriculumRepository.save(curriculumList);
        }
    }
}
