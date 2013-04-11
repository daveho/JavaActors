package edu.ycp.cs365.actordemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Grid {
	private List<Row> rowList;
	
	public Grid() {
		this.rowList = new ArrayList<Row>();
	}
	
	public void addRow(Row row) {
		rowList.add(row);
	}
	
	public void sort() {
		Collections.sort(rowList, new Comparator<Row>() {
			@Override
			public int compare(Row o1, Row o2) {
				if (o1.getY() < o2.getY()) {
					return -1;
				} else if (o1.getY() > o2.getY()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}
	
	public List<Row> getRowList() {
		return rowList;
	}
}
