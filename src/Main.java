import controller.Controller;
import model.Model;
import view.View;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);

        try {
            loadSchemeFiles(model);
            controller.start();
        } catch (Exception e) {
            view.setError(e.getMessage());
        }
    }

    private static void loadSchemeFiles(Model model) throws FileNotFoundException {
        model.loadDefinitionsFromFilePath("src//definitions.scm");
        model.loadDefinitionsFromFilePath("src//tests.scm");
    }
}
