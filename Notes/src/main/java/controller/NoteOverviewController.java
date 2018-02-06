package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import database.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import launcher.App;
import model.NoteModel;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class NoteOverviewController {

  @FXML
  private TableView<NoteModel>           noteTable;
  @FXML
  private TableColumn<NoteModel, String> noteNameColumn;
  @FXML
  private TableColumn<NoteModel, String> noteCreationDateColumn;
  @FXML
  private TableColumn<NoteModel, String> noteLastChangeDateColumn;
  @FXML
  private TextArea                       noteText;

  @FXML
  private ObservableList<NoteModel> notes;

  private final  DateTimeFormatter formatter;
  private static DBHandler         handler;
  private        App               app;

  public NoteOverviewController() {

    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    notes = FXCollections.observableArrayList();

    try {
      handler = DBHandler.getInstance();
    }
    catch (SQLException e) {e.printStackTrace();}

    System.out.println("NoteOverviewController");
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
    app.showNoteEditDialog(null);
  }

  public void edit(ActionEvent actionEvent) {

    System.out.println("edit");

    int selectedIndex = noteTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex == -1) { return; }

    app.showNoteEditDialog(noteTable.getSelectionModel().getSelectedItem());

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

    handler.initializeDataBase(getTestData(5));

    try {
      List<NoteModel> models = handler.getAllNotes();
      if (models.isEmpty()) { return; }
      notes.addAll(models);

      noteNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
      noteCreationDateColumn.setCellValueFactory(cellData -> cellData.getValue().creationDateProperty());
      noteLastChangeDateColumn.setCellValueFactory(cellData -> cellData.getValue().lastChangeDateProperty());
      noteTable.setItems(notes);

      noteTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showNoteText(newValue)));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<NoteModel> getTestData(int n) {
    List<NoteModel> models = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      models.add(new NoteModel("note " + (n - i), formatter.format(LocalDate.now().minusDays((long) i)), "note text " + (n - i)));
    }
    return models;
  }

  public TableView<NoteModel> getNoteTable() {
    return noteTable;
  }

  public TableColumn<NoteModel, String> getNoteNameColumn() {
    return noteNameColumn;
  }

  public TableColumn<NoteModel, String> getNoteCreationDateColumn() {
    return noteCreationDateColumn;
  }

  public TableColumn<NoteModel, String> getNoteLastChangeDateColumn() {
    return noteLastChangeDateColumn;
  }

  public TextArea getNoteText() {
    return noteText;
  }

  public void setApp(final App app) {
    this.app = app;
  }
}
