package com.fmi.raceeventmanagement.mapper;

import com.fmi.raceeventmanagement.dto.TrackDTO;
import com.fmi.raceeventmanagement.model.Track;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackDTO trackToDTO(Track track);
    Track dtoToTrack(TrackDTO track);
}
