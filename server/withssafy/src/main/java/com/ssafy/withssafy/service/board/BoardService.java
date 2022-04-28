package com.ssafy.withssafy.service.board;

import com.ssafy.withssafy.dto.board.BoardRequest;
import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.board.LikeDto;
import com.ssafy.withssafy.dto.recruit.RecruitLikeDto;
import com.ssafy.withssafy.entity.Board;
import com.ssafy.withssafy.entity.LikeManagement;
import com.ssafy.withssafy.entity.RecruitLikeManagement;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.BoardRepository;
import com.ssafy.withssafy.repository.LikeManagementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    private final LikeManagementRepository likeManagementRepository;
    @Transactional
    public void addBoard(BoardRequest boardRequest) {
        Board board = modelMapper.map(boardRequest, Board.class);
        boardRepository.save(board);
    }

    @Transactional
    public void modifyBoardById(BoardRequest boardRequest, Long id) {
        Optional<Board> board = boardRepository.findById(id);

        if (board.isPresent()) {
            board.get().updateBoard(boardRequest);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    public List<BoardResponse> getBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> modelMapper.map(board, BoardResponse.class))
                .collect(Collectors.toList());
    }

    public BoardResponse getBoardById(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.map(value -> modelMapper.map(value, BoardResponse.class)).orElse(null);
    }

    @Transactional
    public void removeBoardById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void doLike(LikeDto likeDto){
        LikeManagement likeManagement = likeManagementRepository.findByBoardIdAndUserId(likeDto.getBoardId(), likeDto.getUserId());
        if(likeManagement == null){
            likeManagement = modelMapper.map(likeDto,LikeManagement.class);
            likeManagementRepository.save(likeManagement);
        }else{
            likeManagementRepository.delete(likeManagement);
        }
    }

    public boolean isLike(Long boardId, Long userId){
        LikeManagement likeManagement = likeManagementRepository.findByBoardIdAndUserId(boardId, userId);
        if(likeManagement == null){
            return false;
        }else{
            return true;
        }
    }
}
