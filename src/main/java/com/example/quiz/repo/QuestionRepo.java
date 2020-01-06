package com.example.quiz.repo;

import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findByQuiz(Quiz quiz);
}
