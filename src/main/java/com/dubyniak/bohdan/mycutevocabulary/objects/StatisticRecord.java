package com.dubyniak.bohdan.mycutevocabulary.objects;

public class StatisticRecord {
    private String directory;
    private Integer numberOfNewWords;
    private Integer numberOfNeedRepeatingWords;
    private Integer numberOfAllWords;

    public StatisticRecord() {
    }

    public StatisticRecord(String directory, Integer numberOfNewWords, Integer numberOfNeedRepeatingWords, Integer numberOfAllWords) {
        this.directory = directory;
        this.numberOfNewWords = numberOfNewWords;
        this.numberOfNeedRepeatingWords = numberOfNeedRepeatingWords;
        this.numberOfAllWords = numberOfAllWords;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Integer getNumberOfNewWords() {
        return numberOfNewWords;
    }

    public void setNumberOfNewWords(Integer numberOfNewWords) {
        this.numberOfNewWords = numberOfNewWords;
    }

    public Integer getNumberOfNeedRepeatingWords() {
        return numberOfNeedRepeatingWords;
    }

    public void setNumberOfNeedRepeatingWords(Integer numberOfNeedRepeatingWords) {
        this.numberOfNeedRepeatingWords = numberOfNeedRepeatingWords;
    }

    public Integer getNumberOfAllWords() {
        return numberOfAllWords;
    }

    public void setNumberOfAllWords(Integer numberOfAllWords) {
        this.numberOfAllWords = numberOfAllWords;
    }
}
