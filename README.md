# REST Assured API Testing Framework

Java-based API testing framework for [Restful Booker API](https://restful-booker.herokuapp.com/apidoc/index.html) using REST Assured, TestNG, and Allure reporting.

## Prerequisites

- Java 11+
- Maven 3.6+

## Quick Start

```bash
# Install dependencies
mvn clean install

# Run tests
mvn clean test

# View Allure report
mvn allure:serve
```

## Project Structure

```
src/
├── main/java/
│   ├── config/          # API configuration
│   ├── models/          # POJOs (Booking, AuthToken, etc.)
│   └── utils/           # Test data generators
└── test/java/
    ├── api/             # API clients (Auth, Booking, HealthCheck)
    └── tests/           # Test classes
```

## Configuration

Edit `src/main/resources/data.properties`:

```properties
base.url=https://restful-booker.herokuapp.com
default.username=admin
default.password=password123
```

## Tests

- **HealthCheckTest** - API availability
- **AuthTest** - Authentication
- **BookingTest** - CRUD operations, filtering, error handling

All tests are self-contained and handle the stateless nature of the API by creating fresh data for each test.

## Key Features

- REST Assured for API testing
- TestNG for test management
- Allure for reporting with request/response details
- Client pattern for reusable API calls
- Automatic test data cleanup
- Random test data generation

## Dependencies

- REST Assured 5.3.2
- TestNG 7.8.0
- Jackson 2.15.2
- Allure 2.24.0

