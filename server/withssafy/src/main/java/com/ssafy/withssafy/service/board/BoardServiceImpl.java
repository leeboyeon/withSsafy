package com.ssafy.withssafy.service.board;

import com.ssafy.withssafy.dto.board.BoardDto;
import com.ssafy.withssafy.entity.Board;
import com.ssafy.withssafy.entity.BoardType;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(BoardDto boardDto) {
        // PK 유무에 따라 insert, update 분기
        Board board = modelMapper.map(boardDto, Board.class);
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void save(BoardDto boardDto, Long id) {
        boardDto.setId(id);
        Board board = modelMapper.map(boardDto, Board.class);

        boardRepository.save(board);
    }


    @Override
    public List<BoardDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BoardDto findById(Long id) {
        Board board = boardRepository.findById(id).get();

        return modelMapper.map(board, BoardDto.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
