package es.ADD.Ejercicios4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AE4 {
	/**
	 * Metodo Main con todo el funcionamiento de la aplicacion.
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		ArrayList<String> contenidoFichero = new ArrayList<String>(); //Primera parte donde leemos el .csv y lo guardamos en una lista.
		String fichero = "AE04_T1_4_JDBC_Datos.csv";
		File f = new File(fichero);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Class.forName("com.mysql.cj.jdbc.Driver");  //Segunda parte donde conectamos con la BD y cargamos los datos extraidos del .csv
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
			Statement stmt = con.createStatement();
			PreparedStatement psInsertar = con.prepareStatement(
					"INSERT INTO libros (titulo,autor,añoNacimiento,añoPublicacion,editorial,numPaginas) VALUES (?,?,?,?,?,?)");
			for (int i = 1; i < contenidoFichero.size(); i++) {
				String libro = contenidoFichero.get(i);
				String[] partes = libro.split(";");
				for (int j = 0; j < partes.length; j++) {
					String titulo = partes[0];
					String autor = partes[1];
					String añoNacimiento = partes[2];
					String añoPublicacion = partes[3];
					String editorial = partes[4];
					String numPaginas = partes[5];
					psInsertar.setString(1, titulo);
					psInsertar.setString(2, autor);
					psInsertar.setString(3, añoNacimiento);
					psInsertar.setString(4, añoPublicacion);
					psInsertar.setString(5, editorial);
					psInsertar.setString(6, numPaginas);
				}
				int resultadoInsertar = psInsertar.executeUpdate();
				if (resultadoInsertar > 0) {
					System.out.println("Libros guardada en la BD");
				} else {
					System.err.println("Error en la inserción.");
				}
			}
			ResultSet rs = stmt
					.executeQuery("SELECT titulo, autor, añoPublicacion FROM `libros` WHERE añoNacimiento < '1950'"); //Consulta:Libros (título, autor y año de publicación) de los autores nacidos antes de 1950.
			System.err.format("%30s%20s%15s%2s", "titulo", "Autor", "AñoPublicacion", "\n");
			System.out.format("%30s%20s%15s%2s", "======", "========", "=======",  "\n");
			while (rs.next()) {
				System.out.format("%30s%20s%15s%2s", rs.getString(1), rs.getString(2), rs.getString(3), "\n");
			}
			ResultSet rst = stmt.executeQuery("SELECT editorial FROM `libros` WHERE añoPublicacion > '2001'"); //Consulta:Editoriales que hayan publicado al menos un libro en el siglo XXI.
			System.err.format("%15s%2s","Editorial", "\n");
			System.out.format("%15s%2s", "======", "\n");
			while (rst.next()) {
				System.out.format("%15s%2s", rst.getString(1),  "\n");
			}
			rs.close();
			rst.close();
			con.close();
		} catch (Exception e) {
			System.err.println("Error en la conexion");
			System.out.println(e);
		}
	}
}
