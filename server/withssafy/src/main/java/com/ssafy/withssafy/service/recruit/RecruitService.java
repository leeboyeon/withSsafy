package com.ssafy.withssafy.service.recruit;

import com.ssafy.withssafy.dto.recruit.RecruitDto;
import com.ssafy.withssafy.dto.recruit.RecruitListResDto;
import com.ssafy.withssafy.entity.Recruit;
import com.ssafy.withssafy.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecruitService {

    private final RecruitRepository recruitRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void saveRecruit(RecruitDto recruitDto){
        Recruit recruit = modelMapper.map(recruitDto, Recruit.class);
        recruitRepository.save(recruit);
    }

    @Transactional
    public void deleteRecruit(Long id){
        recruitRepository.deleteById(id);
    }

    public RecruitDto findById(Long id) {
        Recruit recruit = recruitRepository.findById(id).get();
        return modelMapper.map(recruit, RecruitDto.class);
    }

    public List<RecruitListResDto> findAll(){
        List<Recruit> recruits = recruitRepository.findAll();
        return recruits.stream().map(recruit -> modelMapper.map(recruit, RecruitListResDto.class))
                .collect(Collectors.toList());
    }
}
