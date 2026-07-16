import processing.core.PApplet;

import java.awt.*;
import java.sql.SQLOutput;

public class Snake_Original extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Snake_Original");
    }
    Spielfeld spielfeld;
    public Buttons[] buttonArray = new Buttons[4];

    UserDAO userDAO = new UserDAO();
    ScoreDAO scoreDAO = new ScoreDAO();
    Spieler currentPlayer;
    int selectedButton = 0;
    int loginStep = 0;
    int selectedLoginOption = 0;
    String typedUsername = "";
    String typedPassword = "";
    boolean typingPassword = false;
    String loginMessage = "";

    boolean scoreSaved = false;
    int[] databaseHighscores = new int[3];
    int selectedSetting = 0;
    int moveTime = 150;
    int lastMoveTime = 0;
    String[] speedNames = {"Langsam", "Mittel", "Schnell"};
    int[] speedValues = {200, 150, 90};
    int selectedSpeed = 1; // Start: Normal
    int size;
    
    public void setup() {
        spielfeld = new Spielfeld(this);
        Buttons startButton = new Buttons(this,height/2, width/2, 200, 80, "Start");
        Buttons settingButton = new Buttons(this,height/2, width/2, 200, 80, "Settings");
        Buttons highScoreButton = new Buttons(this,height/2, width/2, 200, 80, "Highscores");
        Buttons loginButton = new Buttons(this, height/2, width/2, 200, 80, "Login");
        buttonArray[0] = startButton;
        buttonArray[1] = settingButton;
        buttonArray[2] = highScoreButton;
        buttonArray[3] = loginButton;
    }

    public void settings() {
        size = Spielfeld.extent*Spielfeld.fieldExtent;
        size(size, size);
    }

    enum GameState {
            MENU,
            LOGIN,
            SETTINGS,
            HIGHSCORES,
            PLAYING,
            GAMEOVER
    }
    GameState state = GameState.MENU;
    boolean hasStarted = false;
    public void draw() {
        background(0,0,0);
        switch (state) {
            case MENU:
                spielfeld.resetSnake();
                background(0);
                fill(255, 255, 255);
                textSize(100);
                text("SNAKE", (float) (width / 2), (float) (height * 0.25));
                buttonArray[0].setPosition((float)width/2, height * 0.45f);
                buttonArray[1].setPosition((float)width/2, height * 0.45f);
                buttonArray[2].setPosition((float) width/2, height * 0.45f);
                buttonArray[3].setPosition((float) width/2, height * 0.45f);
                buttonArray[selectedButton].drawButton(buttonArray[selectedButton].getText());
                textSize(18);
                fill(255);
                text("< / > wechseln, ENTER = zurück", (float)width/2, (float)(height*0.65));
                break;
            case PLAYING:
                /*
                experimente für animierte bewegung mithilfe von lerp()
                problematisch bei 90 grad drehungen da es diagonal gezogen wird
                 */
                float t = (millis() - lastMoveTime) / (float) moveTime;
                t = constrain(t, 0, 1);
                spielfeld.generateField(t);
                spielfeld.getScoreboard().draw();
                if (hasStarted) {
                    if (millis() - lastMoveTime >= moveTime) {
                        checkVerloren();
                        spielfeld.moveSnake();
                        lastMoveTime = millis();
                    }
                } else {
                    textAlign(CENTER);
                    textSize(30);
                    text("Press any direction to begin!", size/2, (size/2)-Spielfeld.extent);

                }
                break;
            case SETTINGS:
                background(0);
                fill(255);
                textAlign(CENTER);
                textSize(50);
                text("SETTINGS", (float) width/2, (float) (height*0.15));
                textSize(30);
                switch (selectedSetting) {
                    case 0:
                        if ((frameCount / 15) % 2 == 0) {
                            fill(0);
                            text("Geschwindigkeit: " + speedNames[selectedSpeed], (float)width/2, (float)(height*0.3));
                        } else {
                            fill(255);
                            text("Geschwindigkeit: " + speedNames[selectedSpeed], (float)width/2, (float)(height*0.3));
                        }
                        fill(255);
                        text("Feldgröße: 10 < " + Spielfeld.fieldExtent + " < 16", (float)width/2, (float)(height*0.4));
                        text("Zellgröße: 30 < " + Spielfeld.extent + " < 60", (float)width/2, (float)(height*0.5));
                        break;
                    case 1:
                        fill(255);
                        text("Geschwindigkeit: " + speedNames[selectedSpeed], (float)width/2, (float)(height*0.3));
                        if ((frameCount / 15) % 2 == 0) {
                            fill(0);
                            text("Feldgröße: 10 < " + Spielfeld.fieldExtent + " < 16", (float)width/2, (float)(height*0.4));
                        } else {
                            fill(255);
                            text("Feldgröße: 10 < " + Spielfeld.fieldExtent + " < 16", (float)width/2, (float)(height*0.4));
                        }
                        fill(255);
                        text("Zellgröße: 30 < " + Spielfeld.extent + " < 60", (float)width/2, (float)(height*0.5));
                        break;
                    case 2:
                        fill(255);
                        text("Geschwindigkeit: " + speedNames[selectedSpeed], (float)width/2, (float)(height*0.3));
                        text("Feldgröße: 10 < " + Spielfeld.fieldExtent + " < 16", (float)width/2, (float)(height*0.4));
                        if ((frameCount / 15) % 2 == 0) {
                            fill(0);
                            text("Zellgröße: 30 < " + Spielfeld.extent + " < 60", (float)width/2, (float)(height*0.5));
                        } else {
                            fill(255);
                            text("Zellgröße: 30 < " + Spielfeld.extent + " < 60", (float) width / 2, (float) (height * 0.5));
                        }
                        break;

                }
                fill(255);
                textSize(18);
                text("< / > wechseln, ENTER = zurück", (float)width/2, (float)(height*0.65));
                fill(255);
                text("^ / v wechseln", (float)width/2, (float)(height*7));
                break;
            case HIGHSCORES:
                background(0);
                fill(255);
                textAlign(CENTER);
                textSize(50);
                text("HIGHSCORES", (float)width/2, (float)(height*0.25));
                textSize(28);
                if (currentPlayer == null) {
                    text("Please login first", width/2, height*0.45f);
                } else {
                    for (int i = 0; i < speedNames.length; i++) {
                        text(speedNames[i] + ": " + databaseHighscores[i], width/2f, height*0.45f + i*40);
                    }
                }
                textSize(18);
                text("ENTER = zurück", (float)width/2, (float)(height*0.8));
                break;
            case LOGIN:
                drawLoginScreen();
                break;
            case GAMEOVER:
                if (!scoreSaved && currentPlayer != null) {
                    scoreDAO.saveScore(
                            currentPlayer.getID(),
                            spielfeld.getScoreboard().getScore(),
                            selectedSpeed,
                            Spielfeld.fieldExtent,
                            Spielfeld.extent
                    );

                    databaseHighscores = scoreDAO.getBestScoresForUser(currentPlayer.getID());
                    scoreSaved = true;
                }

                background(0);
                fill(255, 0, 0);
                textAlign(CENTER);
                textSize(80);
                text("GAME OVER", (float)width/2, (float)(height*0.35));

                fill(255);
                textSize(30);
                text("Score: " + spielfeld.getScoreboard().getScore(), (float)width/2, (float)(height*0.5));

                if (currentPlayer != null) {
                    text("Highscore: " + databaseHighscores[selectedSpeed], (float)width/2, (float)(height*0.5 + 40));
                } else {
                    text("Login to save highscores", (float)width/2, (float)(height*0.5 + 40));
                }

                textSize(20);
                text("ENTER = zurueck ins Menü", (float)width/2, (float)(height*0.75));

                spielfeld.resetSnake();
                break;

    }       //Debug Code zum Überprüfen der einzelnen Zellen
            /*
            stroke(255, 0, 0);
            strokeWeight(3);
            noFill();
            rect(0, 0, width - 1, height - 1);
            */
    }
    public void drawLoginScreen() {
        background(0);
        textAlign(CENTER);

        if (loginStep == 0) {
            drawLoginChoice();
        } else if (loginStep == 1) {
            drawLoginInput("LOGIN");
        } else if (loginStep == 2) {
            drawLoginInput("REGISTER");
        }
    }

    public void drawLoginChoice() {
        fill(255);
        textSize(60);
        text("ACCOUNT", width / 2f, height * 0.20f);

        textSize(30);

        if (selectedLoginOption == 0) {
            fill(255, 255, 0);
        } else {
            fill(255);
        }
        text("Login", width / 2f, height * 0.42f);

        if (selectedLoginOption == 1) {
            fill(255, 255, 0);
        } else {
            fill(255);
        }
        text("Register", width / 2f, height * 0.54f);

        fill(255);
        textSize(18);
        text("< / > wechseln, ENTER = auswählen", width / 2f, height * 0.72f);
        text("R = zurück zum Menü", width / 2f, height * 0.78f);
    }

    public void drawLoginInput(String title) {
        fill(255);
        textSize(55);
        text(title, width / 2f, height * 0.18f);

        textSize(24);

        if (!typingPassword) {
            fill(255, 255, 0);
        } else {
            fill(255);
        }
        text("Username: " + typedUsername, width / 2f, height * 0.38f);

        String hiddenPassword = "";
        for (int i = 0; i < typedPassword.length(); i++) {
            hiddenPassword += "*";
        }

        if (typingPassword) {
            fill(255, 255, 0);
        } else {
            fill(255);
        }
        text("Password: " + hiddenPassword, width / 2f, height * 0.50f);

        fill(255);
        textSize(18);
        text("TAB = Feld wechseln", width / 2f, height * 0.66f);
        text("ENTER = bestätigen", width / 2f, height * 0.72f);
        text("R = zurück", width / 2f, height * 0.78f);

        fill(255, 100, 100);
        text(loginMessage, width / 2f, height * 0.88f);
    }

    public void handleLoginTextInput() {
        if (key == BACKSPACE) {
            if (typingPassword && typedPassword.length() > 0) {
                typedPassword = typedPassword.substring(0, typedPassword.length() - 1);
            } else if (!typingPassword && typedUsername.length() > 0) {
                typedUsername = typedUsername.substring(0, typedUsername.length() - 1);
            }
        } else if (key == TAB) {
            typingPassword = !typingPassword;
        } else if (key != ENTER && key != RETURN && key != CODED) {
            if (typingPassword) {
                typedPassword += key;
            } else {
                typedUsername += key;
            }
        }
    }

    public void resetLoginInput() {
        typedUsername = "";
        typedPassword = "";
        typingPassword = false;
        loginMessage = "";
    }

    public void openLoginScreen() {
        loginStep = 0;
        selectedLoginOption = 0;
        resetLoginInput();
        state = GameState.LOGIN;
    }

    public void goBackFromLogin() {
        if (loginStep == 0) {
            state = GameState.MENU;
        } else {
            loginStep = 0;
            resetLoginInput();
        }
    }

    public void submitLogin() {
        if (typedUsername.length() == 0 || typedPassword.length() == 0) {
            loginMessage = "Username and password required";
            return;
        }

        currentPlayer = userDAO.login(typedUsername, typedPassword);

        if (currentPlayer != null) {
            databaseHighscores = scoreDAO.getBestScoresForUser(currentPlayer.getID());
            resetLoginInput();
            state = GameState.MENU;
        } else {
            loginMessage = "Wrong username or password";
        }
    }

    public void submitRegister() {
        if (typedUsername.length() == 0 || typedPassword.length() == 0) {
            loginMessage = "Username and password required";
            return;
        }

        boolean success = userDAO.register(typedUsername, typedPassword);

        if (success) {
            currentPlayer = userDAO.login(typedUsername, typedPassword);
            databaseHighscores = scoreDAO.getBestScoresForUser(currentPlayer.getID());
            resetLoginInput();
            state = GameState.MENU;
        } else {
            loginMessage = "Username already exists";
        }
    }
    public void checkVerloren() {
        if(spielfeld.kopfZelle.getX() >= Spielfeld.fieldExtent*Spielfeld.extent || spielfeld.kopfZelle.getX() < 0 ||
        (spielfeld.kopfZelle.getY()) >= Spielfeld.fieldExtent*Spielfeld.extent || spielfeld.kopfZelle.getY() < 0) {
            state = GameState.GAMEOVER;
        }
        for (int i = 0; i < spielfeld.bodyCnt; i++)  {
            if (spielfeld.kopfZelle.getX()==spielfeld.snakeBody[i].getX() && spielfeld.kopfZelle.getY() == spielfeld.snakeBody[i].getY()) {
                state = GameState.GAMEOVER;
            }
        }
    }
    public void changeDirection(int richtung, int opposite) {
        hasStarted = true;
        if (spielfeld.kopfZelle.lastRichtung != opposite) {
            spielfeld.nextRichtung = richtung;
        }
    }


    public void menuControlRight() {
        if (selectedButton < buttonArray.length-1) selectedButton++;
        else selectedButton = 0;
    }
    public void menuControlLeft() {
        if (selectedButton > 0) selectedButton--;
        else selectedButton = buttonArray.length-1;
    }
    public void keyPressed() {
        if (state == GameState.LOGIN) {
            if (key == 'r' || key == 'R') {
                goBackFromLogin();
                return;
            }

            if (loginStep == 0) {
                if (key == CODED && (keyCode == LEFT || keyCode == RIGHT)) {
                    selectedLoginOption = 1 - selectedLoginOption;
                }

                if (key == ENTER || key == RETURN) {
                    resetLoginInput();
                    loginStep = selectedLoginOption == 0 ? 1 : 2;
                }
                return;
            }

            if (loginStep == 1) {
                if (key == ENTER || key == RETURN) {
                    submitLogin();
                } else {
                    handleLoginTextInput();
                }
                return;
            }

            if (loginStep == 2) {
                if (key == ENTER || key == RETURN) {
                    submitRegister();
                } else {
                    handleLoginTextInput();
                }
                return;
            }
        }

        if (key == CODED) {
            if (keyCode == UP) {
                if (state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.DOWN) {
                    changeDirection(Zelle.UP, Zelle.DOWN);
                }
                if (state == GameState.SETTINGS) {
                    selectedSetting = (selectedSetting - 1 + 3) % 3;
                }
            }

            if (keyCode == DOWN) {
                if (state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.UP) {
                    changeDirection(Zelle.DOWN, Zelle.UP);
                }
                if (state == GameState.SETTINGS) {
                    selectedSetting = (selectedSetting + 1) % 3;
                }
            }

            if (keyCode == LEFT) {
                if (state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.RIGHT) {
                    changeDirection(Zelle.LEFT, Zelle.RIGHT);
                }

                if (state == GameState.MENU) {
                    menuControlLeft();
                }

                if (state == GameState.SETTINGS) {
                    switch (selectedSetting) {
                        case 0:
                            selectedSpeed = (selectedSpeed - 1 + speedValues.length) % speedValues.length;
                            break;
                        case 1:
                            if (Spielfeld.fieldExtent > 10) {
                                Spielfeld.fieldExtent -= 2;
                            }
                            break;
                        case 2:
                            if (Spielfeld.extent > 30) {
                                Spielfeld.extent -= 10;
                            }
                            break;
                    }
                }
            }

            if (keyCode == RIGHT) {
                if (state == GameState.PLAYING && spielfeld.kopfZelle.lastRichtung != Zelle.LEFT) {
                    changeDirection(Zelle.RIGHT, Zelle.LEFT);
                }

                if (state == GameState.MENU) {
                    menuControlRight();
                }

                if (state == GameState.SETTINGS) {
                    switch (selectedSetting) {
                        case 0:
                            selectedSpeed = (selectedSpeed + 1) % speedValues.length;
                            break;
                        case 1:
                            if (Spielfeld.fieldExtent < 16) {
                                Spielfeld.fieldExtent += 2;
                            }
                            break;
                        case 2:
                            if (Spielfeld.extent < 60) {
                                Spielfeld.extent += 10;
                            }
                            break;
                    }
                }
            }
        }

        if (key == ENTER || key == RETURN) {
            if (state == GameState.MENU) {
                if (selectedButton == 0) {
                    selectedSetting = 0;
                    spielfeld.getScoreboard().reset();
                    hasStarted = false;
                    scoreSaved = false;
                    spielfeld.getScoreboard().setDifficulty(selectedSpeed);
                    moveTime = speedValues[selectedSpeed];
                    state = GameState.PLAYING;
                } else if (selectedButton == 1) {
                    state = GameState.SETTINGS;
                } else if (selectedButton == 2) {
                    if (currentPlayer != null) {
                        databaseHighscores = scoreDAO.getBestScoresForUser(currentPlayer.getID());
                    }
                    state = GameState.HIGHSCORES;
                } else if (selectedButton == 3) {
                    openLoginScreen();
                }
            } else if (state == GameState.SETTINGS) {
                size = Spielfeld.extent * Spielfeld.fieldExtent;
                windowResize(size, size);
                spielfeld = new Spielfeld(this);
                state = GameState.MENU;
            } else if (state == GameState.GAMEOVER || state == GameState.HIGHSCORES) {
                state = GameState.MENU;
            }
        }

        if (key == 'r' || key == 'R') {
            if (state == GameState.PLAYING || state == GameState.GAMEOVER) {
                hasStarted = false;
                state = GameState.MENU;
            }
        }
    }
}
