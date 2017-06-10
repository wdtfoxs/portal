package com.code405.service;

import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.*;
import com.code405.entity.model.*;
import com.code405.entity.util.Select;
import com.code405.repository.models.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final BuildingRepository buildingRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplineRepository disciplineRepository;
    private final MappingJackson2HttpMessageConverter converter;
    private final GroupRepository groupRepository;

    @Autowired
    public TimetableService(TimetableRepository timetableRepository, BuildingRepository buildingRepository, AuditoriumRepository auditoriumRepository, ProfessorRepository professorRepository, DisciplineRepository disciplineRepository, MappingJackson2HttpMessageConverter converter, GroupRepository groupRepository) {
        this.timetableRepository = timetableRepository;
        this.buildingRepository = buildingRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.professorRepository = professorRepository;
        this.disciplineRepository = disciplineRepository;
        this.converter = converter;
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<WeekDay, Map<Class, Timetable>> findWeekTimetableForStudent(Group group) {
        List<Timetable> lessons = timetableRepository.findAllByGroup(group);
        Map<WeekDay, Map<Class, Timetable>> timetable = generateEmptyWeekTimetable();
        feelTimetable(timetable, lessons);
        return timetable;
    }

    public List<Building> getBuildings() {
        return buildingRepository.findAll(new Sort(Sort.Direction.ASC, "name")).stream().map(building -> {
                    building.setAuditoriums(null);
                    return building;
                }
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<WeekDay, Map<Class, Timetable>> findDayTimetableForStudent(Group group) {
        WeekDay presentWeekDay = getPresentWeekDay();
        List<Timetable> lessons = timetableRepository.findAllByGroupAndDay(group, presentWeekDay);
        Map<WeekDay, Map<Class, Timetable>> timetable = generateEmptyDayTimetable(presentWeekDay);
        feelTimetable(timetable, lessons);
        return timetable;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<WeekDay, Map<Class, Timetable>> findWeekTimetableForProfessor(Professor professor) {
        List<Timetable> lessons = timetableRepository.findAllByProfessor(professor);
        Map<WeekDay, Map<Class, Timetable>> timetable = generateEmptyWeekTimetable();
        feelTimetable(timetable, lessons);
        return timetable;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<WeekDay, Map<Class, Timetable>> findDayTimetableForProfessor(Professor professor) {
        WeekDay presentWeekDay = getPresentWeekDay();
        List<Timetable> lessons = timetableRepository.findAllByProfessorAndDay(professor, presentWeekDay);
        Map<WeekDay, Map<Class, Timetable>> timetable = generateEmptyDayTimetable(presentWeekDay);
        feelTimetable(timetable, lessons);
        return timetable;
    }

    public String getWeekType() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date();
        cal.setTime(currentDate);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return (week % 2 == 0 ? "четная" : "нечетная");
    }

    private Map<WeekDay, Map<Class, Timetable>> generateEmptyWeekTimetable() {
        Map<WeekDay, Map<Class, Timetable>> timetable = new LinkedHashMap<>();
        for (int weekDay = 1; weekDay <= 6; weekDay++) {
            Map<Class, Timetable> classes = new LinkedHashMap<>();
            for (Class classNum : Class.values()) {
                classes.put(classNum, null);
            }
            timetable.put(WeekDay.getByValue(weekDay), classes);
        }
        return timetable;
    }

    private Map<WeekDay, Map<Class, Timetable>> generateEmptyDayTimetable(WeekDay weekDay) {
        Map<WeekDay, Map<Class, Timetable>> timetable = new LinkedHashMap<>();
        Map<Class, Timetable> classes = new LinkedHashMap<>();
        for (Class classNum : Class.values()) {
            classes.put(classNum, null);
        }
        timetable.put(weekDay, classes);
        return timetable;
    }

    private void feelTimetable(Map<WeekDay, Map<Class, Timetable>> timetable, List<Timetable> timetables) {
        timetables.forEach(timetableData -> {
            Map<Class, Timetable> classTimetableDTOMap = timetable.get(timetableData.getDay());
            classTimetableDTOMap.put(timetableData.getClassNum(), timetableData);
        });
    }

    private WeekDay getPresentWeekDay() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return WeekDay.getByValue(dayOfWeek);
    }

    public Map<WeekDay, Map<Class, Map<String, List<Select>>>> getTimetableForEmployeeByGroup(String group) {
        List<Timetable> timetableUnits = timetableRepository.findByGroupGroupCode(group);
        List<Building> buildings = buildingRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
        List<Discipline> disciplines = disciplineRepository.findBySemesterOrSemester(Semester.getCurrentSemester(), Semester.EVERY);
        Map<WeekDay, Map<Class, Map<String, List<Select>>>> timetable = new LinkedHashMap<>();
        for (int weekDay = 1; weekDay <= 6; weekDay++) {
            Map<Class, Map<String, List<Select>>> classes = new LinkedHashMap<>();
            for (Class classNum : Class.values()) {
                Map<String, List<Select>> oneTimetable = new LinkedHashMap<>();
                //Найти расписание  у определенной группы на определенный день на определенное время
                Timetable timetableUnit = findTimetableByWeekAndClass(timetableUnits, WeekDay.getByValue(weekDay), classNum); //timetableRepository.findByGroupGroupCodeAndDayAndClassNum(group, WeekDay.getByValue(weekDay), classNum);
                fillTypeSelect(timetableUnit, oneTimetable);

                //Заполнить селект с выбором здания, если расписание не null - сделать опр. активным
                Building buildingSelect = fillBuildingSelect(buildings, timetableUnit, oneTimetable);
                //Заполнить селект с выбором аудитории, заполнять только если селект со зданием не null
                fillAuditoriumSelect(timetableUnit, buildingSelect, oneTimetable);
                //Заполнить селект с выбором предметов, если не расписани не null - сделать опр. активным
                Discipline discipline = fillDisciplineSelect(disciplines, timetableUnit, oneTimetable);
                //Заполнить селект с преподами, если дисциплина не null и не физ-ра
                fillProfessorSelect(timetableUnit, oneTimetable, discipline);
                //Заполнить селект с неделей, если дисциплина не null - сделать опр. активным
                fillWeekSelect(timetableUnit, oneTimetable);
                //Заполнить комментарий, select = false
                fillComment(timetableUnit, oneTimetable);
                classes.put(classNum, oneTimetable);
            }
            timetable.put(WeekDay.getByValue(weekDay), classes);
        }
        return timetable;
    }

    private void fillTypeSelect(Timetable timetableUnit, Map<String, List<Select>> oneTimetable) {
        List<Select> typeSelect = new ArrayList<>();
        typeSelect.add(new Select(true, "ПРАКТИКА", "Практика", true));
        typeSelect.add(new Select(false, "ЛЕКЦИЯ", "Лекция", true));
        if (timetableUnit != null) {
            if (timetableUnit.getType() == Type.ЛЕКЦИЯ) {
                typeSelect.get(1).setSelected(true);
                typeSelect.get(0).setSelected(false);
            }
        }
        oneTimetable.put("types", typeSelect);
    }


    private Timetable findTimetableByWeekAndClass(List<Timetable> timetableUnits, WeekDay byValue, Class classNum) {
        for (Timetable timetable : timetableUnits) {
            if (timetable.getDay() == byValue && timetable.getClassNum() == classNum) {
                return timetable;
            }
        }
        return null;
    }

    private void fillComment(Timetable timetableUnit, Map<String, List<Select>> oneTimetable) {
        Select select = new Select(false, "", "", false);
        if (timetableUnit != null) {
            select.setText(timetableUnit.getComment());
        }
        oneTimetable.put("comment", Collections.singletonList(select));
    }

    private void fillWeekSelect(Timetable timetableUnit, Map<String, List<Select>> oneTimetable) {
        List<Select> weekSelect = new ArrayList<>();
        Select initialSelect = new Select(true, "", "", true);
        weekSelect.add(initialSelect);
        for (Week week : Week.values()) {
            Select select = new Select(false, week.getType(), week.getType(), true);
            if (timetableUnit != null && timetableUnit.getWeek().equals(week)) {
                select.setSelected(true);
                initialSelect.setSelected(false);
            }
            weekSelect.add(select);
        }
        oneTimetable.put("weeks", weekSelect);
    }

    private Discipline fillDisciplineSelect(List<Discipline> disciplines, Timetable timetableUnit, Map<String, List<Select>> oneTimetable) {
        List<Select> disciplineList = new ArrayList<>();
        Select initialSelect = new Select(true, "", "", true);
        disciplineList.add(initialSelect);
        Discipline toReturn = null;
        for (Discipline discipline : disciplines) {
            Select select = new Select(false, discipline.getId().toString(), discipline.getDiscipline(), true);
            if (timetableUnit != null
                    && discipline.getId().equals(timetableUnit.getDiscipline().getId())) {
                select.setSelected(true);
                initialSelect.setSelected(false);
                toReturn = discipline;
            }
            disciplineList.add(select);
        }
        oneTimetable.put("disciplines", disciplineList);
        return toReturn;
    }

    private void fillProfessorSelect(Timetable timetableUnit, Map<String, List<Select>> oneTimetable, Discipline discipline) {
        List<Select> professorList = null;
        if (discipline != null && !discipline.isPhysicalCulture()) {
            List<Professor> professors = discipline.getProfessors();
            professorList = new ArrayList<>();
            Select initialSelect = new Select(true, "", "", true);
            professorList.add(initialSelect);
            for (Professor professor : professors) {
                Select select = new Select(false, professor.getId().toString(), professor.getSurname() + " " + professor.getName(), true);
                if (professor.getId().equals(timetableUnit.getProfessor().getId())) {
                    select.setSelected(true);
                    initialSelect.setSelected(false);
                }
                professorList.add(select);
            }
        }
        oneTimetable.put("professors", professorList);
    }

    private Building fillBuildingSelect(List<Building> buildings, Timetable timetableUnit, Map<String, List<Select>> oneTimetable) {
        List<Select> buildingList = new ArrayList<>();
        Select initialSelect = new Select(true, "", "", true);
        Building selectedBuilding = null;
        buildingList.add(initialSelect);
        for (Building building : buildings) {
            Select select = new Select(false, building.getId().toString(), building.getName(), true);
            if (timetableUnit != null && !timetableUnit.getDiscipline().isPhysicalCulture() && building.getId().equals(timetableUnit.getAuditorium().getBuilding().getId())) {
                selectedBuilding = building;
                select.setSelected(true);
                initialSelect.setSelected(false);
            }
            buildingList.add(select);
        }
        oneTimetable.put("buildings", buildingList);
        return selectedBuilding;
    }

    private void fillAuditoriumSelect(Timetable timetableUnit, Building buildingSelect, Map<String, List<Select>> oneTimetable) {
        List<Select> auditoriumSelect = null;
        if (buildingSelect != null) {
            auditoriumSelect = new ArrayList<>();
            Select initialSelect = new Select(false, "", "", true);
            auditoriumSelect.add(initialSelect);
            List<Auditorium> auditoriums = buildingSelect.getAuditoriums();
            for (Auditorium auditorium : auditoriums) {
                Select select = new Select(false, auditorium.getId().toString(), auditorium.getNumber().toString(), true);
                if (auditorium.getId().equals(timetableUnit.getAuditorium().getId())) {
                    select.setSelected(true);
                }
                auditoriumSelect.add(select);
            }
        }
        oneTimetable.put("auditoriums", auditoriumSelect);
    }


    public boolean findTimetableBy(String classQ, String week, String groupCode, Long auditorium) {
        return timetableRepository.findByDayAndClassNumAndAuditoriumIdAndGroupGroupCodeIsNot(WeekDay.valueOf(week), Class.valueOf(classQ), auditorium, groupCode) != null;
    }

    public boolean checkTimetable(String week, String day, String groupCode, Long professor, String classQ) {
        Week weekEnum = Week.valueOf(week);
        WeekDay weekDay = WeekDay.valueOf(day);
        Class classEnum = Class.valueOf(classQ);
        return timetableRepository.findByWeekAndDayAndGroupGroupCodeIsNotAndProfessorIdAndClassNum(Week.Каждая, weekDay, groupCode, professor, classEnum) != null || timetableRepository.findByWeekAndDayAndGroupGroupCodeIsNotAndProfessorIdAndClassNum(weekEnum, weekDay, groupCode, professor, classEnum) != null;
    }

    @Transactional(rollbackFor = IOException.class)
    public boolean saveTimetable(String timetables, String groupCode) throws IOException {
        ObjectMapper objectMapper = converter.getObjectMapper();
        JsonNode node = objectMapper.readTree(timetables);
        List<Timetable> timetableList = new ArrayList<>();
        Group group = groupRepository.findOne(groupCode);
        for (JsonNode timetable : node) {

            Discipline discipline = disciplineRepository.findOne(timetable.get("discipline").asLong());
            Week week = Week.valueOf(timetable.get("week").asText());
            Class classQ = Class.valueOf(timetable.get("classNum").asText());
            WeekDay weekDay = WeekDay.valueOf(timetable.get("day").asText());
            String comment = timetable.get("comment").asText();
            boolean culture = timetable.get("culture").asBoolean();
            Type type = Type.valueOf(timetable.get("type").asText());
            Professor professor = null;
            Auditorium auditorium = null;
            if (!culture) {
                professor = professorRepository.findOne(timetable.get("professor").asLong());
                auditorium = auditoriumRepository.findOne(timetable.get("auditorium").asLong());
            }
            Timetable unit = Timetable.builder()
                    .auditorium(auditorium)
                    .comment(comment)
                    .discipline(discipline)
                    .professor(professor)
                    .group(group)
                    .week(week)
                    .classNum(classQ)
                    .type(type)
                    .day(weekDay)
                    .build();
            timetableList.add(unit);
        }
        timetableRepository.deleteByGroup(group);
        timetableRepository.save(timetableList);
        return true;

    }

    public Map<String, List<Discipline>> getGroupsAndDisciplinesForProfessor(Professor professor) {
        List<Timetable> timetables = timetableRepository.findGroupsForProfessor(professor);
        Map<String, List<Discipline>> structure = new LinkedHashMap<>();
        List<Discipline> disciplines;
        for (Timetable t : timetables) {
            if (!structure.containsKey(t.getGroup().getGroupCode()))
                disciplines = new ArrayList<>();
            else
                disciplines = structure.get(t.getGroup().getGroupCode());
            disciplines.add(t.getDiscipline());
            structure.put(t.getGroup().getGroupCode(), disciplines);
        }
        return structure;
    }
}
