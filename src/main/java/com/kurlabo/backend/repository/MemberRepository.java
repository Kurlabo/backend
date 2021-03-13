package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByUid(String uid);

    List<Member> findAllByEmail(String email);

    List<Member> findAllByUid(String uid);

    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findByNameAndUidAndEmail(String name, String uid, String email);
}
