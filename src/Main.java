import controller.Controller;
import model.Model;
import view.View;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);

        model.loadDefinitionsFromFilePath("src//definitions.scm");

        controller.start();
    }
}
