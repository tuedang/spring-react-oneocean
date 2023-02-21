# Spring Boot + React - OneOcean Vessel

This project is an application for creating Vessel map/position using Java, Spring Boot, React and Google Maps API.

The Spring Boot API project files is located under the '/api' directory.
The React App project files are located under the '/react-app' directory.

## Getting Started

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

## Functionality

### Result on the map
![vessles map](imgs/vessels_map.png)


### metric
GET http://localhost:8080/api/vessels/metric
 
```
{
    "Vessel 2": {
        "averageSpeed": 12.574131965637207,
        "totalDistance": 37.72239535053248
    },
    "Vessel 1": {
        "averageSpeed": 18.542757034301758,
        "totalDistance": 47.284032133635066
    },
    "Vessel 3": {
        "averageSpeed": 12.79382610321045,
        "totalDistance": 35.82271121372412
    }
}
```

### Collision
GET http://localhost:8080/api/vessels/collision
```
[
    {
        "vesselPosition1": {
            "time": "2020-01-01T08:57:40Z",
            "vessel": {
                "name": "Vessel 1"
            },
            "position": {
                "x": 25.354166597127914,
                "y": 14.958333313465118
            },
            "coordinate": {
                "x": -122.39364583340287,
                "y": 37.78985833331347
            }
        },
        "vesselPosition2": {
            "time": "2020-01-01T08:57:40Z",
            "vessel": {
                "name": "Vessel 3"
            },
            "position": {
                "x": 25.306666672229767,
                "y": 16.03999999165535
            },
            "coordinate": {
                "x": -122.39369333332776,
                "y": 37.79093999999166
            }
        },
        "centerPosition": {
            "time": "2020-01-01T08:57:40Z",
            "vessel": {
                "name": "Vessel 1-Vessel 3"
            },
            "position": {
                "x": 25.33041663467884,
                "y": 15.499166652560234
            },
            "coordinate": {
                "x": -122.39366958336532,
                "y": 37.79039916665256
            }
        }
    }
]
```
