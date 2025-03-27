package com.luca.imdb.movie.reports.enums;

public enum Genre {

    ACTION("Action"),
    ANIMATION("Animation"),
    DRAMA("Drama"),
    CRIME("Crime"),
    THRILLER("Thriller"),
    MYSTERY("Mystery"),
    SCI_FY("Sci-Fi"),
    ROMANCE("Romance"),
    DOCUMENTARY("Documentary"),
    ADULT("Adult"),
    MUSIC("Music"),
    MUSICAL("Musical"),
    GAME_SHOW("Game-Show"),
    FANTASY("Fantasy"),
    REALITY_TV("Reality-TV"),
    FAMILY("Family"),
    ADVENTURE("Adventure"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    SPORT("Sport"),
    NEWS("News"),
    TALK_SHOW("Talk-Show"),
    HORROR("Horror"),
    WESTERN("Western"),
    WAR("War"),
    COMEDY("Comedy");




    private final String genreName;

    private Genre(String genreName){
        this.genreName=genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public static Genre fromString(String text) {
        if (text != null) {
            for (Genre genre : Genre.values()) {
                if (genre.genreName.equalsIgnoreCase(text)) {
                    return genre;
                }
            }
        }
        throw new IllegalArgumentException("No Genre with name: " + text);
    }

}
