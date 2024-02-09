# The technologies used:
* [Spring Boot]
* [MySQL]
* [Flyway]
* [Gradle]

## API Endpoints

Ниже перечислены доступные конечные точки API с примерами тел запросов.

### Транзакции

#### Получение всех транзакций по номеру счета, превышающих лимит

- URL: /api/v1/transactions/{accountNumber}
- Метод: GET
- Параметр URL: accountNumber [обязательный] - Номер счета, для которого требуется получить транзакции.
- Пример URL: /api/v1/transactions/4

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


# Launch

```bash
./gradlew bootRun
```
  
