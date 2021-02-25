package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    public static final String DEFAULT_GRADE = "일반";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private LocalDate date_of_birth;

    private String grade;

    private int total_cost;

    private String role;

    private boolean isDeleted;
}
