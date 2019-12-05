import newCarsCreator.NewCarsCreator;

public class RunServer extends Thread{
    @Override
    public void run() {
        NewCarsCreator newCarsCreator = new NewCarsCreator();
        newCarsCreator.runServer();
    }
}
