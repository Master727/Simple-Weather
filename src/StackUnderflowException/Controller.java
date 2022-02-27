package StackUnderflowException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import sierra.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

public class Controller {

    @FXML TextField zipField;
    @FXML Button getWeather;
    @FXML Button temp;
    @FXML Label cityOutput;
    @FXML Label tempOutput;
    @FXML Label weatherOutput;
    @FXML ImageView satelliteIcon;
    @FXML ImageView radarIcon;
    @FXML ImageView weatherIcon;
    @FXML Tab radarTab;
    @FXML Tab satelliteTab;
    @FXML Label High;
    @FXML Label Low;
    @FXML Label forecastDay1;
    @FXML Label forecastDay2;
    @FXML Label forecastDay3;
    @FXML Label forecastDay4;
    @FXML Label forecastDay5;
    @FXML Label forecastDay6;
    @FXML Label forecastDay7;
    @FXML ImageView forecastIcon1;
    @FXML ImageView forecastIcon2;
    @FXML ImageView forecastIcon3;
    @FXML ImageView forecastIcon4;
    @FXML ImageView forecastIcon5;
    @FXML ImageView forecastIcon6;
    @FXML ImageView forecastIcon7;
    @FXML Label forecastTemp1;
    @FXML Label forecastTemp2;
    @FXML Label forecastTemp3;
    @FXML Label forecastTemp4;
    @FXML Label forecastTemp5;
    @FXML Label forecastTemp6;
    @FXML Label forecastTemp7;
    @FXML Label forecastWeather1;
    @FXML Label forecastWeather2;
    @FXML Label forecastWeather3;
    @FXML Label forecastWeather4;
    @FXML Label forecastWeather5;
    @FXML Label forecastWeather6;
    @FXML Label forecastWeather7;

    // AutoCompletion Info
    private Set autoCompletions;
    private SuggestionProvider provider;

    public void handleGetWeather() {
        String zip = zipField.getText();
        clearAll();
        cityOutput.setVisible(true);
        cityOutput.setText("Loading...");
        if (zip.isEmpty()) {
            initialWeather();
        } else {
            AsyncTask t = new GetDataInBackground();
            t.execute(zip);
        }


    }

    public void updateUI(Weather w){
        cityOutput.setText(w.getLocation());
        cityOutput.setVisible(true);
        tempOutput.setText(w.getTempF() + "° F");
        tempOutput.setVisible(true);
        weatherOutput.setText(w.getWeather());
        weatherOutput.setVisible(true);
        Image img = new Image("https://icons.wxug.com/i/c/i/" + w.getIcon() + ".gif");
        weatherIcon.setImage(img);
        Image sat = new Image("http://api.wunderground.com/api/1655f919bbcd29ed/satellite/q/" + w.getZipcode() + ".png?newmaps=1&radius=100&width=400&height=400");
        satelliteIcon.setImage(sat);
        Image radar = new Image("http://api.wunderground.com/api/1655f919bbcd29ed/animatedradar/q/" + w.getZipcode() + ".png?newmaps=1&radius=100&width=400&height=400");
        radarIcon.setImage(radar);
        temp.setDisable(false);
        radarTab.setDisable(false);
        satelliteTab.setDisable(false);
        High.setVisible(true);
        Low.setVisible(true);

        forecastTemp1.setVisible(true);
        forecastTemp2.setVisible(true);
        forecastTemp3.setVisible(true);
        forecastTemp4.setVisible(true);
        forecastTemp5.setVisible(true);
        forecastTemp6.setVisible(true);
        forecastTemp7.setVisible(true);
        forecastDay1.setVisible(true);
        forecastDay2.setVisible(true);
        forecastDay3.setVisible(true);
        forecastDay4.setVisible(true);
        forecastDay5.setVisible(true);
        forecastDay6.setVisible(true);
        forecastDay7.setVisible(true);
        forecastWeather1.setVisible(true);
        forecastWeather2.setVisible(true);
        forecastWeather3.setVisible(true);
        forecastWeather4.setVisible(true);
        forecastWeather5.setVisible(true);
        forecastWeather6.setVisible(true);
        forecastWeather7.setVisible(true);


        // Forecast Day
        w.getArray();
        forecastDay1.setText(w.getSevenDayDate()[0]+ " " + w.getSevenDayTOM()[0]);
        forecastDay2.setText(w.getSevenDayDate()[1]+ " " + w.getSevenDayTOM()[1]);
        forecastDay3.setText(w.getSevenDayDate()[2]+ " " + w.getSevenDayTOM()[2]);
        forecastDay4.setText(w.getSevenDayDate()[3]+ " " + w.getSevenDayTOM()[3]);
        forecastDay5.setText(w.getSevenDayDate()[4]+ " " + w.getSevenDayTOM()[4]);
        forecastDay6.setText(w.getSevenDayDate()[5]+ " " + w.getSevenDayTOM()[5]);
        forecastDay7.setText(w.getSevenDayDate()[6]+ " " + w.getSevenDayTOM()[6]);
        High.setText(w.getSevenDayTempHighF()[0] + "° F");
        Low.setText(w.getSevenDayTempLowF()[0] + "° F");

        // Forecast Icons
        Image icon1 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[0] + ".gif");
        Image icon2 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[1] + ".gif");
        Image icon3 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[2] + ".gif");
        Image icon4 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[3] + ".gif");
        Image icon5 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[4] + ".gif");
        Image icon6 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[5] + ".gif");
        Image icon7 = new Image("https://icons.wxug.com/i/c/i/" + w.getSevenDayIcon()[6] + ".gif");
        forecastIcon1.setImage(icon1);
        forecastIcon2.setImage(icon2);
        forecastIcon3.setImage(icon3);
        forecastIcon4.setImage(icon4);
        forecastIcon5.setImage(icon5);
        forecastIcon6.setImage(icon6);
        forecastIcon7.setImage(icon7);

        // Forecast Weather

        forecastWeather1.setText(w.getSevenDayCond()[0]);
        forecastWeather2.setText(w.getSevenDayCond()[1]);
        forecastWeather3.setText(w.getSevenDayCond()[3]);
        forecastWeather4.setText(w.getSevenDayCond()[3]);
        forecastWeather5.setText(w.getSevenDayCond()[4]);
        forecastWeather6.setText(w.getSevenDayCond()[5]);
        forecastWeather7.setText(w.getSevenDayCond()[6]);


        // Forecast Temp

        forecastTemp1.setText(w.getSevenDayTempHighF()[0] + "° F" + " / " + w.getSevenDayTempLowF()[0] + "° F");
        forecastTemp2.setText(w.getSevenDayTempHighF()[1] + "° F" + " / " + w.getSevenDayTempLowF()[1] + "° F");
        forecastTemp3.setText(w.getSevenDayTempHighF()[2] + "° F" + " / " + w.getSevenDayTempLowF()[2] + "° F");
        forecastTemp4.setText(w.getSevenDayTempHighF()[3] + "° F" + " / " + w.getSevenDayTempLowF()[3] + "° F");
        forecastTemp5.setText(w.getSevenDayTempHighF()[4] + "° F" + " / " + w.getSevenDayTempLowF()[4] + "° F");
        forecastTemp6.setText(w.getSevenDayTempHighF()[5] + "° F" + " / " + w.getSevenDayTempLowF()[5] + "° F");
        forecastTemp7.setText(w.getSevenDayTempHighF()[6] + "° F" + " / " + w.getSevenDayTempLowF()[6] + "° F");

    }

    public void clearAll()
    {
        cityOutput.setVisible(false);
        tempOutput.setVisible(false);
        weatherOutput.setVisible(false);
        weatherIcon.setImage(null);
        satelliteIcon.setImage(null);
        radarIcon.setImage(null);
        temp.setDisable(true);
        radarTab.setDisable(true);
        satelliteTab.setDisable(true);
        High.setVisible(false);
        Low.setVisible(false);
        forecastTemp1.setVisible(false);
        forecastTemp2.setVisible(false);
        forecastTemp3.setVisible(false);
        forecastTemp4.setVisible(false);
        forecastTemp5.setVisible(false);
        forecastTemp6.setVisible(false);
        forecastTemp7.setVisible(false);
        forecastDay1.setVisible(false);
        forecastDay2.setVisible(false);
        forecastDay3.setVisible(false);
        forecastDay4.setVisible(false);
        forecastDay5.setVisible(false);
        forecastDay6.setVisible(false);
        forecastDay7.setVisible(false);
        forecastIcon1.setImage(null);
        forecastIcon2.setImage(null);
        forecastIcon3.setImage(null);
        forecastIcon4.setImage(null);
        forecastIcon5.setImage(null);
        forecastIcon6.setImage(null);
        forecastIcon7.setImage(null);
        forecastWeather1.setVisible(false);
        forecastWeather2.setVisible(false);
        forecastWeather3.setVisible(false);
        forecastWeather4.setVisible(false);
        forecastWeather5.setVisible(false);
        forecastWeather6.setVisible(false);
        forecastWeather7.setVisible(false);
    }
    int count = 1;
    public void handleTemp(ActionEvent e)
    {
        String zip = zipField.getText();
        Weather w = new Weather(zip);
        w.fetch();
        w.getArray();
        count++;
        tempOutput.setVisible(true);
        if (count % 2 == 0)
        {
            tempOutput.setText(w.getTempC() + "° C");
            temp.setText("°F");
            forecastTemp1.setText(w.getSevenDayTempHighC()[0] + "° C" + " / " + w.getSevenDayTempLowC()[0] + "° C");
            forecastTemp2.setText(w.getSevenDayTempHighC()[1] + "° C" + " / " + w.getSevenDayTempLowC()[1] + "° C");
            forecastTemp3.setText(w.getSevenDayTempHighC()[2] + "° C" + " / " + w.getSevenDayTempLowC()[2] + "° C");
            forecastTemp4.setText(w.getSevenDayTempHighC()[3] + "° C" + " / " + w.getSevenDayTempLowC()[3] + "° C");
            forecastTemp5.setText(w.getSevenDayTempHighC()[4] + "° C" + " / " + w.getSevenDayTempLowC()[4] + "° C");
            forecastTemp6.setText(w.getSevenDayTempHighC()[5] + "° C" + " / " + w.getSevenDayTempLowC()[5] + "° C");
            forecastTemp7.setText(w.getSevenDayTempHighC()[6] + "° C" + " / " + w.getSevenDayTempLowC()[6] + "° C");
            High.setText(w.getSevenDayTempHighC()[0] + "° C");
            Low.setText(w.getSevenDayTempLowC()[0] + "° C");
        }
        else if (count % 2 == 1) {
            tempOutput.setText(w.getTempF() + "° F");
            temp.setText("°C");
            forecastTemp1.setText(w.getSevenDayTempHighF()[0] + "° F" + " / " + w.getSevenDayTempLowF()[0] + "° F");
            forecastTemp2.setText(w.getSevenDayTempHighF()[1] + "° F" + " / " + w.getSevenDayTempLowF()[1] + "° F");
            forecastTemp3.setText(w.getSevenDayTempHighF()[2] + "° F" + " / " + w.getSevenDayTempLowF()[2] + "° F");
            forecastTemp4.setText(w.getSevenDayTempHighF()[3] + "° F" + " / " + w.getSevenDayTempLowF()[3] + "° F");
            forecastTemp5.setText(w.getSevenDayTempHighF()[4] + "° F" + " / " + w.getSevenDayTempLowF()[4] + "° F");
            forecastTemp6.setText(w.getSevenDayTempHighF()[5] + "° F" + " / " + w.getSevenDayTempLowF()[5] + "° F");
            forecastTemp7.setText(w.getSevenDayTempHighF()[6] + "° F" + " / " + w.getSevenDayTempLowF()[6] + "° F");
            High.setText(w.getSevenDayTempHighF()[0] + "° F");
            Low.setText(w.getSevenDayTempLowF()[0] + "° F");
        }
    }

    public void initialize() {
        // Auto Complete Info
        autoCompletions = new HashSet();
        provider = SuggestionProvider.create(autoCompletions);
        TextFields.bindAutoCompletion(zipField,provider);
        zipField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                provider.clearSuggestions();
                autoComplete(newValue);
            }
        });

        zipField.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleGetWeather();
            }
        });

        // Initial Weather Info
        initialWeather();

    }

    private void initialWeather() {
        AsyncTask t = new GetDataInBackground();
        t.execute("95747");
    }

    private void autoComplete(String input) {
        String encode;
        try {
            encode = URLEncoder.encode(input,"UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) { encode = "error"; }

        String request = "http://autocomplete.wunderground.com/aq?c=US&h=0&query=" + encode;
        JsonElement json;
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
            json = null;
            System.out.println("IOE ERROR autoComplete() in Controller.java");
            clearAll();
        }

        JsonArray words = null;
        if (json != null) words = json.getAsJsonObject().get("RESULTS").getAsJsonArray();

        for (int i=0; i<words.size(); i++) {
            provider.addPossibleSuggestions(words.get(i).getAsJsonObject().get("name").getAsString());
        }

    }
    private class GetDataInBackground extends AsyncTask<String, Weather>
    {
        @Override
        protected Weather doInBackground(String zip)
        {
            Weather w = new Weather(zip);
            w.fetch();
            return w;
        }

        @Override
        protected void onPostExecute(Weather w)
        {
            updateUI(w);
        }
    }
}
