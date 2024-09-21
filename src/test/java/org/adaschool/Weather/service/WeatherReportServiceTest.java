package org.adaschool.Weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class WeatherReportServiceTest {

    private static final String API_KEY = "e2e16405cdaa073a95309d3349ee4ba1";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WeatherReportService weatherReportService;

    @Test
    void testGetWeatherReport() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        String url = API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;

        WeatherApiResponse expectedApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setHumidity(84);
        main.setTemperature(0);
        expectedApiResponse.setMain(main);

        WeatherReport expectedReport = new WeatherReport();
        expectedReport.setTemperature(main.getTemperature());
        expectedReport.setHumidity(main.getHumidity());

        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(expectedApiResponse);
        WeatherReport weatherReport = weatherReportService.getWeatherReport(latitude, longitude);
        assertEquals(expectedReport.getTemperature(), weatherReport.getTemperature());
        assertEquals(expectedReport.getHumidity(), weatherReport.getHumidity());
    }
}