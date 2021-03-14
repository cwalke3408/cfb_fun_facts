package com.cfb.facts.cfbfunfacts.Services;

import com.cfb.facts.cfbfunfacts.CfbData.*;
import com.cfb.facts.cfbfunfacts.CfbDataServices.SeasonRankingsService;
import com.cfb.facts.cfbfunfacts.Data.VsRankedOpponentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamVsTop25Service {

    private static final String COMMITTEE_RANKINGS = "Playoff Committee Rankings";
    private static final String AP_POLL =  "AP Top 25";
    private static final String SEASON_TYPE_REGULAR = "regular";
    private static final String SEASON_TYPE_POSTSEASON = "postseason";

    @Autowired
    private SeasonRankingsService seasonRankingsService;

    // Given Team and Year
    // When Team and Year is entered
    // Then return W-L record against top 25 teams in that time period
    public VsRankedOpponentsResponse retrieveTop25Record(String team, Integer year) {
        Games seasonGames = retrieveTeamSeasonSch(team, year);
        Rankings rankingsByYear = retrieveRankingsByYear(year);
        VsRankedOpponentsResponse vsRankedOpponentsResponse = new VsRankedOpponentsResponse();

        int finalRegularSeasonWeek = findFinalWeekOfRegularSeason(rankingsByYear);

        // For each game in the season, determine opponent rank and record result
        for(Game game : seasonGames.getGames()) {
            int week = getWeekOfGame(game, finalRegularSeasonWeek);

            // Find current Week national polls
            Ranking rankingsList = getWeeklyPolls(week, rankingsByYear.getRankings());
            if(rankingsList != null) {
                // Get current week Committee Rankings or AP Poll
                List<Rank> trustedPoll = getSpecificRankingsOfTheWeek(rankingsList.getPolls());

                if(trustedPoll != null) {
                    // Search opponent in rankings
                    String opponent = getOpponentName(game, team);
                    Optional<Rank> opponentRank = retrieveOpponentsRanking(trustedPoll, opponent);

                    if(opponentRank.isPresent()) {
                        // Determine if team won or lost to ranked opponent
                        tallyRankWinLost(vsRankedOpponentsResponse, opponentRank.get(), game);
                    }
                }
            }
        }

        return vsRankedOpponentsResponse;
    }

    // Retrieve list of the national CFB Polls of the week
    private Ranking getWeeklyPolls(int week, List<Ranking> rankingsByYear) {
        return getRankingsByWeek(rankingsByYear, week).orElse(null);
    }

    // Retrieve the committee rankings if available for Polls of the week
    // If not then return AP Polls instead
    private List<Rank> getSpecificRankingsOfTheWeek(List<Poll> weeklyPolls) {

        // Retrieve Playoff Committee Rankings
        Optional<Poll> weekPollOptional = getSpecificPoll(weeklyPolls, COMMITTEE_RANKINGS);
        if(!weekPollOptional.isPresent()) {

            // Retrieve AP Poll if Committee Rankings doesn't exist
            weekPollOptional = getSpecificPoll(weeklyPolls, AP_POLL);
            if(!weekPollOptional.isPresent()) {
                return null;
            }
        }

        return weekPollOptional.get().getRanks();
    }

    // Search poll to find team ranking
    private Optional<Rank> retrieveOpponentsRanking(List<Rank> rankings, String team) {
         return rankings.stream().filter(teamRanking -> teamRanking.school.equalsIgnoreCase(team)).findFirst();
    }

    /**
     * @param rankingsByYear List of CFB Rankings for the season
     * @param week The week of the season
     * @return the rankings by the given week in the rankingsByYear List
     */
    private Optional<Ranking> getRankingsByWeek(List<Ranking> rankingsByYear, int week) {
        return rankingsByYear.stream().filter(ranking -> week == ranking.getWeek()).findFirst();
    }

    /**
     * @param weeklyPolls List of the different polls for the given week
     * @param pollName either ("Playoff Committee Rankings", "AP Top 25", "Coaches Poll", "FCS Coaches Poll", "AFCA Division II Coaches Poll"
     * @return The specific poll if exist
     */
    private Optional<Poll> getSpecificPoll(List<Poll> weeklyPolls, String pollName) {
        return  weeklyPolls.stream().filter(poll -> poll.getPoll().equalsIgnoreCase(pollName)).findFirst();
    }

    /**
     * Record team's win or lost to the ranked opponent
     * @param teamRecordVsRankOpp team's record vs ranked opponents
     * @param opponentRank ranked opponent of the week
     * @param game Result of the game between team and opponent
     */
    private void tallyRankWinLost(VsRankedOpponentsResponse teamRecordVsRankOpp, Rank opponentRank, Game game) {

        if(opponentRank.rank > 0 && opponentRank.rank <= 25) {
            if(game.homeTeam.equalsIgnoreCase(opponentRank.school)) {
                if(game.homePoints < game.awayPoints) {
                    // Team Wins against ranked opponent
                    teamRecordVsRankOpp.tallyRankWin();
                } else if(game.homePoints > game.awayPoints) {
                    // Team Loses to ranked Opponent
                    teamRecordVsRankOpp.tallyRankLose();
                } else {
                    // tie
                }

            } else if(game.awayTeam.equalsIgnoreCase(opponentRank.school)){
                if(game.awayPoints < game.homePoints) {
                    // Team Wins against ranked opponent
                    teamRecordVsRankOpp.tallyRankWin();
                } else if(game.awayPoints > game.homePoints) {
                    // Team Loses to ranked opponent
                    teamRecordVsRankOpp.tallyRankLose();
                } else {
                    // tie
                }
            } else {
                // error: opponent represent one of the teams in the game
            }
        }
    }

    // Determine given team's opponent of the given game
    private String getOpponentName(Game game, String team) {
        if(game.homeTeam.equalsIgnoreCase(team)) {
            return game.awayTeam;
        } else if(game.awayTeam.equalsIgnoreCase(team)) {
            return game.homeTeam;
        }

        return null;
    }

    private int findFinalWeekOfRegularSeason(Rankings rankingsByYear) {
        List<Ranking> listOfRankingsByWeek = rankingsByYear.getRankings().stream().sorted(Comparator.comparingInt(Ranking::getWeek)).collect(Collectors.toList());
        return listOfRankingsByWeek.get(listOfRankingsByWeek.size() - 1).getWeek();
    }

    private int getWeekOfGame(Game game, int finalRegularSeasonWeek) {
        if(game.seasonType.equalsIgnoreCase(SEASON_TYPE_REGULAR)) {
            return game.getWeek();
        }

        return finalRegularSeasonWeek;
    }

    /**
     * Make a call to the CFB_DATA API and retrieve team's season schedule
     */
    private Games retrieveTeamSeasonSch(String team, Integer year) {
        return seasonRankingsService.retrieveGamesPlayedInSeason(team, year.toString());
    }

    /**
     * Make a call to the CFB_DATA API and retrieve the rankings by the given year
     */
    private Rankings retrieveRankingsByYear(Integer year) {
        return seasonRankingsService.retrieveRankingsByYear(year.toString());
    }


}
