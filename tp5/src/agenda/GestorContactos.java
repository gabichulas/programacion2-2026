package agenda;

import java.sql.*;

public class GestorContactos {

  private Connection conn = null;

  public GestorContactos() {
    this("jdbc:sqlite:agenda.sqlite");
  }

  public GestorContactos(String url) {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection(url);
      if (conn != null) {
        Statement stmt = conn.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
        stmt.close();
        System.out.println("Conexión a base de datos ... Ok");
      } else {
        System.out.println("Conexión a base de datos fallida!");
      }
    } catch (SQLException a) {
      System.out.println("Error SQL de conexión: " + a.getMessage());
    } catch (ClassNotFoundException ex) {
      System.out.println("Error de driver: " + ex.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void cerrarConexion() {
    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    } catch (SQLException a) {
      System.out.println("Error al cerrar conexión: " + a.getMessage());
    }
  }

  private void validarTexto(String valor, String campo) throws Exception {
    if (valor == null || valor.trim().isEmpty()) {
      throw new Exception("Dato inválido: el campo '" + campo + "' no puede estar vacío.");
    }
  }

  private void verificarExisteContacto(int contactoId) throws SQLException {
    PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM contactos WHERE id = ?");
    pstmt.setInt(1, contactoId);
    ResultSet rs = pstmt.executeQuery();
    boolean existe = rs.next();
    rs.close();
    pstmt.close();
    if (!existe) {
      throw new SQLException("No existe el contacto con id " + contactoId + ".");
    }
  }

  public void crearTablas() {
    try {
      Statement stmt = conn.createStatement();

      stmt.execute("DROP TABLE IF EXISTS direcciones;");
      stmt.execute("DROP TABLE IF EXISTS emails;");
      stmt.execute("DROP TABLE IF EXISTS contactos;");

      stmt.execute(
          "CREATE TABLE contactos ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "nombre TEXT NOT NULL, "
              + "apellido TEXT NOT NULL, "
              + "telefono TEXT NOT NULL"
              + ");");

      stmt.execute(
          "CREATE TABLE emails ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "contacto_id INTEGER NOT NULL, "
              + "email TEXT NOT NULL, "
              + "FOREIGN KEY (contacto_id) REFERENCES contactos(id)"
              + ");");

      stmt.execute(
          "CREATE TABLE direcciones ("
              + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "contacto_id INTEGER NOT NULL, "
              + "direccion TEXT NOT NULL, "
              + "FOREIGN KEY (contacto_id) REFERENCES contactos(id)"
              + ");");

      stmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public int crearContacto(String nombre, String apellido, String telefono) {
    int idGenerado = -1;
    try {
      validarTexto(nombre, "nombre");
      validarTexto(apellido, "apellido");
      validarTexto(telefono, "telefono");

      String sql = "INSERT INTO contactos (nombre, apellido, telefono) VALUES (?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, nombre.trim());
      pstmt.setString(2, apellido.trim());
      pstmt.setString(3, telefono.trim());
      pstmt.executeUpdate();

      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next()) {
        idGenerado = rs.getInt(1);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return idGenerado;
  }

  public void leerContactos() {
    try {
      String sql = "SELECT c.id, c.nombre, c.apellido, c.telefono, e.email, d.direccion "
          + "FROM contactos c "
          + "LEFT JOIN emails e ON c.id = e.contacto_id "
          + "LEFT JOIN direcciones d ON c.id = d.contacto_id;";

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      boolean hayDatos = false;
      while (rs.next()) {
        hayDatos = true;
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String telefono = rs.getString("telefono");
        String email = rs.getString("email");
        String direccion = rs.getString("direccion");

        System.out.println(
            "ID: "
                + id
                + " | "
                + nombre
                + " "
                + apellido
                + " | Tel: "
                + telefono
                + " | Email: "
                + email
                + " | Dirección: "
                + direccion);
      }
      if (!hayDatos) {
        System.out.println("No hay contactos cargados.");
      }

      rs.close();
      stmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void actualizarTelefono(int id, String nuevoTelefono) {
    try {
      validarTexto(nuevoTelefono, "telefono");
      verificarExisteContacto(id);

      String sql = "UPDATE contactos SET telefono = ? WHERE id = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, nuevoTelefono.trim());
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
      pstmt.close();

      System.out.println("Teléfono actualizado para el contacto ID " + id + ".");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void eliminarContacto(int id) {
    try {
      verificarExisteContacto(id);

      PreparedStatement psEmails = conn.prepareStatement("DELETE FROM emails WHERE contacto_id = ?");
      psEmails.setInt(1, id);
      psEmails.executeUpdate();
      psEmails.close();

      PreparedStatement psDirs = conn.prepareStatement("DELETE FROM direcciones WHERE contacto_id = ?");
      psDirs.setInt(1, id);
      psDirs.executeUpdate();
      psDirs.close();

      PreparedStatement psContacto = conn.prepareStatement("DELETE FROM contactos WHERE id = ?");
      psContacto.setInt(1, id);
      psContacto.executeUpdate();
      psContacto.close();

      System.out.println("Contacto ID " + id + " y sus datos relacionados fueron eliminados.");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public int crearEmail(int contactoId, String email) {
    int idGenerado = -1;
    try {
      validarTexto(email, "email");
      verificarExisteContacto(contactoId);

      String sql = "INSERT INTO emails (contacto_id, email) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setInt(1, contactoId);
      pstmt.setString(2, email.trim());
      pstmt.executeUpdate();

      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next()) {
        idGenerado = rs.getInt(1);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return idGenerado;
  }

  public void leerEmails(int contactoId) {
    try {
      verificarExisteContacto(contactoId);

      PreparedStatement pstmt = conn.prepareStatement("SELECT id, email FROM emails WHERE contacto_id = ?");
      pstmt.setInt(1, contactoId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        System.out.println("Email ID " + rs.getInt("id") + ": " + rs.getString("email"));
      }
      rs.close();
      pstmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void actualizarEmail(int id, String nuevoEmail) {
    try {
      validarTexto(nuevoEmail, "email");

      PreparedStatement pstmt = conn.prepareStatement("UPDATE emails SET email = ? WHERE id = ?");
      pstmt.setString(1, nuevoEmail.trim());
      pstmt.setInt(2, id);
      int filas = pstmt.executeUpdate();
      pstmt.close();

      if (filas == 0) {
        throw new SQLException("No existe el email con id " + id + ".");
      }
      System.out.println("Email ID " + id + " actualizado.");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void eliminarEmail(int id) {
    try {
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM emails WHERE id = ?");
      pstmt.setInt(1, id);
      int filas = pstmt.executeUpdate();
      pstmt.close();

      if (filas == 0) {
        throw new SQLException("No existe el email con id " + id + ".");
      }
      System.out.println("Email ID " + id + " eliminado.");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public int crearDireccion(int contactoId, String direccion) {
    int idGenerado = -1;
    try {
      validarTexto(direccion, "direccion");
      verificarExisteContacto(contactoId);

      String sql = "INSERT INTO direcciones (contacto_id, direccion) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pstmt.setInt(1, contactoId);
      pstmt.setString(2, direccion.trim());
      pstmt.executeUpdate();

      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next()) {
        idGenerado = rs.getInt(1);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return idGenerado;
  }

  public void leerDirecciones(int contactoId) {
    try {
      verificarExisteContacto(contactoId);

      PreparedStatement pstmt = conn.prepareStatement("SELECT id, direccion FROM direcciones WHERE contacto_id = ?");
      pstmt.setInt(1, contactoId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        System.out.println("Dirección ID " + rs.getInt("id") + ": " + rs.getString("direccion"));
      }
      rs.close();
      pstmt.close();
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void actualizarDireccion(int id, String nuevaDireccion) {
    try {
      validarTexto(nuevaDireccion, "direccion");

      PreparedStatement pstmt = conn.prepareStatement("UPDATE direcciones SET direccion = ? WHERE id = ?");
      pstmt.setString(1, nuevaDireccion.trim());
      pstmt.setInt(2, id);
      int filas = pstmt.executeUpdate();
      pstmt.close();

      if (filas == 0) {
        throw new SQLException("No existe la dirección con id " + id + ".");
      }
      System.out.println("Dirección ID " + id + " actualizada.");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void eliminarDireccion(int id) {
    try {
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM direcciones WHERE id = ?");
      pstmt.setInt(1, id);
      int filas = pstmt.executeUpdate();
      pstmt.close();

      if (filas == 0) {
        throw new SQLException("No existe la dirección con id " + id + ".");
      }
      System.out.println("Dirección ID " + id + " eliminada.");
    } catch (SQLException a) {
      System.out.println("Error SQL: " + a.getMessage());
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }
}
