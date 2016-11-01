package Core;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tables extends JFrame{
	
	public static void main(String args[]) {
		Tables t = new Tables();
		t.setDefaultCloseOperation(t.EXIT_ON_CLOSE);
		t.setVisible(true);
		t.setLocationRelativeTo(null);
		
		ArrayList<Book> books = new ArrayList<Book>();
		books.add(new Book("A", "Harper Lee"));
		books.add(new Book("B", "Harper Lee"));
		books.add(new Book("C", "Harper Lee"));
		books.add(new Book("D", "Harper Lee"));
		books.add(new Book("E", "Harper Lee"));
		books.add(new Book("F", "Harper Lee"));
		
		Table table = new Table<Book>(Book.class);
		table.addManyRows(books);
		
		
		
		JPanel panel = new JPanel();
		panel.add(table.getScrollPane());
		
		t.add(panel);
		
		t.pack();
		
	}
	
}

class Book {
	
	public String name, author;
	
	public Book(String name, String author) {
		this.name = name;
		this.author = author;
	}
	
}

