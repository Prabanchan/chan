package com.example.quiz.service;

import com.example.quiz.model.*;
import com.example.quiz.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    ChoiceRepo choiceRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    AnswerRepo answerRepo;

    public void createQuiz() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter quiz title: ");
        String title = in.nextLine();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);

        System.out.println("Enter number of questions to be created for the quiz (1 - 10) ");
        int questionsCount = in.nextInt();
        if (questionsCount < 1 || questionsCount > 10) {
            System.out.println("Invalid count");
            return;
        }

        System.out.println("Enter number of choices to be created for each question (2 - 4) ");
        int choicesCount = in.nextInt();
        if (choicesCount < 2 || choicesCount > 4) {
            System.out.println("Invalid count");
            return;
        }

        quizRepo.save(quiz);
        in.nextLine();

        for (int i = 0; i < questionsCount; i++) {
            System.out.println("Enter the question number " + (i + 1));
            String questionString = in.nextLine();
            System.out.println("Enter the question difficulty ");
            Integer difficulty = in.nextInt();
            System.out.println("Enter the question topics (comma separated)");
            String questionTopics = in.nextLine();
            Question question = new Question();
            question.setQuestion(questionString);
            question.setDifficulty(difficulty);
            question.setTopics(questionTopics);
            question.setQuiz(quiz);
            questionRepo.save(question);
            in.nextLine();

            for (int j = 0; j < choicesCount; j++) {
                System.out.println("Enter the choice number " + (j + 1));
                String choiceString = in.nextLine();
                System.out.println("Enter validity of the choice (true or false)");
                String valid = in.nextLine();

                Choice choice = new Choice();
                choice.setChoice(choiceString);
                choice.setValid(valid.equalsIgnoreCase("true"));
                choice.setQuestion(question);
                choiceRepo.save(choice);
            }

        }


    }

    public void playQuiz(Quiz quiz) {
        System.out.println("Starting quiz " + quiz.getTitle());
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the student name");
        Student student = new Student();
        student.setName(in.nextLine());
        studentRepo.save(student);

        List<Question> questions = questionRepo.findByQuiz(quiz);
        Integer score = 0;
        Integer total = 0;
        Double average = getAverageScore(quiz);

        for (Question question : questions) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Choices: ");
            total += question.getDifficulty();
            List<Choice> choices = choiceRepo.findByQuestion(question);
            for (int i = 0; i < choices.size(); i++) {
                System.out.println((i + 1) + ". " + choices.get(i).getChoice());
            }

            Answer answer = new Answer();
            answer.setStudent(student);
            answer.setQuestion(question);
            System.out.println("Enter the choice number: ");
            int choiceNumber = in.nextInt();
            if (choiceNumber < 1 || choiceNumber > choices.size()) {
                System.out.println("Invalid choice");
            } else {
                Choice choice = choices.get(choiceNumber - 1);
                answer.setChoice(choice);
                if (choice.isValid()) {
                    score += question.getDifficulty();
                }
            }
            answerRepo.save(answer);
            in.nextLine();
        }
        System.out.println("Thanks for completing the quiz");
        System.out.println("Your score is " + score + "/" + total);
        System.out.println("Average score is : " + average);
    }

    public Double getAverageScore(Quiz quiz) {
        List<Answer> answerList = answerRepo.findByQuestion_quiz(quiz);
        OptionalDouble average = answerList.stream()
                .collect(Collectors.groupingBy(a -> a.getStudent(),
                        Collectors.summingInt(a -> a.getChoice().isValid() ? a.getQuestion().getDifficulty() : 0)))
                .values().stream().mapToDouble(a -> a.intValue()).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }
}
