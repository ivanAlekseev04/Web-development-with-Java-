package com.fmi.web.lab.raceeventmanagement.mapper;

import com.fmi.web.lab.raceeventmanagement.dto.TeamDTO;
import com.fmi.web.lab.raceeventmanagement.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RacerMapper.class})
public interface TeamMapper {
    Team dtoToTeam(TeamDTO teamDTO);

    TeamDTO teamToDTO(Team team);
}
