package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUid(String uid);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUid(String uid);
    Optional<Member> findByPhone(String number);
}
