package com.example.quiz.service;

import com.example.quiz.model.Quiz;
import com.example.quiz.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Scanner;

@Service
public class StudentService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizService quizService;

    public void handleQuizStudent() {
        List<Quiz> quizList = quizRepo.findAll();

        if (CollectionUtils.isEmpty(quizList)) {
            System.out.println("No available quizzes to play");
            return;
        }
        for (int i = 1; i <= quizList.size(); i++) {
            System.out.println(i + " " + quizList.get(i - 1).getTitle());
        }
        System.out.println("Enter the quiz number you would like to play");
        Scanner in = new Scanner(System.in);
        int quizNumber = in.nextInt();

        if (quizNumber < 1 || quizNumber > quizList.size()) {
            System.out.println("Invalid quiz number");
            return;
        }
        quizService.playQuiz(quizList.get(quizNumber - 1));
    }
}
