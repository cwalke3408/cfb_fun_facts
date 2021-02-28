package com.cfb.facts.cfbfunfacts.Data;

import lombok.Data;
import java.util.Map;

@Data
public class Rankings {

    public Map<Integer, WeekRankings> rankings;
}
