package com.fmi.raceeventmanagement.mapper;

import com.fmi.raceeventmanagement.dto.RacerDTO;
import com.fmi.raceeventmanagement.model.Racer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RacerMapper {
    Racer dtoToRacer(RacerDTO racerDTO);
    RacerDTO racerToDTO(Racer racer);
}
