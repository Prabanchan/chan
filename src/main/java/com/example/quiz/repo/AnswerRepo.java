package com.example.quiz.repo;

import com.example.quiz.model.Answer;
import com.example.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_quiz(Quiz quiz);
}
