package lessonTwo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static Connection conn;
    public static Statement stmt;

    public static void main(String[] args){
        try {
            connect();
            createTable();
            addString();
            updateFromFile();
            getTable();

        } catch (SQLException | ClassNotFoundException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn= DriverManager.getConnection("jdbc:sqlite:C:/Users/Oldredcomp/IdeaProjects/Main.db");

        stmt= conn.createStatement();
    }
    private void disconnect(){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //1 метод создани таблицы
    private static void createTable() throws SQLException {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS studs (" + " id INTEGER PRIMARY KEY AUTOINCREMENT " + " name TEXT UNIQUE " + " points TEXT" + ");");
    }
    //2 метод для добавления записи
    private static void addString() throws SQLException {

            stmt.executeUpdate("INSERT INTO studs (name, points) VALUES ('Bob4', 80);");

    }
    //3 метод для получения записи
    private static void getTable() throws SQLException {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM studs");
            while (rs.next()){
                System.out.println(rs.getInt(1)+ " "+ rs.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //4 метод для удаления записи

    private static void deleteString() throws SQLException {
        stmt.executeUpdate("DELETE FROM studs WHERE id = 4;");
    }

    //5 удаление таблицы
    private static void deleteTable() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS studs;");
    }

    //Обновить данные в БД из файла
    private static void updateFromFile() throws FileNotFoundException {
        FileInputStream fis= new FileInputStream("C:\\Users\\Oldredcomp\\IdeaProjects\\test.txt");
        Scanner sc = new Scanner(fis);

        while (sc.hasNext()) {
            String[] mass = sc.nextLine().split(" ");
            try {
                updDataBase(mass[0], mass[1]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void updDataBase(String id, String newValue) throws SQLException {
        String row = String.format("UPDATE students where id = %s SET score = %s ", id, newValue);
        stmt.executeUpdate(row);
    }
}

