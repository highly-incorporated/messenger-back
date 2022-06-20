package com.corporate.service;

import com.corporate.dto.GetTeamDto;
import com.corporate.model.Team;

import java.util.List;

public interface TeamService {

    int saveTeam(Team newTeam);

    String getNameById(int id);

    List<String> showTeams(List<Integer> id);

    void addPerson(int teamId, String participantsId);

    GetTeamDto getTeam(int teamId);
}