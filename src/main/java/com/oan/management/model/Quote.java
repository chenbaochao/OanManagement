package com.oan.management.model;

/**
 * Created by Oan on 23/01/2018.
 */
public class Quote {
    private String quote;
    private String author;
    private String cat;

    public Quote() {
    }

    public Quote(String quote, String author, String cat) {
        this.quote = quote;
        this.author = author;
        this.cat = cat;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
