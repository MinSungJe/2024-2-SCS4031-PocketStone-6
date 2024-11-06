package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.ProjectCharterDto;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.charter.*;
import com.pocketstone.team_sync.exception.*;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.charter.*;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectCharterService {

    private final ProjectCharterRepository projectCharterRepository;
    private final ProjectRepository projectRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ScopeRepository scopeRepository;
    private final PositionRepository positionRepository;
    private final VisionRepository visionRepository;
    private final StakeholderRepository stakeholderRepository;
    private final PrincipleRepository principleRepository;
    private final RiskRepository riskRepository;


    //프로젝트 차터 생성
    public ProjectCharterDto saveProjectCharter(User user, Long projectId, ProjectCharterDto projectCharterDto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));

        ProjectValidationUtils.validateProjectOwner(user, project);
        if (projectCharterRepository.findByProjectId(projectId).isPresent())throw new CharterAlreadyExistsException();


        projectCharterRepository.save(projectCharterDto.toProjectCharter(project, projectCharterDto));

        return projectCharterDto;

    }

    //프로젝트 아이디로 프로젝트 차터 조회
    public ProjectCharterDto findByProjectId(User user, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new CharterNotFoundException());
        ProjectValidationUtils.validateCharterOwner(user, projectCharter);


        return new ProjectCharterDto(
                projectCharter.getId(),
                projectCharter.getObjectives(),
                projectCharter.getPositions(),
                projectCharter.getPrinciples(),
                projectCharter.getScopes(),
                projectCharter.getVisions(),
                projectCharter.getStakeholders(),
                projectCharter.getRisks());
    }

    //프로젝트 차터 수정
    public ProjectCharterDto updateProjectCharterByProjectId(User user, Long projectId, ProjectCharterDto projectCharterDto) {
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateCharterOwner(user, projectCharter);

        ProjectCharter updatedProjectCharter = projectCharterDto.toProjectCharter(projectCharter.getProject(), projectCharterDto);
        updateObjectives(projectId, updatedProjectCharter);
        updatePositions(projectId, updatedProjectCharter);
        updatePrinciples(projectId, updatedProjectCharter);
        updateScopes(projectId, updatedProjectCharter);
        updateVisions(projectId, updatedProjectCharter);
        updateStakeholders(projectId, updatedProjectCharter);
        updateRisks(projectId, updatedProjectCharter);

        return projectCharterDto;
    }


    private void updateObjectives(Long projectId, ProjectCharter projectCharter) {
        for (Objective objective : projectCharter.getObjectives()) {
            objectiveRepository.updateObjectiveByProjectId(projectId, objective.getId(), objective.getObjectiveName(), objective.getObjectiveContent());
        }
    }

    private void updatePositions(Long projectId, ProjectCharter projectCharter) {
        for (Position position : projectCharter.getPositions()) {
            positionRepository.updatePositionByProjectId(projectId, position.getId(), position.getPositionName(), position.getPositionContent());
        }
    }

    private void updatePrinciples(Long projectId, ProjectCharter projectCharter) {
        for (Principle principle : projectCharter.getPrinciples()) {
            principleRepository.updatePrincipleByProjectId(projectId, principle.getId(), principle.getPrincipleName(), principle.getPrincipleContent());
        }
    }

    private void updateScopes(Long projectId, ProjectCharter projectCharter) {
        for (Scope scope : projectCharter.getScopes()) {
            scopeRepository.updateScopeByProjectId(projectId, scope.getId(), scope.getScopeName(), scope.getScopeContent());
        }
    }

    private void updateVisions(Long projectId, ProjectCharter projectCharter) {
        for (Vision vision : projectCharter.getVisions()) {
            visionRepository.updateVisionByProjectId(projectId, vision.getId(), vision.getVisionName(), vision.getVisionContent());
        }
    }

    private void updateStakeholders(Long projectId, ProjectCharter projectCharter) {
        for (Stakeholder stakeholder : projectCharter.getStakeholders()) {
            stakeholderRepository.updateStakeholderByProjectId(projectId, stakeholder.getId(), stakeholder.getStakeholderName(), stakeholder.getStakeholderContent());
        }
    }

    private void updateRisks(Long projectId, ProjectCharter projectCharter) {
        for (Risk risk : projectCharter.getRisks()) {
            riskRepository.updateRiskByProjectId(projectId, risk.getId(), risk.getRiskName(), risk.getRiskContent());
        }
    }


}


