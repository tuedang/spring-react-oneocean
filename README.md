# Spring Boot + React - OneOcean Vessel

This project is an application for creating Vessel map/position using Java, Spring Boot, React and Google Maps API.

The Spring Boot API project files is located under the '/api' directory.
The React App project files are located under the '/react-app' directory.

## Getting Started

### Requirements
For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org/download.cgi)
- [Node.js](https://nodejs.org/en/download/)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)

### Clone
To get started you can simply clone this repository using git:
```
git clone https://github.com/tuedang/spring-react-oneocean.git
```

### Run

**API**:

To start the Spring Boot API, launch a Terminal and run from the `api` root folder:

### `mvn spring-boot:run`

The API endpoint will be available on [http://localhost:8080/api](http://localhost:8080/api)

**Frontend**:

To run the React frontend app, run from the `react-app` root folder:

### `npm start` or `yarn start`

The app will be available on [http://localhost:3000](http://localhost:3000)

**Google**:

Create a [Google API Key](https://developers.google.com/maps/documentation/javascript/get-api-key) and replace REACT_APP_GOOGLE_API_KEY on `/react-app/.env`.
