package com.example.quiz.service;

import com.example.quiz.model.Quiz;
import com.example.quiz.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Scanner;

@Service
public class AdminService {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepo quizRepo;

    public void handleQuizAdmin() {
        System.out.println("Enter Admin password: ");
        Scanner in = new Scanner(System.in);
        String password = in.nextLine();
        if (!password.equals("Admin@123")) {
            System.out.println("Invalid Password");
            return;
        }
        System.out.println("Welcome Admin");
        System.out.println("Enter your choice");
        System.out.println("1. Create new quiz");
        System.out.println("2. Get Average score for a quiz");
        System.out.println("3. Return to main menu");

        int choice = in.nextInt();
        if (choice == 1) {
            quizService.createQuiz();
        } else if(choice == 2){
            List<Quiz> quizList = quizRepo.findAll();

            if (CollectionUtils.isEmpty(quizList)) {
                System.out.println("No available quizzes to play");
                return;
            }
            for (int i = 1; i <= quizList.size(); i++) {
                System.out.println(i + " " + quizList.get(i - 1).getTitle());
            }
            System.out.println("Enter the quiz number to get average for");
            int quizNumber = in.nextInt();

            if (quizNumber < 1 || quizNumber > quizList.size()) {
                System.out.println("Invalid quiz number");
                return;
            }
            System.out.println("Average Score is :" + quizService.getAverageScore(quizList.get(quizNumber - 1)));
        }
    }
}
