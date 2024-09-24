package org.adaschool.Weather.service;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.springframework.web.client.RestTemplate;
public class WeatherReportService {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "e2e16405cdaa073a95309d3349ee4ba1";

    private final RestTemplate restTemplate;

    public WeatherReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherReport getWeatherReport(double latitude, double longitude) {
        String url = API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        WeatherReport report = new WeatherReport();
        if (response != null && response.getMain() != null) {
            double temperatureInKelvin = response.getMain().getTemperature();
            double temperatureInCelsius = temperatureInKelvin - 273.15;
            report.setTemperature(temperatureInCelsius);

            report.setHumidity(response.getMain().getHumidity());
        }
        return report;
    }
}
