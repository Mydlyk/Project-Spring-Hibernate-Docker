package com.example.frontend.frontend;

import com.example.frontend.backend.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

     @GetMapping("/home")
    public ModelAndView home(HttpSession session, ModelMap model){
        if(session.getAttribute("userId") == null){
            return new ModelAndView("redirect:/", model);
        }else {
            int userId = (int) session.getAttribute("userId");

            User user = userRepository.getById((int) session.getAttribute("userId"));
            List<Task> tasks = taskRepository.getByUserId(user.getId());
            model.addAttribute("tasks", tasks);

            return new ModelAndView("home", model);
        }
    }

}
