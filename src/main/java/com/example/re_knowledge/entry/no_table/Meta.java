package com.example.re_knowledge.entry.no_table;

public class Meta {
    private String name;
    private String author;
    private String version;

    public Meta() {
    }

    public Meta(String name, String author, String version) {
        this.name = name;
        this.author = author;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
