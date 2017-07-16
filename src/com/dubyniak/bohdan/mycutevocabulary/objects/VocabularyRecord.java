package com.dubyniak.bohdan.mycutevocabulary.objects;

import java.io.Serializable;
import java.util.Date;

public class VocabularyRecord implements Serializable {
    public static final int[] LEVELS_OF_POSTPONING = { 1, 3, 7, 14 , 30, 60 };
    private String foreignWord;
    private String definition;
    private String hiddenPrefix;
    private boolean isShown = true;
    private Date showDate;
    private int rememberingLevel;

    public VocabularyRecord(String foreignWord, String definition) {
        this.foreignWord = foreignWord;
        this.definition = definition;
        hiddenPrefix = "";
    }

    public VocabularyRecord(String foreignWord, String definition, boolean isShown) {
        this.foreignWord = foreignWord;
        this.definition = definition;
        this.isShown = isShown;
        hiddenPrefix = isShown ? "" : "(hidden) ";
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public boolean isShown() {
        return isShown;
    }

    public void hide() {
        isShown = false;
        hiddenPrefix = "(hidden) ";
    }

    public void show() {
        isShown = true;
        hiddenPrefix = "";
    }

    public Date getShowDate() {
        return showDate;
    }

    public void resetShowDateAndRememberingLevel() {
        showDate = null;
        rememberingLevel = 0;
    }

    public void postpone() {
        showDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * LEVELS_OF_POSTPONING[rememberingLevel]);
        if (rememberingLevel < LEVELS_OF_POSTPONING.length - 1)
            rememberingLevel++;
    }

    public int getNumberOfPostponedDays() {
        return LEVELS_OF_POSTPONING[rememberingLevel];
    }

    @Override
    public String toString() {
        return hiddenPrefix + foreignWord + " - " + definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyRecord that = (VocabularyRecord) o;
        return foreignWord.equalsIgnoreCase(that.foreignWord) && definition.equalsIgnoreCase(that.definition);

    }

    @Override
    public int hashCode() {
        int result = foreignWord.hashCode();
        result = 31 * result + definition.hashCode();
        return result;
    }

}
