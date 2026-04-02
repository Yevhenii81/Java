package org.example.hw_27_03.service;

import org.example.hw_27_03.model.Contact;
import org.example.hw_27_03.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    public List<Contact> getAll() {
        return repo.findAll();
    }

    public Contact getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void save(Contact contact) {
        repo.save(contact);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}