package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
		books.add(new Book("D", "Harper Lee"));
		books.add(new Book("C", "Harper Lee"));
		books.add(new Book("D", "Harper Lee"));
		books.add(new Book("E", "Harper Lee"));
		books.add(new Book("F", "Harper Lee"));
		
		Table table = new Table<Book>(Book.class);
		table.addManyRows(books);
		
		JButton button = new JButton("Click Me!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(((List<Book>)table.getRowsWhere("name", "D", 2)).get(1).author);
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(table.getScrollPane());
		panel.add(button);
		
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
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
}

