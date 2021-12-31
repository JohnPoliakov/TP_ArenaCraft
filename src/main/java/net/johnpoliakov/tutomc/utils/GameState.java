package net.johnpoliakov.tutomc.utils;

// Liste des états du jeu
public enum GameState {

    LOBBY("Lobby"),
    PREGAME("Lobby"),
    RUNNING("En cours");

    private final String name;

    GameState(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
