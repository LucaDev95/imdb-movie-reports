# imdb-movie-reports

Java application for generating reports regarding movie ratings updated daily on IMDb.

## Description

This application performs a **daily upload** of movie data and their respective ratings from **IMDb** into a database.  
The data is retrieved from the files available on the [IMDb Datasets](https://developer.imdb.com/non-commercial-datasets/), specifically:

- **`title.basics.tsv.gz`** – contains movie information.
- **`title.ratings.tsv.gz`** – contains the number of votes and the average rating.

### Reports Generated
The application generates a **daily report** considering a given time interval (e.g., 10 days), with the following contents:

1. **Top 100 Movies Report** – A list of the **100 movies with the highest number of votes**.
2. **Movie Statistics Report**, which includes:
    - **Average rating**
    - **Number of new movies**
    - **Number of new votes**
    - **Change in average rating**
    - **Additional insights, broken down by genre**

### Data Filtering
In the total number of movies stored in the database, only the **most recent movies** are considered  
(e.g., movies **not older than 2 years**).

### Report Delivery
The reports are **generated in CSV format** and are **sent via email** to the address configured by the user in the application.

## Future Developments
In the future, the data stored in the database will be used by a **web application** that will be developed later.

## Purpose
This application was developed **as a practice project** to enhance programming skills.

## Getting Started

### Dependencies

To install and run the application and database correctly, Docker must be installed

### Installing

For detailed installation steps, see the [Installation Guide](https://github.com/LucaDev95/imdb-movie-reports/blob/main/Dockerfile).

### Executing program

* To run the application, execute the command `docker start imdb-movie-reports` every day at a fixed time (e.g., 10:00 PM), or create a script and run it via Task Scheduler (Windows) or cron (Linux).
