import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import net.java.games.input.*; 
import org.gamecontrolplus.*; 
import org.gamecontrolplus.gui.*; 
import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Mismatched extends PApplet {

//import g4p_controls.*;





ControlIO control;
ControlDevice device;
ControlHat hat;
ControlButton A;

boolean keyBoardUp, keyBoardDown, keyBoardLeft, keyBoardRight, gpActivate, usingController;
boolean select = true;

Level[] world = new Level[6];
int currentLevel;
Player[] players = new Player[2];
PImage player1, playerL, playerR, player2, player2L, player2R, level1BackgroundL, level1BackgroundR, spaceBackground, spaceBackgroundL, spaceBackgroundR;
boolean gameStart = false;
boolean endGame = false;
int health = 4;
Stopwatch timer = new Stopwatch(100);
Stopwatch timer2 = new Stopwatch(10);
boolean dead = false;
SoundFile TVHum, TVClick, spaceTheme, finalTheme, level2Theme;
boolean menuSetup = true;
boolean player1Ready;
boolean player2Ready;

//level 0 globals
Platform[] level0Plats = new Platform[6];
MovingPlatform[] level0MovingPlats = new MovingPlatform[2];
Door tutDoor1 = new Door(480, 340, 50, 50, false);
Door tutDoor2 = new Door(960, 200, 50, 50, false);
Switch tutButton1 = new Switch(800, 550, 30, 40, "cir");
Switch tutButton2 = new Switch(350, 200, 30, 40, "cir");
Door tutEnd = new Door(300, 150, 50, 50, true);
Door tutEnd2 = new Door(1000, 150, 50, 50, true);

//level 1 globals
Platform[] level1Plats = new Platform[6];
MovingPlatform[] level1MovingPlats = new MovingPlatform[2];
Switch button = new Switch(150, 650, 20, 20, "cir");
Switch button2 = new Button(900, 75, 60, 30, "rect");
Door endDoor = new Door(100, 50, 50, 50, true);
Door endDoor2 = new Door(1180, 50, 50, 50, true);

//level 2 globals
PImage oceanL, oceanR;
Platform[] level2Plats = new Platform[8];
MovingPlatform[] level2MovingPlats = new MovingPlatform[4];
Door level2End2 = new Door(900, 650, 50, 50, true);
Door level2End = new Door(490, 450, 50, 50, true);
Switch level2Switch = new Switch(350, 655, 20, 50, "cir");
Switch level2Switch2 = new Switch(100, 550, 20, 50, "cir");
Switch level2Switch3 = new Switch(865, 300, 20, 50, "cir");
Button level2Button = new Button(1065, 300, 50, 50, "rect");

//level 3 globals
PImage desertL, desertR;
Platform[] level3Plats = new Platform[12];
MovingPlatform[] level3MovingPlats = new MovingPlatform[2];
Door level3End = new Door(550, 160, 50, 50, true);
Door level3End2 = new Door(750, 500, 50, 50, true);
Switch level3Switch = new Switch(350, 360, 20, 50, "cir");
Switch level3Switch2 = new Switch(150, 560, 20, 50, "cir");
Switch level3Switch3 = new Switch(1130, 230, 20, 50, "cir");
Button level3Button = new Button(150, 160, 40, 40, "rect");
Button level3Button2 = new Button(750, 410, 40, 40, "rect");
Button level3Button3 = new Button(930, 250, 40, 40, "rect");

//level 4 globals
Platform[] level4Plats = new Platform[13];
MovingPlatform[] level4MovingPlats = new MovingPlatform[5];
Door level4End = new Door(550, 100, 50, 50, true);
Door level4End2 = new Door(1150, 150, 50, 50, true);
Switch level4Switch = new Switch(360, 600, 20, 40, "cir");
Switch level4Switch2 = new Switch(450, 200, 20, 40, "cir");
Button level4Button = new Button(160, 100, 50, 50, "rect");

Button level4Button2 = new Button(860, 400, 50, 50, "rect");
Switch level4Switch3 = new Switch(925, 670, 20, 40, "cir");
Switch level4Switch4 = new Switch(800, 150, 20, 40, "cir");

//level5globals
Platform[] level5Plats = new Platform[15];
MovingPlatform [] level5MovingPlats = new MovingPlatform[5];
Door level5End = new Door(175, 360, 50, 50, true);
Door level5End2 = new Door(710, 560, 50, 50, true);
Button level5Button = new Button(320, 360, 50, 50, "rect");
Button level5Button2 = new Button(320, 60, 50, 50, "rect");
Switch level5Switch = new Switch(470, 560, 20, 50, "cir");
Switch level5Switch2 = new Switch(320, 650, 20, 50, "cir");
Switch level5Switch3 = new Switch(520, 160, 20, 50, "cir");

Button level5Button3 = new Button(860, 360, 50, 50, "rect");
Button level5Button4 = new Button(1160, 260, 50, 50, "rect");
Switch level5Switch4 = new Switch(1060, 560, 20, 50, "cir");
Switch level5Switch5 = new Switch(1160, 160, 20, 50, "cir");
Switch level5Switch6 = new Switch(860, 160, 20, 50, "cir");
Switch level5Switch7 = new Switch(710, 160, 20, 50, "cir");

PImage sceneDoor, levelDoor, buttonOff, buttonOn, switchOn, switchOff, wall, standardPlat, invisPlat, movingPlat, longPlat, startScreen, startScreenHover, selectScreen, selectScreenGp, forestL, forestR;
MouseButton startButton;

boolean up, down, left, right;
public void setup() {
  //controller setup
  control = ControlIO.getInstance(this);
  timer2.active = false;

  //global creation
  
  forestL = loadImage("forestL.png");
  forestR = loadImage("forestR.png");
  oceanL = loadImage("OceanL.png");
  oceanR = loadImage("OceanR.png");
  desertL = loadImage("DesertL.png");
  desertR = loadImage("DesertR.png");
  spaceTheme = new SoundFile(this, "spaceTheme.mp3");
  TVHum = new SoundFile(this, "TVHum.mp3");
  TVClick = new SoundFile(this, "TVClick.mp3");
  finalTheme = new SoundFile(this, "finalLevel.mp3");
  level2Theme = new SoundFile(this, "level2.mp3");
  spaceBackground = loadImage("SpaceBackground.png");
  spaceBackgroundL = loadImage("SpaceL.png");
  spaceBackgroundR = loadImage("SpaceR.png");
  startScreen = loadImage("startScreen.png");
  startScreenHover = loadImage("startScreenHover.png");
  selectScreen = loadImage("selectScreen.png");
  selectScreenGp = loadImage("selectScreenGp.png");
  wall = loadImage("Wall.png");
  sceneDoor = loadImage("Door.png");
  levelDoor = loadImage("ExitDoor.png");
  buttonOff = loadImage("ButtonOff.png");
  buttonOn = loadImage("ButtonOn.png");
  switchOn = loadImage("SwitchOn.png");
  switchOff = loadImage("SwitchOff.png");
  standardPlat = loadImage("Platform1.png");
  longPlat = loadImage("Platform12.png");
  invisPlat = loadImage("Platform2.png");
  movingPlat = loadImage("Platform3.png");
  player1 = loadImage("player.png");
  playerL = loadImage("PlayerL.png");
  playerR = loadImage("PlayerR.png");
  player2 = loadImage("Player2.png");
  player2L = loadImage("Player2L.png");
  player2R = loadImage("Player2R.png");
  level1BackgroundL = loadImage("Level1BackgroundL.png");
  level1BackgroundR = loadImage("Level1BackgroundR.png");
  world[0] = new Level("0", spaceBackgroundL, spaceBackgroundR);
  world[1] = new Level("1", level1BackgroundL, level1BackgroundR);
  world[2] = new Level("2", oceanL, oceanR);
  world[3] = new Level("3", desertL, desertR);
  world[4] = new Level("4", forestL, forestR);
  world[5] = new Level("5", spaceBackgroundL, spaceBackgroundR);
  currentLevel = 0;
  startButton = new MouseButton(width/2, 250, 500, 300);

  players[0] = new Player(550, 650, player1, "Player1", 60, 60);
  players[1] = new Player((width/4)*3, 650, player1, "Player2", 60, 60);

  //tutorial creation 
  world[0].setStartingPosL((width/4), height/2);
  world[0].setStartingPosR((width/4)*3, (height/2));
  level0Plats[0] = new Platform(width/4, height/2, 400, 100, 20, 20, 20); 
  level0Plats[1] = new Platform((width/4)*3, height/2, 100, 400, 20, 20, 20); 
  level0Plats[2] = new Platform(width/4, height-150, 400, 100, 20, 20, 20);
  level0Plats[5] = new Platform((width/4)*3, height-150, 400, 100, 20, 20, 20);
  level0Plats[3] = new Platform(width/4, 200, 200, 200, 20, 20, 20);
  level0Plats[4] = new Platform((width/4)*3, 200, 200, 200, 20, 20, 20);
  level0MovingPlats[0] = new MovingPlatform(width/4, 320, 100, 100, 20, 20, 20, width/4, 500);
  level0MovingPlats[1] = new MovingPlatform((width/4)*3, 320, 100, 100, 20, 20, 20, (width/4)*3, 500);

  //level 1 creation
  level1Plats[0] = new Platform(550, 650, 100, 100, 20, 20, 20);
  level1Plats[1] = new Platform((width/4)*3, 650, 550, 100, 20, 20, 20);
  level1Plats[2] = new Platform(150, 650, 100, 100, 20, 20, 20);
  level1Plats[3] = new Platform(width/4, 312, 100, 380, 20, 20, 20);
  level1Plats[4] = new Platform((width/4)*3, 75, 550, 100, 20, 20, 20);
  level1Plats[5] = new Platform((width/4), 75, 550, 100, 20, 20, 20);
  level1MovingPlats[0] = new DirectionPlatform(550, 550, 100, 100, 20, 20, 20, 150, 550, false, true);
  level1MovingPlats[1] = new MovingPlatform((width/4)*3, 150, 100, 100, 20, 20, 20, (width/4)*3, 575);

  //level 2 creation
  level2Plats[0] = new Platform(width/4, 650, 100, 100, 20, 20, 20); //player1 start palt
  level2Plats[1] = new Platform(100, 550, 100, 100, 20, 20, 20);
  level2Plats[2] = new Platform(width/4, 100, 200, 100, 20, 20, 20); //dissapearing
  level2Plats[3] = new Platform((width/4)+150, 300, 100, 400, 20, 20, 20);
  level2MovingPlats[0] = new DirectionPlatform(width/4, 550, 100, 110, 20, 20, 20, 180, 600, false, true);
  level2MovingPlats[1] = new MovingPlatform(width/4, 480, 100, 100, 20, 20, 20, width/4, 180);

  level2Plats[4] = new Platform((width/4)*3, 100, 100, 100, 20, 20, 20);
  level2Plats[5] = new Platform(((width/4)*3)-100, 300, 100, 100, 20, 20, 20);
  level2Plats[6] = new Platform(((width/4)*3)+100, 300, 100, 100, 20, 20, 20);
  level2Plats[7] = new Platform((width/4)*3, 650, 200, 100, 20, 20, 20);
  level2MovingPlats[2] = new DirectionPlatform((width/4)*3, 550, 110, 110, 20, 20, 20, (width/4)*3, 400, true, false);  
  level2MovingPlats[3] = new MovingPlatform((width/4)*3, 170, 120, 60, 20, 20, 20, (width/4)*3, 340);

  //level 3 creation
  level3Plats[0] = new Platform(550, 260, 100, 100, 20, 20, 20);
  level3Plats[1] = new Platform(550, 160, 100, 100, 20, 20, 20); //dissapearing
  level3Plats[2] = new Platform(350, 360, 110, 110, 20, 20, 20);
  level3Plats[3] = new Platform(150, 160, 110, 110, 20, 20, 20);
  level3Plats[4] = new Platform(150, 410, 100, 210, 20, 20, 20); //dissapearing
  level3Plats[5] = new Platform(150, 560, 100, 100, 20, 20, 20);
  level3MovingPlats[0] = new MovingPlatform(470, 260, 100, 100, 20, 20, 20, 150, 260);

  level3Plats[6] = new Platform(750, 410, 100, 100, 20, 20, 20);
  level3Plats[7] = new Platform(750, 500, 100, 100, 20, 20, 20); //dissapearing
  level3Plats[8] = new Platform(950, 320, 100, 100, 20, 20, 20); //dissapearing
  level3Plats[9] = new Platform(950, 230, 100, 100, 20, 20, 20);
  level3Plats[10] = new Platform(1040, 230, 100, 100, 20, 20, 20); //dissapearing
  level3Plats[11] = new Platform(1130, 230, 100, 100, 20, 20, 20);
  level3MovingPlats[1] = new DirectionPlatform(1150, 410, 100, 100, 20, 20, 20, 840, 410, true, false);

  //level 4 creation
  level4MovingPlats[0] = new DirectionPlatform(550, 400, 100, 100, 20, 20, 20, 200, 400, false, true);
  level4Plats[0] = new Platform(160, 400, 100, 100, 20, 20, 20); //dissapearing
  level4Plats[1] = new Platform(160, 550, 100, 220, 20, 20, 20); //switch
  level4Plats[2] = new Platform(360, 610, 300, 100, 20, 20, 20);
  level4Plats[3] = new Platform(160, 250, 100, 210, 20, 20, 20); //dissapearing
  level4Plats[4] = new Platform(160, 100, 100, 100, 20, 20, 20); //button
  level4MovingPlats[1] = new MovingPlatform(240, 100, 120, 120, 20, 20, 20, 470, 100);
  level4Plats[5] = new Platform(450, 200, 100, 100, 20, 20, 20);
  level4Plats[6] = new Platform(550, 100, 100, 100, 20, 20, 20);

  level4MovingPlats[2] = new DirectionPlatform(1150, 400, 100, 100, 20, 20, 20, 950, 400, true, false);
  level4Plats[7] = new Platform(860, 400, 100, 100, 20, 20, 20);
  level4Plats[8] = new Platform(1150, 575, 100, 275, 20, 20, 20); //dissapearing
  level4Plats[9] = new Platform(925, 670, 350, 100, 20, 20, 20);
  level4MovingPlats[3] = new DirectionPlatform(700, 400, 100, 100, 20, 20, 20, 700, 250, true, false);
  level4Plats[10] = new Platform(800, 250, 100, 100, 20, 20, 20); //dissapearing
  level4Plats[11] = new Platform(800, 150, 100, 100, 20, 20, 20);
  level4MovingPlats[4] = new MovingPlatform(880, 250, 100, 100, 20, 20, 20, 1150, 250);
  level4Plats[12] = new Platform(1150, 150, 100, 100, 20, 20, 20);

  //level 5 creation
  level5Plats[0] = new Platform(320, 360, 100, 100, 20, 20, 20);
  level5Plats[1] = new Platform(175, 360, 100, 100, 20, 20, 20); //dissapearing/end
  level5MovingPlats[0] = new MovingPlatform(220, 460, 100, 100, 20, 20, 20, 420, 460); //dissapearing
  level5Plats[2] = new Platform(320, 600, 100, 200, 20, 20, 20); //dissapearing
  level5Plats[3] = new Platform(470, 550, 100, 100, 20, 20, 20);
  level5MovingPlats[1] = new DirectionPlatform(420, 260, 100, 100, 20, 20, 20, 220, 260, false, true); //dissapearing
  level5MovingPlats[2] = new MovingPlatform(220, 160, 110, 110, 20, 20, 20, 430, 160);
  level5Plats[4] = new Platform(520, 160, 100, 100, 20, 20, 20);
  level5Plats[5] = new Platform(320, 60, 100, 100, 20, 20, 20);

  level5Plats[6] = new Platform(860, 360, 100, 100, 20, 20, 20);
  level5Plats[7] = new Platform(960, 460, 200, 100, 20, 20, 20); //dissapearing
  level5Plats[8] = new Platform(1060, 555, 100, 100, 20, 20, 20);
  level5MovingPlats[3] = new DirectionPlatform(1160, 560, 100, 100, 20, 20, 20, 1160, 360, true, false);
  level5Plats[9] = new Platform(1160, 265, 100, 100, 20, 20, 20);
  level5Plats[10] = new Platform(1160, 170, 100, 100, 20, 20, 20); //dissapearing
  level5Plats[11] = new Platform(710, 410, 100, 200, 20, 20, 20);
  level5Plats[12] = new Platform(710, 550, 100, 100, 20, 20, 20); //dissapearing/end
  level5MovingPlats[4] = new MovingPlatform(860, 260, 100, 100, 20, 20, 20, 760, 260); //dissapearing
  level5Plats[13] = new Platform(860, 165, 100, 100, 20, 20, 20);
  level5Plats[14] = new Platform(710, 165, 100, 100, 20, 20, 20);
}

public void draw() {
  if (select) {
    selectController();
  } else {
    if (!player2Ready && startButton.clicked && usingController) {
      if (up || down || left || right || device.getButton("Button 0").pressed())
      {
        TVClick.play();
        if (player1Ready) {
          spaceTheme.amp(0.2f);
          spaceTheme.play();
          spaceTheme.stop();
          spaceTheme.loop();
        }
        player2Ready = true;
      }
    }
    //controls
    if (device != null) {
      up = hat.up();
      down = hat.down();
      left = hat.left();
      right = hat.right();
      if (up) {
        println("up"); 
        players[1].moveNorth();
      }
      if (down) {
        println("down"); 
        players[1].moveSouth();
      }
      if (left) {
        println("left"); 
        players[1].moveWest();
      }
      if (right) {
        println("right"); 
        players[1].moveEast();
      }

      if (device.getButton("Button 0").pressed()) {
        if (!timer2.active) {
          players[1].activateObject();
          timer2.resetClock();
        }
      }

      timer2.startClock(); 

      if (!up && players[1].movingUp && !keyBoardUp) {
        players[1].stopPlayer();
      }

      if (!down && players[1].movingDown && !keyBoardDown) {
        players[1].stopPlayer();
      }

      if (!left && players[1].movingLeft && !keyBoardLeft) {
        players[1].stopPlayer();
      }

      if (!right && players[1].movingRight && !keyBoardRight) {
        players[1].stopPlayer();
      }
    }

    //maingame 
    if (!gameStart) {
      gameMenu();
      currentLevel = 0;
    } else {
      if (!endGame) {
        background(255);
        world[currentLevel].playLevel();
        line(width/2, 0, width/2, 720);
        checkGameOver();
      } else {
        gameEnd();
      }
    }
  }
}

public void keyPressed() {
  if(endGame){
    finalTheme.stop();
    gameStart = false;
    endGame = false;
    currentLevel = 0;
    startButton.clicked = false;
    menuSetup = true;
    player1Ready = false;
    player2Ready = false;
    endDoor.entered = false;
    endDoor2.entered = false;
    tutDoor1.entered = false;
    tutDoor2.entered = false;
    tutEnd.entered = false;
    tutEnd2.entered = false;
    level2End.entered = false;
    level2End2.entered = false;
    level3End.entered = false;
    level3End2.entered = false;
    level4End.entered = false;
    level4End2.entered = false;
    level5End.entered = false;
    level5End2.entered = false;
  }
  if (select) {
    if (key == '1') {
      device = control.getMatchedDevice("gpConfig");
      if (device != null) {
        A = device.getButton("Button 0");
        hat = device.getHat("cooliehat: Hat Switch");
        usingController = true;
        select = false;
      }
    } else if (key == '2') {
      usingController = false;
      select = false;
    }
  }
  println("key pressed");
  if (player1Ready && player2Ready && !gameStart) {
    gameStart = true;
  }
  if (!player1Ready && startButton.clicked) {
    if (key == 'w' || key == 'a' || key == 'd' || key == 's' || key ==  'W' || key == 'A' || key == 'S' || key == 'D' || key == 'q' || key == 'Q' || key == 'e' || key == 'E')
    {
      TVClick.play();
      if (player2Ready) {
        spaceTheme.amp(0.2f);
        spaceTheme.play();
        spaceTheme.stop();
        spaceTheme.loop();
      }
      player1Ready = true;
    }
  }
  if (key == 'w') {
    players[0].moveNorth();
  } else if (key == 's' || key == 'S') {
    players[0].moveSouth();
  } else if (key == 'a' || key == 'A') {
    players[0].moveWest();
  } else if (key == 'd' || key == 'D') {
    players[0].moveEast();
  } else if (key == 'q' || key == 'e' || key == 'Q' || key == 'E') {
    players[0].activateObject();
  }
  if (key == CODED) {
    if (!player2Ready && startButton.clicked) {
      if (keyCode == UP || keyCode == 12 || keyCode == DOWN || keyCode == LEFT || keyCode ==  RIGHT || keyCode == 33 || keyCode == 36)
      {
        TVClick.play();
        if (player1Ready) {
          spaceTheme.amp(0.2f);
          spaceTheme.play();
          spaceTheme.stop();
          spaceTheme.loop();
        }
        player2Ready = true;
      }
    }
    if (keyCode == UP) {
      keyBoardUp = true;
      players[1].moveNorth();
    } else if (keyCode == 12 || keyCode == DOWN) {
      keyBoardDown = true;
      players[1].moveSouth();
    } else if (keyCode == LEFT) {
      keyBoardLeft = true;
      players[1].moveWest();
    } else if (keyCode == RIGHT) {
      keyBoardRight = true;
      players[1].moveEast();
    } else if (keyCode == 33 || keyCode == 36) {
      players[1].activateObject();
    }
  }
}

public void keyReleased() {
  if (key == 'w' || key == 's' || key == 'a' || key == 'd') {
    players[0].stopPlayer();
  }
  if (key == CODED) {
    if (keyCode == UP || keyCode == DOWN || keyCode == LEFT || keyCode == RIGHT || keyCode == 12) {
      keyBoardUp = false;
      keyBoardDown = false;
      keyBoardLeft = false;
      keyBoardRight = false;
      players[1].stopPlayer();
    }
  }
}

public void checkCollisionL() {
  int collision = 0;
  if (world[currentLevel].levelName == "1") {
    for (int j = 0; j < (level1Plats.length); j++) {
      for (int t = 0; t < (level1MovingPlats.length); t++) {
        if (players[0].xy.x > (level1Plats[j].pos.x)-(level1Plats[j].size.x/2) && players[0].xy.x < (level1Plats[j].pos.x)+(level1Plats[j].size.x/2) && players[0].xy.y > (level1Plats[j].pos.y)-(level1Plats[j].size.y/2) && players[0].xy.y < (level1Plats[j].pos.y)+(level1Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level1MovingPlats[t].pos.x)-(level1MovingPlats[t].size.x/2) && players[0].xy.x < (level1MovingPlats[t].pos.x)+(level1MovingPlats[t].size.x/2) && players[0].xy.y > (level1MovingPlats[t].pos.y)-(level1MovingPlats[t].size.y/2) && players[0].xy.y < (level1MovingPlats[t].pos.y)+(level1MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "0") {
    for (int j = 0; j < (level0Plats.length); j++) {
      for (int t = 0; t < (level0MovingPlats.length); t++) {
        if (players[0].xy.x > (level0Plats[j].pos.x)-(level0Plats[j].size.x/2) && players[0].xy.x < (level0Plats[j].pos.x)+(level0Plats[j].size.x/2) && players[0].xy.y > (level0Plats[j].pos.y)-(level0Plats[j].size.y/2) && players[0].xy.y < (level0Plats[j].pos.y)+(level0Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level0MovingPlats[t].pos.x)-(level0MovingPlats[t].size.x/2) && players[0].xy.x < (level0MovingPlats[t].pos.x)+(level0MovingPlats[t].size.x/2) && players[0].xy.y > (level0MovingPlats[t].pos.y)-(level0MovingPlats[t].size.y/2) && players[0].xy.y < (level0MovingPlats[t].pos.y)+(level0MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "2") {
    for (int j = 0; j < (level2Plats.length); j++) {
      for (int t = 0; t < (level2MovingPlats.length); t++) {
        if (players[0].xy.x > (level2Plats[j].pos.x)-(level2Plats[j].size.x/2) && players[0].xy.x < (level2Plats[j].pos.x)+(level2Plats[j].size.x/2) && players[0].xy.y > (level2Plats[j].pos.y)-(level2Plats[j].size.y/2) && players[0].xy.y < (level2Plats[j].pos.y)+(level2Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level2MovingPlats[t].pos.x)-(level2MovingPlats[t].size.x/2) && players[0].xy.x < (level2MovingPlats[t].pos.x)+(level2MovingPlats[t].size.x/2) && players[0].xy.y > (level2MovingPlats[t].pos.y)-(level2MovingPlats[t].size.y/2) && players[0].xy.y < (level2MovingPlats[t].pos.y)+(level2MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "3") {
    for (int j = 0; j < (level3Plats.length); j++) {
      for (int t = 0; t < (level3MovingPlats.length); t++) {
        if (players[0].xy.x > (level3Plats[j].pos.x)-(level3Plats[j].size.x/2) && players[0].xy.x < (level3Plats[j].pos.x)+(level3Plats[j].size.x/2) && players[0].xy.y > (level3Plats[j].pos.y)-(level3Plats[j].size.y/2) && players[0].xy.y < (level3Plats[j].pos.y)+(level3Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level3MovingPlats[t].pos.x)-(level3MovingPlats[t].size.x/2) && players[0].xy.x < (level3MovingPlats[t].pos.x)+(level3MovingPlats[t].size.x/2) && players[0].xy.y > (level3MovingPlats[t].pos.y)-(level3MovingPlats[t].size.y/2) && players[0].xy.y < (level3MovingPlats[t].pos.y)+(level3MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "4") {
    for (int j = 0; j < (level4Plats.length); j++) {
      for (int t = 0; t < (level4MovingPlats.length); t++) {
        if (players[0].xy.x > (level4Plats[j].pos.x)-(level4Plats[j].size.x/2) && players[0].xy.x < (level4Plats[j].pos.x)+(level4Plats[j].size.x/2) && players[0].xy.y > (level4Plats[j].pos.y)-(level4Plats[j].size.y/2) && players[0].xy.y < (level4Plats[j].pos.y)+(level4Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level4MovingPlats[t].pos.x)-(level4MovingPlats[t].size.x/2) && players[0].xy.x < (level4MovingPlats[t].pos.x)+(level4MovingPlats[t].size.x/2) && players[0].xy.y > (level4MovingPlats[t].pos.y)-(level4MovingPlats[t].size.y/2) && players[0].xy.y < (level4MovingPlats[t].pos.y)+(level4MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "5") {
    for (int j = 0; j < (level5Plats.length); j++) {
      for (int t = 0; t < (level5MovingPlats.length); t++) {
        if (players[0].xy.x > (level5Plats[j].pos.x)-(level5Plats[j].size.x/2) && players[0].xy.x < (level5Plats[j].pos.x)+(level5Plats[j].size.x/2) && players[0].xy.y > (level5Plats[j].pos.y)-(level5Plats[j].size.y/2) && players[0].xy.y < (level5Plats[j].pos.y)+(level5Plats[j].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else if (players[0].xy.x > (level5MovingPlats[t].pos.x)-(level5MovingPlats[t].size.x/2) && players[0].xy.x < (level5MovingPlats[t].pos.x)+(level5MovingPlats[t].size.x/2) && players[0].xy.y > (level5MovingPlats[t].pos.y)-(level5MovingPlats[t].size.y/2) && players[0].xy.y < (level5MovingPlats[t].pos.y)+(level5MovingPlats[t].size.y/2)) {
          players[0].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[0].floor = false;
          }
        }
      }
    }
  }
}

public void checkCollisionR() {
  int collision = 0;
  if (currentLevel == 1) {
    for (int j = 0; j < (level1Plats.length); j++) {
      for (int t = 0; t < (level1MovingPlats.length); t++) {
        if (players[1].xy.x > (level1Plats[j].pos.x)-(level1Plats[j].size.x/2) && players[1].xy.x < (level1Plats[j].pos.x)+(level1Plats[j].size.x/2) && players[1].xy.y > (level1Plats[j].pos.y)-(level1Plats[j].size.y/2) && players[1].xy.y < (level1Plats[j].pos.y)+(level1Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level1MovingPlats[t].pos.x)-(level1MovingPlats[t].size.x/2) && players[1].xy.x < (level1MovingPlats[t].pos.x)+(level1MovingPlats[t].size.x/2) && players[1].xy.y > (level1MovingPlats[t].pos.y)-(level1MovingPlats[t].size.y/2) && players[1].xy.y < (level1MovingPlats[t].pos.y)+(level1MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  }
  if (currentLevel == 0) {
    for (int j = 0; j < (level0Plats.length); j++) {
      for (int t = 0; t < (level0MovingPlats.length); t++) {
        if (players[1].xy.x > (level0Plats[j].pos.x)-(level0Plats[j].size.x/2) && players[1].xy.x < (level0Plats[j].pos.x)+(level0Plats[j].size.x/2) && players[1].xy.y > (level0Plats[j].pos.y)-(level0Plats[j].size.y/2) && players[1].xy.y < (level0Plats[j].pos.y)+(level0Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level0MovingPlats[t].pos.x)-(level0MovingPlats[t].size.x/2) && players[1].xy.x < (level0MovingPlats[t].pos.x)+(level0MovingPlats[t].size.x/2) && players[1].xy.y > (level0MovingPlats[t].pos.y)-(level0MovingPlats[t].size.y/2) && players[1].xy.y < (level0MovingPlats[t].pos.y)+(level0MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "2") {
    for (int j = 0; j < (level2Plats.length); j++) {
      for (int t = 0; t < (level2MovingPlats.length); t++) {
        if (players[1].xy.x > (level2Plats[j].pos.x)-(level2Plats[j].size.x/2) && players[1].xy.x < (level2Plats[j].pos.x)+(level2Plats[j].size.x/2) && players[1].xy.y > (level2Plats[j].pos.y)-(level2Plats[j].size.y/2) && players[1].xy.y < (level2Plats[j].pos.y)+(level2Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level2MovingPlats[t].pos.x)-(level2MovingPlats[t].size.x/2) && players[1].xy.x < (level2MovingPlats[t].pos.x)+(level2MovingPlats[t].size.x/2) && players[1].xy.y > (level2MovingPlats[t].pos.y)-(level2MovingPlats[t].size.y/2) && players[1].xy.y < (level2MovingPlats[t].pos.y)+(level2MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "3") {
    for (int j = 0; j < (level3Plats.length); j++) {
      for (int t = 0; t < (level3MovingPlats.length); t++) {
        if (players[1].xy.x > (level3Plats[j].pos.x)-(level3Plats[j].size.x/2) && players[1].xy.x < (level3Plats[j].pos.x)+(level3Plats[j].size.x/2) && players[1].xy.y > (level3Plats[j].pos.y)-(level3Plats[j].size.y/2) && players[1].xy.y < (level3Plats[j].pos.y)+(level3Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level3MovingPlats[t].pos.x)-(level3MovingPlats[t].size.x/2) && players[1].xy.x < (level3MovingPlats[t].pos.x)+(level3MovingPlats[t].size.x/2) && players[1].xy.y > (level3MovingPlats[t].pos.y)-(level3MovingPlats[t].size.y/2) && players[1].xy.y < (level3MovingPlats[t].pos.y)+(level3MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "4") {
    for (int j = 0; j < (level4Plats.length); j++) {
      for (int t = 0; t < (level4MovingPlats.length); t++) {
        if (players[1].xy.x > (level4Plats[j].pos.x)-(level4Plats[j].size.x/2) && players[1].xy.x < (level4Plats[j].pos.x)+(level4Plats[j].size.x/2) && players[1].xy.y > (level4Plats[j].pos.y)-(level4Plats[j].size.y/2) && players[1].xy.y < (level4Plats[j].pos.y)+(level4Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level4MovingPlats[t].pos.x)-(level4MovingPlats[t].size.x/2) && players[1].xy.x < (level4MovingPlats[t].pos.x)+(level4MovingPlats[t].size.x/2) && players[1].xy.y > (level4MovingPlats[t].pos.y)-(level4MovingPlats[t].size.y/2) && players[1].xy.y < (level4MovingPlats[t].pos.y)+(level4MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  } else if (world[currentLevel].levelName == "5") {
    for (int j = 0; j < (level5Plats.length); j++) {
      for (int t = 0; t < (level5MovingPlats.length); t++) {
        if (players[1].xy.x > (level5Plats[j].pos.x)-(level5Plats[j].size.x/2) && players[1].xy.x < (level5Plats[j].pos.x)+(level5Plats[j].size.x/2) && players[1].xy.y > (level5Plats[j].pos.y)-(level5Plats[j].size.y/2) && players[1].xy.y < (level5Plats[j].pos.y)+(level5Plats[j].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else if (players[1].xy.x > (level5MovingPlats[t].pos.x)-(level5MovingPlats[t].size.x/2) && players[1].xy.x < (level5MovingPlats[t].pos.x)+(level5MovingPlats[t].size.x/2) && players[1].xy.y > (level5MovingPlats[t].pos.y)-(level5MovingPlats[t].size.y/2) && players[1].xy.y < (level5MovingPlats[t].pos.y)+(level5MovingPlats[t].size.y/2)) {
          players[1].floor = true;
          collision++;
        } else {
          if (collision == 0) {
            players[1].floor = false;
          }
        }
      }
    }
  }
}

public void gameEnd() {
  image(spaceBackground, width/2, height/2);
  textSize(40);
  fill(255);
  textAlign(CENTER);
  text("Congradulations, Players.", width/2, 310);
  text("You have completed your task, you may return to your world.", width/2, 410);
  text("Press any key.", width/2, 460);
}

public void checkGameOver() { 
  fill(255);
  rect(width/2, 40, 55, 25);
  fill(0);
  rect(width/2, 40, 50, 20);
  textSize(20);
  textAlign(CENTER);
  text("health", width/2, 25);
  fill(0, 255, 0);
  rect(width/2, 40, 10*health, 15);
  if (health == 0) {
    players[0].fall();
    players[1].fall();
    health = 4;
    timer.resetClock();
    timer.active = true;
    world[currentLevel].setUp = true;
    dead = true;
  }
  if (dead) {  
    timer.startClock();
    if (timer.active) {
      background(0);
      textAlign(CENTER);
      textSize(50);
      fill(255);
      text("Game Over. Try Again.", width/2, height/2);
    } else if (!timer.active) {
      dead = false;
    }
  }
}

public void gameMenu() {
  if (!startButton.clicked) {
    if (menuSetup) {
      TVHum.amp(0.2f);
      TVHum.play();
      TVHum.stop();
      TVHum.loop();
      menuSetup = false;
    }
    startButton.checkHover();
    startButton.drawHitboxes();
    imageMode(CENTER);
    image(startScreen, width/2, height/2);
    if (startButton.hover == true) {
      image(startScreenHover, width/2, height/2);
      if (mousePressed) {
        TVHum.stop();
        TVClick.amp(0.5f);
        TVClick.play();
        startButton.clicked = true;
      }
    }
  } else if (startButton.clicked) {
    if (!usingController) {
      image(selectScreen, width/2, height/2);
    } else if (usingController) {
      image(selectScreenGp, width/2, height/2);
    }
    if (player1Ready) {
      fill(0, 255, 0);
      noStroke();
      rectMode(CENTER);
      rect(155, 210, 80, 80);
    }
    if (player2Ready) {
      fill(0, 255, 0);
      noStroke();
      rectMode(CENTER);
      rect(1113, 210, 80, 80);
    }

    if (player1Ready && player2Ready) {
      image(spaceBackground, width/2, height/2);
      String text = "A sound echoes through your head... 'We have abducted you, Players, in order to test your problem solving and co-operation. If you pass our set challenges, you are free to leave. If not, well, you might just be stuck here for eternety.'";
      fill(255);
      textSize(30);
      text(text, width/2, 220, 1180, 400);
      text("Press any button to continue", 800, 500);
    }
  }
}

public void selectController() {
  background(0);
  fill(255);
  textSize(50);
  textAlign(CENTER);
  text("Choose a control scheme", width/2, 100);
  text("Press 1 to use Keyboard + Gamepad", width/2, 300);
  text("Press 2 to use Keyboard + Numpad", width/2, 500);
}
class Coord {
  int x;
  int y;
  Coord(int p1, int p2) {
    x = p1;
    y = p2;
  }
}

//stopwatch class can be used if a timer is needed
class Stopwatch {

  int time; 
  int waitTime;
  int lastTime;
  boolean active;

  Stopwatch(int p1) { //time is input here
    active = true;
    waitTime = p1;
    time = waitTime;
  }

  public void startClock() {
    if (time <= waitTime && time > 0) { //time is updated every frames so 30 time = 1 second
      time--;
      active = true;
    } else if (time == 0) {
      active = false;
    }
  }

  public void resetClock() {
    time = waitTime;
  }

  public boolean getSignal() {
    return active;
  }
}
class Level {

  boolean reset;
  String levelName;
  PImage backDropL, backDropR;
  boolean change;
  boolean screen1L, screen2L, screen3L, screen4L, setUp, screen1R, screen2R, screen3R, screen4R;
  Coord startingPosL, startingPosR;

  Level(String p1, PImage p2, PImage p3) {
    levelName = p1;
    setUp = true;
    screen1L = true;
    screen2L = false;
    screen3L = false;
    screen4L = false;
    screen1R = true;
    screen2R = false;
    screen3R = false;
    screen4R = false;
    backDropL = p2;
    backDropR = p3;
    //startingPos = p3;
  }

  public String getCurrentLevel() {
    return levelName;
  }

  public void setCurrentLevel(String p1) {
    levelName = p1;
  }

  public Coord getStartingPosL() {
    //return startingPos;
    return startingPosL;
  }
  public Coord getStartingPosR() {
    //return startingPos;
    return startingPosR;
  }

  public void setStartingPosL(int p1, int p2) {
    startingPosL = new Coord(p1, p2);
  }
  public void setStartingPosR(int p1, int p2) {
    startingPosR = new Coord(p1, p2);
  }

  public void playLevel() {
    drawLevel(levelName);
  }
}

public void drawLevel(String p1) {
  String levelName = p1;
  if (levelName == "1") {
    levelOne();
  } else if (levelName == "2") {
    levelTwo();
  } else if (levelName == "0") {
    levelZero();
  } else if (levelName == "3") {
    levelThree();
  } else if (levelName == "4") {
    levelFour();
  } else if (levelName == "5") {
    levelFive();
  }
}

public void levelZero() {
  if (world[0].setUp == true) {
    tutDoor1.activate();
    tutDoor2.activate();
    tutEnd.activate();
    tutEnd2.activate();
    println("settingUp"); 
    world[0].setStartingPosL((width/4)-100, height/2);
    world[0].setStartingPosR((width/4)*3, (height/2)+100);
    world[0].screen1L = true;
    world[0].screen1R = true;
    health = 4;
    players[0].xy.x = world[0].startingPosL.x;
    players[0].xy.y = world[0].startingPosL.y;
    players[1].xy.x = world[0].startingPosR.x;
    players[1].xy.y = world[0].startingPosR.y;
    world[0].setUp = false;
  }
  checkCollisionL();
  checkCollisionR();
  if (tutEnd.entered && tutEnd2.entered) {
    currentLevel++;
  }
  if (tutDoor1.entered && tutDoor2.entered) {
    level0Plats[0] = new Platform(width/4, height/2, 0, 0, 20, 20, 20); 
    level0Plats[1] = new Platform((width/4)*3, height/2, 0, 0, 20, 20, 20); 
    world[0].screen2L = true;
    world[0].screen2R = true;
    world[0].setStartingPosL((width/4), height-150);
    world[0].setStartingPosR((width/4)*3, height-150);
    world[0].screen1L = false;
    world[0].screen1R = false;
    if (!world[0].change) {
      health = 4;
      players[0].xy.x = world[0].startingPosL.x;
      players[0].xy.y = world[0].startingPosL.y;
      players[1].xy.x = world[0].startingPosR.x;
      players[1].xy.y = world[0].startingPosR.y;
      world[0].change = true;
    }
  }
  if (world[0].screen1L == true) {
    world[0].setStartingPosL((width/4)-100, height/2);
    imageMode(CORNER);
    image(world[0].backDropL, 0, 0);
    level0Plats[0].drawPlatform();
    imageMode(CENTER);
    tutDoor1.drawDoor();
    players[0].drawPlayer();
  }
  if (world[0].screen1R == true) {
    world[0].setStartingPosR((width/4)*3, (height/2)+100);
    imageMode(CORNER);
    image(world[0].backDropR, width/2, 0);
    level0Plats[1].drawPlatform();
    imageMode(CENTER);
    tutDoor2.drawDoor();
    players[1].drawPlayer();
  }
  if (world[0].screen2L == true) {
    world[0].setStartingPosL((width/4), height-150);
    imageMode(CORNER);
    image(world[0].backDropL, 0, 0);
    imageMode(CENTER);
    level0Plats[3].drawPlatform();
    level0MovingPlats[0].drawPlatform();
    if (tutButton1.on) {
      level0MovingPlats[0].movePlatform();
    }
    level0Plats[2].drawPlatform();
    tutButton2.drawSwitch();
    tutEnd.drawDoor();
    players[0].drawPlayer();
  }
  if (world[0].screen2R == true) {
    world[0].setStartingPosR((width/4)*3, height-150);
    imageMode(CORNER);
    image(world[0].backDropR, width/2, 0);
    imageMode(CENTER);
    level0Plats[4].drawPlatform();
    level0MovingPlats[1].drawPlatform();
    if (tutButton2.on) {
      level0MovingPlats[1].movePlatform();
    }
    level0Plats[5].drawPlatform();
    tutButton1.drawSwitch();
    tutEnd2.drawDoor();
    players[1].drawPlayer();
  }
}
public void levelOne() {
  if (world[1].setUp == true) {
    world[1].screen1L = true;
    world[1].screen1R = true;
    
    level1Plats[0] = new Platform(550, 650, 100, 100, 20, 20, 20);
    level1Plats[1] = new Platform((width/4)*3, 650, 550, 100, 20, 20, 20);
    level1Plats[2] = new Platform(150, 650, 100, 100, 20, 20, 20);
    level1Plats[3] = new Platform(width/4, 312, 100, 380, 20, 20, 20);
    level1Plats[4] = new Platform((width/4)*3, 75, 550, 100, 20, 20, 20);
    level1Plats[5] = new Platform((width/4), 75, 550, 100, 20, 20, 20);
    level1MovingPlats[0] = new DirectionPlatform(550, 550, 100, 100, 20, 20, 20, 150, 550, false, true);
    level1MovingPlats[1] = new MovingPlatform((width/4)*3, 150, 100, 100, 20, 20, 20, (width/4)*3, 575);
    
    button = new Switch(150, 650, 20, 20, "cir");
    button2 = new Button(900, 75, 60, 30, "rect");
    endDoor = new Door(100, 50, 50, 50, true);
    endDoor2 = new Door(1180, 50, 50, 50, true);
    
    world[1].setStartingPosL(550, 650);  
    world[1].setStartingPosR(960, 650);  
    
    players[0].xy.x = world[1].startingPosL.x;
    players[0].xy.y = world[1].startingPosL.y;
    players[1].xy.x = world[1].startingPosR.x;
    players[1].xy.y = world[1].startingPosR.y;
    
    level1Plats[3].platformDissapear();
    
    endDoor.activate();
    endDoor2.activate();
    health = 4;
    
    world[1].setUp = false;
  }
  
  checkCollisionL();
  checkCollisionR();
  
  if(endDoor.entered && endDoor2.entered){
   currentLevel++; 
  }
  
   if (world[1].screen1L == true) {
    world[1].setStartingPosL(550, 650);  
    imageMode(CORNER);
    image(world[1].backDropL, 0, 0);
    imageMode(CENTER);
    level1Plats[0].drawPlatform();
    level1Plats[2].drawPlatform();
    level1Plats[3].drawPlatform();
    if(button2.on){
     level1Plats[3].platformAppear(); 
    }
    else if(!button2.on){
     level1Plats[3].platformDissapear(); 
    }
    level1Plats[5].drawPlatform();
    level1MovingPlats[0].drawPlatform();
    level1MovingPlats[0].movePlatform();
    button.drawSwitch();
    endDoor.drawDoor();
    players[0].drawPlayer();
  }
  if (world[1].screen1R == true) {
    world[1].setStartingPosR(960, 650);  
    imageMode(CORNER);
    image(world[1].backDropR, width/2, 0);
    imageMode(CENTER);
    level1Plats[1].drawPlatform();
    level1Plats[4].drawPlatform();
    level1MovingPlats[1].drawPlatform();
    if(button.on){
     level1MovingPlats[1].movePlatform(); 
    }
    button2.drawSwitch();
    endDoor2.drawDoor();
    players[1].drawPlayer();
  }
 }

public void levelTwo() {

  if (world[2].setUp == true) {
    world[2].screen1L = true;
    world[2].screen1R = true;
    spaceTheme.amp(0);
    level2Theme.amp(0.2f);
    level2Theme.play();
    level2Theme.stop();
    level2Theme.loop();
    
    level2Plats[0] = new Platform(width/4, 650, 100, 100, 20, 20, 20); //player1 start palt
    level2Plats[1] = new Platform(100, 550, 100, 100, 20, 20, 20);
    level2Plats[2] = new Platform(width/4, 100, 200, 100, 20, 20, 20); //dissapearing
    level2Plats[3] = new Platform((width/4)+150, 300, 100, 400, 20, 20, 20);
    level2MovingPlats[0] = new DirectionPlatform(width/4, 550, 100, 110, 20, 20, 20, 180, 600, false, true);
    level2MovingPlats[1] = new MovingPlatform(width/4, 480, 100, 100, 20, 20, 20, width/4, 180);

    level2Plats[4] = new Platform((width/4)*3, 100, 100, 100, 20, 20, 20);
    level2Plats[5] = new Platform(((width/4)*3)-100, 300, 100, 100, 20, 20, 20);
    level2Plats[6] = new Platform(((width/4)*3)+100, 300, 100, 100, 20, 20, 20);
    level2Plats[7] = new Platform((width/4)*3, 650, 200, 100, 20, 20, 20);
    level2MovingPlats[2] = new DirectionPlatform((width/4)*3, 550, 110, 110, 20, 20, 20, (width/4)*3, 400, true, false);  
    level2MovingPlats[3] = new MovingPlatform((width/4)*3, 170, 120, 60, 20, 20, 20, (width/4)*3, 340);

    level2End2 = new Door(900, 650, 50, 50, true);
    level2End = new Door(490, 450, 50, 50, true);
    level2Switch = new Switch(350, 655, 20, 50, "cir");
    level2Switch2 = new Switch(100, 550, 20, 50, "cir");
    level2Switch3 = new Switch(865, 300, 20, 50, "cir");
    level2Button = new Button(1065, 300, 50, 50, "rect");
    
    world[2].setStartingPosL((width/4), 650);  
    world[2].setStartingPosR((width/4)*3, 100);  
    
    players[0].xy.x = world[2].startingPosL.x;
    players[0].xy.y = world[2].startingPosL.y;
    players[1].xy.x = world[2].startingPosR.x;
    players[1].xy.y = world[2].startingPosR.y;

    level2Plats[2].platformDissapear();
    level2Plats[6].platformDissapear();
    
    level2End.activate();
    level2End2.activate();
    
    health = 4;
    world[2].setUp = false;
  }

  checkCollisionL();
  checkCollisionR();
  if (level2End.entered && level2End2.entered) {
    currentLevel++;
  }
  
  if (world[2].screen1L == true) {
    world[2].setStartingPosL((width/4), 650);  
    imageMode(CORNER);
    image(world[2].backDropL, 0, 0);
    imageMode(CENTER);
    level2MovingPlats[1].drawPlatform();
    if (level2Switch3.on) {
      level2MovingPlats[1].movePlatform();
    }
    level2MovingPlats[0].drawPlatform();
    level2MovingPlats[0].movePlatform();
    if (level2Button.on) {
      level2Plats[2].platformAppear();
    } else if (!level2Button.on) {
      level2Plats[2].platformDissapear();
    }
    level2Plats[3].drawPlatform();
    level2Plats[2].drawPlatform();
    level2Plats[1].drawPlatform();
    level2Plats[0].drawPlatform();
    level2Switch.drawSwitch();
    level2Switch2.drawSwitch();
    level2End.drawDoor();
    players[0].drawPlayer();
  }
  if (world[2].screen1R == true) {
    world[2].setStartingPosR((width/4)*3, 100);  
    imageMode(CORNER);
    image(world[2].backDropR, width/2, 0);
    imageMode(CENTER);
    level2MovingPlats[2].drawPlatform();
    level2MovingPlats[2].movePlatform();
    level2Plats[7].drawPlatform();
    level2Plats[6].drawPlatform();
    level2Plats[5].drawPlatform();
    level2MovingPlats[3].drawPlatform();
    if (level2Switch.on) {
      level2MovingPlats[3].movePlatform();
    }
    if (level2Switch2.on) {
      level2Plats[6].platformAppear();
    }
    level2Plats[4].drawPlatform();
    level2Switch3.drawSwitch();
    level2Button.drawSwitch();
    level2End2.drawDoor();
    players[1].drawPlayer();
  }
}

public void levelThree() {

  if (world[3].setUp == true) {
    world[3].screen1L = true;
    world[3].screen1R = true;

    level3Plats[0] = new Platform(550, 260, 100, 100, 20, 20, 20);
    level3Plats[1] = new Platform(550, 160, 100, 100, 20, 20, 20); //dissapearing
    level3Plats[2] = new Platform(350, 360, 110, 110, 20, 20, 20);
    level3Plats[3] = new Platform(150, 160, 110, 110, 20, 20, 20);
    level3Plats[4] = new Platform(150, 410, 100, 210, 20, 20, 20); //dissapearing
    level3Plats[5] = new Platform(150, 560, 100, 100, 20, 20, 20);
    level3MovingPlats[0] = new MovingPlatform(470, 260, 100, 100, 20, 20, 20, 150, 260);

    level3Plats[6] = new Platform(750, 410, 100, 100, 20, 20, 20);
    level3Plats[7] = new Platform(750, 500, 100, 100, 20, 20, 20); //dissapearing
    level3Plats[8] = new Platform(950, 320, 100, 100, 20, 20, 20); //dissapearing
    level3Plats[9] = new Platform(950, 230, 100, 100, 20, 20, 20);
    level3Plats[10] = new Platform(1040, 230, 100, 100, 20, 20, 20); //dissapearing
    level3Plats[11] = new Platform(1130, 230, 100, 100, 20, 20, 20);
    level3MovingPlats[1] = new DirectionPlatform(1150, 410, 100, 100, 20, 20, 20, 840, 410, true, false);

    level3End = new Door(550, 160, 50, 50, true);
    level3End2 = new Door(750, 500, 50, 50, true);
    level3Switch = new Switch(350, 360, 20, 50, "cir");
    level3Switch2 = new Switch(150, 560, 20, 50, "cir");
    level3Switch3 = new Switch(1130, 230, 20, 50, "cir");
    level3Button = new Button(150, 160, 40, 40, "rect");
    level3Button2 = new Button(750, 410, 40, 40, "rect");
    level3Button3 = new Button(950, 230, 40, 40, "rect");

    world[3].setStartingPosL(550, 260);  
    world[3].setStartingPosR(750, 410);  
    players[0].xy.x = world[3].startingPosL.x;
    players[0].xy.y = world[3].startingPosL.y;
    players[1].xy.x = world[3].startingPosR.x;
    players[1].xy.y = world[3].startingPosR.y;
    level3Plats[1].platformDissapear();
    level3Plats[4].platformDissapear();
    level3Plats[7].platformDissapear();
    level3Plats[8].platformDissapear();
    level3Plats[10].platformDissapear();
    level3End.activate();
    level3End2.activate();
    health = 4;
    world[3].setUp = false;
  }

  checkCollisionL();
  checkCollisionR();

  if (level3End.entered && level3End2.entered) {
    currentLevel++;
  }

  if (world[3].screen1L == true) {
    world[3].setStartingPosL(550, 260);  
    imageMode(CORNER);
    image(world[3].backDropL, 0, 0);
    imageMode(CENTER);
    level3Plats[1].drawPlatform();
    level3Plats[0].drawPlatform();
    level3Plats[2].drawPlatform();
    level3Plats[3].drawPlatform();
    level3Plats[4].drawPlatform();
    level3Plats[5].drawPlatform();
    level3MovingPlats[0].drawPlatform();
    if (level3Button2.on) {
      level3MovingPlats[0].movePlatform();
    }
    if (level3Button3.on) {
      level3Plats[4].platformAppear();
    } else if (!level3Button3.on) {
      level3Plats[4].platformDissapear();
    }
    if (level3Switch3.on) {
      level3Plats[1].platformAppear();
    }
    level3Switch.drawSwitch();
    level3Switch2.drawSwitch();
    level3Button.drawSwitch();
    level3End.drawDoor();
    players[0].drawPlayer();
  }
  if (world[3].screen1R == true) {
    world[3].setStartingPosR(750, 410);  
    imageMode(CORNER);
    image(world[3].backDropR, width/2, 0);
    imageMode(CENTER);
    if (level3Switch.on) {
      level3Plats[8].platformAppear();
    }
    if (level3Switch2.on) {
      level3Plats[7].platformAppear();
    }
    if (level3Button.on) {
      level3Plats[10].platformAppear();
    } else if (!level3Button.on) {
      level3Plats[10].platformDissapear();
    }
    level3Plats[6].drawPlatform();
    level3Plats[7].drawPlatform();
    level3Plats[9].drawPlatform();
    level3Plats[10].drawPlatform();
    level3Plats[11].drawPlatform();
    level3Plats[8].drawPlatform();
    level3MovingPlats[1].drawPlatform();
    level3MovingPlats[1].movePlatform();
    level3Switch3.drawSwitch();
    level3Button2.drawSwitch();
    level3Button3.drawSwitch();
    level3End2.drawDoor();
    players[1].drawPlayer();
  }
}

public void levelFour() {
  if (world[4].setUp == true) {
    world[4].screen1L = true;
    world[4].screen1R = true;

    level4MovingPlats[0] = new DirectionPlatform(550, 400, 100, 100, 20, 20, 20, 200, 400, false, true);
    level4Plats[0] = new Platform(160, 400, 100, 100, 20, 20, 20); //dissapearing
    level4Plats[1] = new Platform(160, 550, 100, 220, 20, 20, 20); //switch
    level4Plats[2] = new Platform(360, 610, 310, 100, 20, 20, 20);
    level4Plats[3] = new Platform(160, 250, 100, 210, 20, 20, 20); //dissapearing
    level4Plats[4] = new Platform(160, 100, 100, 100, 20, 20, 20); //button
    level4MovingPlats[1] = new MovingPlatform(240, 100, 120, 120, 20, 20, 20, 470, 100);
    level4Plats[5] = new Platform(450, 200, 100, 100, 20, 20, 20);
    level4Plats[6] = new Platform(550, 100, 100, 100, 20, 20, 20);

    level4MovingPlats[2] = new DirectionPlatform(1150, 400, 100, 100, 20, 20, 20, 950, 400, true, false);
    level4Plats[7] = new Platform(860, 400, 100, 100, 20, 20, 20);
    level4Plats[8] = new Platform(1150, 575, 100, 275, 20, 20, 20); //dissapearing
    level4Plats[9] = new Platform(925, 670, 360, 100, 20, 20, 20);
    level4MovingPlats[3] = new DirectionPlatform(770, 530, 100, 100, 20, 20, 20, 770, 340, true, false);
    level4Plats[10] = new Platform(800, 250, 100, 100, 20, 20, 20); //dissapearing
    level4Plats[11] = new Platform(800, 150, 100, 100, 20, 20, 20);
    level4MovingPlats[4] = new MovingPlatform(880, 250, 100, 100, 20, 20, 20, 1150, 250);
    level4Plats[12] = new Platform(1150, 150, 100, 100, 20, 20, 20);

    level4End = new Door(550, 100, 50, 50, true);
    level4End2 = new Door(1150, 150, 50, 50, true);
    level4Switch = new Switch(360, 600, 20, 40, "cir");
    level4Switch2 = new Switch(450, 200, 20, 40, "cir");
    level4Button = new Button(160, 100, 50, 50, "rect");

    level4Button2 = new Button(860, 400, 50, 50, "rect");
    level4Switch3 = new Switch(925, 670, 20, 40, "cir");
    level4Switch4 = new Switch(800, 150, 20, 40, "cir");

    world[4].setStartingPosL(590, 400);  
    world[4].setStartingPosR(1190, 400);  
    players[0].xy.x = world[4].startingPosL.x;
    players[0].xy.y = world[4].startingPosL.y;
    players[1].xy.x = world[4].startingPosR.x;
    players[1].xy.y = world[4].startingPosR.y;
    level4Plats[0].platformDissapear();
    level4Plats[3].platformDissapear();
    level4Plats[8].platformDissapear();
    level4Plats[10].platformDissapear();
    level4End.activate();
    level4End2.activate();
    health = 4;
    world[4].setUp = false;
  }

  checkCollisionL();
  checkCollisionR();

  if (level4End.entered && level4End2.entered) {
    level2Theme.play();
    level2Theme.stop();
    currentLevel++;
  }

  if (world[4].screen1L == true) {
    world[4].setStartingPosL(level4MovingPlats[0].pos.x+40, level4MovingPlats[0].pos.y);  
    imageMode(CORNER);
    image(world[4].backDropL, 0, 0);
    imageMode(CENTER);
    level4MovingPlats[1].drawPlatform();
    if (level4Button2.on) {
      level4Plats[0].platformAppear();
    } else if (!level4Button2.on) {
      level4Plats[0].platformDissapear();
    }
    if (level4Switch3.on) {
      level4Plats[3].platformAppear();
    } else if (!level4Switch3.on) {
      level4Plats[3].platformDissapear();
    }
    if (level4Switch4.on) {
      level4MovingPlats[1].movePlatform();
    }
    level4Plats[4].drawPlatform();
    level4Plats[1].drawPlatform();
    level4Plats[2].drawPlatform();
    level4Plats[3].drawPlatform();
    level4Plats[5].drawPlatform();
    level4Plats[6].drawPlatform();
    level4Plats[0].drawPlatform();
    level4MovingPlats[0].drawPlatform();
    level4MovingPlats[0].movePlatform();
    level4End.drawDoor();
    level4Switch.drawSwitch();
    level4Switch2.drawSwitch();
    level4Button.drawSwitch();
    players[0].drawPlayer();
  }
  if (world[4].screen1R == true) {
    world[4].setStartingPosR(level4MovingPlats[2].pos.x+40, level4MovingPlats[2].pos.y); 
    imageMode(CORNER);
    image(world[4].backDropR, width/2, 0);
    imageMode(CENTER);
    if (level4Switch.on) {
      level4Plats[8].platformAppear();
    } else if (!level4Switch.on) {
      level4Plats[8].platformDissapear();
    }
    if (level4Button.on) {
      level4Plats[10].platformAppear();
    } else if (!level4Button.on) {
      level4Plats[10].platformDissapear();
    }
    if (level4Switch2.on) {
      level4MovingPlats[4].movePlatform();
    }
    level4Plats[11].drawPlatform();
    level4Plats[8].drawPlatform();
    level4MovingPlats[2].drawPlatform();
    level4MovingPlats[2].movePlatform();
    level4Plats[7].drawPlatform();
    level4Plats[9].drawPlatform();
    level4MovingPlats[3].drawPlatform();
    level4MovingPlats[3].movePlatform();
    level4Plats[10].drawPlatform();
    level4MovingPlats[4].drawPlatform();
    level4Plats[12].drawPlatform();
    level4End2.drawDoor();
    level4Switch3.drawSwitch();
    level4Switch4.drawSwitch();
    level4Button2.drawSwitch();
    players[1].drawPlayer();
  }
}

public void levelFive() {
  if (world[5].setUp == true) {
    finalTheme.amp(0.2f);
    finalTheme.stop();
    finalTheme.play();
    world[5].screen1L = true;
    world[5].screen1R = true;
    level5Plats[0] = new Platform(320, 360, 100, 100, 20, 20, 20);
    level5Plats[1] = new Platform(175, 360, 100, 100, 20, 20, 20); //dissapearing/end
    level5MovingPlats[0] = new MovingPlatform(220, 460, 100, 100, 20, 20, 20, 420, 460); //dissapearing
    level5Plats[2] = new Platform(320, 600, 100, 200, 20, 20, 20); //dissapearing
    level5Plats[3] = new Platform(470, 550, 100, 100, 20, 20, 20);
    level5MovingPlats[1] = new DirectionPlatform(420, 260, 100, 100, 20, 20, 20, 220, 260, false, true); //dissapearing
    level5MovingPlats[2] = new MovingPlatform(220, 160, 110, 110, 20, 20, 20, 430, 160);
    level5Plats[4] = new Platform(520, 160, 100, 100, 20, 20, 20);
    level5Plats[5] = new Platform(320, 60, 100, 100, 20, 20, 20);

    level5Plats[6] = new Platform(860, 360, 100, 100, 20, 20, 20);
    level5Plats[7] = new Platform(960, 460, 200, 100, 20, 20, 20); //dissapearing
    level5Plats[8] = new Platform(1060, 555, 100, 100, 20, 20, 20);
    level5MovingPlats[3] = new DirectionPlatform(1160, 560, 110, 100, 20, 20, 20, 1160, 360, true, false);
    level5Plats[9] = new Platform(1160, 265, 100, 100, 20, 20, 20);
    level5Plats[10] = new Platform(1160, 170, 100, 100, 20, 20, 20); //dissapearing
    level5Plats[11] = new Platform(710, 410, 100, 200, 20, 20, 20);
    level5Plats[12] = new Platform(710, 550, 100, 100, 20, 20, 20); //dissapearing/end
    level5MovingPlats[4] = new MovingPlatform(860, 260, 100, 100, 20, 20, 20, 760, 260); //dissapearing
    level5Plats[13] = new Platform(860, 165, 100, 100, 20, 20, 20);
    level5Plats[14] = new Platform(710, 165, 100, 100, 20, 20, 20);
    level5End = new Door(175, 360, 50, 50, true);
    level5End2 = new Door(710, 560, 50, 50, true);
    level5Button = new Button(320, 360, 50, 50, "rect");
    level5Button2 = new Button(320, 60, 50, 50, "rect");
    level5Switch = new Switch(470, 560, 20, 50, "cir");
    level5Switch2 = new Switch(320, 650, 20, 50, "cir");
    level5Switch3 = new Switch(520, 160, 20, 50, "cir");

    level5Button3 = new Button(860, 360, 50, 50, "rect");
    level5Button4 = new Button(1160, 260, 50, 50, "rect");
    level5Switch4 = new Switch(1060, 560, 20, 50, "cir");
    level5Switch5 = new Switch(1160, 160, 20, 50, "cir");
    level5Switch6 = new Switch(860, 160, 20, 50, "cir");
    level5Switch7 = new Switch(710, 160, 20, 50, "cir");
    world[5].setStartingPosL(320, 360);  
    world[5].setStartingPosR(860, 360);  
    players[0].xy.x = world[5].startingPosL.x;
    players[0].xy.y = world[5].startingPosL.y;
    players[1].xy.x = world[5].startingPosR.x;
    players[1].xy.y = world[5].startingPosR.y;
    level5End.activate();
    level5End2.activate();
    health = 4;
    world[5].setUp = false;
  }

  checkCollisionL();
  checkCollisionR();

  if (level5End.entered && level5End2.entered) {
    endGame = true;
  }

  if (world[5].screen1L == true) {
    world[5].setStartingPosL(320, 360);  
    imageMode(CORNER);
    image(world[5].backDropL, 0, 0);
    imageMode(CENTER);
    level5Plats[0].drawPlatform();
    level5Plats[1].drawPlatform();
    if (level5Switch5.on) {
      level5Plats[1].platformAppear();
    } else if (!level5Switch5.on) {
      level5Plats[1].platformDissapear();
    }
    level5Plats[2].drawPlatform();
    if (level5Switch4.on) {
      level5Plats[2].platformAppear();
    } else if (!level5Switch4.on) {
      level5Plats[2].platformDissapear();
    }
    level5Plats[3].drawPlatform();
    level5Plats[4].drawPlatform();
    level5Plats[5].drawPlatform();
    level5MovingPlats[0].drawPlatform();
    if (level5Switch6.on) {
      level5MovingPlats[0].movePlatform();
    }
    if (level5Button3.on || level5Switch7.on) {
      level5MovingPlats[0].platformAppear();
    } else if (!level5Button3.on && !level5Switch7.on) {
      level5MovingPlats[0].platformDissapear();
    }
    level5MovingPlats[1].drawPlatform();
    if (level5Switch7.on) {
      level5MovingPlats[1].platformAppear(); 
      level5MovingPlats[1].movePlatform();
    } else if (!level5Switch7.on) {
      level5MovingPlats[1].platformDissapear();
    }
    level5MovingPlats[2].drawPlatform();
    if (level5Button4.on) {
      level5MovingPlats[2].movePlatform();
    }
    level5Button.drawSwitch();
    level5Button2.drawSwitch();
    level5Switch.drawSwitch();
    level5Switch2.drawSwitch();
    level5Switch3.drawSwitch();
    level5End.drawDoor();
    players[0].drawPlayer();
  }
  if (world[5].screen1R == true) {
    world[5].setStartingPosR(860, 360); 
    imageMode(CORNER);
    image(world[5].backDropR, width/2, 0);
    imageMode(CENTER);
    level5Plats[6].drawPlatform();
    level5Plats[7].drawPlatform();
    if (level5Switch.on) {
      level5Plats[7].platformAppear();
    } else if (!level5Switch.on) {
      level5Plats[7].platformDissapear();
    }
    level5Plats[8].drawPlatform();
    level5Plats[9].drawPlatform();
    level5Plats[10].drawPlatform();
    if (level5Button2.on) {
      level5Plats[10].platformAppear();
    } else if (!level5Button2.on) {
      level5Plats[10].platformDissapear();
    }
    level5Plats[11].drawPlatform();
    level5Plats[12].drawPlatform();
    if (level5Switch3.on) {
      level5Plats[12].platformAppear();
    } else if (!level5Switch3.on) {
      level5Plats[12].platformDissapear();
    }
    level5Plats[13].drawPlatform();
    level5Plats[14].drawPlatform();
    level5MovingPlats[3].drawPlatform();
    level5MovingPlats[3].movePlatform();
    level5MovingPlats[4].drawPlatform();
    if (level5Switch2.on) {
      level5MovingPlats[4].movePlatform();
    }
    if (level5Button.on) {
      level5MovingPlats[4].platformAppear();
    } else if (!level5Button.on) {
      level5MovingPlats[4].platformDissapear();
    }
    level5Button3.drawSwitch();
    level5Button4.drawSwitch();
    level5Switch4.drawSwitch();
    level5Switch5.drawSwitch();
    level5Switch6.drawSwitch();
    level5Switch7.drawSwitch();
    level5End2.drawDoor();
    players[1].drawPlayer();
  }
}
class Platform {
  Coord pos; 
  Coord size; 
  int col; 
  boolean visible;
  PImage sprite;
  boolean square;
  boolean isLong;
  Platform(int p1, int p2, int p3, int p4, int p5, int p6, int p7) {
    visible = true;
    pos = new Coord(p1, p2);  
    size = new Coord(p3, p4);
    col = color(p5, p6, p7);
    if (p3 == p4) {
      square = true;
    } else {
      square = false; 
      if (p3 > p4) {
        isLong = true;
      }
    }
    if (square) {
      sprite = invisPlat;
    } else {
      if (isLong) {  
        sprite = standardPlat;
      } else {
        sprite = longPlat;
      }
    }
  }
  public void drawPlatform() {
    rectMode(CENTER); 
    fill(col);
    imageMode(CORNER);
    if (visible) {
      image(sprite, pos.x-(size.x/2), pos.y-(size.y/2), size.x, size.y+20);
    } else if (!visible) {
      fill(150, 50);
      rect(pos.x, pos.y, size.x, size.y);
      for (int i = 0; i < 2; i++) {
        if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
          players[i].fall();
        }
      }
    }
  }

  public void platformDissapear() {
    visible = false;
  }

  public void platformAppear() {
    visible = true;
  }
}

class MovingPlatform extends Platform {
  Coord moveTo;
  Coord startPos;
  boolean moveBack;
  MovingPlatform(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
    super(p1, p2, p3, p4, p5, p6, p7);
    sprite = movingPlat;
    moveTo = new Coord(p8, p9);
    startPos = new Coord(pos.x, pos.y);
    moveBack = false;
  }

  public void movePlatform() {
    if (moveBack == false) {
      if (pos.x > moveTo.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x--;
          }
        }
        pos.x--;
      }
      if (pos.x < moveTo.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x++;
          }
        }
        pos.x++;
      }
      if (pos.y > moveTo.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y--;
          }
        }
        pos.y--;
      }
      if (pos.y < moveTo.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y++;
          }
        }
        pos.y++;
      }
      if (pos.x == moveTo.x && pos.y == moveTo.y) {
        moveBack = true;
        println(moveBack);
      }
    } else if (moveBack == true) {
      println("movingback");
      if (pos.x > startPos.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x--;
          }
        }
        pos.x--;
      }
      if (pos.x < startPos.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x++;
          }
        }
        pos.x++;
      }
      if (pos.y > startPos.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y--;
          }
        }
        pos.y--;
      }
      if (pos.y < startPos.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y++;
          }
        }
        pos.y++;
      }
      if (pos.x == startPos.x && pos.y == startPos.y) {
        moveBack = false;
        println(moveBack);
      }
    }
  }
}

class DirectionPlatform extends MovingPlatform {
  boolean playerLeft = false;
  boolean playerRight = false;
  Coord constrainX;
  Coord constrainY;
  DirectionPlatform(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, boolean p10, boolean p11) {
    super(p1, p2, p3, p4, p5, p6, p7, p8, p9);
    moveBack = false;
    playerLeft = p10;
    playerRight = p11;
    constrainX = new Coord(p1, p8);
    constrainY = new Coord(p2, p9);
  }

  public void movePlatform() {

    if (playerRight) {
      if (players[1].movingLeft && pos.x > moveTo.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x--;
          }
        }
        pos.x = pos.x-3;
      }
      if (players[1].movingRight && pos.x < startPos.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x++;
          }
        }
        pos.x = pos.x+3;
      }
      if (players[1].movingUp && pos.y > moveTo.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y--;
          }
        }
        pos.y = pos.y-3;
      }
      if (players[1].movingDown && pos.y < startPos.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y++;
          }
        }
        pos.y = pos.y+3;
      }
    }
    if (playerLeft) {
      if (players[0].movingLeft && pos.x > moveTo.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x--;
          }
        }
        pos.x = pos.x-3;
      }
      if (players[0].movingRight && pos.x < startPos.x) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.x++;
          }
        }
        pos.x = pos.x+3;
      }
      if (players[0].movingUp && pos.y > moveTo.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y--;
          }
        }
        pos.y = pos.y-3;
      }
      if (players[0].movingDown && pos.y < startPos.y) {
        for (int i = 0; i < 2; i++) {
          if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
            players[i].xy.y++;
          }
        }
        pos.y = pos.y+3;
      }
    }
  }
}

class Switch {
  boolean on;
  Coord pos;
  Coord size;
  String shape;
  PImage sprite;
  boolean playSound;
  Switch(int p1, int p2, int p3, int p4, String p5) {
    pos = new Coord(p1, p2);
    size = new Coord(p3, p4);
    shape = p5;
    boolean on = false;
    sprite = switchOff;
  }

  public void drawSwitch() {
    if (on) {
      fill(0, 200, 0);
      sprite = switchOn;
    }
    if (!on) {
      fill(200, 0, 0);
      sprite  = switchOff;
    }
    if (shape == "rect") {
      imageMode(CENTER);
      image(sprite, pos.x, pos.y, size.x, size.y);
    } else if (shape == "cir") {
      imageMode(CENTER);
      image(sprite, pos.x, pos.y, size.x, size.y);
    }

    for (int i = 0; i < 2; i++) {
      if (players[i].xy.x > (pos.x)-(size.x*2) && players[i].xy.x < (pos.x)+(size.x*2) && players[i].xy.y > (pos.y)-(size.y*2) && players[i].xy.y < (pos.y)+(size.y*2) && players[i].signal == true) {
        onoff();
        TVClick.play();
      }
    }
  }

  public void onoff() {
    on =! on;
  }

  public boolean recieveSignal() {
    return on;
  }
}

class Button extends Switch {
  boolean pushed;
  boolean playSound = false;
  Button(int p1, int p2, int p3, int p4, String p5) {
    super(p1, p2, p3, p4, p5);
  }

  public void drawSwitch() {
    if (on) {
      fill(0, 200, 0);
      sprite = buttonOn;
    }
    if (!on) {
      fill(200, 0, 0);
      sprite = buttonOff;
    }
    if (shape == "rect") {
      imageMode(CENTER);
      image(sprite, pos.x, pos.y, size.x, size.y);
    } else if (shape == "cir") {
      imageMode(CENTER);
      image(sprite, pos.x, pos.y, size.x, size.y);
    }
      if (players[1].xy.x > (pos.x)-(size.x) && players[1].xy.x < (pos.x)+(size.x) && players[1].xy.y > (pos.y)-(size.y) && players[1].xy.y < (pos.y)+(size.y)) {
        pushed = true;
      }
      else {
              if (players[0].xy.x > (pos.x)-(size.x) && players[0].xy.x < (pos.x)+(size.x) && players[0].xy.y > (pos.y)-(size.y) && players[0].xy.y < (pos.y)+(size.y)) {
        pushed = true;
      }
      else{
        pushed = false;
      }
      }
    if (pushed) {
      if (!playSound) {
        TVClick.play(); 
        playSound = true;
      }
      on = true;
    } else {
      on = false;
      playSound = false;
    }
  }
}

class TimedSwitch extends Switch {
  float time;
  float timeToHold;
  TimedSwitch(int p1, int p2, int p3, int p4, String p5, float p6) {
    super(p1, p2, p3, p4, p5); 
    timeToHold = p6;
    time = timeToHold;
  }

  public void time() {
    if (time > 0) { 
      time = time - 0.01f;
      println(time);
    }
  }

  public void resetTime() {
    time = timeToHold;
  }

  public String getTimeLeft() {
    String timeLeft = Float.toString(time);
    return(timeLeft);
  }

  public void onoff() {
    while (time > 0) {
      on = true;
    }
    while (time <= 0) {
      on = false;
    }
  }
}

class Wall {

  Coord size;
  Coord pos;
  PImage sprite;

  Wall(int p1, int p2, int p3, int p4) {
    sprite = wall;
    size = new Coord(p3, p4);
    pos = new Coord(p1, p2);
  }

  public void drawWall() {
    imageMode(CENTER);
    sprite = wall;
    image(sprite, pos.x, pos.y, size.x, size.y);
    for (int i = 0; i < 2; i++) {
      if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
        players[i].stopPlayer();
      }
    }
  }
}

class Door {

  Coord size;
  Coord pos;
  boolean active;
  boolean entered;
  PImage sprite;
  boolean isEnd;

  Door(int p1, int p2, int p3, int p4, boolean p5) {
    isEnd = p5;
    if (isEnd) {
      sprite = levelDoor;
    } else {
      sprite = sceneDoor;
    }
    size = new Coord(p3, p4);
    pos = new Coord(p1, p2);
    active = false;  
    entered = false;
  }

  public void drawDoor() {
    fill(255);
    imageMode(CENTER);
    if (isEnd) {
      sprite = levelDoor;
    } else {
      sprite = sceneDoor;
    }
    image(sprite, pos.x, pos.y, size.x, size.y);
    if (active) {
      for (int i = 0; i < 2; i++) {
        if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
          entered = true;
        }
      }
    }
    if (!active) {
      sprite = wall;
      for (int i = 0; i < 2; i++) {
        if (players[i].xy.x > (pos.x)-(size.x/2) && players[i].xy.x < (pos.x)+(size.x/2) && players[i].xy.y > (pos.y)-(size.y/2) && players[i].xy.y < (pos.y)+(size.y/2)) {
          entered = false;
        }
      }
    }
  }

  public void activate() {
    active = true;
  }
}

class MouseButton {
  Coord pos;
  Coord size;
  boolean clicked;
  boolean hover;

  MouseButton(int p1, int p2, int p3, int p4) {
    clicked = false;
    hover = false;
    pos = new Coord(p1, p2);
    size = new Coord(p3, p4);
  }

  public void checkHover() {
    if (mouseX > pos.x-(size.x/2) && mouseX < pos.x+(size.x/2) && mouseY > pos.y-(size.y/2) && mouseY < pos.y+(size.y/2)) { //Will send out a signal whenever the button is hovered over
      hover = true;
    } else {
      hover = false;
    }
  }

  public void drawHitboxes() { //drawing hitboxes is used for testing only
    noFill();
    stroke(255, 0, 0);
    strokeWeight(5);
    rectMode(CENTER);
    rect(pos.x, pos.y, size.x, size.y);
  }
}
class Player {
  boolean movingLeft;
  boolean movingRight;
  boolean movingUp;
  boolean movingDown;
  Coord xy;
  Coord size;
  PImage player;
  int speedX, speedY, deathCounter;
  String playerName;
  boolean floor, signal;
  Stopwatch watch = new Stopwatch(0);

  Player(int p1, int p11, PImage p2, String p3, int p4, int p5) {
    deathCounter = 0;
    playerName = p3;
    xy = new Coord(p1, p11);
    if(playerName == "Player1"){
    player = player1;
    }
    if(playerName == "Player2"){
    player = player2;  
    }
    size = new Coord(p4, p5);
  }

  public Coord getPosition() {
    return xy;
  }

  public void setPosition(Coord p1) {
    xy = p1;
  }

  public void drawPlayer() {
    if (xy.x > width || xy.x < 0 || xy.y > height || xy.y < 0) {
      stopPlayer();
    }

    if (playerName == "Player1") {
      xy.x = constrain(xy.x + speedX, 0, width/2);
      xy.y = constrain(xy.y + speedY, 0, 720);
    } else if (playerName == "Player2") {
      xy.x = constrain(xy.x + speedX, width/2, 1280);
      xy.y = constrain(xy.y + speedY, 0, 720);
    }

    //following lines are used to actually draw the characters on screen along with displaying their names and current stamina
    fill(0);
    imageMode(CENTER);
    image(player, xy.x, xy.y, size.x, size.y);
    fill(0);
    if (!floor) {
      fall();
    }
    if (signal) {
      watch.startClock();
      if (watch.active == false) {
        signal = false;
      }
    }
  }
  public void moveEast() {
    if (floor) {
      movingRight = true;
      movingLeft = false;
      movingUp = false;
      movingDown = false;
      speedY = 0;
      speedX = 3;
    }
    if(playerName == "Player1"){
    player = playerL;
    }
    if(playerName == "Player2"){
    player = player2L;  
    }
  }

  public void moveWest() {
    if (floor) {
      movingRight = false;
      movingLeft = true;
      movingUp = false;
      movingDown = false;
      speedY = 0;
      speedX = -3;
    }
    if(playerName == "Player1"){
    player = playerL;
    }
    if(playerName == "Player2"){
    player = player2L;  
    }
  }

  public void moveNorth() {
    if (floor) {
      movingRight = false;
      movingLeft = false;
      movingUp = true;
      movingDown = false;
      speedY = -3;
      speedX = 0;
    }
    if(playerName == "Player1"){
    player = player1;
    }
    if(playerName == "Player2"){
    player = player2;  
    }
  }

  public void moveSouth() {
    if (floor) {
      movingRight = false;
      movingLeft = false;
      movingUp = false;
      movingDown = true;
      speedY = 3;
      speedX = 0;
    }
    if(playerName == "Player1"){
    player = player1;
    }
    if(playerName == "Player2"){
    player = player2;  
    }
  }

  public void stopPlayer() {
    movingRight = false;
    movingLeft = false;
    movingUp = false;
    movingDown = false;
    speedY = 0;
    speedX = 0;
  }

  public void activateObject() {
    watch.resetClock();
    signal = true;
  }

  public void fall() {
    println(playerName + " has died");
    int prevSizeX = size.x;
    int prevSizeY = size.y;
    while (size.x > 0 && size.y > 0) {
      size.x--;
      size.y--;
    }
    deathCounter++;
    health--;
    if (playerName == "Player1") xy = world[currentLevel].getStartingPosL();
    if (playerName == "Player2") xy = world[currentLevel].getStartingPosR();
    size.x = prevSizeX;
    size.y = prevSizeY;
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mismatched" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
