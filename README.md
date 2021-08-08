# Concert Tracker API

Kotlin REST API with MongoDB data stores for the Concert Tracker Web App (see [Concert Tracker UI]()).

## Running Locally
The following is intended to be a step-by-step guide on how to develop and debug locally.
### Prerequisites
In order to run locally this guide assumes you have `Docker` and `Docker Compose CLI` installed.

### Environment Setup

There is a `.env.template` file in the project directory that outlines the expected `env` variables. 
First you will want to make a copy of this in the root and name the file `.env`.
Then you will want to swap out the placeholders (indicated by `<desc>`).

#### MongoDB

After you have your `.env` setup we can start the MongoDb container. 
Simply run the following command from the project directory:
``` bash
docker-compose -f docker-compose.local.yml up -d
```
This will create a 2 docker containers, the Mongo container and a MongoExpress container which will provide a web-based GUI to interact with the DB.

> **Important Note: this will mount the `/data/db` volume which Mongo uses to store its data to the `/mongodb-data` directory to ensure data preservation when the container is destroyed.** 

Once the containers are running you should be able to access the MongoExpress web interface via `http://localhost:8081`. 
At which point you will be prompted for a `Username` and `Password`, these will correspond to `MONGOEXPRESS_USERNAME` and `MONGOEXPRESS_PASSWORD`, respectively, in your `.env` file.

#### API

Now that you have Mongo running you can start the API by running the following command from the project directory:
```bash
./gradlew bootRun
```

It will initially take some time to compile and run, but you will see the following line when the service is up and running:
`Started ConcertTrackerApiApplicationKt in...`

Now that the service is running you can make requests against is using Postman and `http://localhost:8080/{controller}/{route}`.