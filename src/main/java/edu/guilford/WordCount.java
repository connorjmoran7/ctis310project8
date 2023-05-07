package edu.guilford;

public class WordCount implements Comparable<WordCount> {

    // This class will serve the purpose of measuring how many times a word appears
    // in the file.

    // Attributes

    private String word;
    private int count;

    // Constructor

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    // Getters and Setters

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // Methods

    // This method will compare the words in the file and count how many times they
    // appear.

    public int compareTo(WordCount wordCount) {
        if (this.count > wordCount.count) {
            return 1;
        } else if (this.count < wordCount.count) {
            return -1;
        } else {
            return 0;
        }
    }

}
