package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:contacts.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createTable(conn);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addContact(conn, scanner);
                    case 2 -> updateContact(conn, scanner);
                    case 3 -> deleteContact(conn, scanner);
                    case 4 -> viewContacts(conn);
                    case 0 -> {
                        System.out.println("До побачення!");
                        return;
                    }
                    default -> System.out.println("Невірний вибір! Спробуй ще раз.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS contacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                phone TEXT NOT NULL
            )
            """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void printMenu() {
        System.out.println("\nСписок контактів");
        System.out.println("1. Додати контакт");
        System.out.println("2. Оновити контакт");
        System.out.println("3. Видалити контакт");
        System.out.println("4. Переглянути всі контакти");
        System.out.println("0. Вихід");
        System.out.print("Виберіть пункт: ");
    }

    private static void viewContacts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM contacts ORDER BY id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | Ім'я | Прізвище | Телефон");
            System.out.println("------------------------------------");
            boolean hasContacts = false;
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s%n", rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone"));
                hasContacts = true;
            }
            if (!hasContacts) System.out.println("Контактів поки немає");
        }
    }

    private static void addContact(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Ім'я: ");
        String firstName = sc.nextLine();
        System.out.print("Прізвище: ");
        String lastName = sc.nextLine();
        System.out.print("Номер телефону: ");
        String phone = sc.nextLine();

        String sql = "INSERT INTO contacts (first_name, last_name, phone) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
            System.out.println("Контакт успішно додано!");
        }
    }

    private static void updateContact(Connection conn, Scanner sc) throws SQLException {
        viewContacts(conn);
        System.out.print("\nВведіть ID контакту для оновлення: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Нове ім'я: ");
        String firstName = sc.nextLine();
        System.out.print("Нове прізвище: ");
        String lastName = sc.nextLine();
        System.out.print("Новий номер телефону: ");
        String phone = sc.nextLine();

        String sql = "UPDATE contacts SET first_name=?, last_name=?, phone=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.setInt(4, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Контакт оновлено" : "Контакт не знайдено");
        }
    }

    private static void deleteContact(Connection conn, Scanner sc) throws SQLException {
        viewContacts(conn);
        System.out.print("\nВведіть ID контакту для видалення: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM contacts WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Контакт видалено" : "Контакт не знайдено");
        }
    }
}