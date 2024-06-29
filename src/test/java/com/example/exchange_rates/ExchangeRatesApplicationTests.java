package com.example.exchange_rates;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ExchangeRatesApplicationTests {

    @BeforeAll
    public static void setup() {
        ExchangeRatesApplication.loadExchangeRates();
    }

    @Test
    public void testExactDateRate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse("01-02-2022");
        double rate = ExchangeRatesApplication.getExchangeRate("GBP", "EUR", date);
        assertNotEquals(-1, rate);
        assertEquals(1.18, rate, 0.01);
    }

    @Test
    public void testWeekendRate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse("23-12-2023"); //saturday
        double rate = ExchangeRatesApplication.getExchangeRate("USD", "EUR", date);
        assertNotEquals(-1, rate);
        assertEquals(1.19, rate, 0.01); //output should be friday's rate
    }

    @Test
    public void testPublicHolidayRate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //christmas day -> no data available for this date
        Date date = dateFormat.parse("25-12-2024");
        double rate = ExchangeRatesApplication.getExchangeRate("USD", "EUR", date);
        assertNotEquals(-1, rate);
        //the output rate should be the one on the 24th
        assertEquals(1.20, rate, 0.01);
    }

}
