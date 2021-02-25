package com.kurlabo.backend.command;

import com.kurlabo.backend.model.Member;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailsCommand {

    private String uid;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private LocalDate date_of_birth;

    public void getMemberDetails (Member member) {
        member.getUid();
        member.getName();
        member.getEmail();
        member.getPhone();
        member.getGender();
        member.getDate_of_birth();
    }
}
