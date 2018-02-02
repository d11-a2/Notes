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
  private static final String DATABASE = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\sqlite\\db\\notes.db";

  // Используем шаблон одиночка, чтобы не плодить множество
  // экземпляров класса DbHandler
  private static DBHandler instance = null;

  public static synchronized DBHandler getInstance() throws SQLException {
    if (instance == null) { instance = new DBHandler(); }
    return instance;
  }

  // Объект, в котором будет храниться соединение с БД
  private Connection connection;

  private DBHandler() throws SQLException {
    // Регистрируем драйвер, с которым будем работать
    // в нашем случае Sqlite
    DriverManager.registerDriver(new JDBC());
    // Выполняем подключение к базе данных
    System.out.println(DATABASE);
    this.connection = DriverManager.getConnection(DATABASE);
    connection.getCatalog();
    System.out.println(connection.getSchema());
  }

  public List<NoteModel> getAllNotes() {

    // Statement используется для того, чтобы выполнить sql-запрос
    try (Statement statement = this.connection.createStatement()) {
      // В данный список будем загружать наши продукты, полученные из БД
      List<NoteModel> notes = new ArrayList<>();
      // В resultSet будет храниться результат нашего запроса,
      // который выполняется командой statement.executeQuery()
      ResultSet resultSet = statement.executeQuery("SELECT name , date, noteText FROM note");
      // Проходимся по нашему resultSet и заносим данные в products
      while (resultSet.next()) {
        notes.add(new NoteModel(resultSet.getString("name"), resultSet.getString("date"), resultSet.getString("noteText")));
      }
      // Возвращаем наш список
      return notes;

    }
    catch (SQLException e) {
      e.printStackTrace();
      // Если произошла ошибка - возвращаем пустую коллекцию
      return Collections.emptyList();
    }
  }
/*
  // Добавление продукта в БД
  public void addProduct(Product product) {
    // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
    try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Products(`good`, `price`, `category_name`) " + "VALUES(?, ?, ?)")) {
      statement.setObject(1, product.good);
      statement.setObject(2, product.price);
      statement.setObject(3, product.category_name);
      // Выполняем запрос
      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Удаление продукта по id
  public void deleteProduct(int id) {
    try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Products WHERE id = ?")) {
      statement.setObject(1, id);
      // Выполняем запрос
      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  */
}
