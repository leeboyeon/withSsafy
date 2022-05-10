package com.ssafy.withssafy.repository.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.withssafy.entity.Board;
import org.springframework.stereotype.Repository;

import static com.ssafy.withssafy.entity.QBoard.board;

import java.util.List;

@Repository
public class BoardCustomRepositoryImpl implements  BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findByDynamicQuery(Long boardType, Long userId) {
        return jpaQueryFactory
                .selectFrom(board)
                .where(
                        eqUserId(userId),
                        eqBoardType(boardType)
                )
                .fetch();
    }

    private BooleanExpression eqUserId(Long userId){
        return userId == null ? null : board.user.id.eq(userId);
    }

    private BooleanExpression eqBoardType(Long boardType){
        return boardType == null ? null : board.type.id.eq(boardType);
    }
}
