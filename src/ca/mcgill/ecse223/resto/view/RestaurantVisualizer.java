package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.RestoApp;



public class RestaurantVisualizer extends JPanel{
	
	private static final long serialVersionUID = 5765666411683246454L;
	
	//UI elements
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
	private List<Ellipse2D> circles = new ArrayList<Ellipse2D>();
	private static final int SEAT_WIDTH = 20;
	private static final int SEAT_LENGTH = 20;
	private static final int SEAT_SPACE = 30;
	private static final int BUFFER = 4;
	
	//Data elements
	RestoApp restoApp = RestoAppApplication.getRestoApp();
	
	private HashMap<Rectangle2D, Table> currentTables;
	private Table selectedTable;
	
	private HashMap<Ellipse2D, Seat> currentSeats;
	private Seat selectedSeat;

	
	public RestaurantVisualizer() {
		super();
		init();
	}

	
	private void init() {
		
		currentTables = new HashMap<Rectangle2D, Table>();
		selectedTable = null;
		
		currentSeats = new HashMap<Ellipse2D, Seat>();
		selectedSeat = null;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (Rectangle2D rectangle : rectangles) {
					if (rectangle.contains(x, y)) {
						selectedTable = currentTables.get(rectangle);
						break;
					}
				}
				for (Ellipse2D circle : circles) {
					if (circle.contains(x, y)) {
						selectedSeat = currentSeats.get(circle);
						break;
					}
				}
				repaint();
			}
		});
	}
	
	
	public void addNewTable(Table table) {
		repaint();
	}
	
	public void editTable(Table table) {
		repaint();
	}
	
	public void changeTableLocation(Table table) {
		repaint();
	}
	
	public void removeTable(Table table) {
		repaint();
	}
	
	public void add_EndOrder(ArrayList<Table> tables) {
		repaint();
	}
	
	
	
	private void doDrawing(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		rectangles.clear();
		circles.clear();
		currentTables.clear();
		currentSeats.clear();
		
		int seatNumber, x, y, width, length;
		for (Table table : restoApp.getCurrentTables()) {
			x = table.getX();
			y = table.getY();
			width = table.getWidth();
			length = table.getLength();
			Rectangle2D rectangle = new Rectangle2D.Float(x, y, width, length);
			rectangles.add(rectangle);
			currentTables.put(rectangle, table);
			
			Status status = table.getStatus();
			switch (status) {
				case Available:
					g2d.setColor(Color.WHITE);
					g2d.fill(rectangle);
					break;
				default:
					g2d.setColor(Color.GREEN);
					g2d.fill(rectangle);
					break;
			}
			
			g2d.setColor(Color.BLACK);
			g2d.draw(rectangle);
			g2d.drawString(new Integer(table.getNumber()).toString(), x + (width/2) - BUFFER, y + (length/2) + BUFFER);
			
			
			//NEED TO DRAW THE SEATS
			seatNumber=0;
		
			for (Seat seat : table.getCurrentSeats()) {
				seatNumber++;
			}
			
			float xSeat;
			float ySeat;
			//Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
			
			
			if (seatNumber <= 4 || ((seatNumber == 5 || seatNumber == 6) && width<=length)
					|| seatNumber == 7 || seatNumber == 8 || ((seatNumber == 9 || seatNumber == 10) && width > length)) {
				Seat seat1 = null;
				xSeat = x + (width/2) - 2*BUFFER;
				ySeat = y - SEAT_SPACE - BUFFER;
				Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
				circles.add(circle);
				currentSeats.put(circle, seat1);
				
				g2d.setColor(Color.WHITE);
				g2d.fill(circle);
				g2d.setColor(Color.BLACK);
				g2d.draw(circle);
			}
			if ((seatNumber > 1 && seatNumber < 6) || ((seatNumber == 6 || seatNumber == 7) && width <= length)
					|| seatNumber == 8 || seatNumber == 9 || (seatNumber == 10 && width > length)) {
				Seat seat2 = null;
				xSeat = x + (width/2) - 2*BUFFER;
				ySeat = y + length + SEAT_SPACE - 3*BUFFER;
				Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
				circles.add(circle);
				currentSeats.put(circle, seat2);
				
				g2d.setColor(Color.WHITE);
				g2d.fill(circle);
				g2d.setColor(Color.BLACK);
				g2d.draw(circle);
			}
			if (seatNumber == 3 || seatNumber == 4 || ((seatNumber == 5 || seatNumber == 6) && width > length)
					|| seatNumber == 7 || seatNumber == 8 || ((seatNumber == 9 || seatNumber == 10)
							&& width <= length)) {
				Seat seat3 = null;
				xSeat = x - SEAT_SPACE - BUFFER;
				ySeat = y + (length/2) - 2*BUFFER;
				Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
				circles.add(circle);
				currentSeats.put(circle, seat3);
				
				g2d.setColor(Color.WHITE);
				g2d.fill(circle);
				g2d.setColor(Color.BLACK);
				g2d.draw(circle);
			}
			if (seatNumber == 4 || seatNumber == 5 || ((seatNumber == 6 || seatNumber == 7) && width > length)
					|| seatNumber == 8 || seatNumber == 9 || (seatNumber == 10 && width <= length)) {
				Seat seat4 = null;
				xSeat = x + width + SEAT_SPACE - 3*BUFFER;
				ySeat = y + (length/2) - 2*BUFFER;
				Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
				circles.add(circle);
				currentSeats.put(circle, seat4);
				
				g2d.setColor(Color.WHITE);
				g2d.fill(circle);
				g2d.setColor(Color.BLACK);
				g2d.draw(circle);
			}
			if (seatNumber >= 5) {
				if (width > length) {
					Seat seat1 = null;
					xSeat = x + (width/3) - 2*BUFFER;
					ySeat = y - SEAT_SPACE - BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat1);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat5 = null;
					xSeat = x + (2*width/3) - 2*BUFFER;
					ySeat = y - SEAT_SPACE - BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat5);
										
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
				else {
					Seat seat3 = null;
					xSeat = x - SEAT_SPACE - BUFFER;
					ySeat = y + (length/3) - 2*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat3);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat5 = null;
					xSeat = x - SEAT_SPACE - BUFFER;
					ySeat = y + (2*length/3 - 2*BUFFER);
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat5);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
			}
			if (seatNumber >= 6) {
				if (width > length) {
					Seat seat2 = null;
					xSeat = x + (width/3) - 2*BUFFER;
					ySeat = y + length + SEAT_SPACE - 3*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat2);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat6 = null;
					xSeat = x + (2*width/3) - 2*BUFFER;
					ySeat = y + length + SEAT_SPACE - 3*BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat6);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
				else {
					Seat seat4 = null;
					xSeat = x + width + SEAT_SPACE - 3*BUFFER;
					ySeat = y + (length/3) - 2*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat4);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat6 = null;
					xSeat = x + width + SEAT_SPACE - 3*BUFFER;
					ySeat = y + (2*length/3) - 2*BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat6);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
			}
		
			if (seatNumber >= 9) {
				if (width>length) {
					Seat seat3 = null;
					xSeat = x - SEAT_SPACE - BUFFER;
					ySeat = y + (length/3) - 2*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat3);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat9 = null;
					xSeat = x - SEAT_SPACE - BUFFER;
					ySeat = y + (2*length/3 - 2*BUFFER);
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat9);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
				else {
					Seat seat1 = null;
					xSeat = x + (width/3) - 2*BUFFER;
					ySeat = y - SEAT_SPACE - BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat1);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat9 = null;
					xSeat = x + (2*width/3) - 2*BUFFER;
					ySeat = y - SEAT_SPACE - BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat9);
										
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
			}
			if (seatNumber == 10) {
				if (width>length) {
					Seat seat4 = null;
					xSeat = x + width + SEAT_SPACE - 3*BUFFER;
					ySeat = y + (length/3) - 2*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat4);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat10 = null;
					xSeat = x + width + SEAT_SPACE - 3*BUFFER;
					ySeat = y + (2*length/3) - 2*BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat10);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);
				}
				else {
					Seat seat2 = null;
					xSeat = x + (width/3) - 2*BUFFER;
					ySeat = y + length + SEAT_SPACE - 3*BUFFER;
					Ellipse2D circle = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle);
					currentSeats.put(circle, seat2);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle);
					
					Seat seat10 = null;
					xSeat = x + (2*width/3) - 2*BUFFER;
					ySeat = y + length + SEAT_SPACE - 3*BUFFER;
					Ellipse2D circle2 = new Ellipse2D.Float(xSeat, ySeat, SEAT_WIDTH, SEAT_LENGTH);
					circles.add(circle2);
					currentSeats.put(circle2, seat10);
					
					g2d.setColor(Color.WHITE);
					g2d.fill(circle2);
					g2d.setColor(Color.BLACK);
					g2d.draw(circle2);	
				}
			}

			
			
		}
		
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
}
