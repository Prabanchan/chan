package com.example.quiz.repo;

import com.example.quiz.model.Choice;
import com.example.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepo extends JpaRepository<Choice, Long> {

    List<Choice> findByQuestion(Question question);

}
