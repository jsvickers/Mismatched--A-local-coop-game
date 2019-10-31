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

  void startClock() {
    if (time <= waitTime && time > 0) { //time is updated every frames so 30 time = 1 second
      time--;
      active = true;
    } else if (time == 0) {
      active = false;
    }
  }

  void resetClock() {
    time = waitTime;
  }

  boolean getSignal() {
    return active;
  }
}