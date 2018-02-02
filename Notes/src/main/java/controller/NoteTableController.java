package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import database.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import model.NoteModel;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class NoteTableController {

  @FXML
  private TableView<NoteModel>           noteTable;
  @FXML
  private TableColumn<NoteModel, String> noteNameColumn;
  @FXML
  private TableColumn<NoteModel, String> noteDateColumn;
  @FXML
  private TextArea                       noteText;

  @FXML
  private ObservableList<NoteModel> notes;

  private final DateTimeFormatter formatter;

  public NoteTableController() {
    formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    notes = FXCollections.observableArrayList();
    System.out.println("NoteTableController");

  }

  public void save(ActionEvent actionEvent) {

    System.out.println("save");

    NoteModel model = new NoteModel(formatter.format(LocalDate.now()), formatter.format(LocalDate.now()), noteText.getText());
    notes.add(model);
  }

  public void add(ActionEvent actionEvent) {

    System.out.println("add");

    NoteModel model = new NoteModel(formatter.format(LocalDate.now()), formatter.format(LocalDate.now()), noteText.getText());
    notes.add(model);
  }

  public void delete(ActionEvent actionEvent) {

    System.out.println("delete");

    int selectedIndex = noteTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex == -1) { return; }
    noteTable.getItems().remove(selectedIndex);
    noteText.clear();
  }

  private void showNoteText(NoteModel model) {
    System.out.println("showNoteText");
    if (model != null) {
      noteText.setText(model.getNoteText());
    }
    else { noteText.setText(""); }
  }

  private void showOldNoteText(NoteModel model) {
    System.out.println("showOldNoteText");
    if (model != null) {
      noteText.setText(model.getNoteText());
    }
    else { noteText.setText(""); }
  }

  @FXML
  private void initialize() {
    System.out.println("initialize");
    System.out.println(System.getProperty("user.dir"));

    try {
      DBHandler dbHandler = DBHandler.getInstance();
      for (NoteModel model:dbHandler.getAllNotes()) {
        System.out.println(model.toString());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    noteNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    noteDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

    noteTable.setItems(notes);

    noteTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showOldNoteText(oldValue)));
  }
}
