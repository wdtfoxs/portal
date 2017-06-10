package com.code405.constants;

/**
 * Created by birthright on 28.02.17.
 */
public interface Routes {
    String ROOT_URI = "/";
    String ROOT_VIEW = "root/index";

    String NEWS_URI = "/news";
    String NEWS_VIEW = "news/index";
    String NEWS_SHOW_URI = "/news/{id}";
    String NEWS_SHOW_VIEW = "news/show";
    String NEWS_MORE_URI = "/news/more";
    String NEWS_DELETE_URI = "/news/delete/{id}";
    String NEWS_UPDATE_URI = "/news/update/{id}";
    String NEWS_NEW_URI = "/news/new";
    String EVENT_URI = "/event";
    String EVENT_VIEW = "event/index";
    String EVENT_NEW_URI = "/event/new";
    String EVENT_DELETE_URI = "/event/delete/{id}";
    String EVENT_UPDATE_URI = "/event/update/{id}";
    String EVENT_SHOW_URI = "/event/{id}";
    String EVENT_SHOW_VIEW = "event/show";
    String EVENT_MORE_URI = "/event/more";


    String ABOUT_URI = "/about";
    String ABOUT_VIEW = "about/index";

    String LOGOUT_URI = "/logout";
    //################### Registration Routes ##########################
    String REGISTRATION_URI = "/register";
    String REGISTRATION_VIEW = "register/index";

    String REGISTRATION_SUCCESS_URI = REGISTRATION_URI + "?success";
    String REGISTRATION_SUCCESS_VIEW = "register/success";

    String REGISTRATION_INFO_VIEW = "register/info";
    String REGISTRATION_RESEND_TOKEN_VIEW = "register/resend";

    //################### Login Routes ##########################
    String LOGIN_URI = "/login";
    String LOGIN_VIEW = "login/index";

    String LOGIN_PROCESSING_URI = "/login_processing";

    String LOGIN_INFO_URI = LOGIN_URI + "/info";
    String LOGIN_INFO_VIEW = "login/info";

    String LOGIN_RESET_PASSWORD_VIEW = "login/reset_password";

    String LOGIN_NEW_PASSWORD_URI = LOGIN_URI + "/new_password";
    String LOGIN_NEW_PASSWORD_VIEW = LOGIN_URI + "/new_password";

    //################### Error Routes ##########################
    String ERROR_URI = "/error";
    String ERROR_VIEW = "status/error";

    String NOT_FOUND_URI = "/404";
    String NOT_FOUND_VIEW = "status/404";

    String ACCESS_DENIED_URI = "/403";
    String ACCESS_DENIED_VIEW = "status/403";

    //################### Timetable Routes ##########################
    String TIMETABLE_URI = "/timetable";

    //################### Cabinet Routes ##########################
    String CABINET_URI = "/cabinet";
    String CABINET_VIEW = "cabinet/cabinet";

    //################### Journal Routes ##########################
    String JOURNAL_URI = "/journal";
    String JOURNAL_STUDENT_VIEW = "journal/student/index";
    String JOURNAL_PROFESSOR_VIEW = "journal/professor/index";
    String JOURNAL_EMPLOYEE_VIEW = "journal/employee/index";
}
