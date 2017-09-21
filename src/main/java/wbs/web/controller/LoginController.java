package wbs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wbs.model.authentication.User;

import javax.servlet.http.HttpServletRequest;

/*
 * Controller for login page
 */

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        //Add flash attribute to the model if present(login failed)
        //Flash attribute is managed by the SecurityConfig class
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);
        } catch (Exception e) {
            //if no flash attribute present, don't do anything
        }
        return "login";
    }
}
