package com.fmi.raceeventmanagement.mapper;

import com.fmi.raceeventmanagement.dto.TeamDTO;
import com.fmi.raceeventmanagement.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RacerMapper.class})
public interface TeamMapper {
    Team dtoToTeam(TeamDTO teamDTO);
    TeamDTO teamToDTO(Team team);
}
