package net.javaguides.sms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

class StudentControllerTest {
    @Test
    void testListStudents() {

        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.findAll()).thenReturn(new ArrayList<>());
        StudentController studentController = new StudentController(new StudentServiceImpl(studentRepository));
        assertEquals("students", studentController.listStudents(new ConcurrentModel()));
        verify(studentRepository).findAll();
    }

    @Test
    void testCreateStudentForm() {

        StudentController studentController = new StudentController(new StudentServiceImpl(mock(StudentRepository.class)));
        assertEquals("create_student", studentController.createStudentForm(new ConcurrentModel()));
    }

    @Test
    void testSaveStudent() {

        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setId(123L);
        student.setLastName("Doe");
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.save((Student) any())).thenReturn(student);
        StudentController studentController = new StudentController(new StudentServiceImpl(studentRepository));

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setId(123L);
        student1.setLastName("Doe");
        assertEquals("redirect:/students", studentController.saveStudent(student1));
        verify(studentRepository).save((Student) any());
    }


    @Test
    void testEditStudentForm() {

        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setId(123L);
        student.setLastName("Doe");
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.findById((Long) any())).thenReturn(Optional.of(student));
        StudentController studentController = new StudentController(new StudentServiceImpl(studentRepository));
        assertEquals("edit_student", studentController.editStudentForm(1L, new ConcurrentModel()));
        verify(studentRepository).findById((Long) any());
    }


    @Test
    void testUpdateStudent() {

        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setId(123L);
        student.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student);

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setId(123L);
        student1.setLastName("Doe");
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.save((Student) any())).thenReturn(student1);
        when(studentRepository.findById((Long) any())).thenReturn(ofResult);
        StudentController studentController = new StudentController(new StudentServiceImpl(studentRepository));

        Student student2 = new Student();
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setId(123L);
        student2.setLastName("Doe");
        assertEquals("redirect:/students", studentController.updateStudent(1L, student2, new ConcurrentModel()));
        verify(studentRepository).save((Student) any());
        verify(studentRepository).findById((Long) any());
    }

    @Test
    void testDeleteStudent() {

        StudentRepository studentRepository = mock(StudentRepository.class);
        doNothing().when(studentRepository).deleteById((Long) any());
        assertEquals("redirect:/students",
                (new StudentController(new StudentServiceImpl(studentRepository))).deleteStudent(1L));
        verify(studentRepository).deleteById((Long) any());
    }
}