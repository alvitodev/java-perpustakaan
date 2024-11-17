package javaperpustakaan;
import java.sql.*;

public class MySQL {
    public static final String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String username;
    private String passworddb;
    private static Connection conn;

    public MySQL(String hostname, String database, String port, String username, String passworddb) {
        this.username = username;
        this.passworddb = passworddb;
        this.url = String.format("jdbc:mysql://%s:%s/%s", hostname, port, database);
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(MySQL.driver);
        conn = DriverManager.getConnection(url, username, passworddb);
        return conn;
    }

    public static void main(String[] args) {
        String hostname = "00505.h.filess.io";
        String database = "perpustakaan_scaletinof";
        String port = "3307";
        String username = "perpustakaan_scaletinof";
        String passworddb = "15c7764c1bd13c4a21601931c920b6ab6ba0841a";

        MySQL mysql = new MySQL(hostname, database, port, username, passworddb);
        try {
            Connection conn = mysql.connect();

            System.out.println(String.format("Connected: %b", !conn.isClosed()));
            if (conn.isClosed()) {
                System.out.println("Terminating as the connection is closed.");
                System.exit(1);
            }

            // Modified query to retrieve data from the 'perpustakaan' table
            String query = "SELECT * FROM anggota";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Process and print the results
            while (rs.next()) {
                String nama     = rs.getString("nama");
                long nim        = rs.getLong("nim");
                String alamat   = rs.getString("alamat");
                long notelp     = rs.getLong("notelp");
                String email    = rs.getString("email");
                String password = rs.getString("password");

                System.out.println("Nama: " + nama);
                System.out.println("NIM: " + nim);
                System.out.println("Alamat: " + alamat);
                System.out.println("Nomor Telepon: " + notelp);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("------------------------");
            }

            // Close the ResultSet and Statement
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error di MySQL");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error di luar MySQL");
            e.printStackTrace();
        } finally {
            // Close the connection in a finally block to ensure it's closed even if exceptions occur
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}