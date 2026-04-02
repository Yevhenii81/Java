package org.example.hw_27_03.controller;

import org.example.hw_27_03.model.Contact;
import org.example.hw_27_03.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("contacts", service.getAll());
        return "list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add";
    }

    @PostMapping("/add")
    public String add(Contact contact) {
        service.save(contact);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("contact", service.getById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Contact contact) {
        service.save(contact);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }
}