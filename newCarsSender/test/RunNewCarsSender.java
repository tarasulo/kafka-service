import tkhal.sender.controller.NewCarsSender;

import java.io.IOException;

public class RunNewCarsSender extends Thread {

    @Override
    public void run() {
        NewCarsSender newCarsSender = new NewCarsSender();
        try {
            newCarsSender.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
