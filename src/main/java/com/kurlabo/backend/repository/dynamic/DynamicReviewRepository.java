package com.kurlabo.backend.repository.dynamic;

import com.kurlabo.backend.dto.review.QReviewDto;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.QReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DynamicReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QReview qReview = QReview.review;

    public List<ReviewDto> findByMember(Member member){
        return jpaQueryFactory
                .select(new QReviewDto(qReview.id, qReview.product.id, qReview.product.name, qReview.title,
                        qReview.content, qReview.regDate, qReview.help))
                .from(qReview)
                .where(qReview.member.eq(member))
                .fetch();
    }
}
