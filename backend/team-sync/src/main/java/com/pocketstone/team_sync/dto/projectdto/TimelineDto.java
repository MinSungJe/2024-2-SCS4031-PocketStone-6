package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data //초기 타임라인 추가를 위한 dto
public class TimelineDto {

    private Integer sprintOrder;
    private String sprintContent;
    private Integer sprintDurationWeek;


    public TimelineDto(Integer sprintOrder, String sprintContent, Integer sprintDurationWeek) {
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintDurationWeek = sprintDurationWeek;
    }

}


