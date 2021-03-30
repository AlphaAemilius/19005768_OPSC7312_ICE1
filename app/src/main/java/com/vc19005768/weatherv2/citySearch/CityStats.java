package com.vc19005768.weatherv2.citySearch;

public class CityStats {
    private String Key;

    private String LocalizedName;

    private String EnglishName;

    private Country Country;

    private TimeZone TimeZone;

    private GeoPosition GeoPosition;

    private String LocalObservationDateTime;

    private int EpochTime;

    private String WeatherText;

    private int WeatherIcon;

    private boolean HasPrecipitation;

    private String PrecipitationType;

    private boolean IsDayTime;

    private Temperature Temperature;

    private String MobileLink;

    private String Link;

    public void setKey(String Key){
        this.Key = Key;
    }
    public String getKey(){
        return this.Key;
    }
    public void setLocalizedName(String LocalizedName){
        this.LocalizedName = LocalizedName;
    }
    public String getLocalizedName(){
        return this.LocalizedName;
    }
    public void setEnglishName(String EnglishName){
        this.EnglishName = EnglishName;
    }
    public String getEnglishName(){
        return this.EnglishName;
    }
    public void setCountry(Country Country){
        this.Country = Country;
    }
    public Country getCountry(){
        return this.Country;
    }
    public void setTimeZone(TimeZone TimeZone){
        this.TimeZone = TimeZone;
    }
    public TimeZone getTimeZone(){
        return this.TimeZone;
    }
    public void setGeoPosition(GeoPosition GeoPosition){
        this.GeoPosition = GeoPosition;
    }
    public GeoPosition getGeoPosition(){
        return this.GeoPosition;
    }
    public void setLocalObservationDateTime(String LocalObservationDateTime){
        this.LocalObservationDateTime = LocalObservationDateTime;
    }
    public String getLocalObservationDateTime(){
        return this.LocalObservationDateTime;
    }
    public void setEpochTime(int EpochTime){
        this.EpochTime = EpochTime;
    }
    public int getEpochTime(){
        return this.EpochTime;
    }
    public void setWeatherText(String WeatherText){
        this.WeatherText = WeatherText;
    }
    public String getWeatherText(){
        return this.WeatherText;
    }
    public void setWeatherIcon(int WeatherIcon){
        this.WeatherIcon = WeatherIcon;
    }
    public int getWeatherIcon(){
        return this.WeatherIcon;
    }
    public void setHasPrecipitation(boolean HasPrecipitation){
        this.HasPrecipitation = HasPrecipitation;
    }
    public boolean getHasPrecipitation(){
        return this.HasPrecipitation;
    }
    public void setPrecipitationType(String PrecipitationType){
        this.PrecipitationType = PrecipitationType;
    }
    public String getPrecipitationType(){
        return this.PrecipitationType;
    }
    public void setIsDayTime(boolean IsDayTime){
        this.IsDayTime = IsDayTime;
    }
    public boolean getIsDayTime(){
        return this.IsDayTime;
    }
    public void setTemperature(Temperature Temperature){
        this.Temperature = Temperature;
    }
    public Temperature getTemperature(){
        return this.Temperature;
    }
    public void setMobileLink(String MobileLink){
        this.MobileLink = MobileLink;
    }
    public String getMobileLink(){
        return this.MobileLink;
    }
    public void setLink(String Link){
        this.Link = Link;
    }
    public String getLink(){
        return this.Link;
    }
}

