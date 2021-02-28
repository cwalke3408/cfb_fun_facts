package com.cfb.facts.cfbfunfacts.CfbData;

import lombok.Data;

@Data
public class Game {

    public int id;
    public int season;
    public int week;
    public String seasonType;
    public String startDate;
    public boolean StartTimeTbd;
    public boolean neutralSite;
    public boolean conferenceGame;
    public int attendance;
    public int venueId;
    public String venue;
    public int homeId;
    public String homeTeam;
    public String homeConference;
    public int homePoints;
//    home_line_scores	[integer]
    public double homePostWinProb;
    public int awayId;
    public String awayTeam;
    public String awayConference;
    public int awayPoints;
//    public away_line_scores	[...]
    public double awayPostWinProb;
}
