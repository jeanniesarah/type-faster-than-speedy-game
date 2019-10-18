package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import model.ANIMAL;
import model.MediumInfoLabel;
import model.SmallInfoLabel;

import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    // fields for menu stage and image view of animal
    private Stage menuStage;
    private ImageView animal;

    //animation
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "view/resources/cloudy_background_image.png";
    private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";

    //private final static String APPLE_RED_IMAGE = "view/resources/red_apple.png";
    private final static String LETTER_F_IMAGE = "view/resources/letters/letter_F.png";
    private final static String LETTER_J_IMAGE = "view/resources/letters/letter_J.png";
    //private ImageView[] redApples;
    private ImageView[] letterF;
    private ImageView[] letterJ;

    Random randomPositionGenerator;

    private ImageView star;
    private SmallInfoLabel pointsLabel;
    //private MediumInfoLabel titleLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int points;
    private final static String GOLD_STAR_IMAGE = "view/resources/star.png";

    private final static int STAR_RADIUS = 12;
    private final static int ANIMAL_RADIUS = 27;
    //private final static int APPLE_RADIUS = 20;
    private final static int LETTER_RADIUS = 20;

    private Font largeFont = Font.font("Verdana", FontWeight.MEDIUM, 18);
    private KeyCode codeOfLastPressedKey = KeyCode.EXCLAMATION_MARK;
    private Group textGroup = new Group();
    private Text mainTextLine = new Text(0, 0, "Last typed letter: ");
    private Text additionalText = new Text(100, 250, "");

    public GameViewManager() {
        initializeStage();
        // method that initalizes listeners
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

    private void setTextLines() {
        mainTextLine.setText("Last pressed key:  " +
                codeOfLastPressedKey.getName());

        //  The KeyCode enum provides predefined constants (e.g. F1)
        //  which can be used to refer to the individual keys on the keyboard.

        if (codeOfLastPressedKey == KeyCode.F1) {
            additionalText.setText("You pressed the F1 key");
        } else if (codeOfLastPressedKey == KeyCode.UP) {
            additionalText.setText("You pressed the Arrow Up key");
        } else if (codeOfLastPressedKey == KeyCode.DOWN) {
            additionalText.setText("You pressed the Arrow Down key");
        } else {
            additionalText.setText("");
        }
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
            }
        });

        gameScene.setOnKeyPressed((KeyEvent event) -> {
            codeOfLastPressedKey = event.getCode();
            setTextLines();

            System.out.print("\n Key Pressed. Key code =  " +
            codeOfLastPressedKey);
        });

        gameScene.setOnKeyReleased((KeyEvent event) -> {
            System.out.print("\n Key Released.");
        });

        gameScene.setOnKeyTyped((KeyEvent event) -> {
            // KeyTyped event occurs when a visible character is typed.
            // The Space character is considered visible. The pressing of the
            // Shift key does not produce a KeyTyped event.
            System.out.print( "\n Key Typed. Character = "
                    +  event.getCharacter() ) ;
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    // method to hide menu window and show game window
    public void createNewGame(Stage menuStage, ANIMAL chosenAnimal) {
        this.menuStage = menuStage;
        // hide menu stage and show game stage
        this.menuStage.hide();
        createBackground();
        createAnimal(chosenAnimal);
        createGameElements(chosenAnimal);
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(ANIMAL chosenAnimal) {
        playerLife = 2;
        star = new ImageView(GOLD_STAR_IMAGE);
        setNewElementPosition(star);
        gamePane.getChildren().add(star);
        /*titleLabel = new MediumInfoLabel("TYPE THE FALLING LETTERS AS FAST AS YOU CAN");
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(25);*/
        pointsLabel = new SmallInfoLabel("POINTS: 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        textGroup.setLayoutX(100);
        textGroup.setLayoutY(50);
        mainTextLine.setFont(largeFont);
        additionalText.setFont(largeFont);
        textGroup.getChildren().addAll(mainTextLine, additionalText);
        gamePane.getChildren().addAll(pointsLabel, textGroup);
        playerLifes = new ImageView[3];

        for (int i = 0; i < playerLifes.length; i++) {
            playerLifes[i] = new ImageView(chosenAnimal.getUrlLife());
            playerLifes[i].setLayoutX(455 + (i * 50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }

        /*redApples = new ImageView[3];
        for (int i = 0; i < redApples.length; i++) {
            redApples[i] = new ImageView(APPLE_RED_IMAGE);
            setNewElementPosition(redApples[i]);
            gamePane.getChildren().add(redApples[i]);
        }*/

        letterF = new ImageView[3];
        for (int i = 0; i < letterF.length; i++) {
        letterF[i] = new ImageView(LETTER_F_IMAGE);
        setNewElementPosition(letterF[i]);
        gamePane.getChildren().add(letterF[i]);
        }

        letterJ = new ImageView[3];
        for (int i = 0; i < letterJ.length; i++) {
            letterJ[i] = new ImageView(LETTER_J_IMAGE);
            setNewElementPosition(letterJ[i]);
            gamePane.getChildren().add(letterJ[i]);
        }
    }

    //method that makes apples and letters move
    private void moveGameElements() {
        star.setLayoutY(star.getLayoutY() + 5);
       /* for (int i = 0; i < redApples.length; i++) {
            redApples[i].setLayoutY(redApples[i].getLayoutY() + 7);
            redApples[i].setRotate(redApples[i].getRotate() + 4);
        }*/
        for (int i = 0; i < letterF.length; i++) {
            letterF[i].setLayoutY(letterF[i].getLayoutY() + 7);
            letterF[i].setRotate(letterF[i].getRotate() + 4);
        }
        for (int i = 0; i < letterJ.length; i++) {
            letterJ[i].setLayoutY(letterJ[i].getLayoutY() + 7);
            letterJ[i].setRotate(letterJ[i].getRotate() + 4);
        }
    }

    // method that checks if apples, stars or letters are below and relocate them to the top
    private void checkIfElementsAreBehindTheAnimalAndRelocate() {
        if (star.getLayoutY() > 1200) {
            setNewElementPosition(star);
        }
       /* for (int i = 0; i < redApples.length; i++) {
            if (redApples[i].getLayoutY() > 900) {
                setNewElementPosition(redApples[i]);
            }
        }*/
        for (int i = 0; i < letterF.length; i++) {
            if (letterF[i].getLayoutY() > 900) {
                setNewElementPosition(letterF[i]);
            }
        }
        for (int i = 0; i < letterJ.length; i++) {
            if (letterJ[i].getLayoutY() > 900) {
                setNewElementPosition(letterJ[i]);
            }
        }
    }

    private void setNewElementPosition(ImageView image) {
        image.setLayoutX(randomPositionGenerator.nextInt(370));
        image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
    }

    // method to create and show new animal
    private void createAnimal(ANIMAL chosenAnimal) {
        animal = new ImageView(chosenAnimal.getUrl());
        animal.setLayoutX(GAME_WIDTH / 2);
        animal.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(animal);
    }

    // method for animation timer object
    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();
                moveGameElements();
                checkIfElementsAreBehindTheAnimalAndRelocate();
                checkIfElementsCollide();
                moveAnimal();
            }
        };
        gameTimer.start();
    }

    // method for moving and rotating animals
    private void moveAnimal() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            if (angle > -30) {
                angle -= 5;
            }
            animal.setRotate(angle);
            if (animal.getLayoutX() > -20) {
                animal.setLayoutX(animal.getLayoutX() - 3);
            }
        }
        if (isRightKeyPressed && !isLeftKeyPressed) {
            if (angle < 30) {
                angle += 5;
            }
            animal.setRotate(angle);
            if (animal.getLayoutX() < 522) {
                animal.setLayoutX(animal.getLayoutX() + 3);
            }
        }
        if (!isLeftKeyPressed && !isRightKeyPressed) {
            if (angle < 0) {
                angle += 5;
            } else if (angle > 0) {
                angle -= 5;
            }
            animal.setRotate(angle);
        }
        if (isLeftKeyPressed && isRightKeyPressed) {
        }
    }

    // method for background
    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        //for loop to fill gridpanes
        for (int i = 0; i < 12; i++) {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1, i % 3, 1 / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, 1 / 3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }
        gridPane2.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground() {
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
        if (gridPane1.getLayoutY() >= 1024) {
            gridPane1.setLayoutY(-1024);
        }
        if (gridPane2.getLayoutY() >= 1024) {
            gridPane2.setLayoutY(-1024);
        }
    }

    // letter collision logic method
    private void checkIfElementsCollide() {
        if (ANIMAL_RADIUS + STAR_RADIUS > calculateDistance(animal.getLayoutX() + 49, star.getLayoutX() + 15, animal.getLayoutY() + 37, star.getLayoutY() + 15)) {
            setNewElementPosition(star);
            points++;
            String textToSet = "POINTS : ";
            if (points < 10) {
                textToSet = textToSet + "0";
            }
            pointsLabel.setText(textToSet + points);
        }
       /* for(int i = 0; i < redApples.length; i++){
            if(APPLE_RADIUS + ANIMAL_RADIUS > calculateDistance(animal.getLayoutX()+49, redApples[i].getLayoutX()+20, animal.getLayoutY() + 37, redApples[i].getLayoutY()+20)){
                removeLife();
                setNewElementPosition(redApples[i]);
            }
        }*/
        for(int i = 0; i < letterF.length; i++){
            if(LETTER_RADIUS + ANIMAL_RADIUS > calculateDistance(animal.getLayoutX()+49, letterF[i].getLayoutX()+20, animal.getLayoutY() + 37, letterF[i].getLayoutY()+20)){
                removeLife();
                setNewElementPosition(letterF[i]);
            }
        }
        for(int i = 0; i < letterJ.length; i++){
            if(LETTER_RADIUS + ANIMAL_RADIUS > calculateDistance(animal.getLayoutX()+49, letterJ[i].getLayoutX()+20, animal.getLayoutY() + 37, letterJ[i].getLayoutY()+20)){
                removeLife();
                setNewElementPosition(letterJ[i]);
            }
        }
    }

    // method that subtracts life points and finished the game
    private void removeLife(){
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if(playerLife < 0){
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
    }

    // method that calculates distance between 2 points
    private double calculateDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
