package com.ssafy.withssafy.dto.study;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.core.io.FileSystemResource;

@Data
@ToString
public class StudyDto {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private String category;

    private int sbLimit;

    private String writeDt;

    @JsonIgnore
    private String photoPath;

    private FileSystemResource photoFile;

    private int isOuting;
}
