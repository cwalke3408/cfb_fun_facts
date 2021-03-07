package com.cfb.facts.cfbfunfacts.CfbDataServices;

import com.cfb.facts.cfbfunfacts.CfbData.Games;
import com.cfb.facts.cfbfunfacts.CfbData.Rankings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// https://api.collegefootballdata.com/rankings?year=2019&seasonType=regular
// https://api.collegefootballdata.com/games?year=2019&seasonType=both&team=Clemson

@Service
public class SeasonRankingsService {

    private static final String CFB_RANKINGS_URL = "https://api.collegefootballdata.com/rankings";

    @Autowired
    protected HttpClient httpClient;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     *
     * Return CFB data Rankings
     */
    public Rankings retrieveRankingsByYear() {
        Rankings rankings = null;
        HttpGet httpGet = new HttpGet(CFB_RANKINGS_URL + "?year=2019&seasonType=regular");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String jsonString = "{ \"rankings\":"+ httpResponseToString(httpResponse)+"}";
            rankings = objectMapper.readValue(jsonString, Rankings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rankings;
    }

    /**
     * Retrieve a specific teams W/L data by year
     *
     * @return Games
     */
    public Games retrieveGamesPlayedInSeason() {
        Games games = null;
        HttpGet httpGet = new HttpGet("https://api.collegefootballdata.com/games?year=2019&seasonType=both&team=Clemson");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String jsonString = "{ \"games\":" + httpResponseToString(httpResponse) + "}";
            games = objectMapper.readValue(jsonString, Games.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return games;
    }

    /**
     * Converts binary contents from the given httpResponse.getEntity to a string
     *
     * @param httpResponse
     * @return json String
     * @throws IOException
     */
    private String httpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
