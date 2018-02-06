package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sqlite.JDBC;

import model.NoteModel;

/**
 * Created by nikita.shubarev@masterdata.ru on 02.02.2018.
 */
public class DBHandler { // Константа, в которой хранится адрес подключения
  private static final String DATABASE = String.format("jdbc:sqlite: %1$s %2$s sqlite %2$s db %2$s notes.db", System.getProperty("user.dir"), System.getProperty("file.separator")).replaceAll(" ", "");

  private static DBHandler instance = null;

  public static synchronized DBHandler getInstance() throws SQLException {
    if (instance == null) { instance = new DBHandler(); }
    return instance;
  }

  private Connection connection;

  private DBHandler() throws SQLException {
    DriverManager.registerDriver(new JDBC());
    this.connection = DriverManager.getConnection(DATABASE);
  }

  public List<NoteModel> getAllNotes() {

    try (Statement statement = this.connection.createStatement()) {
      List<NoteModel> notes = new ArrayList<>();
      ResultSet resultSet = statement.executeQuery("SELECT id, name, creationDate, lastChangeDate, noteText FROM note");
      while (resultSet.next()) {
        NoteModel model = new NoteModel(resultSet.getString("name"), resultSet.getString("creationDate"), resultSet.getString("noteText"));
        model.setId(resultSet.getInt("id"));
        notes.add(model);
      }
      return notes;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public void addNote(NoteModel model) {
    try (PreparedStatement statement = this.connection.prepareStatement(" INSERT INTO note (name, creationDate, lastChangeDate, noteText) VALUES(?, ?, ?, ?) ")) {
      statement.setObject(1, model.getName());
      statement.setObject(2, model.getCreationDate());
      statement.setObject(3, model.getLastChangeDate());
      statement.setObject(4, model.getNoteText());

      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveNote(NoteModel model) {
    try (PreparedStatement statement = this.connection.prepareStatement(" UPDATE note SET name = ? , noteText = ? , lastChangeDate = ? WHERE id = ? ")) {
      statement.setObject(1, model.getName());
      statement.setObject(2, model.getNoteText());
      statement.setObject(3, model.getLastChangeDate());
      statement.setObject(4, model.getId());

      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteNote(int id) {
    try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM note WHERE id = ?")) {
      statement.setObject(1, id);

      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void initializeDataBase(List<NoteModel> models) {
    try (Statement statement = connection.createStatement()) {
      String openAndDropTable = "DROP TABLE IF EXISTS note;";
      String createTable = "CREATE TABLE note (" +
                           "id       INTEGER PRIMARY KEY AUTOINCREMENT," +
                           "name     TEXT NOT NULL," +
                           "creationDate     TEXT NOT NULL," +
                           "lastChangeDate     TEXT NOT NULL," +
                           "noteText TEXT NOT NULL" +
                           ");";

      statement.executeUpdate(openAndDropTable);
      statement.executeUpdate(createTable);

      for (NoteModel model : models) {
        addNote(model);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
