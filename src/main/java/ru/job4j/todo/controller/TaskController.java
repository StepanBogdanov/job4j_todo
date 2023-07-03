package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping("/list/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list/all";
    }

    @GetMapping("/list/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findDone());
        return "tasks/list/done";
    }

    @GetMapping("/list/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/list/new";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
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
