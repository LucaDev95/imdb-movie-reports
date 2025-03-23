# imdb-ratings

### per lanciare la build con dockerfile : docker build . -t imdb-ratings:latest

### per avviare il container per la prima volta: docker run -d --name imdb-ratings --network imdb-movie-rating_postgres -p 8080:8080 imdb-ratings:latest