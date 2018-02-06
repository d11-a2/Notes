package launcher;

import java.io.IOException;

import controller.NoteEditDialogController;
import controller.NoteOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.NoteModel;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class App extends Application {

  private Stage                    primaryStage;
  private BorderPane               rootLayout;
  private NoteOverviewController   overviewController;
  private NoteEditDialogController dialogController;

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("NotePad");

    initRootLayout();

    showNoteOverview();
  }

  public void initRootLayout() {
    try {
      String fxml = "/view/RootLayout.fxml";

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(App.class.getResource(fxml));
      rootLayout = (BorderPane) loader.load();

      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showNoteOverview() {
    try {
      String fxml = "/view/NoteOverview.fxml";
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(App.class.getResource(fxml));
      AnchorPane personOverview = (AnchorPane) loader.load();
      overviewController = loader.getController();
      overviewController.setApp(this);

      rootLayout.setCenter(personOverview);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean showNoteEditDialog(NoteModel model) {
    try {
      String fxml = "/view/NoteCreationDialog_.fxml";
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(App.class.getResource(fxml));
      BorderPane page = (BorderPane) loader.load();

      Stage dialogStage = new Stage();
      dialogStage.setTitle("Create new note");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      dialogController = loader.getController();
      dialogController.setNoteOverviewController(overviewController);
      dialogController.setModel(model);
      dialogController.setDialogStage(dialogStage);

      dialogStage.showAndWait();

      return dialogController.isSaveClicked();
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
