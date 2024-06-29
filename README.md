# Exchange Rates Application

## Overview

The Exchange Rates Application is a Java-based program designed to retrieve historical exchange rates for various currency pairs. The application reads exchange rate data from a CSV file and allows users to query rates based on specified base currency, target currency, and date. Additionally, the application includes unit tests to ensure its functionality is robust and accurate.

## Features

- **Load Exchange Rates**: Reads exchange rates from a CSV file.
- **Query Exchange Rates**: Retrieves rates based on user-provided base currency, target currency, and date.
- **Handle Edge Cases**: Manages scenarios such as weekends and invalid dates gracefully.
- **Unit Tests**: Includes comprehensive tests to validate the application's core functionalities.

## Prerequisites

To run the Exchange Rates Application, ensure you have the following installed:

- Java Development Kit (JDK) 8 or later.
- Maven for dependency management.
- A CSV file named `exchange_rates.csv` containing the exchange rates data.

## CSV File Format

The `exchange_rates.csv` file should adhere to the following format:

1. **Date**: Date in the format `dd-MM-yyyy`.
2. **Base Currency**: The base currency code.
3. **Target Currency**: The target currency code.
4. **Exchange Rate**: Numeric value representing the exchange rate.


## How to Run

1. **Open the Project**: Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
2. **Compile and Run**:
    - Compile and execute the `ExchangeRatesApplication` class.
3. **Provide Input**:
    - Follow the prompts to input the base currency, target currency, and date in the format `dd-MM-yyyy`.
4. **View Results**:
    - The application will display the exchange rate for the specified inputs.

This README file is structured in Markdown format to provide clear instructions for setting up and running the Exchange Rates Application, ensuring consistency and ease of use when viewed on Git platforms.
