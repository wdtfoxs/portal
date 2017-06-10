package com.code405.web.controller;

import com.code405.constants.Routes;
import com.code405.constants.SecurityConstants;
import com.code405.constants.SessionConstants;
import com.code405.entity.PasswordResetToken;
import com.code405.entity.enumeration.RoleEnumeration;
import com.code405.entity.model.User;
import com.code405.service.interfaces.IPasswordResetTokenService;
import com.code405.service.interfaces.ISecureService;
import com.code405.service.interfaces.IUserService;
import com.code405.util.CreateEmailMessageHelper;
import com.code405.util.UrlApplicationHelper;
import com.code405.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Birthright on 01.05.2016.
 */
//  UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
@Controller
public class LoginController {
    private static final String MESSAGE = "message";
    @Autowired
    private IUserService userService;
    @Autowired
    private IPasswordResetTokenService tokenService;
    @Autowired
    private CreateEmailMessageHelper createEmailMessageHelper;
    @Autowired
    private ISecureService secureService;

    /**
     * Show login page
     */
    @GetMapping(Routes.LOGIN_URI)
    public String index() {
        return Routes.LOGIN_VIEW;
    }

    /**
     * Show reset password page
     */
    @GetMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public String resetPassword() {
        return Routes.LOGIN_RESET_PASSWORD_VIEW;
    }

    /**
     * @param userEmail  -  a user's email, which need to reset password
     * @param lastResend - The date eventDate the user has requested a resending email If the user requests a letter in less
     *                   than 5 minutes ago - prohibit the sending and caution.
     * @return without Exception always redirect to Login Info Page, with Exception - to Error Page
     */

    @PostMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public RedirectView resetPassword(HttpServletRequest request, @RequestParam("email") final String userEmail,
                                      RedirectAttributes redirectAttributes, HttpSession session,
                                      @SessionAttribute(required = false, name = SessionConstants.LAST_RESEND) Long lastResend) {
        if (lastResend != null && Calendar.getInstance().getTime().getTime() - lastResend <= 600000) {
           // String message = messageSource.getMessage("auth.message.too_many_resend", null, request.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, "Слишком много запросов на сброс пароля. Попробуйте 5 минут спустя.");
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
           // String message = messageSource.getMessage("Пользователь с таким email не найден", null, request.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, "Пользователь с таким email не найден");
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        String token = UUID.randomUUID().toString();
        tokenService.createPasswordResetToken(token, user);
        String appUrl = UrlApplicationHelper.getAppUrl(request);
        try {
            createEmailMessageHelper.sendResetPasswordEmail(appUrl, token, user);
        } catch (Exception e) {
           // redirectAttributes.addAttribute(MESSAGE, e.getMessage());
            return new RedirectView(Routes.LOGIN_URI +"?reset_password");
        }
        //String message = messageSource.getMessage("login.message.reset_password", null, locale);
        redirectAttributes.addFlashAttribute(MESSAGE, "Письмо со сбросом пароля отправлено.");
        session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
        return new RedirectView(Routes.LOGIN_INFO_URI);
    }

    @GetMapping(value = Routes.LOGIN_URI, params = {"token", "u", "reset_password"})
    public RedirectView showResetPassword(@RequestParam String token, @RequestParam Long u,
                                          RedirectAttributes redirectAttributes) {
        //Locale locale = localeResolver.resolveLocale(request);
        PasswordResetToken resetToken = tokenService.findPasswordResetToken(token);
        String invalidResult = secureService.checkConfirmResetPasswordToken(resetToken, u);
        if (invalidResult != null) {
          //  String message = messageSource.getMessage("auth.message." + invalidResult, null, locale);
            redirectAttributes.addFlashAttribute(MESSAGE, invalidResult);
            return new RedirectView(Routes.LOGIN_INFO_URI);
        }
        User user = resetToken.getUser();
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(SecurityConstants.DEFAULT_ROLE_PREFIX + RoleEnumeration.TEMPORARY_ACCESS.toString())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
    }

    @GetMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public String showNewPasswordPage() {
        return Routes.LOGIN_NEW_PASSWORD_VIEW;
    }

    @PostMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public RedirectView saveNewPassword(@RequestParam String password, @RequestParam String matchingPassword,
                                        RedirectAttributes redirectAttributes,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (!password.equals(matchingPassword)) {
            //String message = messageSource.getMessage("auth.message.wrong_match", null, localeResolver.resolveLocale(request));
            redirectAttributes.addFlashAttribute(MESSAGE, "Пароли не эквивалентны. Введите заново.");
            return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
        }
        //UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userDetails.getUser();
        userService.changeUserPassword(user, password);
        tokenService.deletePasswordResetToken(user);
        userDetails.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      //  String message = messageSource.getMessage("auth.message.success_reset", null, localeResolver.resolveLocale(request));
        return new RedirectView(Routes.CABINET_URI);
    }

    @GetMapping(Routes.LOGIN_INFO_URI)
    public String showLoginInfo(Model model) {
        if (model.containsAttribute(MESSAGE)) {
            return Routes.LOGIN_INFO_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

}
