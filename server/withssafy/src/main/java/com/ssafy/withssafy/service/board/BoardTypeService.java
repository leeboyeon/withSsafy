package com.ssafy.withssafy.service.board;

import com.ssafy.withssafy.dto.board.BoardTypeDto;
import com.ssafy.withssafy.entity.BoardType;
import com.ssafy.withssafy.repository.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardTypeService {

    private final ModelMapper modelMapper;

    private final BoardTypeRepository boardTypeRepository;

    public List<BoardTypeDto> getBoardTypes() {
        List<BoardType> boardTypes = boardTypeRepository.findAll();

        return boardTypes.stream().map(boardType -> modelMapper.map(boardType, BoardTypeDto.class))
                .collect(Collectors.toList());
    }
}
