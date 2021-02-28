package com.cfb.facts.cfbfunfacts.Data;

import lombok.Data;

@Data
public class Game {
    public int week;
    public String seasonType;
    public String homeTeam;
    public String awayTeam;
    public int homePoints;
    public int awayPoints;
}
