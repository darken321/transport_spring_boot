package com.example.transport2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Builder
public class StopTransportInfoName {
    Integer id;
    String transportName;
    String transportType;
    Integer startStopId;
    Integer endStopId;
    Time time;
    String routeName;
}