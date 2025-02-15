package library.management.system;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Welcome extends Application {
     TextField tfm,tfcm;
    @Override
    public void start(Stage primaryStage) {
        Button meter=new Button("->");
        Button cm=new Button("<-");
        tfm = new TextField();
        tfcm = new TextField();
        mtocm obj=new mtocm();
        cmtom obj2=new cmtom();
        meter.setOnAction(obj);
        cm.setOnAction(obj2);
        GridPane grid=new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        Label meters=new Label("meters");
        grid.add(tfm,1,0);
        grid.add(meter,2,0);
        grid.add(tfcm,3,0);
        Label centi=new Label("cm");
        grid.add(cm, 0, 0);
        Scene s=new Scene(grid,400,400);
        primaryStage.setScene(s);
        primaryStage.show();
    }
    public class mtocm implements EventHandler<ActionEvent>{
 
    @Override
    public void handle(ActionEvent t) {
       Double m=Double.parseDouble(tfm.getText());
       Double cm=m*100;
       tfcm.setText(String.valueOf(cm));
        
    }
   
}
     public class cmtom implements EventHandler<ActionEvent>{
 
    @Override
    public void handle(ActionEvent t) {
       Double m;
       Double cm;
       
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
}
