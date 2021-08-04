package com.kurlabo.backend.model;

import com.kurlabo.backend.dto.member.MemberDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
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

    public void update(MemberDto dto, PasswordEncoder encoder){
        if(dto.getPassword() != null && (dto.getPassword().equals(dto.getCheckPassword()))){
            this.password = encoder.encode(dto.getPassword());
        }
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.date_of_birth = dto.getDate_of_birth();
        this.gender = dto.getGender();
        this.check_term = dto.getCheck_term();
        this.check_sns = dto.getCheck_sns();
    }

    public MemberDto toMemberDto(){
        return MemberDto.builder()
                .uid(this.getUid())
                .name(this.getName())
                .phone(this.getPhone())
                .email(this.getEmail())
                .gender(this.getGender())
                .date_of_birth(this.getDate_of_birth())
                .check_term(this.getCheck_term())
                .check_sns(this.getCheck_sns())
                .build();
    }

    public void setDeleted(boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }
}
