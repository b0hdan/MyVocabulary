package com.dubyniak.bohdan.mycutevocabulary.controllers;

import com.dubyniak.bohdan.mycutevocabulary.interfaces.Storage;
import com.dubyniak.bohdan.mycutevocabulary.objects.VocabularyRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FlashcardsController {
    private static int numberOfNewCardsToUse = 15;
    private static Storage storage;
    private static Random random = new Random();
    private List<VocabularyRecord> flashcards = new ArrayList<>();
    private int currentCard;
    private int realNumberOfCards;

    @FXML
    private Label lblCount;

    @FXML
    private Label lblQuestion;

    @FXML
    private Label lblAnswer;

    @FXML
    private VBox cardLabelsPane;

    @FXML
    private BorderPane noCardsLabelPane;

    @FXML
    Button btnPositive;

    @FXML
    private HBox answerButtonsPane;

    @FXML
    private HBox checkButtonPane;

    static void setStorage(Storage storage) {
        FlashcardsController.storage = storage;
    }

    void start() {
        int counterOfNewCards = 0;
        List<Integer> numbersOfCards = new ArrayList<>();

        flashcards.clear();

        flashcards.addAll(storage.read().stream().filter(flashcard -> flashcard.getShowDate() != null).filter(flashcard -> !flashcard.getShowDate().after(new Date())).collect(Collectors.toList()));

        for (VocabularyRecord flashcard : storage.read())
            if (flashcard.getShowDate() == null)
                counterOfNewCards++;

        if (flashcards.size() == 0 && counterOfNewCards == 0) {
            lblCount.setVisible(false);
            cardLabelsPane.setVisible(false);
            answerButtonsPane.setVisible(false);
            checkButtonPane.setVisible(false);
            noCardsLabelPane.setVisible(true);
            return;
        }

        if (counterOfNewCards <= numberOfNewCardsToUse)
            flashcards.addAll(storage.read().stream().filter(flashcard -> flashcard.getShowDate() == null).collect(Collectors.toList()));
        else
            for (int i = 0; i < numberOfNewCardsToUse; i++) {
                int temp = random.nextInt(counterOfNewCards);
                if (!numbersOfCards.contains(temp))
                    numbersOfCards.add(temp);
                else i--;
            }
        for (Integer numbersOfCard : numbersOfCards) {
            counterOfNewCards = 0;
            for (VocabularyRecord flashcard : storage.read())
                if (flashcard.getShowDate() == null)
                    if (counterOfNewCards < numbersOfCard)
                        counterOfNewCards++;
                    else {
                        flashcards.add(flashcard);
                        break;
                    }
        }

        realNumberOfCards = flashcards.size();
        showNextCard();
    }

    public void negativeButtonClicked(ActionEvent actionEvent) {
        flashcards.get(currentCard).resetShowDateAndRememberingLevel();
        if (flashcards.get(currentCard).isReversed())
            flashcards.get(currentCard).setReversed(false);
        showNextCard();
    }

    public void positiveButtonPressed(ActionEvent actionEvent) {
        if (flashcards.get(currentCard).isReversed()) {
            flashcards.get(currentCard).setReversed(false);
            flashcards.get(currentCard).postpone();
            flashcards.remove(currentCard);
        } else
            flashcards.get(currentCard).setReversed(true);

        if (flashcards.size() != 0)
            showNextCard();
        else {
            lblCount.setVisible(false);
            cardLabelsPane.setVisible(false);
            noCardsLabelPane.setVisible(true);
            answerButtonsPane.setVisible(false);
        }
    }

    public void checkButtonClicked(ActionEvent actionEvent) {
        if (flashcards.get(currentCard).isReversed())
            lblAnswer.setText(flashcards.get(currentCard).getForeignWord());
        else
            lblAnswer.setText(flashcards.get(currentCard).getDefinition());
        lblAnswer.setVisible(true);
        checkButtonPane.setVisible(false);
        answerButtonsPane.setVisible(true);
        btnPositive.requestFocus();
    }

    private void showNextCard() {
        currentCard = random.nextInt(flashcards.size());
        if (flashcards.get(currentCard).isReversed())
            lblQuestion.setText(flashcards.get(currentCard).getDefinition());
        else
            lblQuestion.setText(flashcards.get(currentCard).getForeignWord());
        lblCount.setText(realNumberOfCards - flashcards.size() + "/" + realNumberOfCards);
        btnPositive.setText("I know this word\n(Show in " + flashcards.get(currentCard).getNumberOfPostponedDays() +
                (flashcards.get(currentCard).getNumberOfPostponedDays() == 1 ? " day)" : " days)"));
        lblCount.setVisible(true);
        lblAnswer.setVisible(false);
        cardLabelsPane.setVisible(true);
        noCardsLabelPane.setVisible(false);
        answerButtonsPane.setVisible(false);
        checkButtonPane.setVisible(true);
    }
}
