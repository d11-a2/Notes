package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

  private final  DateTimeFormatter formatter;
  private static DBHandler         handler;

  public NoteTableController() {

    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    notes = FXCollections.observableArrayList();

    try {
      handler = DBHandler.getInstance();
    }
    catch (SQLException e) {e.printStackTrace();}

    System.out.println("NoteTableController");
  }

  public void save(ActionEvent actionEvent) {

    System.out.println("save");

    int selectedIndex = noteTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex == -1) { return; }

    NoteModel model = noteTable.getSelectionModel().getSelectedItem();
    String text = noteText.getText();
    if (text == null || text.equals("")) {
      //сделать кнопку не активной
      return;
    }

    model.setNoteText(text);
    handler.saveNote(model);
    showNoteText(model);
  }

  public void add(ActionEvent actionEvent) {

    System.out.println("add");

    String text = noteText.getText();
    if (text == null || text.equals("")) {
      //сделать кнопку не активной
      return;
    }

    String noteHeader = "";

    if (noteHeader == null || noteHeader.equals("")) {
      int headerLastIndex = text.lastIndexOf("\n");
      headerLastIndex = (headerLastIndex == -1 ? (text.length() > 255 ? 255 : text.length()) : (headerLastIndex > 255 ? 255 : headerLastIndex));
      noteHeader = text.substring(0, headerLastIndex);
    }
    NoteModel model = new NoteModel(noteHeader, formatter.format(LocalDate.now()), text);
    handler.addNote(model);
    noteTable.getItems().add(model);
    showNoteText(model);
  }

  public void delete(ActionEvent actionEvent) {

    System.out.println("delete");

    int selectedIndex = noteTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex == -1) { return; }

    NoteModel model = noteTable.getSelectionModel().getSelectedItem();

    noteTable.getItems().remove(selectedIndex);

    handler.deleteNote(model.getId());

    noteText.clear();

    showNoteText(noteTable.getSelectionModel().getSelectedItem());
  }

  private void showNoteText(NoteModel model) {
    System.out.println("showNoteText");
    if (model != null) {
      noteText.setText(model.getNoteText());
    }
    else { noteText.setText(""); }
  }

  @FXML
  private void initialize() {

    System.out.println("initialize");

    try {
      List<NoteModel> models = handler.getAllNotes();
      if (models.isEmpty()) { return; }
      notes.addAll(models);

      noteNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
      noteDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
      noteTable.setItems(notes);

      noteTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showNoteText(newValue)));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
