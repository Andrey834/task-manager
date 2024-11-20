# Task Management System v0.01a

Система управления задачами

## Authors

- [AndreyM](https://t.me/andrey86m)

## Tech Stack

**Java:** 21

**Spring:** Web, Data-JDBC, Liquibase, Security

PostgreSQL

REDIS

Docker


## Environment Variables

Чтобы запустить этот проект, вам нужно будет определить следующие переменные окружения в ваш .env-файл.

### TMS
`TMS_PORT` -> Указывается порт на котором будет происходить запуск приложения

`TMS_LIFECYCLE_ACCESS` -> Период действия токена доступа к системе. Например "5m" что будет означать 5 минут.

`TMS_LIFECYCLE_REFRESH` -> Период действия токена обновления. Например "7d" что будет означать 7 дней.

`TMS_ADMIN_SECRET` -> Данная переменная используется при регистрации в системе пользователя с правами администратора.
Минимальное количество символом 6.

#### Необходимо предварительно сгенерировать ключи шифрования(пример ниже)
#### Ключи шифрования размещенные в проекте предназначены только для тестов

`TMS_PRIVATEKEY` -> Необходимо указать путь до приватного ключа(rsa). 

`TMS_PUBLICKEY` -> Необходимо указать путь до публичного ключа(rsa).

#### Сгенерировать приватный ключ на UNIX* системах: `openssl genrsa -out privatekey.pem 2048`
#### Сгенерировать публичный ключ на UNIX* системах: `openssl rsa -in privatekey.pem -pubout -out publickey.pem`

### Database SQL PostgreSQL

`TMS_PG_PORT` -> Внешний порт для подключения к контейнеру PostgreSQL

`TMS_PG_DB` -> Наименование БД

`TMS_PG_USER` -> Имя пользователя СУБД

`TMS_PG_PASSWORD` -> Пароль пользователя СУБД

### Database NOSQL REDIS
#### Для подключения используется стандартное имя пользователя -> default

`TMS_REDIS_PORT` -> Внешний порт для подключения к контейнеру REDIS

`TMS_REDIS_PASSWORD` -> Пароль пользователя СУБД


## Deployment

Запуск приложения(Docker compose)

```bash
  docker-compose up -d
```
