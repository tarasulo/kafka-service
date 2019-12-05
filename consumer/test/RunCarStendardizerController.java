import controller.CarStandardizerController;

import java.sql.SQLException;

public class RunCarStendardizerController extends Thread {
    String[] args = new String[] {"test"};
    @Override
    public void run() {
        CarStandardizerController carStandardizerController = new CarStandardizerController();
        try {
            carStandardizerController.main(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
