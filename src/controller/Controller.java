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
        if (((JButton) e.getSource()).getText().startsWith("Submit") && !view.getCommandsFromText().isEmpty()) {
            try {
                evaluateCommands();
            } catch (NullPointerException e1) {
                view.setError("Not defined/wrong command: \"" + view.getCommandsFromText() + "\"");
            } catch (Exception e2) {
                view.setError(e2.getMessage());
            }
        }
    }

    private void evaluateCommands() {
        String command = view.getCommandsFromText();
        String[] commands = command.split("\n");
        view.prepareCanvas();

        for(int i = 0; i < commands.length; i++)
        {
            if(i == commands.length - 1)
                view.highlight("red");
            executeCommand(commands[i]);
        }
    }

    public void executeCommand(String command)
    {
        Pair schemeResult = (Pair) model.eval(command);

        if(command.contains("TEXT-AT")) {
            drawText(schemeResult);
        }

        else if(command.contains("DRAW")){
            String color = (String) schemeResult.getFirst();
            view.highlight(color);
            drawResultsFromScheme((Pair) schemeResult.getRest());
        }

        else{
            drawResultsFromScheme(schemeResult);
        }


        view.setError(schemeResult.toString());
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
