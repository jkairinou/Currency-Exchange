package com.example.exchange_rates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ExchangeRatesApplication {

    static String path = "exchange_rates.csv";
    static List<String[]> exchangeRates = new ArrayList<>();

    public static void main(String[] args) {
        loadExchangeRates();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input base currency: ");
        String base = scanner.nextLine().toUpperCase();

        while (base.isEmpty()) {
            System.out.println("Please provide base currency to proceed: ");
            base = scanner.nextLine().toUpperCase();
        }

        System.out.println("Input target currency: ");
        String target = scanner.nextLine().toUpperCase();

        while (target.isEmpty()) {
            System.out.println("Please provide target currency to proceed: ");
            target = scanner.nextLine().toUpperCase();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;

        while (date == null) {
            System.out.println("Input date (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine().trim();

            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy format.");
            }
        }

        double exchangeRate = getExchangeRate(base, target, date);

        if (exchangeRate != -1) {
            System.out.println("Exchange rate from " + base + " to " + target + " on " + dateFormat.format(date) + " is " + String.format("%.2f", exchangeRate));
        } else {
            System.out.println("No exchange rate available for " + base + " to " + target + " on " + dateFormat.format(date));
        }

        scanner.close();
    }

    static void loadExchangeRates() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); //skip file header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                exchangeRates.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double getExchangeRate(String baseCurrency, String targetCurrency, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        double lastAvailableRate = -1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        while (true) {
            for (String[] row : exchangeRates) {
                if (row.length == 4) {
                    String dateStr = row[0];
                    String base = row[1];
                    String target = row[2];
                    double exchangeRate = Double.parseDouble(row[3]);

                    Date rateDate;
                    try {
                        rateDate = dateFormat.parse(dateStr);
                    } catch (ParseException e) {
                        continue;
                    }

                    if (base.equals(baseCurrency) && target.equals(targetCurrency)) {
                        if (rateDate.equals(calendar.getTime())) {
                            return exchangeRate;
                        }
                        if (rateDate.before(calendar.getTime())) {
                            lastAvailableRate = exchangeRate;
                        }
                    }
                }
            }

            if (lastAvailableRate != -1) {
                return lastAvailableRate;
            }

            calendar.add(Calendar.DAY_OF_MONTH, -1);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -1); //if saturday go back to friday
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -2); //if sunday go back to friday
            }
        }
    }
}
