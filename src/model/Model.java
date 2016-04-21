package model;

import kawa.standard.Scheme;

public class Model {

    private Scheme scheme;

    public Model()
    {
        this.scheme = new Scheme();
    }

    public Object eval(String command)
    {
        try {
            return scheme.eval(command);
        } catch (Throwable throwable) {
            return null;
        }
    }

}