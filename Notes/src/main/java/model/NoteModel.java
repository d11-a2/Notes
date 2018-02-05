package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class NoteModel {

  private final IntegerProperty id;
  private final StringProperty  name;
  private final StringProperty  date;
  private final StringProperty  noteText;

  public NoteModel() {
    this(null, null, null);
  }

  public NoteModel(String name, String date, String noteText) {
    this.id = new SimpleIntegerProperty();
    this.name = new SimpleStringProperty(name);
    this.date = new SimpleStringProperty(date);
    this.noteText = new SimpleStringProperty(noteText);
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public String getDate() {
    return date.get();
  }

  public StringProperty dateProperty() {
    return date;
  }

  public String getNoteText() {
    return noteText.get();
  }

  public StringProperty noteTextProperty() {
    return noteText;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public void setDate(String date) {
    this.date.set(date);
  }

  public void setNoteText(String noteText) {
    this.noteText.set(noteText);
  }

  @Override
  public String toString() {
    return "NoteModel{" +
           "noteText=" + noteText +
           ", date=" + date +
           ", name=" + name +
           ", id=" + id +
           '}';
  }
}
