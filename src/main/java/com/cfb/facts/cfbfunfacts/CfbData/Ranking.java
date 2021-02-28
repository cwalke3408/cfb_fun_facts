package com.cfb.facts.cfbfunfacts.CfbData;

import lombok.Data;

import java.util.List;

@Data
public class Ranking {

    public int season;
    public String seasonType;
    public int week;
    public List<Poll> polls;
}
