package net.teamimpromptu.fieldmanager.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by donal on 5/10/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    @JsonProperty("Select")
    private String selectType;

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }
}
