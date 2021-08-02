package com.kurlabo.backend.model;

import com.kurlabo.backend.dto.member.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_Deleted = false") // for soft-deleted.
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String gender;

    private LocalDate date_of_birth;

    @Column(nullable = false)
    private String grade;

    private int check_term;

    private int check_sns;

    private int total_cost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @ColumnDefault("false")
    private boolean isDeleted;

    public MemberInfoDto toMemberInfoDto(Member member) {
        return MemberInfoDto.builder()
                .uid(member.getUid())
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .gender(member.getGender())
                .date_of_birth(member.getDate_of_birth())
                .check_term(member.getCheck_term())
                .check_sns(member.getCheck_sns())
                .build();
    }
}
