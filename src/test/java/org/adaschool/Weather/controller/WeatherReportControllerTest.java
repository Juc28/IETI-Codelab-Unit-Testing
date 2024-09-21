package org.adaschool.Weather.controller;

import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherReportControllerTest {

    @Mock
    WeatherReportService weatherReportService;

    @InjectMocks
    WeatherReportController weatherReportController;

    // 1. Prueba de un reporte de clima exitoso
    @Test
    void testGetWeatherReport_Success() {
        double latitude = 37.8267;
        double longitude = -122.4233;
        WeatherReport mockReport = new WeatherReport();
        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(mockReport);

        WeatherReport result = weatherReportController.getWeatherReport(latitude, longitude);

        assertEquals(mockReport, result);
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }

    // 2. Prueba con parámetros límite (borde)
    @Test
    void testGetWeatherReport_WithBoundaryCoordinates() {
        double latitude = 90.0;  // Máxima latitud posible
        double longitude = 180.0;  // Máxima longitud posible
        WeatherReport mockReport = new WeatherReport();
        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(mockReport);

        WeatherReport result = weatherReportController.getWeatherReport(latitude, longitude);

        assertEquals(mockReport, result);
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }

    // 3. Prueba con latitud y longitud inválidas
    @Test
    void testGetWeatherReport_InvalidCoordinates() {
        double latitude = -100.0;  // Latitud inválida
        double longitude = 200.0;  // Longitud inválida

        when(weatherReportService.getWeatherReport(latitude, longitude))
                .thenThrow(new IllegalArgumentException("Invalid coordinates"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            weatherReportController.getWeatherReport(latitude, longitude);
        });

        assertEquals("Invalid coordinates", exception.getMessage());
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }

    // 4. Prueba para el caso de una respuesta nula (reporte no encontrado)
    @Test
    void testGetWeatherReport_ReportNotFound() {
        double latitude = 37.8267;
        double longitude = -122.4233;

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(null);

        WeatherReport result = weatherReportController.getWeatherReport(latitude, longitude);

        assertNull(result);
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }

    // 5. Prueba de excepción en el servicio
    @Test
    void testGetWeatherReport_ServiceException() {
        double latitude = 37.8267;
        double longitude = -122.4233;

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenThrow(new RuntimeException("Service error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            weatherReportController.getWeatherReport(latitude, longitude);
        });

        assertEquals("Service error", exception.getMessage());
        verify(weatherReportService, times(1)).getWeatherReport(latitude, longitude);
    }
}
