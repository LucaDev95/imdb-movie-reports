## Installation & Execution

* Once the project has been downloaded, replace the 'CHANGEME' entries in the docker-compose.yml
  file with the desired values for user, password, and email.


* Create a `secrets.yml` file under `src/main/resources` with the following structure:

```yaml
db:
  username: changeme
  password: changeme

mail:
  sender: changeme
  password: changeme
  recipient: changeme
```

* To enable email sending for automatic reports, make sure to generate an app password token from your email provider (e.g., Google Mail) and use it in the password field under mail.


* From the root directory, run the command `docker compose up -d` from the command line.


* Go to http://localhost:5050/ (pgAdmin interface) and create a database called imdb-movie-ratings, setting the username and password specified in the docker-compose.yml, with the hostname set to postgres.


* In the newly created database, run the schema generation scripts contained in init_schema.sql.


* From the root of the project, run the command: `docker build . -t imdb-movie-reports:latest`.


* For the first run, execute the command: `docker run -d --name imdb-movie-reports --network imdb-movie-reports_postgres imdb-movie-reports:latest`.

