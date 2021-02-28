package com.cfb.facts.cfbfunfacts.Data;

import lombok.Data;
import java.util.Map;

@Data
public class WeekRankings {

    public int week;
    public Map<String, Integer> APRankings;
    public Map<String, Integer> CommitteeRankings;
}
