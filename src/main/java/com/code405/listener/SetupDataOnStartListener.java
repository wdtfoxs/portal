package com.code405.listener;

import com.code405.entity.embeddable.Address;
import com.code405.entity.embeddable.Time;
import com.code405.entity.enumeration.Class;
import com.code405.entity.enumeration.*;
import com.code405.entity.model.*;
import com.code405.repository.models.*;
import com.code405.service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Peter Kozlovsky on 14.05.17.
 */
@Component
public class SetupDataOnStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private final static String SUPER_TEXT = "  \n" +
            "В ходе этой вступительной лекции профессор Эйнштейн рассматривает состояние теории относительности. Профессор Эйнштейн обсудит деятельность с Николой Теслой.\n" +
            "                    <p><strong class=\"dark-text\">Спикеры:</strong> Альберт Эйнштейн, Никола Тесла </p>\n" +
            "                    <p><strong class=\"dark-text\">Место: Казань, КФУ </strong></p>\n" +
            "                    <p>Эйнштейн -   физик-теоретик, один из основателей современной теоретической физики, лауреат Нобелевской премии по физике 1921 года, общественный деятель-гуманист. Жил в Германии (1879—1893, 1914—1933), Швейцарии (1893—1914) и США (1933—1955). Почётный доктор около 20 ведущих университетов мира, член многих Академий наук, в том числе иностранный почётный член АН СССР (1926).\n" +
            "\n</p>\n" +
            "                 ";
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private final EventRepository eventRepository;
    private final NewsRepository newsRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final CurriculumRepository curriculumRepository;
    private final AdminService adminService;
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final TimetableRepository timetableRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final DisciplineRepository disciplineRepository;
    private final ProfessorService professorService;
    private final BuildingRepository buildingRepository;
    private final RoleRepository roleRepository;
    private final DayBookRepository dayBookRepository;
    private final ReferenceService referenceService;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final PositionRepository positionRepository;
    private volatile boolean isAlreadySetup = false;

    @Autowired
    public SetupDataOnStartListener(CurriculumRepository curriculumRepository,
                                    TimetableRepository timetableRepository,
                                    AuditoriumRepository auditoriumRepository,
                                    DisciplineRepository disciplineRepository,
                                    BuildingRepository buildingRepository,
                                    RoleRepository roleRepository,
                                    EventRepository eventRepository,
                                    NewsRepository newsRepository,
                                    StudentRepository studentRepository,
                                    DayBookRepository dayBookRepository,
                                    ExamRepository examRepository,
                                    ExamResultRepository examResultRepository,
                                    GroupRepository groupRepository,
                                    StudentService studentService,
                                    EmployeeService employeeService,
                                    ProfessorService professorService,
                                    ReferenceService referenceService,
                                    PositionRepository positionRepository,
                                    AdminService adminService) {
        this.curriculumRepository = curriculumRepository;
        this.timetableRepository = timetableRepository;
        this.adminService = adminService;
        this.auditoriumRepository = auditoriumRepository;
        this.disciplineRepository = disciplineRepository;
        this.employeeService = employeeService;
        this.studentRepository = studentRepository;
        this.professorService = professorService;
        this.buildingRepository = buildingRepository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
        this.newsRepository = newsRepository;
        this.groupRepository = groupRepository;
        this.studentService = studentService;
        this.dayBookRepository = dayBookRepository;
        this.referenceService = referenceService;
        this.positionRepository = positionRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent e) {
        if (isAlreadySetup) {
            return;
        }
        //загоняем роли
        for (RoleEnumeration roleEnumeration : RoleEnumeration.values()) {
            roleRepository.save(new Role(roleEnumeration));
        }

        Event event = Event.builder()
                .title("День открытых дверей магистратуры Высшей школы ИТИС КФУ!")
                .eventDate(Timestamp.valueOf(LocalDateTime.of(2017, 6, 9, 16, 00)))
                .text("Если ты:\n" +
                        "✒Хочешь изучать IT;\n" +
                        "✒Скоро получишь диплом бакалавра и планируешь продолжать обучение;\n" +
                        "✒Хочешь получить степень магистра в #ИТИС #КФУ\n" +
                        "\n" +
                        "То мы ждем именно тебя 9 июня в 16.00 на Дне открытых дверей магистратуры Высшей школы ИТИС КФУ!\n" +
                        "Мы ответим на все вопросы:\n" +
                        "✏о поступлении;\n" +
                        "✏о направлениях обучения;\n" +
                        "✏о твоих возможностях!\n" +
                        "\n" +
                        "Интересно? Регистрируйся: https://itiskfu.timepad.ru/event/503503/")
                .time(new Time(Timestamp.valueOf(LocalDateTime.of(2017, 5, 9, 16, 00)), Timestamp.valueOf(LocalDateTime.of(2017, 6, 9, 17, 30))))
                .image("/resources/image/open_day.jpg").build();
        eventRepository.save(event);

        Event event2 = Event.builder()
                .title("Круглый стол \"МЫ СВЯЗАНЫ С ТОБОЙ,РОССИЯ\"")
                .eventDate(Timestamp.valueOf(LocalDateTime.of(2017, 6, 8, 12, 00)))
                .text("Уважаемые студенты!\n" +
                        "8 июня 2017 года на базе Института психологии и образования состоится Круглый стол \"МЫ СВЯЗАНЫ С ТОБОЙ,РОССИЯ\". \n" +
                        "Приглашаем Вас к участию в этом мероприятии.\n" +
                        "\n" +
                        "Количество мест ограничено, поэтому необходима предварительная регистрация https://docs.google.com/forms/d/e/1FAIpQLSfO88Ue-EWdZ..\n" +
                        "\n" +
                        "Специальной подготовки не требуется. Выступать не придется, однако, интересные вопросы приветствуются.")
                .time(new Time(Timestamp.valueOf(LocalDateTime.of(2017, 6, 8, 12, 00)), Timestamp.valueOf(LocalDateTime.of(2017, 6, 8, 14, 00))))
                .image("/resources/image/table.jpg").build();
        eventRepository.save(event2);

        Event event3 = Event.builder()
                .title("Общеуниверситетская Ярмарка вакансий")
                .eventDate(Timestamp.valueOf(LocalDateTime.of(2017, 5, 24, 15, 00)))
                .text("ВНИМАНИЕ!!! Приглашаем обучающихся последних курсов посетить общеуниверситетскую Ярмарку вакансий, которая состоится \n" +
                        "24 мая 2017 года \n" +
                        "2 – ой учебный корпус (Кремлевская 35), мраморный зал с 10:00 до 14:00")
                .time(new Time(Timestamp.valueOf(LocalDateTime.of(2017, 5, 24, 15, 00)), Timestamp.valueOf(LocalDateTime.of(2017, 5, 24, 17, 00))))
                .image("/resources/image/university.jpg").build();
        eventRepository.save(event3);

        News news = News.builder()
                .text("Дорогие студенты!\n" +
                        "Открыт прием заявок в федеральный акселератор Phil.Tech для технологических решений в области филантропии. \n" +
                        "\n" +
                        "Цель программы — создать продукты и технологии для более эффективной и прозрачной работы некоммерческих организаций: благотворительных фондов, программ корпоративной социальной ответственности, социальных предпринимателей, гражданских активистов и волонтеров. Организаторы программы: Рыбаков Фонд и Бизнес-инкубатор НИУ ВШЭ.\n" +
                        "\n" +
                        "Программа стартует в июле в Москве, Санкт-Петербурге и Казани. Финальный интенсив пройдет в столице в октябре. К участию приглашаются самостоятельные профессионалы и готовые команды.\n" +
                        "Прием заявок открыт до 19 июня на сайте http://go.philtech.ru/")
                .title("Федеральный акселератор Phil")
                .image("/resources/image/phil.jpg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 6, 1, 16, 26)))
                .build();
        newsRepository.save(news);

        News news2 = News.builder()
                .text("Фарид Абдулганиев поддержал проект Высшей школы #ИТИС и Института филологии и межкультурных коммуникаций им. Л.Н.Толстого #КФУ II Всемирную онлайн контрольную по татарскому языку и литературе \"Barudi\"!\n")
                .title("Министр экологии и природных ресурсов РТ Фарид Абдулганиев принял участие в контрольной \"Barudi\"")
                .image("/resources/image/ministr.jpg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 5, 31, 19, 07)))
                .build();
        newsRepository.save(news2);

        News news3 = News.builder()
                .text("Студенты лаборатории E-Lab, Высшей школы #ИТИС #КФУ представили свои разработки Председателю Правительства РФ Дмитрию Медведеву!\n" +
                        "Какие проблемы ребята не побоялись поднять на встрече, и что им пообещал Премьер?\n" +
                        "Читайте на портале \"Казанский репортер\" http://kazanreporter.ru/post/2355_startapy_buduschego-_braslet_emociy_i_vydacha_kredita_cherez_social-nye_seti")
                .title("Разработки студентов ИТИС представили Дмитрию Медведеву")
                .image("/resources/image/med.jpg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 5, 31, 12, 24)))
                .build();
        newsRepository.save(news3);

        News news4 = News.builder()
                .text("Ксения Шабалина, студентка Высшей школы #ИТИС,заняла второе место в конкурсе лучших научных работ #КФУ!\n" +
                        "Поздравляем Ксению! Новых высот, побед и достижений!")
                .title("Студенты ИТИС снова лучшие!")
                .image("/resources/image/magid.jpg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 5, 31, 10, 48)))
                .build();
        newsRepository.save(news4);

        News news5 = News.builder()
                .text("Внимание, 4 курс. Пожалуйста, присылайте свои дипломные работы для проверки на антиплагиат на почту: evgsos@it.kfu.ru. Деканат")
                .title("Внимание, дипломники!")
                .image("/resources/image/diplom.jpg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 10, 38)))
                .build();
        newsRepository.save(news5);

        News news6 = News.builder()
                .text("Внимание, студенты 2 и 3 курса! В связи с тем, что в КФУ внедряется новая система проверки на антиплагиат, просим вас самостоятельно проверять свои курсовые работы через бесплатные сайты \"Антиплагиат\". Приносить свои работы вместе с отзывом научного руководителя и отчетами о проверке в деканат до 2 июня.")
                .title("Антиплагиат")
                .image("/resources/image/antiplagiat.jpeg")
                .created(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 9, 21)))
                .build();
        newsRepository.save(news6);

        Group group = Group.builder().groupCode("11-405").created(format.parse("01.09.2014")).build();
        group = groupRepository.save(group);
        Group group1 = Group.builder().groupCode("11-404").created(format.parse("01.09.2014")).build();
        group1 = groupRepository.save(group1);
        Group group2 = Group.builder().groupCode("11-501").created(format.parse("01.09.2015")).build();
        group2 = groupRepository.save(group2);
        Group group3 = Group.builder().groupCode("11-502").created(format.parse("01.09.2015")).build();
        group3 = groupRepository.save(group3);
        Set<Role> ROLES = Collections.singleton(roleRepository.findByName(RoleEnumeration.STUDENT));
        Student student = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5050@gmail.com").enabled(true).name("Петр").surname("Козловский").sex(true).username("PAKozlovskij")
                .msisdn("+79272433117").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(student, true);
        Student student2 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5051@gmail.com").enabled(true).name("Никита").surname("Прозоров").sex(true).username("NDProozorov")
                .msisdn("+79272433119").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(student2, true);
        Student stud1 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("amirkhanova@gmail.com").enabled(true).name("Айгуль").surname("Амирханова").sex(true).username("ARAmirchanova")
                .msisdn("89172365740").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud1, true);
        Student stud2 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("igor@gmail.com").enabled(true).name("Игорь").surname("Астафьев").sex(true).username("ISAstafev")
                .msisdn("89172365741").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud2, true);
        Student stud3 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("aupova@gmail.com").enabled(true).name("Эндже").surname("Аюпова").sex(true).username("ERAyupova")
                .msisdn("89172365742").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud3, true);
        Student stud4 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("karina@gmail.com").enabled(true).name("Карина").surname("Аязгулова").sex(true).username("KRAyazgulova")
                .msisdn("89172365743").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud4, true);
        Student stud5 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("regina@gmail.com").enabled(true).name("Регина").surname("Гилязова").sex(true).username("RegRGilyazova")
                .msisdn("89172365744").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud5, true);
        Student stud6 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("elena@gmail.com").enabled(true).name("Елена").surname("Караваева").sex(true).username("EMKaravaeva")
                .msisdn("89172365745").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud6, true);
        Student stud7 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("domer@gmail.com").enabled(true).name("Дамир").surname("Нургалиев").sex(true).username("DaRNurgaliev")
                .msisdn("89172365746").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud7, true);
        Student stud8 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("syren@gmail.com").enabled(true).name("Сурен").surname("Саакян").sex(true).username("SSSaakyan")
                .msisdn("89172365747").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud8, true);
        Student stud9 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("anton@gmail.com").enabled(true).name("Антон").surname("Сарматин").sex(true).username("AMSarmatin")
                .msisdn("89172365748").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud9, true);
        Student stud10 = Student.builder().group(group).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("maria@gmail.com").enabled(true).name("Мария").surname("Тимофеева").sex(true).username("MVTimofeeva")
                .msisdn("89172365749").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(stud10, true);

        Student s1 = Student.builder().group(group1).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5052@gmail.com").enabled(true).name("Алия").surname("Ахметова").sex(true).username("AAAchmetova")
                .msisdn("+79272433118").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(s1, true);
        Student s2 = Student.builder().group(group1).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5053@gmail.com").enabled(true).name("Дмитрий").surname("Бадыгин").sex(true).username("DDBadigin")
                .msisdn("+79272433120").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(s2, true);
        Student s3 = Student.builder().group(group1).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5058@gmail.com").enabled(true).name("Регина").surname("Шараева").sex(true).username("RRSharaeva")
                .msisdn("+79272433451").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(s3, true);
        Student s4 = Student.builder().group(group1).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5059@gmail.com").enabled(true).name("Максим").surname("Иванов").sex(true).username("MMIvanov")
                .msisdn("+79272453120").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(s4, true);
        Student s5 = Student.builder().group(group1).birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("birthright5060@gmail.com").enabled(true).name("Камилла").surname("Шагова").sex(true).username("KKShagova")
                .msisdn("+77672433120").password("1").roles(ROLES).admissionDate(Timestamp.valueOf(LocalDateTime.of(2014, 9, 1, 0, 0))).build();
        studentService.save(s5, true);


        HashSet<Student> group405 = new HashSet<>();
        group405.add(student);
        group405.add(student2);
        group405.add(stud1);
        group405.add(stud2);
        group405.add(stud3);
        group405.add(stud4);
        group405.add(stud5);
        group405.add(stud6);
        group405.add(stud7);
        group405.add(stud8);
        group405.add(stud9);
        group405.add(stud10);

        HashSet<Student> group404 = new HashSet<>();
        group404.add(s1);
        group404.add(s2);
        group404.add(s3);
        group404.add(s4);
        group404.add(s5);


        Discipline ки = Discipline.builder().discipline("Кратные интегралы").semester(Semester.FIRST).build();
        Discipline бжд = Discipline.builder().discipline("Безопасность жизнедеятельности").semester(Semester.FIRST).build();
        Discipline ос = Discipline.builder().discipline("Операционные системы").semester(Semester.FIRST).build();
        Discipline экономика = Discipline.builder().discipline("Экономика").semester(Semester.FIRST).build();
        Discipline пи1 = Discipline.builder().discipline("Программная инженерия").semester(Semester.FIRST).build();
        Discipline пис = Discipline.builder().discipline("Проектирование информационных систем").semester(Semester.FIRST).build();
        Discipline иб = Discipline.builder().discipline("Информационная безопасность").semester(Semester.FIRST).build();
        Discipline физика1 = Discipline.builder().discipline("Физика").semester(Semester.FIRST).build();

        Discipline правоведение = Discipline.builder().discipline("Правоведение").semester(Semester.SECOND).build();
        Discipline физика2 = Discipline.builder().discipline("Физика").semester(Semester.SECOND).build();
        Discipline пи2 = Discipline.builder().discipline("Программная инженерия").semester(Semester.SECOND).build();
        Discipline пп = Discipline.builder().discipline("Параллельное программирование").semester(Semester.SECOND).build();
        Discipline уп = Discipline.builder().discipline("Управление проектами").semester(Semester.SECOND).build();
        Discipline оп = Discipline.builder().discipline("Основы предпринимательства").semester(Semester.SECOND).build();

        Discipline физкультура = Discipline.builder().discipline("Физкультура").physicalCulture(true).semester(Semester.EVERY).build();

        физкультура = disciplineRepository.save(физкультура);
        ки = disciplineRepository.save(ки);
        бжд = disciplineRepository.save(бжд);
        ос = disciplineRepository.save(ос);
        экономика = disciplineRepository.save(экономика);
        пи1 = disciplineRepository.save(пи1);
        пис = disciplineRepository.save(пис);
        иб = disciplineRepository.save(иб);
        физика1 = disciplineRepository.save(физика1);
        правоведение = disciplineRepository.save(правоведение);
        физика2 = disciplineRepository.save(физика2);
        пи2 = disciplineRepository.save(пи2);
        пп = disciplineRepository.save(пп);
        уп = disciplineRepository.save(уп);
        оп = disciplineRepository.save(оп);

        HashSet<Discipline> d = new HashSet<>();
        d.add(ки);
        d.add(бжд);
        d.add(ос);
        d.add(экономика);
        d.add(пи1);
        d.add(пис);
        d.add(иб);
        d.add(физика1);
        d.add(правоведение);
        d.add(физика2);
        d.add(пи2);
        d.add(пп);
        d.add(уп);
        d.add(оп);

        HashSet<Discipline> сети = new HashSet<>();
        сети.add(ос);
        сети.add(иб);
        HashSet<Discipline> остальное = new HashSet<>();
        остальное.add(ки);


        Curriculum curriculum1 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(ки)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(18)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum2 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(бжд)
                .group(group)
                .labAmount(18)
                .lectureAmount(0)
                .practiceAmount(18)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum3 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(ос)
                .group(group)
                .labAmount(0)
                .lectureAmount(36)
                .practiceAmount(36)
                .passType(PassType.ЭКЗАМЕН)
                .build();
        Curriculum curriculum4 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(экономика)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(18)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum5 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(пи1)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(36)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum6 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(пис)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(36)
                .passType(PassType.ЭКЗАМЕН)
                .build();
        Curriculum curriculum7 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(иб)
                .group(group)
                .labAmount(36)
                .lectureAmount(36)
                .practiceAmount(0)
                .passType(PassType.ЭКЗАМЕН)
                .build();
        Curriculum curriculum8 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(физика1)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(36)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum9 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(правоведение)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(18)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum10 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(физика2)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(36)
                .passType(PassType.ДИФ_ЗАЧЕТ)
                .build();
        Curriculum curriculum11 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(пи2)
                .group(group)
                .labAmount(0)
                .lectureAmount(36)
                .practiceAmount(36)
                .passType(PassType.ЭКЗАМЕН)
                .build();
        Curriculum curriculum12 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(пп)
                .group(group)
                .labAmount(0)
                .lectureAmount(36)
                .practiceAmount(36)
                .passType(PassType.ЭКЗАМЕН)
                .build();
        Curriculum curriculum13 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(оп)
                .group(group)
                .labAmount(0)
                .lectureAmount(18)
                .practiceAmount(18)
                .passType(PassType.ЗАЧЕТ)
                .build();
        Curriculum curriculum14 = Curriculum.builder()
                .course(Course.ТРЕТИЙ)
                .discipline(уп)
                .group(group)
                .labAmount(0)
                .lectureAmount(36)
                .practiceAmount(18)
                .passType(PassType.ЭКЗАМЕН)
                .build();

        curriculumRepository.save(curriculum1);
        curriculumRepository.save(curriculum2);
        curriculumRepository.save(curriculum3);
        curriculumRepository.save(curriculum4);
        curriculumRepository.save(curriculum5);
        curriculumRepository.save(curriculum6);
        curriculumRepository.save(curriculum7);
        curriculumRepository.save(curriculum8);
        curriculumRepository.save(curriculum9);
        curriculumRepository.save(curriculum10);
        curriculumRepository.save(curriculum11);
        curriculumRepository.save(curriculum12);
        curriculumRepository.save(curriculum13);
        curriculumRepository.save(curriculum14);

        Position pos1 = Position.builder().position("Преподаватель").build();
        Position pos2 = Position.builder().position("Заместитель директора").build();
        Position доцент = Position.builder().position("Доцент").build();
        positionRepository.save(pos1);
        positionRepository.save(pos2);
        доцент = positionRepository.save(доцент);
        HashSet<Position> p = new HashSet<>();
        p.add(pos1);
        p.add(pos2);
        Professor prof = Professor.builder().birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("professor@gmail.com").enabled(true).name("admin").surname("admin").sex(true).username("professor")
                .msisdn("admin").password("1").roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR))).build();
        Employee employee = Employee.builder().birthday(Date.valueOf(LocalDate.of(1996, 1, 4))).email("sotrudnik@gmail.com")
                .enabled(true).msisdn("sotrudnik").name("Татьяна").password("1").sex(true)
                .surname("Глумова").username("sotrudnik").roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.EMPLOYEE))).build();
        employee.setPositions(p);
        employeeService.save(employee, true);
        prof.setDisciplines(d);
        prof.setPositions(p);
        professorService.save(prof, true);

        Employee collaborator = Employee.builder().birthday(Date.valueOf(LocalDate.of(1996, 1, 4)))
                .email("col@gmail.com").enabled(true).name("col").surname("col").sex(true).username("col")
                .msisdn("col").password("1").roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.EMPLOYEE))).build();
        employeeService.save(collaborator, true);

        Admin admin = Admin.builder()
                .birthday(new Date(Instant.now().getEpochSecond()))
                .sex(true)
                .password("SerendipitY")
                .email("admin@gmail.com")
                .enabled(true)
                .msisdn("+792724331")
                .name("Admin")
                .username("Admin")
                .surname("Admin")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.ADMIN)))
                .build();
        adminService.save(admin, true);

        Reference reference = Reference.builder().id(1L).created(Timestamp.valueOf(LocalDateTime.now()))
                .status(Status.CREATED).student(student).type(ReferenceType.ABOUT_STUDING).build();
        Reference reference2 = Reference.builder().id(2L).created(Timestamp.valueOf(LocalDateTime.now()))
                .status(Status.CREATED).student(student).type(ReferenceType.TO_WORK).build();
        Reference reference3 = Reference.builder().id(3L).created(Timestamp.valueOf(LocalDateTime.now()))
                .status(Status.READY).student(student2).type(ReferenceType.ABOUT_STUDING).build();
        Reference reference4 = Reference.builder().id(4L).created(Timestamp.valueOf(LocalDateTime.now()))
                .status(Status.READY).student(s3).type(ReferenceType.TO_WORK).build();
        referenceService.save(reference);
        referenceService.save(reference2);
        referenceService.save(reference3);
        referenceService.save(reference4);

        Building building = Building.builder()
                .id(1L)
                .address(
                        Address.builder()
                                .longitude(123123.00)
                                .latitude(123123.00)
                                .build()
                )
                .name("Двойка")
                .build();
        Building building2 = Building.builder()
                .id(2L)
                .address(
                        Address.builder()
                                .longitude(123123.00)
                                .latitude(123123.00)
                                .build()
                )
                .name("Физфак")
                .build();
        building = buildingRepository.save(building);
        building2 = buildingRepository.save(building2);
        Auditorium auditorium1 = Auditorium.builder()
                .id(1L)
                .building(building)
                .capacity(20)
                .number(108)
                .build();
        Auditorium auditorium2 = Auditorium.builder()
                .id(2L)
                .building(building)
                .capacity(20)
                .number(109)
                .build();
        Auditorium auditorium3 = Auditorium.builder()
                .id(3L)
                .building(building)
                .capacity(20)
                .number(1304)
                .build();
        Auditorium auditorium4 = Auditorium.builder()
                .id(4L)
                .building(building)
                .capacity(20)
                .number(1306)
                .build();
        Auditorium auditorium5 = Auditorium.builder()
                .id(5L)
                .building(building)
                .capacity(20)
                .number(1307)
                .build();
        Auditorium auditorium6 = Auditorium.builder()
                .id(6L)
                .building(building)
                .capacity(20)
                .number(1308)
                .build();
        Auditorium auditorium7 = Auditorium.builder()
                .id(7L)
                .building(building)
                .capacity(20)
                .number(1408)
                .build();
        Auditorium auditorium8 = Auditorium.builder()
                .id(8L)
                .building(building)
                .capacity(20)
                .number(1409)
                .build();
        Auditorium auditorium9 = Auditorium.builder()
                .id(9L)
                .building(building)
                .capacity(20)
                .number(1509)
                .build();
        Auditorium auditorium10 = Auditorium.builder()
                .id(10L)
                .building(building)
                .capacity(20)
                .number(1310)
                .build();
        Auditorium auditorium11 = Auditorium.builder()
                .id(11L)
                .building(building2)
                .capacity(20)
                .number(112)
                .build();
        auditorium1 = auditoriumRepository.save(auditorium1);
        auditorium2 = auditoriumRepository.save(auditorium2);
        auditorium3 = auditoriumRepository.save(auditorium3);
        auditorium4 = auditoriumRepository.save(auditorium4);
        auditorium5 = auditoriumRepository.save(auditorium5);
        auditorium6 = auditoriumRepository.save(auditorium6);
        auditorium7 = auditoriumRepository.save(auditorium7);
        auditorium8 = auditoriumRepository.save(auditorium8);
        auditorium9 = auditoriumRepository.save(auditorium9);
        auditorium10 = auditoriumRepository.save(auditorium10);
        auditorium11 = auditoriumRepository.save(auditorium11);

        Professor мокичев = Professor.builder()
                .name("Сергей")
                .birthday(new java.util.Date())
                .email("mokichev@gmail.com")
                .enabled(true)
                .msisdn("89058473641")
                .surname("Мокичев")
                .disciplines(Collections.singleton(экономика))
                .sex(true)
                .password("1")
                .username("mokichev@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        мокичев = professorService.save(мокичев, true);
        Professor григорян = Professor.builder()
                .name("Карен")
                .birthday(new java.util.Date())
                .email("grigoryan@gmail.com")
                .enabled(true)
                .msisdn("89058473642")
                .surname("Григорян")
                .disciplines(Collections.singleton(оп))
                .sex(true)
                .password("1")
                .username("grigoryan@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        григорян = professorService.save(григорян, true);
        Professor салихов = Professor.builder()
                .name("Ильсур")
                .birthday(new java.util.Date())
                .email("salihov@gmail.com")
                .enabled(true)
                .msisdn("89058473643")
                .surname("Салихов")
                .disciplines(Collections.singleton(правоведение))
                .sex(true)
                .password("1")
                .username("salihov@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        салихов = professorService.save(салихов, true);
        Professor хасанов = Professor.builder()
                .name("Ришат")
                .birthday(new java.util.Date())
                .email("rish@gmail.com")
                .enabled(true)
                .msisdn("89058473644")
                .surname("Хасанов")
                .disciplines(Collections.singleton(правоведение))
                .sex(true)
                .password("1")
                .username("rish@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        хасанов = professorService.save(хасанов, true);
        Professor бубнов = Professor.builder()
                .name("Денис")
                .birthday(new java.util.Date())
                .email("hajrullinbulat@gmail.com")
                .enabled(true)
                .msisdn("89058763425")
                .surname("Бубнов")
                .sex(true)
                .password("1")
                .username("bybnov@gmail.com")
                .disciplines(Collections.singleton(ос))
                .positions(Collections.singleton(pos1))
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        бубнов = professorService.save(бубнов, true);
        Professor иванов = Professor.builder()
                .name("Константин")
                .birthday(new java.util.Date())
                .email("ivanov@gmail.com")
                .enabled(true)
                .msisdn("89058473646")
                .surname("Иванов")
                .disciplines(сети)
                .sex(true)
                .password("1")
                .username("ivanov@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        иванов = professorService.save(иванов, true);
        Professor кугуракова = Professor.builder()
                .name("Влада")
                .birthday(new java.util.Date())
                .email("vlada@gmail.com")
                .enabled(true)
                .msisdn("89058473647")
                .surname("Кугуракова")
                .disciplines(Collections.singleton(пи2))
                .sex(true)
                .password("1")
                .username("vlada@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        кугуракова = professorService.save(кугуракова, true);
        Professor купцова = Professor.builder()
                .name("Анна")
                .birthday(new java.util.Date())
                .email("anna@gmail.com")
                .enabled(true)
                .msisdn("89058473648")
                .surname("Купцова")
                .disciplines(Collections.singleton(бжд))
                .sex(true)
                .password("1")
                .username("anna@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        купцова = professorService.save(купцова, true);
        Professor каримов = Professor.builder()
                .name("Роман")
                .birthday(new java.util.Date())
                .email("roma@gmail.com")
                .enabled(true)
                .msisdn("8905847369")
                .surname("Каримов")
                .disciplines(Collections.singleton(иб))
                .sex(true)
                .password("1")
                .username("roma@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        каримов = professorService.save(каримов, true);
        Professor скворцова = Professor.builder()
                .name("Галия")
                .birthday(new java.util.Date())
                .email("galiya@gmail.com")
                .enabled(true)
                .msisdn("8905847360")
                .surname("Скворцова")
                .disciplines(Collections.singleton(ки))
                .sex(true)
                .password("1")
                .username("galiya@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        скворцова = professorService.save(скворцова, true);
        Professor кац = Professor.builder()
                .name("Борис")
                .birthday(new java.util.Date())
                .email("boris@gmail.com")
                .enabled(true)
                .msisdn("8905847323")
                .surname("Кац")
                .disciplines(Collections.singleton(ки))
                .sex(true)
                .password("1")
                .username("boris@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        кац = professorService.save(кац, true);
        Professor новиков = Professor.builder()
                .name("Петр")
                .birthday(new java.util.Date())
                .email("novikov@gmail.com")
                .enabled(true)
                .msisdn("8905847334")
                .surname("Новиков")
                .disciplines(Collections.singleton(пп))
                .sex(true)
                .password("1")
                .username("novikov@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        новиков = professorService.save(новиков, true);
        Professor скворцов = Professor.builder()
                .name("Андрей")
                .birthday(new java.util.Date())
                .email("skvor@gmail.com")
                .enabled(true)
                .msisdn("89058473665")
                .surname("Скворцов")
                .disciplines(Collections.singleton(физика2))
                .sex(true)
                .password("1")
                .username("skvor@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        скворцов = professorService.save(скворцов, true);
        Professor салихова = Professor.builder()
                .name("Олеся")
                .birthday(new java.util.Date())
                .email("sali@gmail.com")
                .enabled(true)
                .msisdn("8905847312")
                .surname("Салихова")
                .disciplines(Collections.singleton(физика2))
                .sex(true)
                .password("1")
                .username("sali@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        салихова = professorService.save(салихова, true);
        Professor моисеев = Professor.builder()
                .name("Георгий")
                .birthday(new java.util.Date())
                .email("moiseev@gmail.com")
                .enabled(true)
                .msisdn("8905847390")
                .surname("Моисеев")
                .disciplines(Collections.singleton(уп))
                .sex(true)
                .positions(Collections.singleton(доцент))
                .password("1")
                .username("GVMoiseev")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        моисеев = professorService.save(моисеев, true);
        Professor таланов = Professor.builder()
                .name("Максим")
                .birthday(new java.util.Date())
                .email("max@gmail.com")
                .enabled(true)
                .msisdn("8905847377")
                .surname("Таланов")
                .disciplines(Collections.singleton(пис))
                .sex(true)
                .password("1")
                .username("max@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        таланов = professorService.save(таланов, true);
        Professor шамсутдинов = Professor.builder()
                .name("Тимур")
                .birthday(new java.util.Date())
                .email("timur@gmail.com")
                .enabled(true)
                .msisdn("8905847314")
                .surname("Шамсутдинов")
                .disciplines(Collections.singleton(пис))
                .sex(true)
                .password("1")
                .username("timur@gmail.com")
                .roles(Collections.singleton(roleRepository.findByName(RoleEnumeration.PROFESSOR)))
                .build();
        шамсутдинов = professorService.save(шамсутдинов, true);
        Timetable timetable = Timetable.builder()
                .auditorium(auditorium9)
                .discipline(пп)
                .professor(новиков)
                .group(group)
                .type(Type.ПРАКТИКА)
                .classNum(Class.ТРЕТЬЯ)
                .day(WeekDay.ПОНЕДЕЛЬНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable);
        Timetable timetable2 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(оп)
                .professor(григорян)
                .group(group)
                .type(Type.ЛЕКЦИЯ)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.ПОНЕДЕЛЬНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable2);
        Timetable timetable3 = Timetable.builder()
                .auditorium(auditorium7)
                .discipline(пи2)
                .professor(кугуракова)
                .group(group)
                .classNum(Class.ПЯТАЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.ПОНЕДЕЛЬНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable3);
        Timetable timetable4 = Timetable.builder()
                .discipline(физкультура)
                .group(group)
                .type(Type.ПРАКТИКА)
                .classNum(Class.ПЕРВАЯ)
                .comment("8:00 - 9:30 УНИКС")
                .day(WeekDay.ВТОРНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable4);
        Timetable timetable5 = Timetable.builder()
                .auditorium(auditorium5)
                .discipline(оп)
                .professor(григорян)
                .group(group)
                .type(Type.ПРАКТИКА)
                .classNum(Class.ТРЕТЬЯ)
                .day(WeekDay.ВТОРНИК)
                .week(Week.Нечетная)
                .build();
        timetableRepository.save(timetable5);
        Timetable timetable6 = Timetable.builder()
                .auditorium(auditorium8)
                .discipline(правоведение)
                .professor(хасанов)
                .group(group)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.ВТОРНИК)
                .type(Type.ПРАКТИКА)
                .week(Week.Нечетная)
                .build();
        timetableRepository.save(timetable6);
        Timetable timetable7 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(правоведение)
                .professor(салихов)
                .group(group)
                .type(Type.ЛЕКЦИЯ)
                .classNum(Class.ПЯТАЯ)
                .day(WeekDay.ВТОРНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable7);
        Timetable timetable8 = Timetable.builder()
                .auditorium(auditorium4)
                .discipline(уп)
                .professor(моисеев)
                .group(group)
                .classNum(Class.ТРЕТЬЯ)
                .day(WeekDay.СРЕДА)
                .type(Type.ПРАКТИКА)
                .week(Week.Нечетная)
                .build();
        timetableRepository.save(timetable8);
        Timetable timetable9 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(уп)
                .professor(моисеев)
                .group(group)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .type(Type.ЛЕКЦИЯ)
                .day(WeekDay.СРЕДА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable9);
        Timetable timetable10 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(пп)
                .professor(новиков)
                .group(group)
                .classNum(Class.ПЕРВАЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .type(Type.ЛЕКЦИЯ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable10);
        Timetable timetable11 = Timetable.builder()
                .auditorium(auditorium6)
                .discipline(физика2)
                .professor(салихова)
                .group(group)
                .classNum(Class.ТРЕТЬЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .type(Type.ПРАКТИКА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable11);
        Timetable timetable12 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(пи2)
                .professor(кугуракова)
                .group(group)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .type(Type.ЛЕКЦИЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable12);
        Timetable timetable13 = Timetable.builder()
                .auditorium(auditorium11)
                .discipline(физика2)
                .professor(скворцов)
                .group(group)
                .classNum(Class.ПЯТАЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .type(Type.ЛЕКЦИЯ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable13);
        Timetable timetable14 = Timetable.builder()
                .discipline(физкультура)
                .group(group)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .type(Type.ПРАКТИКА)
                .comment("14:00 - 15:30 УНИКС")
                .day(WeekDay.ПЯТНИЦА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable14);
        Timetable timetable15 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(оп)
                .professor(григорян)
                .group(group1)
                .type(Type.ЛЕКЦИЯ)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.ПОНЕДЕЛЬНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable15);
        Timetable timetable16 = Timetable.builder()
                .auditorium(auditorium5)
                .discipline(оп)
                .professor(григорян)
                .group(group1)
                .classNum(Class.ПЯТАЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.ПОНЕДЕЛЬНИК)
                .week(Week.Четная)
                .build();
        timetableRepository.save(timetable16);
        Timetable timetable17 = Timetable.builder()
                .discipline(физкультура)
                .group(group1)
                .classNum(Class.ПЕРВАЯ)
                .comment("8:00 - 9:30 УНИКС")
                .type(Type.ПРАКТИКА)
                .day(WeekDay.ВТОРНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable17);
        Timetable timetable18 = Timetable.builder()
                .auditorium(auditorium4)
                .discipline(уп)
                .professor(моисеев)
                .group(group1)
                .type(Type.ЛЕКЦИЯ)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.ВТОРНИК)
                .week(Week.Четная)
                .build();
        timetableRepository.save(timetable18);
        Timetable timetable19 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(правоведение)
                .professor(салихов)
                .group(group1)
                .classNum(Class.ПЯТАЯ)
                .type(Type.ЛЕКЦИЯ)
                .day(WeekDay.ВТОРНИК)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable19);
        Timetable timetable20 = Timetable.builder()
                .auditorium(auditorium7)
                .discipline(пи2)
                .professor(кугуракова)
                .group(group1)
                .classNum(Class.ВТОРАЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.СРЕДА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable20);
        Timetable timetable21 = Timetable.builder()
                .auditorium(auditorium5)
                .discipline(пп)
                .professor(новиков)
                .group(group1)
                .classNum(Class.ТРЕТЬЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.СРЕДА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable21);
        Timetable timetable22 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(уп)
                .professor(моисеев)
                .group(group1)
                .type(Type.ЛЕКЦИЯ)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.СРЕДА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable22);
        Timetable timetable23 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(пп)
                .professor(новиков)
                .group(group1)
                .classNum(Class.ПЕРВАЯ)
                .type(Type.ЛЕКЦИЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable23);
        Timetable timetable24 = Timetable.builder()
                .auditorium(auditorium6)
                .discipline(физика2)
                .professor(салихова)
                .group(group1)
                .classNum(Class.ВТОРАЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.ЧЕТВЕРГ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable24);
        Timetable timetable25 = Timetable.builder()
                .auditorium(auditorium10)
                .discipline(правоведение)
                .professor(хасанов)
                .group(group1)
                .classNum(Class.ТРЕТЬЯ)
                .type(Type.ПРАКТИКА)
                .day(WeekDay.ЧЕТВЕРГ)
                .week(Week.Четная)
                .build();
        timetableRepository.save(timetable25);
        Timetable timetable26 = Timetable.builder()
                .auditorium(auditorium1)
                .discipline(пи2)
                .professor(кугуракова)
                .group(group1)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .type(Type.ЛЕКЦИЯ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable26);
        Timetable timetable27 = Timetable.builder()
                .auditorium(auditorium11)
                .discipline(физика2)
                .professor(скворцов)
                .group(group1)
                .classNum(Class.ПЯТАЯ)
                .type(Type.ЛЕКЦИЯ)
                .day(WeekDay.ЧЕТВЕРГ)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable27);
        Timetable timetable28 = Timetable.builder()
                .discipline(физкультура)
                .group(group1)
                .classNum(Class.ЧЕТВЕРТАЯ)
                .type(Type.ПРАКТИКА)
                .comment("14:00 - 15:30 УНИКС")
                .day(WeekDay.ПЯТНИЦА)
                .week(Week.Каждая)
                .build();
        timetableRepository.save(timetable28);


        Exam exam1 = Exam.builder()
                .auditorium(auditorium10)
                .discipline(ки)
                .group(group)
                .professor(кац)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 21, 10, 10)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 21, 11, 40)))
                                .build()
                ).build();
        examRepository.save(exam1);
        Exam exam2 = Exam.builder()
                .auditorium(auditorium10)
                .discipline(ки)
                .group(group1)
                .professor(кац)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 21, 11, 50)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 21, 13, 20)))
                                .build()
                ).build();
        examRepository.save(exam2);
        Exam exam3 = Exam.builder()
                .auditorium(auditorium8)
                .discipline(бжд)
                .group(group)
                .professor(купцова)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 19, 8, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 19, 10, 00)))
                                .build()
                ).build();
        examRepository.save(exam3);
        Exam exam4 = Exam.builder()
                .auditorium(auditorium8)
                .discipline(бжд)
                .group(group1)
                .professor(купцова)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 19, 10, 10)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 19, 11, 40)))
                                .build()
                ).build();
        examRepository.save(exam4);
        Exam exam5 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(ос)
                .group(group)
                .professor(иванов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 10, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 10, 16, 00)))
                                .build()
                ).build();
        examRepository.save(exam5);
        Exam exam6 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(ос)
                .group(group1)
                .professor(иванов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 9, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 9, 16, 00)))
                                .build()
                ).build();
        examRepository.save(exam6);
        Exam exam7 = Exam.builder()
                .auditorium(auditorium10)
                .discipline(экономика)
                .group(group)
                .professor(мокичев)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 25, 8, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 25, 10, 00)))
                                .build()
                ).build();
        examRepository.save(exam7);
        Exam exam8 = Exam.builder()
                .auditorium(auditorium10)
                .discipline(экономика)
                .group(group1)
                .professor(мокичев)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 25, 10, 10)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 25, 11, 40)))
                                .build()
                ).build();
        examRepository.save(exam8);
        Exam exam9 = Exam.builder()
                .auditorium(auditorium7)
                .discipline(пи1)
                .group(group)
                .professor(кугуракова)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 22, 13, 35)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 22, 15, 05)))
                                .build()
                ).build();
        examRepository.save(exam9);
        Exam exam10 = Exam.builder()
                .auditorium(auditorium7)
                .discipline(пи1)
                .group(group1)
                .professor(кугуракова)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 22, 11, 50)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 22, 13, 20)))
                                .build()
                ).build();
        examRepository.save(exam10);
        Exam exam11 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(пис)
                .group(group)
                .professor(таланов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 13, 11, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 13, 14, 30)))
                                .build()
                ).build();
        examRepository.save(exam11);
        Exam exam12 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(пис)
                .group(group1)
                .professor(таланов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 12, 11, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 12, 14, 30)))
                                .build()
                ).build();
        examRepository.save(exam12);
        Exam exam13 = Exam.builder()
                .auditorium(auditorium9)
                .discipline(иб)
                .group(group)
                .professor(иванов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 17, 10, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 17, 12, 00)))
                                .build()
                ).build();
        examRepository.save(exam13);
        Exam exam14 = Exam.builder()
                .auditorium(auditorium9)
                .discipline(иб)
                .group(group1)
                .professor(иванов)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 1, 18, 10, 30)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 1, 18, 12, 00)))
                                .build()
                ).build();
        examRepository.save(exam14);
        Exam exam15 = Exam.builder()
                .auditorium(auditorium11)
                .discipline(физика1)
                .group(group)
                .professor(скворцов)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 29, 15, 20)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 29, 16, 50)))
                                .build()
                ).build();
        examRepository.save(exam15);
        Exam exam16 = Exam.builder()
                .auditorium(auditorium11)
                .discipline(физика1)
                .group(group1)
                .professor(скворцов)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2016, 12, 29, 17, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2016, 12, 29, 18, 30)))
                                .build()
                ).build();
        examRepository.save(exam16);
        Exam exam17 = Exam.builder()
                .auditorium(auditorium1)
                .discipline(правоведение)
                .group(group)
                .professor(салихов)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 13, 35)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 15, 05)))
                                .build()
                ).build();
        examRepository.save(exam17);
        Exam exam18 = Exam.builder()
                .auditorium(auditorium1)
                .discipline(правоведение)
                .group(group1)
                .professor(салихов)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 11, 50)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 13, 20)))
                                .build()
                ).build();
        examRepository.save(exam18);
        Exam exam19 = Exam.builder()
                .auditorium(auditorium11)
                .discipline(физика2)
                .group(group)
                .professor(скворцов)
                .passType(PassType.ДИФ_ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 25, 15, 20)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 25, 16, 50)))
                                .build()
                ).build();
        examRepository.save(exam19);
        Exam exam20 = Exam.builder()
                .auditorium(auditorium11)
                .discipline(физика1)
                .group(group1)
                .professor(скворцов)
                .passType(PassType.ДИФ_ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 25, 17, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 25, 18, 30)))
                                .build()
                ).build();
        examRepository.save(exam20);
        Exam exam21 = Exam.builder()
                .auditorium(auditorium7)
                .discipline(пи2)
                .group(group)
                .professor(кугуракова)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 10, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 10, 13, 30)))
                                .build()
                ).build();
        examRepository.save(exam21);
        Exam exam22 = Exam.builder()
                .auditorium(auditorium7)
                .discipline(пи2)
                .group(group1)
                .professor(кугуракова)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 9, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 9, 13, 30)))
                                .build()
                ).build();
        examRepository.save(exam22);
        Exam exam23 = Exam.builder()
                .auditorium(auditorium9)
                .discipline(пп)
                .group(group)
                .professor(новиков)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 5, 9, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 5, 14, 30)))
                                .build()
                ).build();
        examRepository.save(exam23);
        Exam exam24 = Exam.builder()
                .auditorium(auditorium9)
                .discipline(пп)
                .group(group1)
                .professor(новиков)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 6, 9, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 6, 14, 30)))
                                .build()
                ).build();
        examRepository.save(exam24);
        Exam exam25 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(уп)
                .group(group)
                .professor(моисеев)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 14, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 14, 13, 30)))
                                .build()
                ).build();
        examRepository.save(exam25);
        Exam exam26 = Exam.builder()
                .auditorium(auditorium4)
                .discipline(уп)
                .group(group1)
                .professor(моисеев)
                .passType(PassType.ЭКЗАМЕН)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 6, 13, 10, 00)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 6, 13, 13, 30)))
                                .build()
                ).build();
        examRepository.save(exam26);
        Exam exam27 = Exam.builder()
                .auditorium(auditorium8)
                .discipline(оп)
                .group(group)
                .professor(григорян)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 11, 50)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 13, 20)))
                                .build()
                ).build();
        examRepository.save(exam27);
        Exam exam28 = Exam.builder()
                .auditorium(auditorium8)
                .discipline(оп)
                .group(group1)
                .professor(григорян)
                .passType(PassType.ЗАЧЕТ)
                .time(
                        Time.builder()
                                .from(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 13, 35)))
                                .to(Timestamp.valueOf(LocalDateTime.of(2017, 5, 30, 15, 05)))
                                .build()
                ).build();
        examRepository.save(exam28);


        HashSet<Exam> экзамены405 = new HashSet<>();
        экзамены405.add(exam1);
        экзамены405.add(exam3);
        экзамены405.add(exam5);
        экзамены405.add(exam7);
        экзамены405.add(exam9);
        экзамены405.add(exam11);
        экзамены405.add(exam13);
        экзамены405.add(exam15);
        экзамены405.add(exam17);
        экзамены405.add(exam19);
        экзамены405.add(exam21);
        экзамены405.add(exam23);
        экзамены405.add(exam25);
        экзамены405.add(exam27);

        HashSet<Exam> экзамены404 = new HashSet<>();
        экзамены404.add(exam2);
        экзамены404.add(exam4);
        экзамены404.add(exam6);
        экзамены404.add(exam8);
        экзамены404.add(exam10);
        экзамены404.add(exam12);
        экзамены404.add(exam14);
        экзамены404.add(exam16);
        экзамены404.add(exam18);
        экзамены404.add(exam20);
        экзамены404.add(exam22);
        экзамены404.add(exam24);
        экзамены404.add(exam26);
        экзамены404.add(exam28);

        for (Student студент : group405) {
            for (Exam ex : экзамены405) {
                ExamResult examResult = ExamResult.builder()
                        .exam(ex)
                        .student(студент)
                        .points((int) (Math.random() * 22 + 28))
                        .build();
                examResultRepository.save(examResult);
            }
        }

        for (Student студент : group404) {
            for (Exam ex : экзамены404) {
                ExamResult examResult = ExamResult.builder()
                        .exam(ex)
                        .student(студент)
                        .points((int) (Math.random() * 22 + 28))
                        .build();
                examResultRepository.save(examResult);
            }
        }

        isAlreadySetup = true;
    }
}
