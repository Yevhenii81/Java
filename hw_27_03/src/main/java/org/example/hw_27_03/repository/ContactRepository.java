package org.example.hw_27_03.repository;

import org.example.hw_27_03.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}