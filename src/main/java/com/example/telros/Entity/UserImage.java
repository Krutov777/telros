package com.example.telros.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "user_image")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_contact_id")
    @JsonManagedReference
    private UserContactInfo userContactInfo;

    public UserImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UserContactInfo getUserContactInfo() {
        return userContactInfo;
    }

    public void setUserContactInfo(UserContactInfo userContactInfo) {
        this.userContactInfo = userContactInfo;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", userContactInfo=" + userContactInfo +
                '}';
    }
}
