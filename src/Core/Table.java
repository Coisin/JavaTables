package Core;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Table<E> extends JTable{
	
	private int width, height;
	private Dimension size;
	
	private Field[] fields;
	private String[] columnNames;
	private List<E> data;
	private DefaultTableModel model;
	
	private JScrollPane pane;
	
	public Table(Class<E> type) {
		pane = new JScrollPane(this);
		
		fields = type.getFields();
		columnNames = new String[fields.length];
		for(int i = 0;i<fields.length;i++) {
			columnNames[i] = fields[i].getName();
		}

		model = new DefaultTableModel() {
			public Class getColumnClass(int column) {
				return fields[column].getClass();
			}
		};
		
		model.setColumnIdentifiers(columnNames);
		setModel(model);
		
		data = new ArrayList();
		
		setPaneSize(300, 150);
		
	}
	
	public void setPaneSize(int width, int height) {
		this.width = width;
		this.height = height;
		size = new Dimension(width, height);
		
		pane.setPreferredSize(size);
	}
	
	public Dimension getPaneSize() { return size; }
	
	public JScrollPane getScrollPane() { return pane; }
	
	public List<E> getData() { return data; }
	
	public List<Integer> getRowIndexesWhere(Field field, Object value, int quantity) {
		ArrayList<Integer> results = new ArrayList();
		int found = 0;
		int i = 0;
		try {
			for(E e:data) {
				if(field.get(e) == value || value instanceof String && value.equals(field.get(e))) {
					results.add(i);
					found++;
					if(found == quantity)break;
				}
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Integer> getRowIndexesWhere(String column, Object value, int quantity) {
		
		for(Field f:fields) {
			if(f.getName().equalsIgnoreCase(column)) {
				return getRowIndexesWhere(f, value, quantity);
			}
		}
		return null;

	}
	
	public List<E> getRowsWhere(Field field, Object value, int quantity) {
		List<Integer> indexes = getRowIndexesWhere(field, value, quantity);
		List<E> result = new ArrayList();
		for(int index:indexes) {
			result.add(data.get(index));
		}
		return result;
	}
	
	public List<E> getRowsWhere(String column, Object value, int quantity) {
		List<Integer> indexes = getRowIndexesWhere(column, value, quantity);
		List<E> result = new ArrayList();
		for(int index:indexes) {
			result.add(data.get(index));
		}
		return result;
	}
	
	public int getIndexOf(E focal) {
		int i = 0;
		boolean equal;
		try {
			for(E e:data) {
				equal = true;
				for(Field f:fields) {
					if(f.get(e) != f.get(focal) && !f.get(e).equals(f.get(focal))) {
						equal = false;
						break;
					}
				}
				if(equal) {
					return i;
				}
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void addRow(E toAdd) {
		Object[] newRowData = new Object[fields.length];
		try {
			for(int i = 0;i < fields.length;i++) {
				newRowData[i] = fields[i].get(toAdd);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addRow(newRowData);
		data.add(toAdd);
	}
	
	public void addManyRows(Collection<E> toAdd) {
		for(E e:toAdd) {
			addRow(e);
		}
	}
	
	public void removeRow(int index) {
		model.removeRow(index);
		data.remove(index);
	}
	
	public void removeRow(E e) {
		model.removeRow(getIndexOf(e));
		data.remove(e);
	}
	
	public void removeManyRows(int[] indexes) {
		Arrays.sort(indexes);
		for(int i = indexes.length - 1;i>=0;i--) {
			removeRow(indexes[i]);
		}
	}
	
	public void removeManyRows(List<E> es) {
		for(int i = es.size() - 1;i >= 0;i--) {
			removeRow(i);
		}
	}
	
	public List<E> getHighlightedRows() {
		int[] indexes = super.getSelectedRows();
		return getManyRows(indexes);
	}

	public List<E> getAllRows() {
		return data;
	}

	public void removeAllRows() {
		removeManyRows(getData());
	}

	public void removeHighlightedRows() {
		removeManyRows(getHighlightedRows());
	}

	public E getRow(int index) {
		return data.get(index);
	}

	public List<E> getManyRows(int[] indexes) {
		List<E> rows = new ArrayList<E>();
		for(int index:indexes) {
			rows.add(data.get(index));
		}
		return rows;
	}

	public void avoidColumn(String column) {
		String[] columns = new String[columnNames.length - 1];
		Field[] fields = new Field[columnNames.length - 1];
		int index = 0;

		for(int i = 0;i<columnNames.length;i++) {
			if(!columnNames[i].equalsIgnoreCase(column)) {
				columns[index] = columnNames[i];
				fields[index] = this.fields[i];
				index++;
			}
		}
		this.columnNames = columns;
		this.fields = fields;
		model.setColumnIdentifiers(columns);
	}

	public void update(E e) {
		int index = getIndexOf(e);
		update(index);
	}

	public void update(int index) {
		Object[] newRowData = new Object[fields.length];
		try {
			for(int i = 0;i < fields.length;i++) {
				model.setValueAt(fields[i].get(data.get(index)), index, i);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
