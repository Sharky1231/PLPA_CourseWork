package model;

import jscheme.JScheme;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Model {

    private JScheme jScheme;

    public Model() {
        this.jScheme = new JScheme();
    }

    public void loadDefinitionsFromFilePath(String path) throws FileNotFoundException {
        this.jScheme.load(new FileReader(path));
    }

    public Object eval(String command) {
        try {
            return jScheme.eval(command);
        } catch (Throwable throwable) {
            return null;
        }
    }

}