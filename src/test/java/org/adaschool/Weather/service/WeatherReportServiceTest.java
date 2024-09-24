package org.adaschool.Weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;

public class WeatherReportServiceTest {

    @InjectMocks
    private WeatherReportService weatherReportService;

    @Mock
    private RestTemplate restTemplate;

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "e2e16405cdaa073a95309d3349ee4ba1";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        double latitude = 37.8267;
        double longitude =  -123.0000;
        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(293.15);
        main.setHumidity(65.0);
        response.setMain(main);
        String url = API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(response);
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(20.0, report.getTemperature(), 0.01, "La temperatura retornada no es la esperada");
        assertEquals(65.0, report.getHumidity(), "La humedad retornada no es la esperada");
        verify(restTemplate, times(1)).getForObject(url, WeatherApiResponse.class);
    }
    @Test
    public void testGetWeatherReportWithMinus5DegreesAndLongitudeMinus120() {
        double latitude = 37.8267;
        double longitude = -120.0000;
        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(268.15);
        main.setHumidity(80.0);
        response.setMain(main);
        String url = API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(response);
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);
        assertEquals(-5.0, report.getTemperature(), 0.01, "La temperatura retornada no es la esperada");
        assertEquals(80.0, report.getHumidity(), "La humedad retornada no es la esperada");
        verify(restTemplate, times(1)).getForObject(url, WeatherApiResponse.class);
    }
}
