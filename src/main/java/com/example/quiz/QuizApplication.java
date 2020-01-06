package com.example.quiz;

import com.example.quiz.service.AdminService;
import com.example.quiz.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner {

    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Select a choice from below");
        System.out.println("1. Manage quiz as Admin");
        System.out.println("2. Play quiz as Student");
        System.out.println("3. Exit");
        Integer choice = in.nextInt();
        while (choice != 3) {
            if (choice == 1) {
                adminService.handleQuizAdmin();
            } else if (choice == 2) {
                studentService.handleQuizStudent();
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Select a choice from below");
            System.out.println("1. Manage quiz as Admin");
            System.out.println("2. Play quiz as Student");
            System.out.println("3. Exit");
            choice = in.nextInt();

        }
        in.close();
        System.exit(0);
    }
}
