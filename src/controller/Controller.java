package controller;

import model.Model;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller {

    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void start() {
        view.registerEvents(this);
        view.showView();
    }

    public void executeAction(ActionEvent e)
    {
        if (((JButton) e.getSource()).getText().startsWith("Submit") && !view.getCommandFromText().isEmpty())
        {
            try {
                String command = view.getCommandFromText();
                Object result = model.eval(command);

                view.setError(result.toString());
                view.update(command);
            }
            catch (NullPointerException e1)
            {
                view.setError("Wrong command");
            }
            catch (Exception e2)
            {
                view.setError(e2.getMessage());
            }
        }
    }
}
