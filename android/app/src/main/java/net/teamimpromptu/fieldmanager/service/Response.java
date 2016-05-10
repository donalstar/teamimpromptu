package net.teamimpromptu.fieldmanager.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by donal on 5/10/16.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("Count")
    private int count;

    @JsonProperty("Items")
    public Member[] items;
}
