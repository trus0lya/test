# The technologies used:
* Spring Boot
* MySQL
* Flyway
* Gradle

## API Endpoints

Ниже перечислены доступные конечные точки API с примерами тел запросов.

### Транзакции

#### Получение всех транзакций по номеру счета, превышающих лимит

- URL: /api/v1/transactions/{accountNumber}
- Метод: GET
- Параметр URL: accountNumber [обязательный] - Номер счета, для которого требуется получить транзакции.
- Пример URL: /api/v1/transactions/1
- Тело ответа:
```json
{
  "id": 36,
  "accountNumber": 1,
  "expenseCategory": "product",
  "limitUsd": 1000.0000,
  "creationDate": "2024-02-09T08:01:31.000+00:00",
  "remainsBeforeExceed": 1000.00,
  "updateDate": "2024-02-09T08:01:31.000+00:00"
}
```

#### Добавление транзакции

- URL: /api/v1/transactions
- Метод: POST
- Тело запроса: 
```json
{
    "accountNumFrom": 4,
    "accountNumTo": 5,
    "expenseCategory": "product",
    "amount": 9000,
    "currency": "RUB"
  }
```
- Описание: Добавляет новую транзакцию в систему.

### Лимиты

#### Получение лимитов по номеру счета

- URL: /api/v1/limits/{accountNumber}
- Метод: GET
- Параметр URL: accountNumber [обязательный] - Номер счета, для которого требуется получить информацию о лимитах.
- Пример URL: /api/v1/limits/5
- Тело ответа:
```json
{
  "id": 2,
  "accountNumFrom": 1,
  "accountNumTo": 2,
  "expenseCategory": "service",
  "amount": 500.0000,
  "currency": "USD",
  "dateTime": "2024-02-09T08:02:16.000+00:00",
  "limitsEntity": {
    "id": 37,
    "accountNumber": 1,
    "expenseCategory": "service",
    "limitUsd": 1000.0000,
    "creationDate": "2024-02-09T08:01:45.000+00:00",
    "remainsBeforeExceed": -100.00,
    "updateDate": "2024-02-09T08:01:45.000+00:00"
  }
}
```

#### Добавление нового лимита

- URL: /api/v1/limits/add
- Метод: POST
- Тело запроса:
```json
 {
    "accountNumber": 5,
    "expenseCategory": "service",
    "limitUsd": 1800
  }
```

- Описание: Устанавливает новый лимит для заданного номера счета и категории расходов.

### Курсы валют

#### Получение всех курсов обмена валют

- URL: /api/v1/rates
- Метод: GET
- Описание: Возвращает список всех текущих курсов обмена валют.
- Тело ответа:
```json
[
    {
        "currencyFrom": "KZT",
        "currencyTo": "RUB",
        "rate": 0.2031,
        "dateOfUpdate": "2024-02-08T11:21:02.000+00:00"
    },
    {
        "currencyFrom": "KZT",
        "currencyTo": "USD",
        "rate": 0.0000,
        "dateOfUpdate": "2024-02-08T11:21:02.000+00:00"
    },
    {
        "currencyFrom": "RUB",
        "currencyTo": "KZT",
        "rate": 4.9300,
        "dateOfUpdate": "2024-02-08T11:21:01.000+00:00"
    },
    {
        "currencyFrom": "RUB",
        "currencyTo": "USD",
        "rate": 0.0110,
        "dateOfUpdate": "2024-02-08T11:21:01.000+00:00"
    },
    {
        "currencyFrom": "USD",
        "currencyTo": "KZT",
        "rate": 450.1550,
        "dateOfUpdate": "2024-02-08T11:21:03.000+00:00"
    },
    {
        "currencyFrom": "USD",
        "currencyTo": "RUB",
        "rate": 90.5300,
        "dateOfUpdate": "2024-02-08T11:21:03.000+00:00"
    }
]
```


# Launch

```bash
./gradlew bootRun
```
  
