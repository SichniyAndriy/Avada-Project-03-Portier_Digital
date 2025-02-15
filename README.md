# Project-03-Portier\_Digital

## Опис проєкту

Project-03-Portier Digital — це веб-додаток, який містить можливості керування контентом, статтями, портфоліо та іншими функціями для цифрового простору. Проєкт реалізовано на Java з використанням Spring Boot.

## Основні технології

- **Java 17**
- **Spring Boot**
- **Maven**
- **JPA (Hibernate)**
- **Thymeleaf (або інший шаблонізатор, якщо використовується)**
- **Docker (за наявності контейнеризації)**

## Структура проєкту

- `src/main/java/s_lab/sichniy_andriy/portier_digital/controller/` – Контролери
- `src/main/java/s_lab/sichniy_andriy/portier_digital/model/` – Моделі (Entity-класи для БД)
- `pom.xml` – Налаштування Maven
- `Dockerfile` – Конфігурація для контейнеризації

## Запуск проєкту

1. **Клонувати репозиторій**
   ```sh
   git clone <repo-url>
   cd Project-03-Portier_Digital
   ```
2. **Запуск локально** (без Docker)
   ```sh
   mvn spring-boot:run
   ```
3. **Запуск через Docker** (якщо передбачено)
   ```sh
   docker build -t portier_digital .
   docker run -p 8080:8080 portier_digital
   ```

## API-ендпоїнти (якщо є REST API)

- `GET /articles` – отримати список статей
- `GET /companies` – список компаній, в яких прцював
- `POST /projects` – список виконаних проектів

## Автор

- **Андрій Січний**

## Ліцензія

MIT або інша ліцензія (вказати за потреби).

