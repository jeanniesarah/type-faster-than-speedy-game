package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AnimalPicker extends VBox {

    private ImageView circleImage;
    private ImageView animalImage;

    private String circleNotChosen = "view/resources/animalChooser/grey_circle.png";
    private String circleChosen = "view/resources/animalChooser/circle_chosen.png";

    private ANIMAL animal;

    private boolean isCircleChosen;


    public AnimalPicker(ANIMAL animal){
        circleImage = new ImageView(circleNotChosen);
        animalImage = new ImageView(animal.getUrl());
        this.animal = animal;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(animalImage);
    }

    //getter for animal
    public ANIMAL getAnimal(){
        return animal;
    }

    //getter for boolean
    public boolean getIsCircleChosen(){
        return isCircleChosen;
    }

    //method that sets circle if it is chosen or not chosen
    public void setIsCircleChosen(boolean isCircleChosen){
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
