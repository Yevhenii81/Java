package com.example.contactapp.dao;

import com.example.contactapp.model.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    private static final String DB_URL;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver registered successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite Driver not found!");
            e.printStackTrace();
        }
        String projectRoot = "D:\\source\\repos\\Java\\hw_20_03_web";
        DB_URL = "jdbc:sqlite:" + projectRoot.replace("\\", "/") + "/contacts.db";

        System.out.println("=== SQLite DB path: " + DB_URL + " ===");
    }

    public void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS contacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                phone TEXT NOT NULL
            )
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблиця contacts створена або вже існує");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contact c = new Contact();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setPhone(rs.getString("phone"));
                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void add(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getPhone());
            pstmt.executeUpdate();
            System.out.println("Контакт успішно додано в БД");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Contact contact) {
        String sql = "UPDATE contacts SET first_name=?, last_name=?, phone=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getPhone());
            pstmt.setInt(4, contact.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM contacts WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getById(int id) {
        String sql = "SELECT * FROM contacts WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Contact c = new Contact();
                    c.setId(rs.getInt("id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setPhone(rs.getString("phone"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}