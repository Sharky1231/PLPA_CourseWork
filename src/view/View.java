package view;

import controller.Controller;
import jsint.Pair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class View extends JFrame{

    private CustomCanvas canvasArea;
    private javax.swing.JTextArea codeTextArea;
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField textField;

    // Canvas dimensions
    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 450;


    public View() {
        init();
        addExtraSettings();
    }

    private void addExtraSettings() {
        textField.grabFocus();
        codeTextArea.setEditable(true);
        errorTextArea.setEditable(false);

        setSize(1000, 700);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(submitButton);
        setLocationRelativeTo(null);
    }

    private void init() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        submitButton = new javax.swing.JButton();
        textField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        codeTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        canvasArea = new CustomCanvas();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        submitButton.setText("Submit");

        codeTextArea.setColumns(20);
        codeTextArea.setLineWrap(true);
        codeTextArea.setRows(5);
        jScrollPane2.setViewportView(codeTextArea);

        jLabel2.setText("Commands");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                        .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addComponent(textField)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
//                                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitButton)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel2)
                                .addGap(3, 3, 3)
                                .addComponent(jScrollPane2)
                                .addContainerGap())
        );

        errorTextArea.setColumns(20);
        errorTextArea.setLineWrap(true);
        errorTextArea.setRows(5);
        jScrollPane3.setViewportView(errorTextArea);

        jLabel1.setText("Errors");

        jLabel3.setText("Canvas - (Ratio 1:32) ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane3)
                                                .addComponent(canvasArea, javax.swing.GroupLayout.DEFAULT_SIZE, CANVAS_WIDTH, Short.MAX_VALUE))
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(canvasArea, javax.swing.GroupLayout.PREFERRED_SIZE, CANVAS_HEIGHT, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    public synchronized void drawCartesianPlane() {
        try {
            // Wait for components to be loaded and then draw cartesian plane
            wait(200);
            canvasArea.drawCartesianPlane();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawPoint(Pair pair) {
        canvasArea.drawPixel((int)pair.getFirst(), (int)pair.getRest());
    }

    public void prepareCanvas() {
        canvasArea.prepare();
    }

    public void drawText(int xCord, int yCord, String text) {
        canvasArea.drawText(xCord, yCord, text);
    }

    private class ButtonHandler implements ActionListener {
        public Controller controller;
        public ButtonHandler(Controller controller) {
            this.controller = controller;
        }

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == submitButton) {
                controller.executeAction(event);
            }
        }
    }

    public String getCommandsFromText(){
        return codeTextArea.getText();
    }

    public void registerEvents(Controller controller) {
        ActionListener listener = new ButtonHandler(controller);
        submitButton.addActionListener(listener);
    }

    public void setError(String error) {
        errorTextArea.append(error + "\n");
    }

    public void showView() {
        setVisible(true);
    }

}
