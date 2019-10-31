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

  Coord getPosition() {
    return xy;
  }

  void setPosition(Coord p1) {
    xy = p1;
  }

  void drawPlayer() {
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
  void moveEast() {
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

  void moveWest() {
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

  void moveNorth() {
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

  void moveSouth() {
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

  void stopPlayer() {
    movingRight = false;
    movingLeft = false;
    movingUp = false;
    movingDown = false;
    speedY = 0;
    speedX = 0;
  }

  void activateObject() {
    watch.resetClock();
    signal = true;
  }

  void fall() {
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