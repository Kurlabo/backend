package com.kurlabo.backend.memberjoin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String gender;

    private String dateOfBirth;

    private String grade;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<Cart> cartList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<OrderHistory> orderHistoryList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<UserReview> userReviewList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<PrivateInquiry> privateInquiryList;


}
