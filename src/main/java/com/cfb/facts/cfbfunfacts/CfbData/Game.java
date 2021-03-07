package com.cfb.facts.cfbfunfacts.CfbData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Game {

    public int id;
    public int season;
    public int week;

    @JsonProperty("season_type")
    public String seasonType;

    @JsonProperty("start_date")
    public String startDate;

    @JsonProperty("start_time_tbd")
    public boolean startTimeTbd;

    @JsonProperty("neutral_site")
    public boolean neutralSite;

    @JsonProperty("conference_game")
    public boolean conferenceGame;

    public int attendance;

    @JsonProperty("venue_id")
    public int venueId;

    public String venue;

    @JsonProperty("home_id")
    public int homeId;

    @JsonProperty("home_team")
    public String homeTeam;

    @JsonProperty("home_conference")
    public String homeConference;

    @JsonProperty("home_points")
    public int homePoints;

//    home_line_scores	[integer]

    @JsonProperty("home_post_win_prob")
    public double homePostWinProb;

    @JsonProperty("away_id")
    public int awayId;

    @JsonProperty("away_team")
    public String awayTeam;

    @JsonProperty("away_conference")
    public String awayConference;

    @JsonProperty("away_points")
    public int awayPoints;

//    public away_line_scores	[...]

    @JsonProperty("away_post_win_prob")
    public double awayPostWinProb;
}
