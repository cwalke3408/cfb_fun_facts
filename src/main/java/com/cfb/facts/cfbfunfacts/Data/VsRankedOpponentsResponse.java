package com.cfb.facts.cfbfunfacts.Data;

import lombok.Data;

@Data
public class VsRankedOpponentsResponse {

    private int rankWins;

    private int rankLoses;

    public VsRankedOpponentsResponse() {
        this.rankWins = 0;
        this.rankLoses = 0;
    }

    public void tallyRankWin() {
        rankWins += 1;
    }

    public void tallyRankLose() {
        rankLoses += 1;
    }
}
