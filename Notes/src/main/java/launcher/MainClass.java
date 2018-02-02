package launcher;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class MainClass extends Application {

  private Stage    primaryStage;
  private BorderPane rootLayout;

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("NotePad");

    initRootLayout();

    showPersonOverview();
  }

  /**
   * Инициализирует корневой макет.
   */
  public void initRootLayout() {
    try {
      String fxml = "/view/RootLayout.fxml";

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainClass.class.getResource(fxml));
      rootLayout = (BorderPane) loader.load();

      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showPersonOverview() {
    try {
      String fxml = "/view/NoteOverview.fxml";
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainClass.class.getResource(fxml));
      AnchorPane personOverview = (AnchorPane) loader.load();

      rootLayout.setCenter(personOverview);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Возвращает главную сцену.
   *
   * @return
   */
  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
