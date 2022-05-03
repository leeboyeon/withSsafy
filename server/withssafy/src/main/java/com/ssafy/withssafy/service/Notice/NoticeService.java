package com.ssafy.withssafy.service.Notice;

import com.ssafy.withssafy.dto.Notice.NoticeModifyReqDto;
import com.ssafy.withssafy.dto.Notice.NoticeReqDto;
import com.ssafy.withssafy.dto.Notice.NoticeResDto;
import com.ssafy.withssafy.dto.Notice.NoticeTypeDto;
import com.ssafy.withssafy.dto.board.BoardRequest;
import com.ssafy.withssafy.dto.board.BoardResponse;
import com.ssafy.withssafy.dto.board.LikeDto;
import com.ssafy.withssafy.entity.Board;
import com.ssafy.withssafy.entity.LikeManagement;
import com.ssafy.withssafy.entity.Notice;
import com.ssafy.withssafy.entity.NoticeType;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.NoticeRepository;
import com.ssafy.withssafy.repository.NoticeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;
    private final NoticeTypeRepository noticeTypeRepository;

    @Transactional
    public void addNotice(NoticeReqDto noticeReqDto) {
        noticeReqDto.setWriteDt(LocalDateTime.now());
        Notice notice = modelMapper.map(noticeReqDto, Notice.class);
        noticeRepository.save(notice);
    }

    @Transactional
    public void modifyNoticeById(NoticeModifyReqDto noticeReqDto, Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);

        if (notice.isPresent()) {
            notice.get().updateNotice(noticeReqDto);
        } else {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    public List<NoticeResDto> getNotices(Long classRoomId) {
        List<Notice> notices = noticeRepository.findAllByClassRoomId(classRoomId);
        return notices.stream().map(notice -> modelMapper.map(notice, NoticeResDto.class))
                .collect(Collectors.toList());
    }

    public NoticeResDto getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.map(value -> modelMapper.map(value, NoticeResDto.class)).orElse(null);
    }

    @Transactional
    public void removeNoticeById(Long id) {
        noticeRepository.deleteById(id);
    }

    public List<NoticeTypeDto> getNoticeTypes() {
        List<NoticeType> noticeTypeDtos = noticeTypeRepository.findAll();
        return noticeTypeDtos.stream().map(noticeType -> modelMapper.map(noticeType,NoticeTypeDto.class))
                .collect(Collectors.toList());
    }
}
