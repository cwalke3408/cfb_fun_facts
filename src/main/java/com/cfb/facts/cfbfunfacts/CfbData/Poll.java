package com.cfb.facts.cfbfunfacts.CfbData;

import lombok.Data;

import java.util.List;

@Data
public class Poll {

    public String poll;
    public List<Rank> ranks;
}
