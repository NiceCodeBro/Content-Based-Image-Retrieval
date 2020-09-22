package other;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// java 8 code
public class Main extends Application {
    //https://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx
    //Class containing grid (see below)
    private GridDisplay gridDisplay;

    //Class responsible for displaying the grid containing the Rectangles
    public class GridDisplay {

        private static final double ELEMENT_SIZE = 100;
        private static final double GAP = ELEMENT_SIZE / 10;

        private TilePane tilePane = new TilePane();
        private Group display = new Group(tilePane);
        private int nRows;
        private int nCols;

        public GridDisplay(int nRows, int nCols) {
            tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
            tilePane.setHgap(GAP);
            tilePane.setVgap(GAP);
            setColumns(nCols);
            setRows(nRows);
        }

        public void setColumns(int newColumns) {
            nCols = newColumns;
            tilePane.setPrefColumns(nCols);
            createElements();
        }

        public void setRows(int newRows) {
            nRows = newRows;
            tilePane.setPrefRows(nRows);
            createElements();
        }

        public Group getDisplay() {
            return display;
        }

        private void createElements() {
            tilePane.getChildren().clear();
            for (int i = 0; i < nCols; i++) {
                for (int j = 0; j < nRows; j++) {
                    tilePane.getChildren().add(createElement());
                }
            }
        }

        private Rectangle createElement() {
            Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
            rectangle.setStroke(Color.ORANGE);
            rectangle.setFill(Color.STEELBLUE);

            return rectangle;
        }

    }

    @Override
    public void start(Stage primaryStage) {

        //Represents the grid with Rectangles
        gridDisplay = new GridDisplay(2, 4);

        //Fields to specify number of rows/columns
        TextField rowField = new TextField("2");
        TextField columnField = new TextField("4");

        //Function to set an action when text field loses focus
        buildTextFieldActions(rowField, columnField);

        HBox fields = new HBox(10);
        fields.getChildren().add(rowField);
        fields.getChildren().add(new Label("x"));
        fields.getChildren().add(columnField);

        BorderPane mainPanel = new BorderPane();
        mainPanel.setCenter(gridDisplay.getDisplay());
        mainPanel.setTop(fields);

        Scene scene = new Scene(mainPanel, 1000, 800);
        primaryStage.setTitle("Test grid display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void buildTextFieldActions(final TextField rowField, final TextField columnField) {
        rowField.focusedProperty().addListener((ov, t, t1) -> {
            if (!t1) {
                if (!rowField.getText().equals("")) {
                    try {
                        int nbRow = Integer.parseInt(rowField.getText());
                        gridDisplay.setRows(nbRow);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            }
        });

        columnField.focusedProperty().addListener((ov, t, t1) -> {
            if (!t1) {
                if (!columnField.getText().equals("")) {
                    try {
                        int nbColumn = Integer.parseInt(columnField.getText());
                        gridDisplay.setColumns(nbColumn);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            }
        });
    }
}