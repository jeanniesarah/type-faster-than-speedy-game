package model;

public enum ANIMAL {
    SNAKE("view/resources/animalChooser/snake.png", "view/resources/animalChooser/green_life.png"),
    GIRAFFE("view/resources/animalChooser/giraffe.png", "view/resources/animalChooser/yellow_life.png"),
    PARROT("view/resources/animalChooser/parrot.png", "view/resources/animalChooser/red_life.png"),
    HORSE("view/resources/animalChooser/horse.png", "view/resources/animalChooser/brown_life.png");

    private String urlAnimal;
    private String urlLife;

    private ANIMAL(String urlAnimal, String urlLife){

        this.urlAnimal = urlAnimal;
        this.urlLife = urlLife;
    }

    public String getUrl(){

        return this.urlAnimal;
    }

    public String getUrlLife(){
        return urlLife;
    }
}
