package com.example.frontend.frontend;

import com.example.frontend.backend.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.ConsoleHandler;

/**
 * Ogólnie uwaga bo się zajebałem i w formularzu zamiast title dałem name
 * A już za dużo zrobiłem żeby zmieniać... xd
 */

@Controller
public class AddEdditController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    //Wysywietlenie dodania
    @GetMapping("/addTaskForm")
    public ModelAndView addTaskForm(HttpSession session, ModelMap model){
        if(session.getAttribute("userId") == null){
            return new ModelAndView("redirect:/", model);
        }else {
            return new ModelAndView("addTaskForm", model);
        }
    }


    @PostMapping("/addTaskForm")
    public ModelAndView addingTaskForm(@RequestParam(name="title", required = false) String name, @RequestParam(name = "content", required = false) String content,
                                       @RequestParam(name="deadline", required = false) String deadline_form, @RequestParam(name="priority" , required = false) String priority_form,
                                       HttpSession session, ModelMap model){
        if(session.getAttribute("userId") == null){
            return new ModelAndView("redirect:/", model);
        }else {
            String error = "";
            User user = userRepository.getById((int) session.getAttribute("userId"));

            if(name==null || name=="")
                error += "Title field cannot be null! ";
//            if(content==null || content=="")
//                error += "Content field cannot be null! ";
            if(deadline_form==null || deadline_form=="")
                error += "Deadline field cannot be null! ";


            if(!error.equals("")) {
                model.addAttribute("error", error);
                return new ModelAndView("addTaskform", model);
            }else{

                LocalDateTime deadline = null;
                if(deadline_form != "")
                    deadline = LocalDateTime.parse(deadline_form);

                Task newTask = new Task(1, name, content, LocalDateTime.now(), deadline, null, null, priority_form, user.getId());

                taskRepository.save(newTask);
                return new ModelAndView("redirect:/home",model);
            }
        }

    }


    //wyswietlenie formularza edycji
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam(name = "taskId") int taskId, HttpSession httpSession, ModelMap model){
        if(httpSession.getAttribute("userId") == null){
            return new ModelAndView("redirect:/", model);
        }else {

            Task editedTask = taskRepository.getById(taskId);
            editFormInsertData(editedTask, model);

            return new ModelAndView("editTaskForm", model);
        }
    }

    //Obsługa zedytowanego zadania - walidacja i zmiana
    @PostMapping("/editTaskForm")
    public ModelAndView editTaskForm(@RequestParam(name = "id", required = false) int taskId,
                                     @RequestParam(name = "title", required = false)String title,
                                     @RequestParam(name = "content", required = false)String content,
                                     @RequestParam(name = "added", required = false) String added_form,
                                     @RequestParam(name = "deadline", required = false) String deadline_form,
                                     @RequestParam(name = "finished", required = false) String finished_form,
                                     @RequestParam(name = "priority", required = false) String priority_form,
                                     @RequestParam(name = "status", required = false) String status_form,
                                     HttpSession httpSession, ModelMap model){
        if(httpSession.getAttribute("userId") == null){
            return new ModelAndView("redirect:/", model);
        }else {
            String error = "";

            if(title==null || title=="")
                error += "Title field cannot be null ";
//            if(content==null || content=="")
//                error += "Content field cannot be null ";
            if(added_form==null || added_form=="")
                error += "Added field cannot be null ";
            if(deadline_form==null || deadline_form=="")
                error += "Deadline field cannot be null ";
//            if(finished_form==null || finished_form=="")
//                error += "finished field cannot be null";

            LocalDateTime deadline = null;
            if(deadline_form != "")
                deadline = LocalDateTime.parse(deadline_form);

            LocalDateTime added = LocalDateTime.now();
            if(added_form != "")
                added = LocalDateTime.parse(added_form);

            LocalDateTime finished = null;
            if(finished_form != "")
                finished = LocalDateTime.parse(finished_form);

            if(!error.equals("")) {
                model.addAttribute("error", error);

                Task editedTask = taskRepository.getById(taskId);
                editFormInsertData(editedTask, model);
                return new ModelAndView("editTaskForm", model);
            }else {
                Task updateTask = taskRepository.getById(taskId);

                updateTask.setStatus(status_form);
                updateTask.setTitle(title);
                updateTask.setAdded(added);
                updateTask.setContent(content);
                updateTask.setStatus(status_form);
                updateTask.setDeadline(deadline);
                updateTask.setFinished(finished);
                updateTask.setPriority(priority_form);

                taskRepository.update(updateTask);
                return new ModelAndView("redirect:/home", model);
            }
        }
    }

    @GetMapping("/deleteTask")
    public String deleteTask(@RequestParam(name = "taskId") int taskId){

        taskRepository.delete(taskId);
        return "redirect:/home";
    }

    //Zamiana daty na string
    public String dateTimeToHtmlValue(LocalDateTime dateTime){
        if(dateTime != null){
            DateTimeFormatter day = DateTimeFormatter.ISO_LOCAL_DATE;
            DateTimeFormatter hours = DateTimeFormatter.ofPattern("HH:mm");
            return day.format(dateTime) + "T" + hours.format(dateTime);
        }
        return "";
    }

    public void editFormInsertData(Task editedTask, ModelMap model){
        model.addAttribute("task", editedTask);
        model.addAttribute("deadline", dateTimeToHtmlValue(editedTask.deadline));
        model.addAttribute("added", dateTimeToHtmlValue(editedTask.added));
        model.addAttribute("finished", dateTimeToHtmlValue(editedTask.finished));
    }

}
