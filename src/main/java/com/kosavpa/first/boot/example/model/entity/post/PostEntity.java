package com.kosavpa.first.boot.example.model.entity.post;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "post_entity")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title, publicationDate, anons;
    @Column(columnDefinition = "varchar(1000)")
    private String fullText;

    public PostEntity() {
    }

    public PostEntity(String title, String publicationDate, String anons, String fullText) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.anons = anons;
        this.fullText = fullText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
}
