//import g4p_controls.*;
import net.java.games.input.*;
import org.gamecontrolplus.*;
import org.gamecontrolplus.gui.*;
import processing.sound.*;

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
void setup() {
  //controller setup
  control = ControlIO.getInstance(this);
  timer2.active = false;

  //global creation
  size(1280, 720);
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

void draw() {
  if (select) {
    selectController();
  } else {
    if (!player2Ready && startButton.clicked && usingController) {
      if (up || down || left || right || device.getButton("Button 0").pressed())
      {
        TVClick.play();
        if (player1Ready) {
          spaceTheme.amp(0.2);
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

void keyPressed() {
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
        spaceTheme.amp(0.2);
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
          spaceTheme.amp(0.2);
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

void keyReleased() {
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

void checkCollisionL() {
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

void checkCollisionR() {
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

void gameEnd() {
  image(spaceBackground, width/2, height/2);
  textSize(40);
  fill(255);
  textAlign(CENTER);
  text("Congradulations, Players.", width/2, 310);
  text("You have completed your task, you may return to your world.", width/2, 410);
  text("Press any key.", width/2, 460);
}

void checkGameOver() { 
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

void gameMenu() {
  if (!startButton.clicked) {
    if (menuSetup) {
      TVHum.amp(0.2);
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
        TVClick.amp(0.5);
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

void selectController() {
  background(0);
  fill(255);
  textSize(50);
  textAlign(CENTER);
  text("Choose a control scheme", width/2, 100);
  text("Press 1 to use Keyboard + Gamepad", width/2, 300);
  text("Press 2 to use Keyboard + Numpad", width/2, 500);
}