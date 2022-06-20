package com.corporate.service;

import com.corporate.dto.GetTeamDto;
import com.corporate.model.Team;
import com.corporate.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public int saveTeam(Team team) {
        return teamRepository.save(team).getId();
    }

    @Override
    public  String getNameById(int id) {
        return teamRepository.findById(id).getName();
    }

    @Override
    public List<String> showTeams(List<Integer> teamsId) {
        List<String> names = new ArrayList<>();
        if (teamsId.size() == 0) { return names; }
        for (int id: teamsId) {
            names.add(getNameById(id));
        }
        return names;
    }

    @Override
    public void addPerson(int teamId, String participantsId) {
        teamRepository.updateParticipants(teamRepository.findById(teamId).getParticipants() + participantsId, teamId);
    }

    @Override
    public GetTeamDto getTeam(int teamId) {
        Team team = teamRepository.findById(teamId);
        return new GetTeamDto(team.getName(), team.getAvatar());
    }
}