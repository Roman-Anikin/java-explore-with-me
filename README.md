# Проект Explore With Me

Технологии: Java 18, Spring Boot 3.4.5, Hibernate, PostgreSQL, Maven, Docker

---

## Описание

Backend для приложения-афиши, где можно предложить какое-либо событие от выставки до похода в кино и набрать компанию
для участия в нём.

---

### Проект состоит из 2 микросервисов:

### Основной сервис

Содержит всё необходимое для работы продукта, запускается на порте 8080, имеет свою БД.

API основного сервиса делится на три части:

- Публичная, доступна без регистрации любому пользователю сети. Пользователи могут просматривать события, комментарии к
  опубликованным событиям, подборки событий и категории событий.
- Закрытая, доступна только авторизованным пользователям. Позволяет пользователям добавлять новые события, изменять
  их или отменять; оставлять, обновлять или удалять свои комментарии к событиям; оставлять запросы на участие в
  событиях; подтверждать или отклонять запросы других участников на участие в собственных событиях.
- Административная, для администраторов сервиса. Позволяет администратору редактировать, публиковать или отклонять
  события пользователей; редактировать или удалять комментарии к событиям; добавлять, изменять или удалять категории
  событий; добавлять или удалять пользователей; добавлять или удалять подборки событий; закреплять и откреплять
  подборки на главной странице.

[Подробная спецификация API основного сервиса](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/Roman-Anikin/java-explore-with-me/main/ewm-main-service-spec.json)

---

### Сервис статистики

Собирает информацию о количестве обращений пользователей к спискам событий и о количестве запросов к подробной
информации о событии, запускается на порте 9090, имеет свою БД.

[Подробная спецификация API сервиса статистики](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/Roman-Anikin/java-explore-with-me/main/ewm-stats-service-spec.json)

---

#### Модель базы данных основного сервиса

![Модель базы данных](main/src/main/resources/main.png)

---

#### Модель базы данных сервиса статистики

![Модель базы данных](stat/src/main/resources/stat.png)

---

## Запуск приложения

Необходимые инструменты:

* [Java (JDK) 18](https://github.com/corretto/corretto-18/releases)
* [PostgreSQL 15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

Создайте две БД в PostgreSQL.
Настройте подключение к БД (через `main/src/main/resources/application.properties` и
`stat/src/main/resources/application.properties`). Укажите:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

---

### С помощью командной строки

Находясь в корневой папке проекта, выполнить:

Linux/macOS:

* ./mvnw package

Windows:

* mvnw.cmd package

После успешной сборки в разных терминалах:

* java -jar main/target/explore-with-me-main-1.0.jar
* java -jar stat/target/explore-with-me-stat-1.0.jar

---

### С помощью среды разработки (IntelliJ IDEA, Eclipse, NetBeans)

* Найдите `ExploreWithMeMain` в main/src/main/java/explore/app
* Нажмите ▶️ рядом с классом (или Shift+F10 в IntelliJ IDEA)
* Найдите `ExploreWithMeStat` в stat/src/main/java/explore/app
* Нажмите ▶️ рядом с классом (или Shift+F10 в IntelliJ IDEA)

---

### С помощью Docker

Необходимые инструменты:

* [Docker](https://www.docker.com/)

Находясь в корневой папке проекта, запустить Docker и выполнить:

* docker compose up --build