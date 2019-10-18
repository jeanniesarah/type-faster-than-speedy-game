package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;

    private SnakeGameSubScene creditsSubScene;
    private SnakeGameSubScene helpSubScene;
    private SnakeGameSubScene scoreSubScene;
    private SnakeGameSubScene animalChooserSubScene;
    private SnakeGameSubScene sceneToHide;

    List<SnakeGameButton> menuButtons;
    List<AnimalPicker> animalsList;

    private ANIMAL chosenAnimal;

    public ViewManager(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();
    }

    //method that shows and hides the subscenes
    private void showSubScene(SnakeGameSubScene subScene){
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    //method that creates subscenes
    private void createSubScenes(){
        creditsSubScene = new SnakeGameSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new SnakeGameSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new SnakeGameSubScene();
        mainPane.getChildren().add(scoreSubScene);

        createAnimalChooserSubScene();

    }

    private void createAnimalChooserSubScene() {
        animalChooserSubScene = new SnakeGameSubScene();
        mainPane.getChildren().add(animalChooserSubScene);

        InfoLabel chooseAnimalLabel = new InfoLabel("CHOOSE YOUR LEVEL");
        chooseAnimalLabel.setLayoutX(110);
        chooseAnimalLabel.setLayoutY(25);
        animalChooserSubScene.getPane().getChildren().add(chooseAnimalLabel);
        animalChooserSubScene.getPane().getChildren().add(createAnimalsToChoose());
        animalChooserSubScene.getPane().getChildren().add(createButtonToStart());

    }

    // initialize animals and for each animal, add image
   private HBox createAnimalsToChoose(){
        HBox box = new HBox();
        box.setSpacing(45);
        animalsList = new ArrayList<>();
        for(ANIMAL animal : ANIMAL.values()){
            AnimalPicker animalToPick = new AnimalPicker(animal);
            animalsList.add(animalToPick);
            box.getChildren().add(animalToPick);
            animalToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (AnimalPicker animal : animalsList){
                        animal.setIsCircleChosen(false);
                    }
                    animalToPick.setIsCircleChosen(true);
                    chosenAnimal = animalToPick.getAnimal();
                }
            });
        }
        box.setLayoutX(300 -(118*2));
        box.setLayoutY(100);
        return box;
   }

   private SnakeGameButton createButtonToStart(){
        SnakeGameButton startButton = new SnakeGameButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // check if player has chosen animal, if so open new window
                if( chosenAnimal != null){
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage, chosenAnimal);
                }
            }
        });


        return startButton;
   }

    public Stage getMainStage(){
        return mainStage;
    }

    private void addMenuButton(SnakeGameButton button){
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton(){
        SnakeGameButton startButton = new SnakeGameButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(animalChooserSubScene);
            }
        });
    }

    private void createScoresButton(){
        SnakeGameButton scoresButton = new SnakeGameButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton(){
        SnakeGameButton helpButton = new SnakeGameButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton(){
        SnakeGameButton creditsButton = new SnakeGameButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton(){
        SnakeGameButton exitButton = new SnakeGameButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }



    private void createBackground(){
        Image backgroundImage = new Image("view/resources/cloudy_background_image.png",1086, 768, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo(){
        ImageView logo = new ImageView("view/resources/speedysnakelogo.png");
        logo.setLayoutX(350);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);

    }
}
