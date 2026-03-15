import org.example.Student;
import org.example.Group;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTests {

    @Test
    void testGroupCreation() {
        Group group = new Group("P-26");

        assertEquals("P-26", group.getGroupName());
        assertTrue(group.isEmpty());
        assertEquals(0, group.getStudentCount());
    }

    @Test
    void testAddStudent() {
        Group group = new Group("P-26");

        Student student = new Student("Ivan Ivanov", LocalDate.of(2000,1,1));
        group.addStudent(student);

        assertEquals(1, group.getStudentCount());
        assertTrue(group.getStudents().contains(student));
    }

    @Test
    void testInvalidGroupName() {
        assertThrows(IllegalArgumentException.class, () -> new Group(""));
        assertThrows(IllegalArgumentException.class, () -> new Group(null));
    }

    @Test
    void testAddNullStudent() {
        Group group = new Group("P-26");

        assertThrows(IllegalArgumentException.class, () -> group.addStudent(null));
    }

    @Test
    void testRemoveStudentByName() {

        Group group = new Group("P-26");

        Student s1 = new Student("Ivan Ivanov", LocalDate.of(2000,1,1));
        Student s2 = new Student("Petro Petrov", LocalDate.of(2001,2,2));

        group.addStudent(s1);
        group.addStudent(s2);

        boolean removed = group.removeStudentByName("Ivan Ivanov");

        assertTrue(removed);
        assertEquals(1, group.getStudentCount());
    }

    @Test
    void testSortByAverageExamGrade() {

        Group group = new Group("P-26");

        Student s1 = new Student("Ivan Ivanov", LocalDate.of(2000,1,1));
        Student s2 = new Student("Petro Petrov", LocalDate.of(2001,2,2));

        s1.addExamGrade(6);
        s1.addExamGrade(7);

        s2.addExamGrade(10);
        s2.addExamGrade(11);

        group.addStudent(s1);
        group.addStudent(s2);

        List<Student> sorted = group.getStudentsSortedByAverageExamGrade();

        assertEquals("Petro Petrov", sorted.get(0).getFullName());
    }
}