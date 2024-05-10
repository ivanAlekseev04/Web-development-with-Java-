package com.fmi.web.lab.raceeventmanagement.mapper;

import com.fmi.web.lab.raceeventmanagement.dto.RacerDTO;
import com.fmi.web.lab.raceeventmanagement.model.Racer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RacerMapper {
    Racer dtoToRacer(RacerDTO racerDTO);
    RacerDTO racerToDTO(Racer racer);
}
