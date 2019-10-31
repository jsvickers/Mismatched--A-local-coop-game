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

  String getCurrentLevel() {
    return levelName;
  }

  void setCurrentLevel(String p1) {
    levelName = p1;
  }

  Coord getStartingPosL() {
    //return startingPos;
    return startingPosL;
  }
  Coord getStartingPosR() {
    //return startingPos;
    return startingPosR;
  }

  void setStartingPosL(int p1, int p2) {
    startingPosL = new Coord(p1, p2);
  }
  void setStartingPosR(int p1, int p2) {
    startingPosR = new Coord(p1, p2);
  }

  void playLevel() {
    drawLevel(levelName);
  }
}

void drawLevel(String p1) {
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

void levelZero() {
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
void levelOne() {
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

void levelTwo() {

  if (world[2].setUp == true) {
    world[2].screen1L = true;
    world[2].screen1R = true;
    spaceTheme.amp(0);
    level2Theme.amp(0.2);
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

void levelThree() {

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

void levelFour() {
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

void levelFive() {
  if (world[5].setUp == true) {
    finalTheme.amp(0.2);
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