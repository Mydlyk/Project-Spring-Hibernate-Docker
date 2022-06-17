package com.example.frontend.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.frontend.backend.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String loggin(HttpSession sesion, Model model){
        if(sesion.getAttribute("userId") == null)
            return "login";
        else
            return "redirect:/home";
    }

    @PostMapping("/")
    public ModelAndView logging(@RequestParam(name="login") String login, @RequestParam(name = "password") String password, HttpSession session, ModelMap model){
        String error = "";
        Boolean correctLogin = false;

        List<User> users = userRepository.getAll();
        User loggedUser = new User();


        User user;
        try {
            user = userRepository.getUserByLogin(login);
        }catch (Exception e){
            user = null;
        }

        if(user == null){
            error = "Incorrect login";
        }else{
            if(user.getPassword().equals(password)){
                error = "";
                correctLogin = true;
                loggedUser = user;
                System.out.println(loggedUser);
            }
            else{
                error = "Incorrect password!";
            }
        }
        if(!error.equals("")) {
            model.addAttribute("error", error);
            return new ModelAndView("login", model);
        }else{
            session.setAttribute("userId", loggedUser.getId() );
            return new ModelAndView("redirect:/home",model);
        }
    }

    @GetMapping("/logout")
    public String logout (HttpSession session){
        if(session.getAttribute("userId") != null){
            session.removeAttribute("userId");
        }

        return "redirect:/";
    }

}
