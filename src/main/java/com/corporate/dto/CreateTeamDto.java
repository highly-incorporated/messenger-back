package com.corporate.dto;

import lombok.Data;

@Data
public class CreateTeamDto {
    private String name;
    private String team_participants;
    private String avatar;
}