package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.objects.TestMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class TestController {
    private int currentQuestion, correctAnswers;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label lblQuestion;

    @FXML
    private TextField txtAnswer;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblCheck;
    
    @FXML
    private Label lblCount;

    public void nextButtonClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().setOnCloseRequest(event -> restartTest());
        if (btnNext.getText().equals("Check")) {
            if (TestMaker.getRecords().get(currentQuestion).getDefinition().equalsIgnoreCase(txtAnswer.getText())) {
                correctAnswers++;
                TestMaker.getRecords().get(currentQuestion).hide();
                lblCheck.setText("Correct!");
                lblCheck.setTextFill(Paint.valueOf("Green"));
            } else {
                lblCheck.setText("Incorrect! (answer: " +
                        TestMaker.getRecords().get(currentQuestion).getDefinition() + ")");
                lblCheck.setTextFill(Paint.valueOf("Red"));
            }
            txtAnswer.setEditable(false);
            currentQuestion++;
            progressBar.setProgress((double) currentQuestion / TestMaker.getRecords().size());
            lblCount.setText(correctAnswers + "/" + TestMaker.getRecords().size());
            if ((double) currentQuestion / TestMaker.getRecords().size() == 1)
                btnNext.setText("Restart");
            else
                btnNext.setText("Next");
        } else if (btnNext.getText().equals("Restart"))
            restartTest();
        else if (btnNext.getText().equals("Next")) {
            lblQuestion.setText(TestMaker.getRecords().get(currentQuestion).getForeignWord());
            txtAnswer.clear();
            txtAnswer.requestFocus();
            lblCheck.setText("");
            txtAnswer.setEditable(true);
            btnNext.setText("Check");
        }
    }

    private void restartTest() {
        correctAnswers = 0;
        currentQuestion = 0;
        txtAnswer.clear();
        txtAnswer.requestFocus();
        lblCheck.setText("");
        txtAnswer.setEditable(true);
        btnNext.setText("Check");
        TestMaker.startTest();
        lblQuestion.setText(TestMaker.getRecords().get(0).getForeignWord());
        progressBar.setProgress(0);
        lblCount.setText("0/" + TestMaker.getRecords().size());
    }
}
