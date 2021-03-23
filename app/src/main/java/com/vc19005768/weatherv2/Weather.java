package com.vc19005768.weatherv2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "Headline",
//        "DailyForecasts"
//})
public class Weather {

    //@JsonProperty("Headline")
    private Headline headline;
    //@JsonProperty("DailyForecasts")
    private List<DailyForecasts> dailyForecasts = null;
    //@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    //@JsonProperty("Headline")
    public Headline getHeadline() {
        return headline;
    }

    //@JsonProperty("Headline")
    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    //@JsonProperty("DailyForecasts")
    public List<DailyForecasts> getDailyForecasts() {
        return dailyForecasts;
    }

    //@JsonProperty("DailyForecasts")
    public void setDailyForecasts(List<DailyForecasts> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    //@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    //@JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
