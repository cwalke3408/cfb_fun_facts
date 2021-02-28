package com.cfb.facts.cfbfunfacts.Controllers;

import com.cfb.facts.cfbfunfacts.CfbDataServices.SeasonRankingsService;
import com.cfb.facts.cfbfunfacts.Data.VsRankedOpponentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/game")
public class TeamGameResultsController {

    @Autowired
    SeasonRankingsService seasonRankingsService;

    @GetMapping(value = "/vsRankOpp", consumes = "application/json", produces = "application/json")
    public VsRankedOpponentsResponse getTeamVsRankedOpponentsResults() {
        seasonRankingsService.retrieveRankingsByYear();
        return null;
    }
}
