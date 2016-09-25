package com.dubyniak.bohdan.mycutevocabulary.objects;

public class VocabularyRecord {
    private String englishWord;
    private String ukrainianWord;
    private String hiddenPrefix;
    private boolean isShown = true;

    public VocabularyRecord(String englishWord, String ukrainianWord) {
        this.englishWord = englishWord;
        this.ukrainianWord = ukrainianWord;
        hiddenPrefix = "";
    }

    public VocabularyRecord(String englishWord, String ukrainianWord, boolean isShown) {
        this.englishWord = englishWord;
        this.ukrainianWord = ukrainianWord;
        this.isShown = isShown;
        hiddenPrefix = isShown ? "" : "(hidden) ";
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getUkrainianWord() {
        return ukrainianWord;
    }

    public void setUkrainianWord(String ukrainianWord) {
        this.ukrainianWord = ukrainianWord;
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

    @Override
    public String toString() {
        return hiddenPrefix + englishWord + " - " + ukrainianWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyRecord that = (VocabularyRecord) o;
        return englishWord.equalsIgnoreCase(that.englishWord) && ukrainianWord.equalsIgnoreCase(that.ukrainianWord);

    }

    @Override
    public int hashCode() {
        int result = englishWord.hashCode();
        result = 31 * result + ukrainianWord.hashCode();
        return result;
    }

}
