public class RobotAction {
    public void runRobot(Robot robbie) throws RobotException {
        boolean found = false;
        
        while (!robbie.isFrontBlocked()) {
            robbie.turnLeft();
        }
        //robbie.turnRight();
        
        while (!robbie.isFrontBlocked()) {
            robbie.forward();
        }
        robbie.turnRight();
        
        while (found == false) {
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 11; i++) {
                    if (!robbie.isFrontBlocked()) {
                        robbie.forward();
                    } else {
                        found = true;
                        break;
                    }
                }
                
                if (found == true) {
                    break;
                } else if (j % 2 == 0) {
                    robbie.turnRight();
                    if (robbie.isFrontBlocked()) {
                        found = true;
                    } else {
                        robbie.forward();
                        robbie.turnRight();
                    }
                } else {
                    robbie.turnLeft();
                    if (robbie.isFrontBlocked()) {
                        found = true;
                    } else {
                        robbie.forward();
                        robbie.turnLeft();
                    }
                }
            }
        }
        
        
        
        
        
        
        
        // if (robbie.isFrontBlocked()) {
            // while (robbie.isFrontBlocked()) {
                // counter++;
                // robbie.turnLeft();
            // }
            // while (!robbie.isFrontBlocked()) {
                // robbie.forward();
                // robbie.turnRight();
                
                // if (!robbie.isFrontBlocked()) {
                    // robbie.forward();
                    // robbie.turnRight();
                    // if (!robbie.isFrontBlocked()) {
                        // counter++;
                        
                        // if (counter >= 4) {
                            // break;
                        // }
                    // }
                // }
            // }
        // } else if (!robbie.isFrontBlocked()) {
            // robbie.forward();
        // }
        
    }
}