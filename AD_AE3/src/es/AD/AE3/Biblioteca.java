package es.AD.AE3;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Biblioteca {
	
	/**
	 * Metodo para desplegar el menu de interacción con el usuario.
	 * @throws IOException
	 */
	
	public static void mostrarOpciones() throws IOException {
		Scanner teclado = new Scanner(System.in);
		int opcion;
		int id = 0;
		do {
			System.out.println("\nElije una de las opciones: ");
			System.out.println("1. Mostrar todos los titulos de la biblioteca.");
			System.out.println("2. Mostrar informacion detallada de un libro.");
			System.out.println("3. Crear nuevo libro.");
			System.out.println("4. Actualizar libro.");
			System.out.println("5. Borrar libro");
			System.out.println("6. Cerrar la biblioteca");
			opcion = teclado.nextInt();
			switch (opcion) {
			case 1:
				recuperarTodos();
				break;
			case 2:
				mostrarLibro(id);
				break;
			case 3:
				Libro nuevolibro = null;
				crearLibro(nuevolibro);
				break;
			case 4:
				actualizarLibro(id);
				break;
			case 5:
				borrarLibro(id);
				break;
			case 6:
				opcion = 6;
				break;
			}
		} while (opcion != 6);
		teclado.close();
	}
	/**
	 * Borra un libro al pasarle el identificador.
	 * @param ide
	 */
	public static void borrarLibro(int ide) {
		ArrayList<Libro> listLibros = new ArrayList<Libro>();
		Libro lib;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String anyo_publicacion = eElement.getElementsByTagName("anyo_publicacion").item(0).getTextContent();;
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String paginas = eElement.getElementsByTagName("paginas").item(0).getTextContent();
				lib = new Libro(id, titulo, autor, anyo_publicacion, editorial, paginas);
				listLibros.add(lib);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		recuperarTodos();
		Scanner teclado = new Scanner(System.in);
		int indice;
		System.out.print("Vamos a eliminar un libro.");
		System.out.print("\nMarca el id del libro que quieres eliminar: ");
		indice = teclado.nextInt();
		listLibros.remove(indice);
		System.out.println("Libro eliminado.");
		
		writeXmlFile(listLibros);
	}
	/**
	 * Actualiza la información del libro a partir del identificador.
	 * @param ide
	 */
	public static void actualizarLibro(int ide) {
		ArrayList<Libro> listaLbros = new ArrayList<Libro>();
		Libro lib;
		int idUltimo = 0;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				System.out.println("ID libro : " + id );
				String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
				System.out.println("Titulo : " + titulo);
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				System.out.println("Autor : " + autor);
				String anyo_publicacion = eElement.getElementsByTagName("anyo_publicacion").item(0).getTextContent();
				System.out.println("Año Publicacion: " + anyo_publicacion);
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				System.out.println("Editorial : " + editorial);
				String paginas = eElement.getElementsByTagName("paginas").item(0).getTextContent();
				System.out.println("Paginas : " + paginas);
				lib = new Libro(id, titulo, autor, anyo_publicacion, editorial, paginas);
				listaLbros.add(lib);
				idUltimo = Integer.parseInt(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Scanner teclado = new Scanner(System.in);
			int id;
			System.out.println(" ");
			System.out.println("Vamos a actualizar un libro.");
			System.out.println("Marca el id del libro que quieres actualizar: ");
			id = teclado.nextInt();
			
			System.out.print("Titulo: ");				//Tengo un error aquí y no se como corregirlo, lo he cambiado y borrado, no hay forma de solucionarlo
			String titulo = teclado.nextLine();			//No se porque no me deja poner la opción del titulo
			System.out.print("Autor: ");
			String autor = teclado.nextLine();
			System.out.print("Año de publicacion: ");
			String anyo_publicacion = teclado.nextLine();
			System.out.print("Editorial: ");
			String editorial = teclado.nextLine();
			System.out.print("Paginas: ");
			String paginas = teclado.nextLine();
			
			String iden = Integer.toString(id);
			Libro nuevoLibro = new Libro(iden, titulo, autor, anyo_publicacion, editorial, paginas);
			System.out.println("Has creado el " + nuevoLibro);
			listaLbros.set(id, nuevoLibro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeXmlFile(listaLbros);
	}

	/**
	 * Muestra los atributos del libro por pantalla.
	 * @param ide
	 */
	public static void mostrarLibro(int ide) {
		recuperarTodos();
		Libro lib;
		ArrayList<Libro> list = new ArrayList<Libro>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String anyo_publicacion = eElement.getElementsByTagName("anyo_publicacion").item(0).getTextContent();
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String paginas = eElement.getElementsByTagName("paginas").item(0).getTextContent();
				lib = new Libro(id, titulo, autor, anyo_publicacion, editorial, paginas);
				list.add(lib);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner teclado = new Scanner(System.in);
		int valor;
		System.out.print("Vamos a ver la información un libro.");
		System.out.print("\nMarca el nº de id para ver la información de ese libro: ");
		valor = teclado.nextInt();
		System.out.println(list.get(valor));
	}
	/**
	 * devuelve una lista con todos los libros de la biblioteca.
	 */
	public static void recuperarTodos(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			System.out.println("Contenido XML " + raiz.getNodeName() + ":");
			NodeList nodeList = document.getElementsByTagName("libro");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				System.out.println("ID libro : " + eElement.getAttribute("id"));
				System.out.println("Titulo : " + eElement.getElementsByTagName("titulo").item(0).getTextContent());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo para modificar el archivo Libros.xml.
	 * @param listaLibros
	 */
	public static void writeXmlFile(ArrayList<Libro> listaLibros) {
		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			Element raiz = doc.createElement("libros");
			doc.appendChild(raiz);
			for (Libro lib : listaLibros) {
				Element libro = doc.createElement("libro");
				String id = String.valueOf(lib.getId());
				libro.setAttribute("id", id);
				raiz.appendChild(libro);
				Element titulo = doc.createElement("titulo");
				titulo.appendChild(doc.createTextNode(String.valueOf(lib.getTitulo())));
				libro.appendChild(titulo);
				Element autor = doc.createElement("autor");
				autor.appendChild(doc.createTextNode(String.valueOf(lib.getAutor())));
				libro.appendChild(autor);
				Element anyo_publicacion = doc.createElement("anyo_publicacion");
				anyo_publicacion.appendChild(doc.createTextNode(String.valueOf(lib.getAnyo_publicacion())));
				libro.appendChild(anyo_publicacion);
				Element editorial = doc.createElement("editorial");
				editorial.appendChild(doc.createTextNode(String.valueOf(lib.getEditorial())));
				libro.appendChild(editorial);
				Element paginas = doc.createElement("paginas");
				paginas.appendChild(doc.createTextNode(String.valueOf(lib.getPaginas())));
				libro.appendChild(paginas);
			}
			TransformerFactory tranFactory = TransformerFactory.newInstance(); // Crear serializador
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); // Darle formato al documento
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			try {
				FileWriter fw = new FileWriter("Libros.xml"); // Definir el nombre del fichero y guardar
				StreamResult result = new StreamResult(fw);
				aTransformer.transform(source, result);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (TransformerException ex) {
			System.out.println("Error escribiendo el documento");
		} catch (ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento");
		}
	}
	/**
	 * Crea un libro nuevo apartir de los datos que le proporciona el usuario.
	 * @param nuevoLibro
	 * @return el identificador del libro nuevo
	 */
	public static int crearLibro(Libro nuevoLibro) {

		ArrayList<Libro> lisLibros = new ArrayList<Libro>();
		Libro lib;
		int idUltimo = 0;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String anyo_publicacion = eElement.getElementsByTagName("anyo_publicacion").item(0).getTextContent();
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String paginas = eElement.getElementsByTagName("paginas").item(0).getTextContent();
				lib = new Libro(id, titulo, autor, anyo_publicacion, editorial, paginas);
				lisLibros.add(lib);
				idUltimo = Integer.parseInt(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scanner teclado = new Scanner(System.in);
			System.out.print("Vamos a crear un nuevo libro.");
			System.out.print("\nTitulo: ");
			String titulo = teclado.nextLine();
			System.out.print("Autor: ");
			String autor = teclado.nextLine();
			System.out.print("Año de publicacion: ");
			String anyo_publicacion = teclado.nextLine();
			System.out.print("Editorial: ");
			String editorial = teclado.nextLine();
			System.out.print("Paginas: ");
			String paginas = teclado.nextLine();
			idUltimo++;
			nuevoLibro = new Libro(Integer.toString(idUltimo), titulo, autor, anyo_publicacion, editorial, paginas);
			System.out.println("Has creado el " + nuevoLibro);
			lisLibros.add(nuevoLibro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeXmlFile(lisLibros);
		return idUltimo;
	}
	
	public static Libro recuperarLibro(int identificador) {
		recuperarTodos();
		Libro lib;
		ArrayList<Libro> lista = new ArrayList<Libro>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("Libros.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String anyo_publicacion = eElement.getElementsByTagName("anyo_publicacion").item(0).getTextContent();
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String paginas = eElement.getElementsByTagName("paginas").item(0).getTextContent();
				lib = new Libro(id, titulo, autor, anyo_publicacion, editorial, paginas);
				lista.add(lib);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner teclado = new Scanner(System.in);
		int valor;
		System.out.print("Vamos a recuperar un libro.");
		System.out.print("\nMarca el nº de id para ver la información del libro deseado: ");
		valor = teclado.nextInt();
		System.out.println(lista.get(valor));
		Libro libro = lista.get(valor);
		return libro;
	}

	public static void main(String[] args) throws IOException {
		mostrarOpciones();
	}

}
