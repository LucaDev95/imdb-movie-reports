## Installation & Execution


* In the `.env` file under the project root  add your own properties.
Example:

```
DB_PASSWORD=myPassword
DB_USERNAME=myUsername
MAIL_PASSWORD=mailPassword
MAIL_RECIPIENT=mail.recipient@gmail.com
MAIL_SENDER=mail.sender@recipient.com
```

* To enable email sending for automatic reports, make sure to generate an app password token from your email provider (e.g., Google Mail) and use it in the `MAIL_PASSWORD` property.


* From the root directory, run the command `docker compose up -d` from the command line.


* Go to http://localhost:5050/ (pgAdmin interface) and create a database called imdb-movie-ratings, setting the username and password specified in the .env file, with the hostname set to `postgres`.


* From the root of the project, run the command: `docker build . -t imdb-movie-reports:latest`.


* For the first run, execute the command: `docker run -d --name imdb-movie-reports --network imdb-movie-reports_postgres imdb-movie-reports:latest`.

