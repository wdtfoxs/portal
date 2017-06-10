package com.code405.web.controller;

import com.code405.entity.embeddable.Time;
import com.code405.entity.enumeration.Course;
import com.code405.entity.enumeration.Semester;
import com.code405.entity.model.Student;
import com.code405.service.RatingService;
import com.code405.util.UserDetailsImpl;
import com.code405.web.dto.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/rating")
@Controller
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasRole(T(com.code405.entity.enumeration.RoleEnumeration).STUDENT)")
    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        Student student = (Student) user.getUser();
        Time timeForCourse = Course.getTimeForCourse(student.getAdmissionDate());

        List<RatingDTO> ratingForStudent = ratingService.getRatingForStudent(student, timeForCourse);

        Map<Semester, List<RatingDTO>> rating = ratingForStudent.stream()
                .sorted(Comparator.comparing(r -> r.getPassTime().getFrom()))
                .collect(Collectors.groupingBy(r -> r.getDiscipline().getSemester()));

        Double averageRating = ratingForStudent.stream()
                .mapToInt(RatingDTO::getTotalPoints)
                .average()
                .orElse(0.0);

        request.setAttribute("rating_first", rating.getOrDefault(Semester.FIRST, null));
        request.setAttribute("rating_second", rating.getOrDefault(Semester.SECOND, null));
        request.setAttribute("average_rating", averageRating);
        request.setAttribute("place_group", ratingService.getStudentPlaceInGroup(student, timeForCourse));
        request.setAttribute("place_course", ratingService.getStudentPlaceInCourse(student, timeForCourse));

        return "rating/index";
    }
}
