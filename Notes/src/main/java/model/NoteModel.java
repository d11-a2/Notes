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
  private final StringProperty  creationDate;
  private final StringProperty  lastChangeDate;
  private final StringProperty  noteText;

  public NoteModel() {
    this(null, null, null);
  }

  public NoteModel(String name, String creationDate, String noteText) {
    this.id = new SimpleIntegerProperty();
    this.name = new SimpleStringProperty(name);
    this.creationDate = new SimpleStringProperty(creationDate);
    this.lastChangeDate = new SimpleStringProperty(creationDate);
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

  public String getCreationDate() {
    return creationDate.get();
  }

  public StringProperty creationDateProperty() {
    return creationDate;
  }

  public String getLastChangeDate() {
    return lastChangeDate.get();
  }

  public StringProperty lastChangeDateProperty() {
    return lastChangeDate;
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

  public void setCreationDate(String creationDate) {
    this.creationDate.set(creationDate);
  }

  public void setLastChangeDate(String lastChangeDate) {
    this.lastChangeDate.set(lastChangeDate);
  }

  public void setNoteText(String noteText) {
    this.noteText.set(noteText);
  }

  @Override
  public String toString() {
    return "NoteModel{" +
           "id=" + id +
           ", name=" + name +
           ", creationDate=" + creationDate +
           ", lastChangeDate=" + lastChangeDate +
           ", noteText=" + noteText +
           '}';
  }
}
