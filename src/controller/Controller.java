package controller;

import jsint.Pair;
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
        view.drawCartesianPlane();
    }

    public void executeAction(ActionEvent e) {
        if (((JButton) e.getSource()).getText().startsWith("Submit") && !view.getCommandFromText().isEmpty()) {
            try {
                executeSchemeCommand();
            } catch (NullPointerException e1) {
                view.setError("Not defined/wrong command: \"" + view.getCommandFromText() + "\"");
            } catch (Exception e2) {
                view.setError(e2.getMessage());
            }
        }
    }

    private void executeSchemeCommand() {
        String command = view.getCommandFromText();
        Object schemeResult = model.eval(command);

        if(command.contains("TEXT-AT")) {
            drawText((Pair)schemeResult);
        }

        else {
            view.prepareCanvas();
            drawResultsFromScheme((Pair) schemeResult);
            view.setOriginBottomLeft();
        }

        view.setError(schemeResult.toString());
        view.addCommandToTextArea(command);
    }

    private void drawText(Pair schemeResult) {
        Pair coordinates = (Pair) schemeResult.getFirst();
        int xCord = (int) coordinates.getFirst();
        int yCord = (int) coordinates.getRest();

        String text = (String) schemeResult.getRest();
        view.drawText(xCord, yCord, text);
    }

    private void drawResultsFromScheme(Pair result) {
        if (result.length() > 1) {
            view.drawPoint((Pair) result.getFirst());
            drawResultsFromScheme((Pair) result.getRest());
        }
    }
}
