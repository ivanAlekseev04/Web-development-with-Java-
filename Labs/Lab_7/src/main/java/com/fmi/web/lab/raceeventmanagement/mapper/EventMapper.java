package com.fmi.web.lab.raceeventmanagement.mapper;

import com.fmi.web.lab.raceeventmanagement.dto.EventDTO;
import com.fmi.web.lab.raceeventmanagement.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeamMapper.class,TrackMapper.class})
public interface EventMapper {
    Event dtoToEvent(EventDTO eventDTO);
    EventDTO eventToDTO(Event event);
}
