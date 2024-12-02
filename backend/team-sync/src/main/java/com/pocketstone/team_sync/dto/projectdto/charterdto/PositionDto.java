package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.charter.Position;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.enums.Role;
import com.pocketstone.team_sync.entity.enums.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PositionDto {
    private Long id;
    @NotEmpty
    private Role positionName;
    @NotNull
    private Skill positionContent;

    public PositionDto(Role positionName, Skill positionContent) {
        this.positionName = positionName;
        this.positionContent = positionContent;
    }

    @JsonCreator
    public PositionDto(@JsonProperty("id") Long id,
                        @JsonProperty("positionName") Role positionName,
                        @JsonProperty("positionContent") Skill positionContent) {
        this.id = id;
        this.positionName = positionName;
        this.positionContent = positionContent;
    }

    public Position toPosition(ProjectCharter projectCharter, PositionDto positionDto) {
        return Position.createPositionWithoutCount()
                .projectCharter(projectCharter)
                .positionName(positionDto.getPositionName())
                .positionContent(positionDto.getPositionContent())
                .build();
    }

    public PositionDto toPositionDto(Position position) {
        return new PositionDto(position.getPositionName(), position.getPositionContent());
    }
}
