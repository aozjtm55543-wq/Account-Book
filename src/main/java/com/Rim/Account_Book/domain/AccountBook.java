package com.Rim.Account_Book.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Long amount;
    private String category;
    private LocalDate date;
    private String memo;

    public AccountBook(String type, Long amount, String category, LocalDate date, String memo) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.memo = memo;

    }

    public void update(String type, Long amount, String category, LocalDate date, String memo) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.memo = memo;
    }
}