package com.cfb.facts.cfbfunfacts.Controllers;

import com.cfb.facts.cfbfunfacts.Data.VsRankedOpponentsResponse;
import com.cfb.facts.cfbfunfacts.Services.TeamVsTop25Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/game")
public class TeamGameResultsController {

    @Autowired
    private TeamVsTop25Service teamVsTop25Service;

    @GetMapping(value = "/vsRankOpp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VsRankedOpponentsResponse> getTeamVsRankedOpponentsResults(@RequestParam String team, @RequestParam int year) {
        VsRankedOpponentsResponse vsRankedOpponentsResponse = teamVsTop25Service.retrieveTop25Record(team, year);
        return new ResponseEntity<>(vsRankedOpponentsResponse, HttpStatus.OK);
    }
}
