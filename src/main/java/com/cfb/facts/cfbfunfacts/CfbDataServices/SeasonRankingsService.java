package com.cfb.facts.cfbfunfacts.CfbDataServices;

import com.cfb.facts.cfbfunfacts.CfbData.Rankings;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

// https://api.collegefootballdata.com/rankings?year=2019&seasonType=regular

@Service
public class SeasonRankingsService {

    private static final String CFB_RANKINGS_URL = "https://api.collegefootballdata.com/rankings";

    @Autowired
    private HttpClient httpClient;

    /**
     *
     * Return CFB data Rankings
     */
    public Rankings retrieveRankingsByYear() {

        HttpGet httpGet = new HttpGet(CFB_RANKINGS_URL + "?year=2019&seasonType=regular");

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
