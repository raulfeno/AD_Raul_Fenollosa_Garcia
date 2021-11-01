package es.AD.AE3;

public class Libro {

	String id, titulo, autor, anyo_publicacion, editorial, paginas;

	Libro() {
	}

	Libro(String id, String titulo, String autor, String anyo_publicacion, String editorial, String paginas) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.anyo_publicacion = anyo_publicacion;
		this.editorial= editorial;
		this.paginas = paginas;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getAnyo_publicacion() {
		return anyo_publicacion;
	}
	public void setAnyo_publicacion(String anyo_publicacion) {
		this.anyo_publicacion = anyo_publicacion;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getPaginas() {
		return paginas;
	}
	public void setPaginas(String paginas) {
		this.paginas = paginas;
	}

	public String toString() {
		String infoCompleta = " Libro -> Nºid: "+id+ " -Titulo: " +titulo+ " - Autor: " +autor+ " - Año Publicacion: " +anyo_publicacion+ " - Editorial: " +editorial+ " - Paginas: " +paginas;
		return infoCompleta;
	}
}