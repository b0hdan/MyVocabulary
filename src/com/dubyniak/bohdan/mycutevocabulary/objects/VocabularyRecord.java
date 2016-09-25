package com.dubyniak.bohdan.mycutevocabulary.objects;

public class VocabularyRecord {
    private String englishWord;
    private String ukrainianWord;
    private boolean isShown = true;

    public VocabularyRecord(String englishWord, String ukrainianWord) {
        this.englishWord = englishWord;
        this.ukrainianWord = ukrainianWord;
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

    public void setShown(boolean shown) {
        isShown = shown;
    }

    @Override
    public String toString() {
        return englishWord + " - " + ukrainianWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyRecord that = (VocabularyRecord) o;
        return englishWord.equals(that.englishWord) && ukrainianWord.equals(that.ukrainianWord);

    }

    @Override
    public int hashCode() {
        int result = englishWord.hashCode();
        result = 31 * result + ukrainianWord.hashCode();
        return result;
    }

}
