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
[
  {
    "accountNumFrom": 1,
    "accountNumTo": 2,
    "expenseCategory": "PRODUCT",
    "amount": 2000.0000,
    "currency": "USD",
    "dateTime": "2024-02-10 18:01:06",
    "limit": {
      "accountNumber": 1,
      "expenseCategory": "PRODUCT",
      "limitUsd": 1000.0000,
      "creationDate": "2024-02-10 18:01:06",
      "remainsBeforeExceed": -1000.0000,
      "updateDate": "2024-02-10 18:01:06"
    }
  }
]
```

#### Добавление транзакции

- URL: /api/v1/transactions
- Метод: POST
- Тело запроса: 
```json
{
    "accountNumFrom": 4,
    "accountNumTo": 5,
    "expenseCategory": "PRODUCT",
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
[
  {
    "accountNumber": 1,
    "expenseCategory": "SERVICE",
    "limitUsd": 1800.0000,
    "creationDate": "2024-02-10 18:00:07",
    "remainsBeforeExceed": 1800.0000,
    "updateDate": "2024-02-10 18:00:07"
  },
  {
    "accountNumber": 1,
    "expenseCategory": "PRODUCT",
    "limitUsd": 1000.0000,
    "creationDate": "2024-02-10 18:01:06",
    "remainsBeforeExceed": -1000.0000,
    "updateDate": "2024-02-10 18:01:06"
  },
  {
    "accountNumber": 1,
    "expenseCategory": "PRODUCT",
    "limitUsd": 3000.0000,
    "creationDate": "2024-02-10 18:01:28",
    "remainsBeforeExceed": 1000.0000,
    "updateDate": "2024-02-10 18:01:28"
  }
]
```

#### Добавление нового лимита

- URL: /api/v1/limits/add
- Метод: POST
- Тело запроса:
```json
 {
    "accountNumber": 5,
    "expenseCategory": "SERVICE",
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
    "rate": 0.2035,
    "dateOfUpdate": "2024-02-11 16:02:01"
  },
  {
    "currencyFrom": "KZT",
    "currencyTo": "USD",
    "rate": 0.0000,
    "dateOfUpdate": "2024-02-11 16:02:02"
  },
  {
    "currencyFrom": "RUB",
    "currencyTo": "KZT",
    "rate": 4.9132,
    "dateOfUpdate": "2024-02-11 16:02:01"
  },
  {
    "currencyFrom": "RUB",
    "currencyTo": "USD",
    "rate": 0.0110,
    "dateOfUpdate": "2024-02-11 16:02:01"
  },
  {
    "currencyFrom": "USD",
    "currencyTo": "KZT",
    "rate": 446.6050,
    "dateOfUpdate": "2024-02-11 16:02:03"
  },
  {
    "currencyFrom": "USD",
    "currencyTo": "RUB",
    "rate": 91.3850,
    "dateOfUpdate": "2024-02-11 16:02:02"
  }
]
```


# Launch

```bash
./gradlew bootRun
```
  
