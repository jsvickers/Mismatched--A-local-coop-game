class Platform {
  Coord pos; 
  Coord size; 
  color col; 
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
  void drawPlatform() {
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

  void platformDissapear() {
    visible = false;
  }

  void platformAppear() {
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

  void movePlatform() {
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

  void movePlatform() {

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

  void drawSwitch() {
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

  void onoff() {
    on =! on;
  }

  boolean recieveSignal() {
    return on;
  }
}

class Button extends Switch {
  boolean pushed;
  boolean playSound = false;
  Button(int p1, int p2, int p3, int p4, String p5) {
    super(p1, p2, p3, p4, p5);
  }

  void drawSwitch() {
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

  void time() {
    if (time > 0) { 
      time = time - 0.01;
      println(time);
    }
  }

  void resetTime() {
    time = timeToHold;
  }

  String getTimeLeft() {
    String timeLeft = Float.toString(time);
    return(timeLeft);
  }

  void onoff() {
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

  void drawWall() {
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

  void drawDoor() {
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

  void activate() {
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

  void checkHover() {
    if (mouseX > pos.x-(size.x/2) && mouseX < pos.x+(size.x/2) && mouseY > pos.y-(size.y/2) && mouseY < pos.y+(size.y/2)) { //Will send out a signal whenever the button is hovered over
      hover = true;
    } else {
      hover = false;
    }
  }

  void drawHitboxes() { //drawing hitboxes is used for testing only
    noFill();
    stroke(255, 0, 0);
    strokeWeight(5);
    rectMode(CENTER);
    rect(pos.x, pos.y, size.x, size.y);
  }
}