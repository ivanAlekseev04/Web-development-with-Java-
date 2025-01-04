package com.fmi.web.lab.raceeventmanagement.mapper;

import com.fmi.web.lab.raceeventmanagement.dto.TrackDTO;
import com.fmi.web.lab.raceeventmanagement.model.Track;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackDTO trackToDTO(Track track);
    Track dtoToTrack(TrackDTO track);
}
