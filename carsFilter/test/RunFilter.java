import tkhal.filter.controller.MessageFilterController;

public class RunFilter extends Thread {
    String[] args = new String[] {"test"};
    @Override
    public void run() {
        MessageFilterController filter = new MessageFilterController();
        filter.main(args);
    }
}
