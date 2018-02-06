package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import database.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.NoteModel;

/**
 * Created by nikita.shubarev@masterdata.ru on 06.02.2018.
 */
public class NoteEditDialogController {

  @FXML
  private TextField noteHeaderField;

  @FXML
  private TextArea noteTextArea;

  @FXML
  private NoteOverviewController noteOverviewController;

  private final  DateTimeFormatter formatter;
  private static DBHandler         handler;

  private Stage     dialogStage;
  private NoteModel model;
  private boolean saveClicked = false;

  @FXML
  private void initialize() {

  }

  public NoteEditDialogController() {
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    try {
      handler = DBHandler.getInstance();
    }
    catch (SQLException e) {e.printStackTrace();}

    System.out.println("NoteOverviewController");
  }

  public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
  }

  public boolean isSaveClicked() {
    return saveClicked;
  }

  public void save(ActionEvent actionEvent) {

    System.out.println("save new");

    String noteHeader = noteHeaderField.getText();
    String noteText = noteTextArea.getText();

    if (noteHeader == null || noteHeader.equals("")) {
      int headerLastIndex = noteText.lastIndexOf("\n");
      headerLastIndex = (headerLastIndex == -1 ? (noteText.length() > 255 ? 255 : noteText.length()) : (headerLastIndex > 255 ? 255 : headerLastIndex));
      noteHeader = noteText.substring(0, headerLastIndex);
    }

    model = new NoteModel(noteHeader, formatter.format(LocalDate.now()), noteText);
    handler.addNote(model);
    noteOverviewController.getNoteTable().getItems().add(model);
    saveClicked = true;
    dialogStage.close();
  }

  public void cancel(ActionEvent actionEvent) {
    saveClicked = false;
    dialogStage.close();
  }

  private void showNoteText(NoteModel model) {
    System.out.println("showNoteText");
    if (model != null) {
      noteTextArea.setText(model.getNoteText());
    }
    else { noteTextArea.setText(""); }
  }

  public void setNoteOverviewController(NoteOverviewController noteOverviewController) {
    this.noteOverviewController = noteOverviewController;
  }

  public void setModel(NoteModel model) {
    this.model = model;
    this.noteHeaderField.setText(model.getName());
    this.noteTextArea.setText(model.getNoteText());
  }
}
