package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.LoyaltyCard;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;



public class RestoAppController {	
	
	public RestoAppController() {
	}
	
	
	
	//ADD TABLE - ERICA
	public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats)
			throws InvalidInputException { 
			
		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> currentTables = getCurrentTables();
		boolean overlaps;
		String error = "";//will be used to display errors
		Table table;//will be used to create new table
		
		if (x < 0) {
			error = "The x coordinate of the table cannot be negative.";
		}
			
		if (y < 0) {
			error = error + "The y coordinate of the table cannot be negative.";
		}
			
		if (number <= 0) {
			error = error + "The number of the table cannot be negative or zero.";			
		}
			
		if (width <= 0) {
			error = error + "The width of the table cannot be negative or zero.";
		}
			
		if (length <= 0) {
			error = error + "The length of the table cannot be negative or zero.";
		}
			
		if (numberOfSeats <= 0) {
			error = error + "The number of seats cannot be negative or zero.";
		} //these 6 if statements accumulate errors in case of incorrect inputs for table creation
			
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim()); //displays errors
		}
			
		for(Table currentTable : currentTables) {
			int currentWidth = currentTable.getWidth();
			int currentLength = currentTable.getLength();
				
			overlaps = currentTable.doesOverlap(x, y, currentWidth, currentLength);
				
			if (overlaps)
			{
				error = "The table overlaps with another table.";
				throw new InvalidInputException(error.trim());
			}
		}//for loop makes sure new table does not overlap with any existent table
			
		try {
			table = new Table(number, x, y, width, length, r);
		}//creates new table
			
		catch (RuntimeException e) {
			throw new InvalidInputException("Runtime Exception" + e.getMessage());
		}//in case number of table already exists
			
		r.addCurrentTable(table);
			
		for (int i=1; i<=numberOfSeats; i++) {
			Seat seat = table.addSeat();
			table.addCurrentSeat(seat);
		}//adds all necessary seats
			
		
		RestoAppApplication.save();

	}

	
	
	//CHANGE LOCATION OF TABLE - GARINE
	public static void moveTable (Table table, int x, int y) throws InvalidInputException {
		
		String error = "";
		
		if (table == null) 
			error = "This table does not exist.";
		
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		int width = table.getWidth();
		int length = table.getLength();
		Boolean overlaps;
		
		if (x < 0) 
			error = error + "The x coordinate of the table cannot be negative.";
		
		if (y < 0) 
			error = error + "The y coordinate of the table cannot be negative.";
		
		if (x - (20+30+10) < 0) 
			error = error + "The x coordinate of the table is too small for the restaurant layout.";
		
		if (y - (20+30+10) < 0) 
			error = error + "The y coordinate of the table is too small for the restaurant layout.";
		
		if (x + width + (20+30+10) > 6000) 
			error = error + "The x coordinate of the table is too big for the restaurant layout.";
		
		if (y + length + (20+30+10) > 10000) 
			error = error + "The y coordinate of the table is too big for the restaurant layout.";
		
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());

		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		for (Table currentTable : r.getCurrentTables()) {
			overlaps = currentTable.doesOverlap (x, y, width, length);
			
			if (overlaps && !table.equals(currentTable)) {
				error = "The table overlaps with another table.";
				throw new InvalidInputException(error.trim());
			}
		}
		
		table.setX(x);
		table.setY(y);
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}
	
	
	public static List<Table> getCurrentTables() {
		return RestoAppApplication.getRestoApp().getCurrentTables();
	}
		  
		  
	// UPDATE TABLE AND SEATS - AUREL  
	public static void updateTable (Table table , int newNumber, int numberofSeats) throws InvalidInputException{
		String error = "";
		if (table == null) 
			error = "This table does not exist.";
		if (newNumber <= 0)
			error = "The Table number cannot be negative";
		if (numberofSeats <= 0)
			error = "The number of Seats cannot be negative";
		if (numberofSeats <=0 && newNumber <=0)
			error = "The number of Seats and the table number cannot be negative";
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		List<Reservation> reserved = table.getReservations();
		if (reserved.size() > 0) {
			error = "There is a reservation";
			throw new InvalidInputException(error.trim());
		}			
					
		RestoApp r = RestoAppApplication.getRestoApp();
		boolean inUse = false;
		for (Order order : r.getCurrentOrders()) {
			List<Table> orderTables = order.getTables();
			inUse = orderTables.contains(table); 
		}

		if (inUse == true) {
			error = "This table is in use. You cannot update it.";
			throw new InvalidInputException(error);	
		}
		
		try {
			table.setNumber(newNumber);
		}
		catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("Cannot create due to duplicate number")) {
				error = "A table with this number already exists. Please use a different number.";
			}
			throw new InvalidInputException(error);
		}		
		
		//Seats
		int n = table.numberOfCurrentSeats();
		for (int i = 0 ; i< numberofSeats - n ; i++) {
			Seat seat = table.addSeat();			
			table.addCurrentSeat(seat);
		}
		
		for (int i = 0 ; i < n-numberofSeats ; i++) {
			Seat seat = table.getCurrentSeat(0);
			table.removeCurrentSeat(seat);		
		}
			
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}		
	}
		
		
	// REMOVE TABLE - ALEXA   
	public static void removeTable (Table table , int Number) throws InvalidInputException{
		boolean inUse = false;
		
		String error = "";
		if (table == null) 
			error = "This table does not exist.";
		if (Number < 0)
			error = "The Table number cannot be negative";
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		List<Reservation> reserved = table.getReservations();
		if (reserved.size() > 0) {
			error = error + "There is a reservation";
			throw new InvalidInputException(error.trim());
		}		
				
		RestoApp r = RestoAppApplication.getRestoApp();
		for (Order order : r.getCurrentOrders()) {
			List<Table> orderTables = order.getTables();
			inUse = orderTables.contains(table); 
		}	
		
		if (inUse == false)
			r.removeCurrentTable(table);
		if (inUse == true)
			error = error + "Could not remove table";
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}	

	}
	
	//GET MENU ITEMS FOR EACH CATEGORY, ROGER AND MIKE
	public static List<MenuItem> getMenutItems(ItemCategory itemCategory) throws InvalidInputException {
		String error = "";
		
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		RestoApp r = RestoAppApplication.getRestoApp();
		
	//	System.out.println(result);
		
		for(MenuItem item : r.getMenu().getMenuItems()) {
			if(item.getItemCategory() == null) {
				error = "Item has no category";
				throw new InvalidInputException(error.trim());
			}
			if(item.getItemCategory().equals(itemCategory) && item.hasCurrentPricedMenuItem()) {
				result.add(item);
			}
		}
		return result;
	}
	

	
	//MAKING A RESERVATION
	public static void reserveTable(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress, 
			String contactPhoneNumber, ArrayList<Table> tables) throws InvalidInputException {
		
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		Date currentDate = java.sql.Date.valueOf(localDate);
		Time currentTime = java.sql.Time.valueOf(localTime);
		
		String error = "";
		
		if (tables == null) 
			error =  error + "This table or these tables do not exist.";
		
		if (date == null || time == null) 
			error =  error + "The date and time is not valid.";
		
		if (contactName.equals("")) 
			error =  error + "The reservation must be under a name.";
		
		if (contactEmailAddress.equals("") && contactPhoneNumber.equals("")) 
			error =  error + "The reservation must have a contact email adress or a contact phone number.";
		
		if (numberInParty <= 0) 
			error =  error + "The reservation must be made for a valid party number.";
		
		if (tables.isEmpty() == true) 
			error =  error + "There must be at least one table selected";		
		
		if(date.before(currentDate) || (date.equals(currentDate) && time.before(currentTime))) 
			error =  error + "The reservation cannot be made in the past.";
		
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());

		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		Reservation newReservation;
		int seatCapacity = 0;
		Boolean current = true;
		Boolean overlaps = false;
		
		for (Table table : tables) {
			
			current = currentTables.contains(table);
			if (current == false) {
				error = "This table is not a current table";
				throw new InvalidInputException(error);	
			}
			
			seatCapacity = seatCapacity + table.numberOfCurrentSeats();
			
			
			List<Reservation> reservations = table.getReservations();
			for (Reservation reservation : reservations) {
				overlaps = reservation.doesOverlap(date, time);
				if (overlaps == true) {
					error = "This reservation overlaps with another reservation.";
					throw new InvalidInputException(error);	
				}
			}
		}
		if (seatCapacity < numberInParty) {
			error = "There are not enough seats for the party number.";
			throw new InvalidInputException(error);	
		}
		
		Table[] tableArray = tables.toArray(new Table[tables.size()]);
		
		try {
			newReservation = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, tableArray);
		}
			
		catch (RuntimeException e) {
			throw new InvalidInputException("Runtime Exception" + e.getMessage());
		}
			
		r.addReservation(newReservation);
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	
	
	//CREATE ORDER
	public static void startOrder(List<Table> tables) throws InvalidInputException {
		
		String error = "";
		if (tables == null) 
			error =  error + "This table or these tables do not exist.";				
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Table> currentTables = r.getCurrentTables();
		Boolean current = true;
		
		for (Table table : tables) {
			current = currentTables.contains(table);
			if (current == false) {
				error = "This table is not a current table";
				throw new InvalidInputException(error);	
			}
		}
		
		Boolean orderCreated = false;
		Order newOrder = null;
		Order lastOrder = null;
		
		for (Table table : tables) {
			if (orderCreated) {
				table.addToOrder(newOrder);
			}
			
			else {
				lastOrder = null;												
				if(table.numberOfOrders() > 0) {								
					lastOrder = table.getOrder(table.numberOfOrders()-1);		
				}																
			
				table.startOrder();
				
				if(table.numberOfOrders() > 0 && !table.getOrder(table.numberOfOrders()-1).equals(lastOrder)) {
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders()-1);
				}
			}
		}
		
		if (orderCreated == false) {
			error = "Something went wrong. The order was not created.";
			throw new InvalidInputException(error);	
		}	
		
		r.addCurrentOrder(newOrder);
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	
	
	//END ORDER
	public static void endOrder(Order order) throws InvalidInputException{
		
		String error = "";
		if (order == null) 
			error =  error + "This order does not exist.";				
		if (error.length() > 0) 
			throw new InvalidInputException(error.trim());
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Order> currentOrders = r.getCurrentOrders();
		Boolean current = true;
		current = currentOrders.contains(order);
		if (current == false) {
			error = "This table is not a current order.";
			throw new InvalidInputException(error);	
		}
		
		List<Table> tables = new ArrayList<Table>(order.getTables());
		
		for (Table table : tables) {
			if (table.numberOfOrders()>0 && table.getOrder(table.numberOfOrders()-1).equals(order)) {
				table.endOrder(order);
			}
		}
		
		if(allTablesAvailableOrDifferentCurrentOrder(tables, order)) {
			r.removeCurrentOrder(order);
		}
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	
	
	//METHOD FOR END ORDER
	public static boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order) {
			
		for(Table table : tables) {
			
		    Status status = table.getStatus();
		    switch (status) {
		    
		      case Available:
		    	  break;
		    
		      default:
		    	  List<Order> tableOrders = table.getOrders();
		    	  if (tableOrders != null) {
		    		  int lastOrderIndex = (table.numberOfOrders()) - 1;
		    		  int orderIndex = table.indexOfOrder(order);

		    		  if (orderIndex == lastOrderIndex)		return false;
		    	  }
		    	  break;
		    }
		}
		return true;
	}
	
	
	public static List<Reservation> getReservationsForDate(Date date) {
		date = cleanDate(date);
		RestoApp r = RestoAppApplication.getRestoApp();
		ArrayList<Reservation> result = new ArrayList<Reservation>();
		for (Reservation reservation : r.getReservations()) {
			if((reservation.getDate()).toString().equals((date).toString())) {
				result.add(reservation);
			}
		}
		return result;
	}
	
	public static List<LoyaltyCard> getLoyaltyCards() {
		RestoApp r = RestoAppApplication.getRestoApp();
		ArrayList<LoyaltyCard> result = new ArrayList<LoyaltyCard>();
		for (LoyaltyCard loyaltyCard : r.getLoyaltyCards()) {
				result.add(loyaltyCard);
		}
		return result;
	}
	
	private static Date cleanDate(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(date.getTime());
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    java.util.Date tempCleanedDate = cal.getTime();
	    java.sql.Date cleanedDate = new java.sql.Date(tempCleanedDate.getTime());
	    return cleanedDate;
	}
	
	//CANCEL ORDER ITEM - ERICA
			public static void cancelOrderItem(OrderItem orderItem) throws InvalidInputException {
				
				RestoApp r = RestoAppApplication.getRestoApp();
				List<Seat> seats = orderItem.getSeats();
				Order order = orderItem.getOrder();
				List<Table> tables = r.getCurrentTables(); //not too sure abt this, maybe list of tables tht belong to the order?
				//do i need to do any more error handling?
				for(Seat seat : seats) {
					Table table = seat.getTable();
					Order lastOrder = null;
					if (table.numberOfOrders() > 0) {
						lastOrder = table.getOrder(table.numberOfOrders() - 1);
					}
					else {
						throw new InvalidInputException("The table has no order.");
					}
					if (lastOrder.equals(order) && !tables.contains(table)) {
						tables.add(table);
					}
				}
				
				for (Table table : tables) {
					table.cancelOrderItem(orderItem);
				}
				RestoAppApplication.save();
			}
			
		//CANCEL ORDER - ERICA
		public static void cancelOrder(Table table) throws InvalidInputException {
			
			RestoApp r = RestoAppApplication.getRestoApp();
			List<Table> currentTables = r.getCurrentTables();
			boolean current = currentTables.contains(table);
			
			if (!current) {
				throw new InvalidInputException("The selected table is not part of the current list of tables.");
			}
			
			table.cancelOrder();
			RestoAppApplication.save();
		}
		
		//ROGER STUFF
		
		public static void removeMenuItem(MenuItem menuItem) throws InvalidInputException {
			
			String error = "";
			
			if(menuItem == null) 
				error = "No item to remove.";
			if(menuItem.hasCurrentPricedMenuItem() == false)
				error = "Item doesn't exist";
			if (error.length() > 0) 
				throw new InvalidInputException(error.trim());
			
			menuItem.setCurrentPricedMenuItem(null);
			
			try {
				RestoAppApplication.save();
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
				
		}
		
		public static void updateMenuItem(MenuItem menuItem, String name, ItemCategory category, double price) throws InvalidInputException{
			
			String error = "";
			
			if(menuItem == null)
				error = "No item to update.";
			if(name.length() == 0 && category == null && price == -1)
				error = "No information to update.";
			if(MenuItem.hasWithName(name) && MenuItem.getWithName(name).hasCurrentPricedMenuItem()) {
				error = "Item with that name already exists.";
			}
			if (error.length() > 0) 
				throw new InvalidInputException(error.trim());
			
			
			RestoApp r = RestoAppApplication.getRestoApp();
			
			
			if(!name.equals("")) {
				menuItem.setName(name);
			}
			if(!(category == null)) {
				menuItem.setItemCategory(category);
			}
			if(!(price == -1)) {
				PricedMenuItem pmi = menuItem.addPricedMenuItem(price, r);
				menuItem.setCurrentPricedMenuItem(pmi);
			}
				
			try {
				RestoAppApplication.save();
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
			
		}
		
		public static void addMenuItem(String name, ItemCategory category, double price) throws InvalidInputException {
			
			
			
			String error = "";
			
			if(name == null || name.equals(""))
				error = "New item has no name.";
			if(category == null)
				error = "New item has no category.";
			if(price < 0)
				error = "Price cannot be negative";
			if(MenuItem.hasWithName(name) && MenuItem.getWithName(name).hasCurrentPricedMenuItem()) {
				error = "Item with that name already exists.";
			}
			if (error.length() > 0) 
				throw new InvalidInputException(error.trim());
			
			
			RestoApp r = RestoAppApplication.getRestoApp();
			Menu menu = r.getMenu();
			
			if(error.length() == 0) {
				if(MenuItem.hasWithName(name) && !(MenuItem.getWithName(name).hasCurrentPricedMenuItem())) {
					PricedMenuItem pmi = MenuItem.getWithName(name).addPricedMenuItem(price, r);
					MenuItem.getWithName(name).setCurrentPricedMenuItem(pmi);
				}
				else {
				MenuItem newItem = new MenuItem(name, menu);
				newItem.setItemCategory(category);
				PricedMenuItem pmi = newItem.addPricedMenuItem(price, r);
				newItem.setCurrentPricedMenuItem(pmi);
				}
			}
			
			try {
				RestoAppApplication.save();
			}
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}

		}
	
}
