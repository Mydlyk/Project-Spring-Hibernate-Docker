package com.example.frontend.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.frontend.backend.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(HttpSession session, Model model){
        if(session.getAttribute("userId") == null)
            return "register";
        else
            return "redirect:/home";
    }

    @PostMapping("/register")
    public String registering(@RequestParam(name="login") String login, @RequestParam(name = "password") String password,
                          @RequestParam(name = "email") String email, Model model){

        String error = "";

        List<User> users = userRepository.getAll();
        for(User user:users){
            if(user.getLogin() == login){
                error = "The user exists on such login";
            }else if(user.getEmail() == email){
                error = "The user exists on such email";
            }
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);




        if(!error.equals("")) {
            model.addAttribute("error", error);
            return "register";
        }else{
            model.addAttribute("Correct_registration", "\n" +
                    "Correctly registered");
            try{
                userRepository.save(user);
            }catch(Exception ex){
                error = ex.toString();
            }
            return "login";
        }

    }

}
