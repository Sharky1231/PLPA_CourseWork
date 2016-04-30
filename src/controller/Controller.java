package controller;

import jsint.Pair;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.*;
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
        view.drawCartesianPlane();
    }

    public void executeAction(ActionEvent e) {
        if (((JButton) e.getSource()).getText().startsWith("Submit") && !view.getCommandFromText().isEmpty()) {
            try {
                executeSchemeCommand();
            } catch (NullPointerException e1) {
                view.setError("Wrong command!");
            } catch (Exception e2) {
                view.setError(e2.getMessage());
            }
        }
    }

    private void executeSchemeCommand() {
        String command = view.getCommandFromText().toUpperCase();
        Object result = model.eval(command);

        drawResults((Pair) result);

        view.setError(result.toString());
        view.updateCommand(command);
    }

    private void drawResults(Pair result) {
        if(result.length() > 1) {
            view.drawPoint((Pair)result.getFirst());
            drawResults((Pair) result.getRest());
        }
    }
}
