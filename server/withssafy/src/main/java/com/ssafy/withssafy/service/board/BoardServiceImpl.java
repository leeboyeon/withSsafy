package com.ssafy.withssafy.service.board;

import com.ssafy.withssafy.dto.board.BoardDto;
import com.ssafy.withssafy.entity.Board;
import com.ssafy.withssafy.entity.BoardType;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void save(BoardDto boardSave) {
        // PK 유무에 따라 insert, update 분기
        Board board = modelMapper.map(boardSave, Board.class);
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
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
