package StackUnderflowException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Weather
{

    private final String token = "1655f919bbcd29ed";
    private String zipcode;
    private String citi;
    private String state;
    private JsonElement json;
    private JsonArray sevenDay;

    public Weather(String zip)
    {
        zipcode = zip;

    }
// You know how long the array is put the city back together and according to that
    public String getCiti(String citi)
    {
        String fullCity = "";
        String[] strArr = citi.split(" ");
        for(int i = 0; i < strArr.length-1; i++)
        {
            if(i != strArr.length-2)
            {
                fullCity = fullCity + strArr[i] + "_";
            }
            else {
                fullCity = fullCity + strArr[i];
            }
        }
        return fullCity;
    }
    public String getState(String state)
    {
        String[] strArr = state.split(" ");
        for(int i = 0; i < strArr.length; i++)
        {
            if(i == strArr.length - 1)
            {
                return strArr[i];
            }
        }
        return null;
    }

    private String getTime()
    {
        String timezone = json.getAsJsonObject().get("current_observation").getAsJsonObject().get("local_tz_long").getAsString();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM d YYYY");
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime remoteTime = ldt.atZone(ZoneId.of(timezone));
        return format.format(remoteTime);
    }

    public String getLocation()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("display_location").
                getAsJsonObject().get("full").getAsString() + " | " + getTime();
    }

    public String getZipcode()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("display_location").
                getAsJsonObject().get("zip").getAsString();
    }

    public String getTempF()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("temp_f").getAsString();
    }

    public String getTempC()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("temp_c").getAsString();
    }

    public String getWeather()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("weather").getAsString();
    }

    public String getIcon()
    {
        return json.getAsJsonObject().get("current_observation").getAsJsonObject().get("icon").getAsString();
    }

    public void getArray()
    {
        sevenDay = json.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday")
                .getAsJsonArray();
    }

    public String[] getSevenDayDate()
    {
        String[] date = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size(); i++)
        {
            date[i] = sevenDay.get(i).getAsJsonObject().get("date").getAsJsonObject().get("weekday_short").getAsString();
        }
        return date;
    }

    public String[] getSevenDayTOM()
    {
        String[] tom = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size(); i++)
        {
            tom[i] = sevenDay.get(i).getAsJsonObject().get("date").getAsJsonObject().get("day").getAsString();
        }
        return tom;
    }

    public String[] getSevenDayTempHighF()
    {
        String high[] = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size();i++)
        {
            high[i] = sevenDay.get(i).getAsJsonObject().get("high").getAsJsonObject().get("fahrenheit").getAsString();
        }
        return high;
    }

    public String[] getSevenDayTempLowF()
    {
        String[] low = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size();i++)
        {
            low[i] = sevenDay.get(i).getAsJsonObject().get("low").getAsJsonObject().get("fahrenheit").getAsString();
        }
        return low;
    }

    public String[] getSevenDayTempHighC()
    {
        String[] high = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size();i++)
        {
            high[i] = sevenDay.get(i).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString();
        }
        return high;
    }

    public String[] getSevenDayTempLowC()
    {
        String[] low = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size();i++)
        {
            low[i] = sevenDay.get(i).getAsJsonObject().get("low").getAsJsonObject().get("celsius").getAsString();
        }
        return low;
    }

    public String[] getSevenDayIcon()
    {
        String[] icon = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size();i++)
        {
            icon[i] = sevenDay.get(i).getAsJsonObject().get("icon").getAsString();
        }
        return icon;
    }

    public String[] getSevenDayCond()
    {
        String[] cond = new String[sevenDay.size()+1];
        for(int i = 0; i < sevenDay.size(); i++)
        {
            cond[i] = sevenDay.get(i).getAsJsonObject().get("conditions").getAsString();
        }
        return cond;
    }

    private boolean isInt(String zip)
    {
        try{
            int input = Integer.parseInt(zip);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }

    }

    public void fetch()
    {
        String request;
        if (zipcode.length()>5 && zipcode.substring(0,6) == "/q/zmw:") {
            request = "http://api.wunderground.com/api/" + token + "/conditions/forecast7day/" + zipcode + ".json";
        } else if (isInt(zipcode) || zipcode == "autoip") {
            request = "http://api.wunderground.com/api/" + token + "/conditions/forecast7day/q/" + zipcode + ".json";
        } else {
            citi = getCiti(zipcode);
            state = getState(zipcode);
            request = "http://api.wunderground.com/api/" + token + "/conditions/forecast7day/q/" + state + "/" + citi + ".json";
        }

        try {
            URL weatherURL = new URL(request);

            InputStream is = weatherURL.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            JsonParser parser =  new JsonParser();
            json = parser.parse(br);
        }
        catch (java.io.IOException ioe)
        {
            System.out.println("IOE ERROR fetch() in WEATHER.java");
            System.exit(1);
        }
    }
}
