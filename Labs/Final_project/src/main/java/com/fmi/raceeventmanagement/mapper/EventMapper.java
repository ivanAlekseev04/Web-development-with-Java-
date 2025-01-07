package com.fmi.raceeventmanagement.mapper;

import com.fmi.raceeventmanagement.dto.EventDTO;
import com.fmi.raceeventmanagement.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeamMapper.class,TrackMapper.class})
public interface EventMapper {
    Event dtoToEvent(EventDTO eventDTO);
    EventDTO eventToDTO(Event event);
}
