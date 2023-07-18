package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;
    private PriorityService priorityService;
    private CategoryService categoryService;

    @GetMapping("/list/all")
    public String getAll(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        var timeZone = user.getTimezone() == null ? TimeZone.getDefault() : ZoneId.of(user.getTimezone());
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("timeZone", timeZone);
        return "tasks/list/all";
    }

    @GetMapping("/list/done")
    public String getDone(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        var timeZone = user.getTimezone() == null ? TimeZone.getDefault() : ZoneId.of(user.getTimezone());
        model.addAttribute("tasks", taskService.findDone());
        model.addAttribute("timeZone", timeZone);
        return "tasks/list/done";
    }

    @GetMapping("/list/new")
    public String getNew(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        var timeZone = user.getTimezone() == null ? TimeZone.getDefault() : ZoneId.of(user.getTimezone());
        model.addAttribute("tasks", taskService.findNew());
        model.addAttribute("timeZone", timeZone);
        return "tasks/list/new";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId, Model model, HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        task.addCategories(categoryService.findById(categoriesId));
        if (taskService.save(task) == null) {
            model.addAttribute("message", "Не удалось сохранить задание");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        return "/tasks/one";
    }

    @GetMapping("/done/{id}")
    public String doneTask(Model model, @PathVariable int id) {
        if (!taskService.done(id)) {
            model.addAttribute("message", "Не удалось выполнить задание");
            return "errors/404";
        }
        return "redirect:/tasks/list/done";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        if (!taskService.update(task)) {
            model.addAttribute("message", "Не удалось обновить задание");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        if (!taskService.delete(id)) {
            model.addAttribute("message", "Не удалось удалить задание");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }
}
