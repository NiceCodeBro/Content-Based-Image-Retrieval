import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application{
    private static final int WIDTH = 550;
    private static final int HEIGHT = 550;
    private Stage window;
    private TableView<ImageInformation> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.getIcons().add(new Image("file:google.png"));

        window.setTitle("Content Based Image Retrieval");

        TableColumn<ImageInformation, String> nameColumn = new TableColumn<>("Search Results");
        nameColumn.setMinWidth(170);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        Text selectedPhotoText = new Text("Selected Photo: None");
        Text successRate = new Text("Success Rate:");

        table = new TableView<>();

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Display2D.invoke(Load.invoke(table.getSelectionModel().getSelectedItem().getImageAbsPath()));
                    table.refresh();
                }
            }
        });
        table.getColumns().addAll(nameColumn);

        HBox generalHbox = new HBox();
        VBox tools = new VBox();
        VBox SMTools  = new VBox();
        VBox FETools  = new VBox();


        Text smilariyMeasurement = new Text("Supported Smilarity Measurement Methods:   ");
        ToggleGroup smToggleGroup = new ToggleGroup();
        RadioButton euclideanButton = new RadioButton("Euclidean");
        RadioButton manhattanButton = new RadioButton("Manhattan");
        RadioButton chisquareButton = new RadioButton("ChiSquare");
        euclideanButton.setSelected(true);
        smToggleGroup.getToggles().addAll(euclideanButton,manhattanButton,chisquareButton);
        SMTools.getChildren().addAll(smilariyMeasurement,euclideanButton,manhattanButton,chisquareButton);



        Text featureExtraction = new Text("Supported Feature Extraction Methods:   ");
        ToggleGroup feToggleGroup = new ToggleGroup();
        RadioButton fourierButton = new RadioButton(" Fourier ");
        RadioButton husButton = new RadioButton(" Hu's ");
        RadioButton eccentricityButton = new RadioButton(" Eccentricity ");
        fourierButton.setSelected(true);

        feToggleGroup.getToggles().addAll(fourierButton,husButton,eccentricityButton);

        FETools.getChildren().addAll(featureExtraction,fourierButton,husButton,eccentricityButton);

        Button fileChoosing = new Button("Select File");





        File recordsDir = new File("ImageSource/");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(recordsDir);
        fileChooser.setTitle("Choose Resource File");
        fileChoosing.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                File selectedfile = fileChooser.showOpenDialog(window);

                selectedPhotoText.setText("Selected Photo: " + selectedfile.getName() );


                RadioButton fe = (RadioButton) feToggleGroup.getSelectedToggle();
                RadioButton sm = (RadioButton) smToggleGroup.getSelectedToggle();
                int smNum=1;

                ObservableList<ImageInformation> products = FXCollections.observableArrayList();
                if(sm.getText().equals("Euclidean"))
                    smNum = 1;
                else if(sm.getText().equals("Manhattan"))
                    smNum = 2 ;
                else if(sm.getText().equals("ChiSquare"))
                    smNum = 3;

                ArrayList<ImageInformation> smilarImages = null;
                if(fe.getText().equals(" Hu's "))
                {
                    smilarImages = FeatureExtractionModule.getResultByHUSMoments(selectedfile.getAbsolutePath(),smNum);

                    for(int i = 1; i < 20; ++i)
                    {
                        products.add(new ImageInformation(smilarImages.get(i).getImageName(),"ImageSource\\"+smilarImages.get(i).getImageName()));
                    }
                }
                else if(fe.getText().equals(" Fourier "))
                {
                   smilarImages = FeatureExtractionModule.getResultByFourierTransform(selectedfile.getAbsolutePath(),smNum);

                    for(int i = 1; i < 20; ++i)
                    {
                        products.add(new ImageInformation(smilarImages.get(i).getImageName(),"ImageSource\\"+smilarImages.get(i).getImageName()));
                    }
                }
                else if (fe.getText().equals(" Eccentricity "))
                {
                    smilarImages = FeatureExtractionModule.getResultByEccentricity(selectedfile.getAbsolutePath(),smNum);

                    for(int i = 1; i < 20; ++i)
                    {
                        products.add(new ImageInformation(smilarImages.get(i).getImageName(),"ImageSource\\"+smilarImages.get(i).getImageName()));
                    }

                }
                successRate.setText("Success Rate: " + Main.calculateSuccessRate(smilarImages,selectedfile.getName()) + "%");



                table.setItems(products);
            }
        });




        generalHbox.setSpacing(10);
        generalHbox.setPadding(new Insets(0, 20, 10, 20));


        SMTools.setPadding(new Insets(5, 10, 5, 10));
        SMTools.setSpacing(5);
        FETools.setPadding(new Insets(5, 10, 5, 10));
        FETools.setSpacing(5);
        tools.setPadding(new Insets(5, 10, 5, 10));
        tools.getChildren().addAll(SMTools,FETools,fileChoosing,selectedPhotoText,successRate);

        generalHbox.getChildren().addAll(table,tools);

        Scene scene = new Scene(generalHbox,WIDTH,HEIGHT);
        window.setScene(scene);
        window.show();
    }

    private static String calculateSuccessRate(ArrayList<ImageInformation> information,String selectedPhoto)
    {
        double counter = 0;
        String []photo= selectedPhoto.split("-");
        for(int i = 0; i < 20; ++i)
        {
            String[] temp = information.get(i).getImageName().split("-");
            if(temp[0].equals(photo[0]))
                ++counter;
        }
        return String.format("%.2s",((counter*100.0)/19));
    }






}