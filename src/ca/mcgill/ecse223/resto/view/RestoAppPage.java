package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.view.DateLabelFormatter;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.LoyaltyCard;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Table.Status;


public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;
	
	// UI elements
	private JLabel errorMessage;
	
	//Top Labels
	private JLabel restaurantLabel;
	private JLabel menuLabel;
	
	//New Table
	private JLabel addTableLabel;
	private JButton addTableButton;
	private JLabel addNumberOfSeatsLabel;
	private JComboBox<Integer> addNumberOfSeatsList;
	private JLabel addWidthLabel;
	private JTextField addWidthField;
	private JLabel addLengthLabel;
	private JTextField addLengthField;
	private JLabel addXPositionLabel;
	private JTextField addXPositionField;
	private JLabel addYPositionLabel;
	private JTextField addYPositionField;
	private JLabel addTableNumberLabel;
	private JTextField addTableNumberField;
	
	//Choose Table
	private JLabel chooseTableLabel;
	private JComboBox<Integer> currentTableNumberList;
	
	//Edit Table
	private JLabel editTableLabel;
	private JButton editTableButton;
	private JLabel editTableNumberLabel;
	private JTextField editTableNumberField;
	private JLabel editNumberOfSeatsLabel;
	private JComboBox<Integer> editNumberOfSeatsList;
	
	//Move Table
	private JLabel moveTableLabel;
	private JButton moveTableButton;
	private JLabel moveXPositionLabel;
	private JTextField moveXPositionField;
	private JButton moveLeftButton;
	private JButton moveRightButton;
	private JLabel moveYPositionLabel;
	private JTextField moveYPositionField;
	private JButton moveUpButton;
	private JButton moveDownButton;
	
	//Remove Table
	private JLabel removeTableLabel;	
	private JButton removeTableButton;
	
	//Start/End Order
	private JLabel start_EndOrderLabel;
	private JLabel orderChooseTableLabel;
	private JComboBox<Integer> orderCurrentTableNumberList;
	private JTextField orderTablesField;
	private JButton orderAddTableButton;
	private JButton orderRemoveTableButton;
	private JButton startOrderButton;
	private JButton endOrderButton;
	
	//Reservation
	private JLabel makeReservationLabel;
	private JButton makeReservationButton;
	private JLabel reservChooseTableLabel;
	private JComboBox<Integer> reservCurrentTableNumberList;
	private JTextField reservTablesField;
	private JButton reservAddTableButton;
	private JButton reservRemoveTableButton;
	private JLabel reservDateLabel;
	private JDatePickerImpl reservDatePicker;
	private JLabel reservTimeLabel;
	private JComboBox<String> reservTimeList;
	private JLabel reservNumPartyLabel;
	private JTextField reservNumPartyField;
	private JLabel reservNameLabel;
	private JTextField reservNameField;
	private JLabel reservEmailLabel;
	private JTextField reservEmailField;
	private JLabel reservPhoneLabel;
	private JTextField reservPhoneField;
	
	//Reservation Overview
	private JDatePickerImpl overviewDatePicker;
	private JLabel overviewDateLabel; 
	private JTable overviewTable;
	private JScrollPane overviewScrollPane;
	
	//Create Loyalty Card
	private JLabel createCardLabel;
	private JButton createCardButton;
	private JLabel cardNameLabel;
	private JLabel cardPhoneLabel;
	private JLabel cardEmailLabel;
	private JLabel cardPostalCodeLabel;
	private JTextField cardNameField;
	private JTextField cardPhoneField;
	private JTextField cardEmailField;
	private JTextField cardPostalCodeField;
	
	//Assign Loyalty Card
	private JLabel assignCardLabel;
	private JButton assignCardButton;
	private JLabel cardChooseTableLabel;
	private JComboBox<Integer> cardCurrentTableNumberList;
	private JLabel assignCardNumberLabel;
	private JTextField assignCardNumberField;
	
	//Loyalty Card Overview
	private JTable cardOverviewTable;
	private JScrollPane cardOverviewScrollPane;
	
	//Bill and OrderItem
	private JLabel billChooseTableLabel;
	private JLabel billChooseSeatLabel;
	private JButton billAddToListButton;
	private JButton billOrderItemButton;
	private JButton billIssueButton;
	private JTextField billSeatsField;
	private JComboBox<Integer> billCurrentTableNumberList;
	private JComboBox<String> billCurrentSeatList;
	
	//Cancel Complete Order
	private JLabel cancelOrder;
	private JButton cancelOrderButton;
	private JLabel tableCanceled;
	private JComboBox<Integer> cancelCurrentTableNumberList;
	
	//Menu Buttons
	private JButton appetizerButton;
	private JButton mainButton;
	private JButton dessertButton;
	private JButton alcoholicBeverageButton;
	private JButton nonAlcoholicBeverageButton;
	private JButton uploadMenuButton;
	
	//Menu Items
	 JLabel selectCategory;
	 JLabel newName;
	 JLabel newPrice;
	 JLabel selectItem;
	 JLabel itemPrice;
	 JLabel changedPrice;
	 JLabel changedName;
	 JLabel changedCategory;
	 JLabel remove;
	
	 JButton addItem;
	 JButton removeItem;
	 JButton updateItem;
	
	 JTextField selectCategoryTF;
	 JTextField newNameTF;
	 JTextField newPriceTF;
	 JTextField selectItemTF;
	 JTextField itemPriceTF;
	 JTextField changedPriceTF;
	 JTextField changedNameTF;
	 JTextField changedCategoryTF;
	
	//Restaurant Table and Seat Visualization
	private RestaurantVisualizer restaurantVisualizer;
	
	//Menu Format
	private MenuFormatter menuFormatter;

	
	
	// data elements
	private String error = null;

	//Add Table
	private Integer newNumberOfSeats = -1;
	
	//Choose Table
	private Integer selectedTable = -1;
	private HashMap<Integer, Table> currentTables;
	
	//Edit Table
	private Integer editedNumberOfSeats = -1;
	
	//Order - Choose Table
	private Integer orderSelectedTable = -1;
	private HashMap<Integer, Table> orderCurrentTables;
	
	//Reservation - Choose Table
	private Integer reservSelectedTable = -1;
	private HashMap<Integer, Table> reservCurrentTables;
	
	//Reservation - Choose Time
	private Integer reservSelectedTime = -1;
	
	//Loyalty Card - Choose Table
	private Integer cardSelectedTable = -1;
	private HashMap<Integer, Table> cardCurrentTables;
	
	//Bill - Choose Table
	private Integer billSelectedTable = -1;
	private HashMap<Integer, Table> billCurrentTables;
	
	//Bill - Choose Seat
	private Integer billSelectedSeat = -1;
	private HashMap<String, Seat> billCurrentSeats;
	
	//Bill - Choose Table
	private Integer cancelSelectedTable = -1;
	private HashMap<Integer, Table> cancelCurrentTables;
	
	//Reservation overview
	private DefaultTableModel overviewDtm;
	private String overviewColumnNames[] = {"Table", "Time", "Reservation Number", "Party Number", "Name", "E-mail", "Phone Number"};
	private static final int HEIGHT_OVERVIEW_TABLE = 100;
	
	//Loyalty Card overview
	private DefaultTableModel cardOverviewDtm;
	private String cardOverviewColumnNames[] = {"Loyalty Card Number", "Name", "Phone", "Email", "Postal Code", "Number of Orders"};
	private static final int HEIGHT_CARD_OVERVIEW_TABLE = 50;
	
	//Restaurant Table Visualization
	private static final int WIDTH_RESTO_VISUALIZATION = 600;
	private static final int HEIGHT_RESTO_VISUALIZATION = 200;
	
	//Menu Format
	private static final int WIDTH_MENU_FORMAT = 200;
	private static final int HEIGHT_MENU_FORMAT = 400;
	String BUTTON_SIZE = "70";
	String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;
	
	
	/** Creates new form BtmsPage */
	public RestoAppPage() {
		initComponents();
		refreshData();
	}
	

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		
		//Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//Elements for top Labels
		restaurantLabel = new JLabel();
		menuLabel = new JLabel();
		
		//Elements for New Table
		addTableLabel = new JLabel();
		addTableButton = new JButton();
		addNumberOfSeatsLabel = new JLabel();
		addNumberOfSeatsList = new JComboBox<Integer>(new Integer[0]);
		addNumberOfSeatsList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				newNumberOfSeats = cb.getSelectedIndex();	
			}
		});
		addWidthLabel = new JLabel();
		addWidthField = new JTextField();
		addLengthLabel = new JLabel();
		addLengthField = new JTextField();
		addXPositionLabel = new JLabel();
		addXPositionField = new JTextField();
		addYPositionLabel = new JLabel();
		addYPositionField = new JTextField();
		addTableNumberLabel = new JLabel();
		addTableNumberField = new JTextField();
		
		//Elements for choosing a table
		chooseTableLabel = new JLabel();
		currentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		currentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedTable = cb.getSelectedIndex();	
			}
		});
		
		//Elements for Edit Table
		editTableLabel = new JLabel();
		editTableButton = new JButton();
		editTableNumberLabel = new JLabel();
		editTableNumberField = new JTextField();
		editNumberOfSeatsLabel = new JLabel();
		editNumberOfSeatsList = new JComboBox<Integer>(new Integer[0]);
		editNumberOfSeatsList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				editedNumberOfSeats = cb.getSelectedIndex();	
			}
		});
		
		//Elements for Move Table
		moveTableLabel = new JLabel();
		moveTableButton = new JButton();
		moveXPositionLabel = new JLabel();
		moveXPositionField = new JTextField();
		moveLeftButton = new JButton();
		moveRightButton = new JButton();
		moveYPositionLabel = new JLabel();
		moveYPositionField = new JTextField();
		moveUpButton = new JButton();
		moveDownButton = new JButton();
		
		//Elements for Remove Table
		removeTableLabel = new JLabel();	
		removeTableButton = new JButton();
		
		//Elements for Start/End Order
		start_EndOrderLabel = new JLabel();
		orderChooseTableLabel = new JLabel();
		orderCurrentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		orderCurrentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				orderSelectedTable = cb.getSelectedIndex();	
			}
		});
		orderTablesField = new JTextField();
		orderAddTableButton = new JButton();
		orderRemoveTableButton = new JButton();
		startOrderButton = new JButton();
		endOrderButton = new JButton();
		
		//Elements for reservation
		makeReservationLabel = new JLabel();
		makeReservationButton = new JButton();
		reservChooseTableLabel = new JLabel();
		reservCurrentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		reservCurrentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				reservSelectedTable = cb.getSelectedIndex();	
			}
		});
		reservTablesField = new JTextField();
		reservAddTableButton = new JButton();
		reservRemoveTableButton = new JButton();
		reservDateLabel = new JLabel();
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		reservDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		reservTimeLabel = new JLabel();
		reservTimeList = new JComboBox<String>();
		reservTimeList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				reservSelectedTime = cb.getSelectedIndex();	
			}
		});
		reservNumPartyLabel = new JLabel();
		reservNumPartyField = new JTextField();
		reservNameLabel = new JLabel();
		reservNameField = new JTextField();
		reservEmailLabel = new JLabel();
		reservEmailField = new JTextField();
		reservPhoneLabel = new JLabel();
		reservPhoneField = new JTextField();
		
		//Reservation Overview
		SqlDateModel overviewModel = new SqlDateModel();
		LocalDate now = LocalDate.now();
		overviewModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		overviewModel.setSelected(true);
		Properties pO = new Properties();
		pO.put("text.today", "Today");
		pO.put("text.month", "Month");
		pO.put("text.year", "Year");
		JDatePanelImpl overviewDatePanel = new JDatePanelImpl(overviewModel, pO);
		overviewDatePicker = new JDatePickerImpl(overviewDatePanel, new DateLabelFormatter());
		overviewDateLabel = new JLabel();
		
		overviewTable = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!c.getBackground().equals(getSelectionBackground())) {
					Object obj = getModel().getValueAt(row, column);
					c.setBackground(Color.WHITE);
				}
				return c;
			}
		};

		overviewScrollPane = new JScrollPane(overviewTable);
		this.add(overviewScrollPane);
		Dimension d = overviewTable.getPreferredSize();
		overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
		overviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Create Loyalty Card
		createCardLabel = new JLabel();
		createCardButton = new JButton();
		cardNameLabel = new JLabel();
		cardPhoneLabel = new JLabel();
		cardEmailLabel = new JLabel();
		cardPostalCodeLabel = new JLabel();
		cardNameField = new JTextField();
		cardPhoneField = new JTextField();
		cardEmailField = new JTextField();
		cardPostalCodeField = new JTextField();
		
		//Assign Loyalty Card
		assignCardLabel = new JLabel();
		assignCardButton = new JButton();
		cardChooseTableLabel = new JLabel();
		cardCurrentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		cardCurrentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				cardSelectedTable = cb.getSelectedIndex();	
			}
		});
		assignCardNumberLabel = new JLabel();
		assignCardNumberField = new JTextField();
		
		//LoyaltyCard Overview
		cardOverviewTable = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!c.getBackground().equals(getSelectionBackground())) {
					Object obj = getModel().getValueAt(row, column);
					c.setBackground(Color.WHITE);
				}
				return c;
			}
		};
		cardOverviewScrollPane = new JScrollPane(cardOverviewTable);
		this.add(cardOverviewScrollPane);
		Dimension cardD = cardOverviewTable.getPreferredSize();
		cardOverviewScrollPane.setPreferredSize(new Dimension(cardD.width, HEIGHT_CARD_OVERVIEW_TABLE));
		cardOverviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		//Bill and OrderItem
		billChooseTableLabel = new JLabel();
		billChooseSeatLabel = new JLabel();
		billAddToListButton = new JButton();
		billOrderItemButton = new JButton();
		billIssueButton = new JButton();
		billSeatsField = new JTextField();
		billCurrentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		billCurrentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				billSelectedTable = cb.getSelectedIndex();	
			}
		});
		billCurrentSeatList = new JComboBox<String>();
		billCurrentSeatList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				billSelectedSeat = cb.getSelectedIndex();	
			}
		});
		
		
		//Cancel Complete Order
		cancelOrder = new JLabel();
		cancelOrderButton = new JButton();
		tableCanceled = new JLabel();
		cancelCurrentTableNumberList = new JComboBox<Integer>(new Integer[0]);
		cancelCurrentTableNumberList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				cancelSelectedTable = cb.getSelectedIndex();	
			}
		});
		
		//Menu Buttons
		appetizerButton = new JButton();
		mainButton = new JButton();
		dessertButton = new JButton();
		alcoholicBeverageButton = new JButton();
		nonAlcoholicBeverageButton = new JButton();
		uploadMenuButton = new JButton();
		
		//Menu Items
		selectCategory = new JLabel();
		newName = new JLabel();
		newPrice = new JLabel();
		selectItem = new JLabel();
		itemPrice = new JLabel();
		changedPrice = new JLabel();
		changedName = new JLabel();
		changedCategory = new JLabel();
		remove = new JLabel();
		
		addItem = new JButton();
		removeItem = new JButton();
		updateItem = new JButton();
		
		selectCategoryTF = new JTextField();
		newNameTF = new JTextField();
		newPriceTF = new JTextField();
		selectItemTF = new JTextField();
		itemPriceTF = new JTextField();
		changedPriceTF = new JTextField();
		changedNameTF = new JTextField();
		changedCategoryTF = new JTextField();
		
		// elements for resto visualization
		restaurantVisualizer = new RestaurantVisualizer();
		restaurantVisualizer.setMinimumSize(new Dimension(WIDTH_RESTO_VISUALIZATION, HEIGHT_RESTO_VISUALIZATION));
		
		//elements for the menu formatter
		menuFormatter = new MenuFormatter();
		menuFormatter.setMinimumSize(new Dimension(WIDTH_MENU_FORMAT, HEIGHT_MENU_FORMAT));
		
		
	
		// global settings and listeners
		setPreferredSize(new Dimension(1900, 1000));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RestoApp");
		
		Font smallFont = new Font("Arial", Font.BOLD, 12);
		Font bigFont = new Font ("Arial", Font.BOLD, 28);
		
		restaurantLabel.setText("RESTAURANT");
		restaurantLabel.setFont(bigFont);
		restaurantLabel.setSize(1200, 20);
		menuLabel.setText("MENU");
		menuLabel.setFont(bigFont);
		menuLabel.setSize(700, 20);
		
		errorMessage.setText(error);
		
		addTableLabel.setText("ADD TABLE");
		addTableLabel.setFont(smallFont);
		addTableLabel.setSize(40, 20);
		addTableButton.setText("Add Table");
		addTableButton.setFont(smallFont);
		addTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addTableButtonActionPerformed(evt);
			}
		});
		addNumberOfSeatsLabel.setText("Number of seats :");
		addNumberOfSeatsLabel.setFont(smallFont);
		addWidthLabel.setText("Table width :");
		addWidthLabel.setFont(smallFont);
		addLengthLabel.setText("Table length :");
		addLengthLabel.setFont(smallFont);
		addXPositionLabel.setText("X coordinate of top left corner :");
		addXPositionLabel.setFont(smallFont);
		addYPositionLabel.setText("Y coordinate of top left corner :");
		addYPositionLabel.setFont(smallFont);
		addTableNumberLabel.setText("Table number :");
		addTableNumberLabel.setFont(smallFont);
		
		int indexAdd = 0;
		for (indexAdd = 0; indexAdd<11; indexAdd++) {
			addNumberOfSeatsList.addItem(indexAdd);
		};
		newNumberOfSeats = -1;
		addNumberOfSeatsList.setSelectedIndex(newNumberOfSeats);
		
		chooseTableLabel.setText("Choose Table :");
		chooseTableLabel.setFont(smallFont);
		int indexCurrentTable = 0;
		currentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			currentTables.put(indexCurrentTable, table);
			currentTableNumberList.addItem(table.getNumber());
			indexCurrentTable++;
		};
		selectedTable = -1;
		currentTableNumberList.setSelectedIndex(selectedTable);
		
		editTableLabel.setText("EDIT TABLE");
		editTableLabel.setFont(smallFont);
		editTableButton.setText("Edit Table");
		editTableButton.setFont(smallFont);
		editTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editTableButtonActionPerformed(evt);
			}
		});
		editTableNumberLabel.setText("Table number :");
		editTableNumberLabel.setFont(smallFont);
		editNumberOfSeatsLabel.setText("Number of seats :");
		editNumberOfSeatsLabel.setFont(smallFont);
		int indexEdit = 0;
		for (indexEdit = 0; indexEdit<11; indexEdit++) {
			editNumberOfSeatsList.addItem(indexEdit);
		};
		editedNumberOfSeats = -1;
		editNumberOfSeatsList.setSelectedIndex(editedNumberOfSeats);
		
		moveTableLabel.setText("MOVE TABLE");
		moveTableLabel.setFont(smallFont);
		moveTableButton.setText("Move Table");
		moveTableButton.setFont(smallFont);
		moveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveTableButtonActionPerformed(evt);
			}
		});
		moveXPositionLabel.setText("X variation :");
		moveXPositionLabel.setFont(smallFont);

		moveRightButton.setText("Right");
		moveRightButton.setFont(smallFont);
		moveRightButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveRightButtonActionPerformed(evt);
			}
		});
		moveYPositionLabel.setText("Y variation :");
		moveYPositionLabel.setFont(smallFont);
		moveYPositionField.setSize(40, 60);
		moveYPositionField.setText("0");
		moveYPositionField.setFont(smallFont);
		moveUpButton.setText("Up");
		moveUpButton.setFont(smallFont);
		moveUpButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveUpButtonActionPerformed(evt);
			}
		});
		moveXPositionField.setSize(40, 60);
		moveXPositionField.setText("0");
		moveXPositionField.setFont(smallFont);
		moveLeftButton.setText("Left");
		moveLeftButton.setFont(smallFont);
		moveLeftButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveLeftButtonActionPerformed(evt);
			}
		});
		moveDownButton.setText("Down");
		moveDownButton.setFont(smallFont);
		moveDownButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveDownButtonActionPerformed(evt);
			}
		});
		
		removeTableLabel.setText("REMOVE TABLE");
		removeTableLabel.setFont(smallFont);
		removeTableButton.setText("Remove Table");
		removeTableButton.setFont(smallFont);
		removeTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeTableButtonActionPerformed(evt);
			}
		});
		
		start_EndOrderLabel.setText("START / END ORDER");
		start_EndOrderLabel.setFont(smallFont);
		orderChooseTableLabel.setText("		Choose Table");
		orderChooseTableLabel.setFont(smallFont);
		orderTablesField.setFont(smallFont);
		int indexOrderCurrentTable = 0;
		orderCurrentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			orderCurrentTables.put(indexOrderCurrentTable, table);
			orderCurrentTableNumberList.addItem(table.getNumber());
			indexOrderCurrentTable++;
		};
		orderSelectedTable = -1;
		orderCurrentTableNumberList.setSelectedIndex(orderSelectedTable);
		orderAddTableButton.setText("Add Table to List");
		orderAddTableButton.setFont(smallFont);
		orderAddTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				orderAddTableButtonActionPerformed(evt);
			}
		});
		orderRemoveTableButton.setText("Remove Table from List");
		orderRemoveTableButton.setFont(smallFont);
		orderRemoveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				orderRemoveTableButtonActionPerformed(evt);
			}
		});
		startOrderButton.setText("Start Order");
		startOrderButton.setFont(smallFont);
		startOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startOrderButtonActionPerformed(evt);
			}
		});
		endOrderButton.setText("End Order");
		endOrderButton.setFont(smallFont);
		endOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				endOrderButtonActionPerformed(evt);
			}
		});
		
		makeReservationLabel.setText("MAKE RESERVATION");
		makeReservationLabel.setFont(smallFont);
		makeReservationButton.setText("Make Reservation");
		makeReservationButton.setFont(smallFont);
		makeReservationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				makeReservationButtonActionPerformed(evt);
			}
		});
		reservChooseTableLabel.setText("Choose Table: ");
		reservChooseTableLabel.setFont(smallFont);
		int indexReservCurrentTable = 0;
		reservCurrentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			reservCurrentTables.put(indexReservCurrentTable, table);
			reservCurrentTableNumberList.addItem(table.getNumber());
			indexReservCurrentTable++;
		};
		reservSelectedTable = -1;
		reservCurrentTableNumberList.setSelectedIndex(reservSelectedTable);
		reservTablesField.setFont(smallFont);
		reservAddTableButton.setText("Add Table to Reservation");
		reservAddTableButton.setFont(smallFont);
		reservAddTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reservAddTableButtonActionPerformed(evt);
			}
		});
		reservRemoveTableButton.setText("Remove Table from Reservation");
		reservRemoveTableButton.setFont(smallFont);
		reservRemoveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reservRemoveTableButtonActionPerformed(evt);
			}
		});
		reservDateLabel.setText("Date: ");
		reservDateLabel.setFont(smallFont);
		reservTimeLabel.setText("Time: ");
		reservTimeLabel.setFont(smallFont);
			reservTimeList.addItem("0:00");	reservTimeList.addItem("0:15");
			reservTimeList.addItem("0:30");	reservTimeList.addItem("0:45");
			reservTimeList.addItem("1:00");	reservTimeList.addItem("1:15");
			reservTimeList.addItem("1:30"); reservTimeList.addItem("1:45");
			reservTimeList.addItem("2:00");	reservTimeList.addItem("2:15");
			reservTimeList.addItem("2:30");	reservTimeList.addItem("2:45");
			reservTimeList.addItem("3:00");	reservTimeList.addItem("3:15");
			reservTimeList.addItem("3:30");	reservTimeList.addItem("3:45");
			reservTimeList.addItem("4:00");	reservTimeList.addItem("4:15");
			reservTimeList.addItem("4:30");	reservTimeList.addItem("4:45");
			reservTimeList.addItem("5:00");	reservTimeList.addItem("5:15");
			reservTimeList.addItem("5:30");	reservTimeList.addItem("5:45");
			reservTimeList.addItem("6:00");	reservTimeList.addItem("6:15");
			reservTimeList.addItem("6:30");	reservTimeList.addItem("6:45");
			reservTimeList.addItem("7:00");	reservTimeList.addItem("7:15");
			reservTimeList.addItem("7:30");	reservTimeList.addItem("7:45");
			reservTimeList.addItem("8:00");	reservTimeList.addItem("8:15");
			reservTimeList.addItem("8:30");	reservTimeList.addItem("8:45");
			reservTimeList.addItem("9:00");	reservTimeList.addItem("9:15");
			reservTimeList.addItem("9:30");	reservTimeList.addItem("9:45");
			reservTimeList.addItem("10:00");	reservTimeList.addItem("10:15");
			reservTimeList.addItem("10:30");	reservTimeList.addItem("10:45");
			reservTimeList.addItem("11:00");	reservTimeList.addItem("11:15");
			reservTimeList.addItem("11:30");	reservTimeList.addItem("11:45");
			reservTimeList.addItem("12:00");	reservTimeList.addItem("12:15");
			reservTimeList.addItem("12:30");	reservTimeList.addItem("12:45");
			reservTimeList.addItem("13:00");	reservTimeList.addItem("13:15");
			reservTimeList.addItem("13:30");	reservTimeList.addItem("13:45");
			reservTimeList.addItem("14:00");	reservTimeList.addItem("14:15");
			reservTimeList.addItem("14:30");	reservTimeList.addItem("14:45");
			reservTimeList.addItem("15:00");	reservTimeList.addItem("15:15");
			reservTimeList.addItem("15:30");	reservTimeList.addItem("15:45");
			reservTimeList.addItem("16:00");	reservTimeList.addItem("16:15");
			reservTimeList.addItem("16:30");	reservTimeList.addItem("16:45");
			reservTimeList.addItem("17:00");	reservTimeList.addItem("17:15");
			reservTimeList.addItem("17:30");	reservTimeList.addItem("17:45");
			reservTimeList.addItem("18:00");	reservTimeList.addItem("18:15");
			reservTimeList.addItem("18:30");	reservTimeList.addItem("18:45");
			reservTimeList.addItem("19:00");	reservTimeList.addItem("19:15");
			reservTimeList.addItem("19:30");	reservTimeList.addItem("19:45");
			reservTimeList.addItem("20:00");	reservTimeList.addItem("20:15");
			reservTimeList.addItem("20:30");	reservTimeList.addItem("20:45");
			reservTimeList.addItem("21:00");	reservTimeList.addItem("21:15");
			reservTimeList.addItem("21:30");	reservTimeList.addItem("21:45");
			reservTimeList.addItem("22:00");	reservTimeList.addItem("22:15");
			reservTimeList.addItem("22:30");	reservTimeList.addItem("22:45");
			reservTimeList.addItem("23:00");	reservTimeList.addItem("23:15");
			reservTimeList.addItem("23:30");	reservTimeList.addItem("23:45");

		reservSelectedTime = -1;
		reservTimeList.setSelectedIndex(reservSelectedTime);
		reservNumPartyLabel.setText("Party Number: ");
		reservNumPartyLabel.setFont(smallFont);
		reservNumPartyField.setFont(smallFont);
		reservNameLabel.setText("Name: ");
		reservNameLabel.setFont(smallFont);
		reservNameField.setFont(smallFont);
		reservEmailLabel.setText("E-mail: ");
		reservEmailLabel.setFont(smallFont);
		reservEmailField.setFont(smallFont);
		reservPhoneLabel.setText("Phone: ");
		reservPhoneLabel.setFont(smallFont);
		reservPhoneField.setFont(smallFont);
		
		overviewDateLabel.setText("Date for Overview: ");
		overviewDateLabel.setFont(smallFont);
		overviewDatePicker.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				refreshData();
			}
		});
		
		createCardLabel.setText("NEW LOYALTY CARD");
		createCardLabel.setFont(smallFont);
		createCardButton.setText("Create Card");
		createCardButton.setFont(smallFont);
		createCardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createCardButtonActionPerformed(evt);
			}
		});
		cardNameLabel.setText("Name:");
		cardNameLabel.setFont(smallFont);
		cardPhoneLabel.setText("Phone:");
		cardPhoneLabel.setFont(smallFont);
		cardEmailLabel.setText("E-mail:");
		cardEmailLabel.setFont(smallFont);
		cardPostalCodeLabel.setText("PostalCode");
		cardPostalCodeLabel.setFont(smallFont);
		cardNameField.setFont(smallFont);
		cardPhoneField.setFont(smallFont);
		cardEmailField.setFont(smallFont);
		cardPostalCodeField.setFont(smallFont);
		
		assignCardLabel.setText("ASSIGN LOYALTY CARD");
		assignCardLabel.setFont(smallFont);
		assignCardButton.setText("Assign Card");
		assignCardButton.setFont(smallFont);
		assignCardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				assignCardButtonActionPerformed(evt);
			}
		});
		assignCardNumberLabel.setText("Card Number:");
		assignCardNumberLabel.setFont(smallFont);
		assignCardNumberField.setFont(smallFont);
		cardChooseTableLabel.setText("Choose Table:");
		cardChooseTableLabel.setFont(smallFont);
		int indexCardCurrentTable = 0;
		cardCurrentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			cardCurrentTables.put(indexCardCurrentTable, table);
			cardCurrentTableNumberList.addItem(table.getNumber());
			indexCardCurrentTable++;
		};
		cardSelectedTable = -1;
		cardCurrentTableNumberList.setSelectedIndex(cardSelectedTable);
		
		
		billChooseTableLabel.setText("Choose Table:");
		billChooseTableLabel.setFont(smallFont);
		billChooseSeatLabel.setText("Choose Seat:");
		billChooseSeatLabel.setFont(smallFont);
		billAddToListButton.setText("Add to Seat List");
		billAddToListButton.setFont(smallFont);
		billAddToListButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				billAddToListButtonActionPerformed(evt);
			}
		});
		billOrderItemButton.setText("Add to Order");
		billOrderItemButton.setFont(smallFont);
		billOrderItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				billOrderItemButtonActionPerformed(evt);
			}
		});
		billIssueButton.setText("Issue Bill");
		billIssueButton.setFont(smallFont);
		billIssueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				billIssueButtonActionPerformed(evt);
			}
		});
		billSeatsField.setFont(smallFont);

		int indexBillCurrentTable = 0;
		billCurrentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			billCurrentTables.put(indexBillCurrentTable, table);
			billCurrentTableNumberList.addItem(table.getNumber());
			indexBillCurrentTable++;
		};
		billSelectedTable = -1;
		billCurrentTableNumberList.setSelectedIndex(billSelectedTable);
		
		//CurrentSeat combo box to do
		
		
		cancelOrder.setText("Order Cancellation");
		cancelOrder.setFont(smallFont);
		cancelOrderButton.setText("Cancel Order");
		cancelOrderButton.setFont(smallFont);
		cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelOrderButtonActionPerformed(evt);
			}
		});
		tableCanceled.setText("Table:");
		tableCanceled.setFont(smallFont);
		int indexCancelCurrentTable = 0;
		cancelCurrentTables = new HashMap<Integer, Table>();
		for (Table table : RestoAppController.getCurrentTables()) {
			cancelCurrentTables.put(indexCancelCurrentTable, table);
			cancelCurrentTableNumberList.addItem(table.getNumber());
			indexCancelCurrentTable++;
		};
		cancelSelectedTable = -1;
		cancelCurrentTableNumberList.setSelectedIndex(cancelSelectedTable);
		
		appetizerButton.setText("Appetizer");
		appetizerButton.setFont(smallFont);
		appetizerButton.setSize(40,40 );
		appetizerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				appetizerButtonActionPerformed(evt);
			}
		});
		mainButton.setText("Main");
		mainButton.setFont(smallFont);
		mainButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mainButtonActionPerformed(evt);
			}
		});
		dessertButton.setText("Dessert");
		dessertButton.setFont(smallFont);
		dessertButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dessertButtonActionPerformed(evt);
			}
		});
		alcoholicBeverageButton.setText("AlcoholicBeverage");
		alcoholicBeverageButton.setFont(smallFont);
		alcoholicBeverageButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				alcoholicBeverageButtonActionPerformed(evt);
			}
		});
		nonAlcoholicBeverageButton.setText("NonAlcoholicBeverage");
		nonAlcoholicBeverageButton.setFont(smallFont);
		nonAlcoholicBeverageButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nonAlcoholicBeverageButtonActionPerformed(evt);
			}
		});
		uploadMenuButton.setText("Display Menu");
		uploadMenuButton.setFont(smallFont);
		uploadMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				uploadMenuButtonActionPerformed(evt);
			}
		});
		
		//ROGER UI ELEMENTS		
		selectCategory.setText("Selected Category:");
		selectCategory.setFont(smallFont);
		newName.setText("New item name:");
		newName.setFont(smallFont);
		newPrice.setText("New item price:");
		newPrice.setFont(smallFont);
		selectItem.setText("Selected item:");
		selectItem.setFont(smallFont);
		itemPrice.setText("Price:");
		itemPrice.setFont(smallFont);
		changedPrice.setText("Change price to:");
		changedPrice.setFont(smallFont);
		changedName.setText("Change name to:");
		changedName.setFont(smallFont);
		changedCategory.setText("Change category to:");
		changedCategory.setFont(smallFont);
		remove.setText("Remove item:");
		remove.setFont(smallFont);
		
		selectCategoryTF.setFont(smallFont);
		newNameTF.setFont(smallFont);
		newPriceTF.setFont(smallFont);
		selectItemTF.setFont(smallFont);
		itemPriceTF.setFont(smallFont);
		changedPriceTF.setFont(smallFont);
		changedNameTF.setFont(smallFont);
		changedCategoryTF.setFont(smallFont);
		
		selectCategoryTF.setEditable(false);
		selectItemTF.setEditable(false);
		itemPriceTF.setEditable(false);
		
		addItem.setText("Add new item");
		addItem.setFont(smallFont);
		addItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addItemButtonActionPerformed(evt);
			}
		});
		removeItem.setText("Remove Item");
		removeItem.setFont(smallFont);
		removeItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeItemButtonActionPerformed(evt);
			}
		});
		updateItem.setText("Update");
		updateItem.setFont(smallFont);
		updateItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateButtonActionPerformed(evt);
			}
		});
		
		
		// horizontal line elements
		JSeparator horizontalLine1 = new JSeparator();
		JSeparator horizontalLine2 = new JSeparator();
		JSeparator horizontalLine3 = new JSeparator();
		JSeparator horizontalLine4 = new JSeparator();
		JSeparator horizontalLine5 = new JSeparator();
		JSeparator horizontalLine6 = new JSeparator();
		JSeparator horizontalLine7 = new JSeparator();
		JSeparator horizontalLine8 = new JSeparator();
		JSeparator horizontalLine9 = new JSeparator();
		JSeparator verticalLine = new JSeparator(SwingConstants.VERTICAL);
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(restaurantLabel)
						.addComponent(errorMessage)
						.addComponent(restaurantVisualizer)
						.addComponent(horizontalLine1)
						.addComponent(horizontalLine2)
						.addComponent(horizontalLine3)
						.addComponent(horizontalLine7)
						.addComponent(horizontalLine8)
						.addComponent(cardOverviewScrollPane)
						.addComponent(horizontalLine4)
						.addComponent(horizontalLine5)
						.addComponent(overviewScrollPane)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(addTableLabel)
										.addComponent(addTableNumberLabel)
										.addComponent(addNumberOfSeatsLabel)
										.addComponent(chooseTableLabel)
										.addComponent(editTableLabel)
										.addComponent(moveTableLabel)
										.addComponent(removeTableLabel)
										.addComponent(start_EndOrderLabel)
										.addComponent(createCardLabel)
										.addComponent(assignCardLabel)
										.addComponent(makeReservationLabel)
										.addComponent(reservDateLabel)
										.addComponent(reservNameLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(addTableButton)
										.addComponent(addTableNumberField)
										.addComponent(addNumberOfSeatsList)
										.addComponent(currentTableNumberList)
										.addComponent(editTableButton)
										.addComponent(moveTableButton)
										.addComponent(removeTableButton)
										.addComponent(orderChooseTableLabel)
										.addComponent(createCardButton)
										.addComponent(assignCardButton)
										.addComponent(makeReservationButton)
										.addComponent(reservDatePicker)
										.addComponent(reservNameField)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(addWidthLabel)
										.addComponent(addLengthLabel)
										.addComponent(editTableNumberLabel)
										.addGroup(layout.createSequentialGroup()
												.addComponent(moveXPositionLabel)
												.addComponent(moveXPositionField)
										)
										.addComponent(orderCurrentTableNumberList)
										.addComponent(orderTablesField)
										.addComponent(cardNameLabel)
										.addComponent(cardEmailLabel)
										.addComponent(cardChooseTableLabel)
										.addGroup(layout.createSequentialGroup()
												.addComponent(reservChooseTableLabel)
												.addComponent(reservCurrentTableNumberList)
										)
										.addComponent(reservTimeLabel)
										.addComponent(reservEmailLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(addWidthField)
										.addComponent(addLengthField)
										.addComponent(editTableNumberField)
										.addGroup(layout.createSequentialGroup()
												.addComponent(moveLeftButton)
												.addComponent(moveRightButton)
										)
										.addComponent(orderAddTableButton)
										.addComponent(startOrderButton)
										.addComponent(cardNameField)
										.addComponent(cardEmailField)
										.addComponent(cardCurrentTableNumberList)
										.addComponent(reservTablesField)
										.addComponent(reservTimeList)
										.addComponent(reservEmailField)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(addXPositionLabel)
										.addComponent(addYPositionLabel)
										.addComponent(editNumberOfSeatsLabel)
										.addGroup(layout.createSequentialGroup()
												.addComponent(moveYPositionLabel)
												.addComponent(moveYPositionField)
										)
										.addComponent(orderRemoveTableButton)
										.addComponent(endOrderButton)
										.addComponent(cardPhoneLabel)
										.addComponent(cardPostalCodeLabel)
										.addComponent(assignCardNumberLabel)
										.addComponent(reservAddTableButton)
										.addComponent(reservNumPartyLabel)
										.addComponent(reservPhoneLabel)
										.addComponent(overviewDateLabel)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(addXPositionField)
										.addComponent(addYPositionField)
										.addComponent(editNumberOfSeatsList)
										.addGroup(layout.createSequentialGroup()
												.addComponent(moveUpButton)
												.addComponent(moveDownButton)
										)
										.addComponent(cardPhoneField)
										.addComponent(cardPostalCodeField)
										.addComponent(assignCardNumberField)
										.addComponent(reservRemoveTableButton)
										.addComponent(reservNumPartyField)
										.addComponent(reservPhoneField)
										.addComponent(overviewDatePicker)
								)
						)	
				)
				
				.addComponent(verticalLine)
				
				.addGroup(layout.createParallelGroup()
						.addComponent(menuLabel)
						.addComponent(horizontalLine6)
						.addComponent(horizontalLine9)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(appetizerButton)
										.addComponent(alcoholicBeverageButton)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(uploadMenuButton)
										.addComponent(mainButton)
										.addComponent(nonAlcoholicBeverageButton)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(dessertButton)
								)
						)
						.addComponent(menuFormatter)	
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(billChooseTableLabel)
										.addComponent(billAddToListButton)
										.addComponent(cancelOrder)
										.addComponent(selectCategory)
										.addComponent(newName)
										.addComponent(newPrice)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(billCurrentTableNumberList)
										.addComponent(billSeatsField)
										.addComponent(tableCanceled)
										.addComponent(selectCategoryTF)
										.addComponent(newNameTF)
										.addComponent(newPriceTF)
										.addComponent(addItem)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(billChooseSeatLabel)
										.addComponent(billOrderItemButton)
										.addComponent(cancelCurrentTableNumberList)
										.addComponent(selectItem)
										.addComponent(itemPrice)
										.addComponent(changedPrice)
										.addComponent(changedName)
										.addComponent(changedCategory)
										.addComponent(remove)
								)
								.addGroup(layout.createParallelGroup()
										.addComponent(billCurrentSeatList)
										.addComponent(billIssueButton)
										.addComponent(cancelOrderButton)
										.addComponent(selectItemTF)
										.addComponent(itemPriceTF)
										.addComponent(changedPriceTF)
										.addComponent(changedNameTF)
										.addComponent(changedCategoryTF)
										.addComponent(updateItem)
										.addComponent(removeItem)
								)
						)
				)
		);
		
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] 
				{restaurantLabel, menuLabel});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] 
				{addTableLabel, addTableNumberLabel, addNumberOfSeatsLabel, chooseTableLabel, editTableLabel,
				 moveTableLabel, removeTableLabel, addWidthLabel, addLengthLabel, editTableNumberLabel,
				 addXPositionLabel, addYPositionLabel, editNumberOfSeatsLabel, moveYPositionLabel, moveXPositionLabel,
				 addTableButton, addTableNumberField, addNumberOfSeatsList, currentTableNumberList, editTableButton,
				 moveTableButton, removeTableButton, addWidthField, addLengthField, editTableNumberField,
				 moveXPositionField, addXPositionField, addYPositionField, editNumberOfSeatsList, moveYPositionField,
				 moveLeftButton, moveRightButton, moveUpButton, moveDownButton, start_EndOrderLabel, orderChooseTableLabel,
				 orderCurrentTableNumberList, orderTablesField, orderAddTableButton, orderRemoveTableButton, startOrderButton,
				 endOrderButton, makeReservationLabel, makeReservationButton, reservChooseTableLabel, reservCurrentTableNumberList,
				 reservTablesField, reservAddTableButton, reservRemoveTableButton, reservDateLabel, reservDatePicker,
				 reservTimeLabel, reservTimeList, reservNumPartyLabel, reservNumPartyField, reservNameLabel, reservNameField,
				 reservEmailLabel, reservEmailField, reservPhoneLabel, reservPhoneField, overviewDatePicker, overviewDateLabel, selectCategoryTF,
				 selectCategory, newName, newPrice, newNameTF, newPriceTF, selectItem, itemPrice, changedPrice, changedName, changedCategory, remove,
				 selectItemTF, itemPriceTF, changedPriceTF, changedNameTF, changedCategoryTF, removeItem, 
				createCardLabel, createCardButton, cardNameLabel, cardPhoneLabel, cardEmailLabel, cardPostalCodeLabel, cardNameField,
				cardPhoneField, cardEmailField, cardPostalCodeField, assignCardLabel, assignCardButton, cardChooseTableLabel, 
				cardCurrentTableNumberList, assignCardNumberLabel, assignCardNumberField,
				billChooseTableLabel, billChooseSeatLabel, billAddToListButton, billOrderItemButton, billIssueButton, 
				billSeatsField, billCurrentTableNumberList, billCurrentSeatList,
				cancelOrder, tableCanceled, cancelCurrentTableNumberList, cancelOrderButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{addXPositionField, addYPositionField, 
				 addXPositionLabel, addYPositionLabel, editNumberOfSeatsLabel, editNumberOfSeatsList,
				 addTableButton, addTableNumberField, addNumberOfSeatsList, currentTableNumberList, editTableButton,
				 moveTableButton, removeTableButton, addWidthField, addLengthField, editTableNumberField, 
				 orderChooseTableLabel, orderTablesField, 
				 orderAddTableButton, orderRemoveTableButton, startOrderButton, orderCurrentTableNumberList, 
				 endOrderButton, makeReservationButton, reservTablesField, reservAddTableButton, reservRemoveTableButton,
				 reservDatePicker, reservTimeList, reservNumPartyLabel, reservNumPartyField, reservNameField,
				 reservEmailField, reservPhoneLabel, reservPhoneField, overviewDatePicker, overviewDateLabel,
				 createCardButton, assignCardButton, cardNameLabel, cardEmailLabel, cardChooseTableLabel,
				 cardNameField, cardEmailField, cardCurrentTableNumberList, cardPhoneLabel, cardPostalCodeLabel,
				 assignCardNumberLabel, cardPhoneField, cardPostalCodeField, assignCardNumberField});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{addTableLabel, addTableNumberLabel,  addNumberOfSeatsLabel, editTableLabel, moveTableLabel, removeTableLabel,
				 start_EndOrderLabel, makeReservationLabel, reservDateLabel, reservNameLabel, chooseTableLabel,
				 createCardLabel, assignCardLabel });
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{addWidthLabel, addLengthLabel, editTableNumberLabel, reservTimeLabel, reservEmailLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{moveXPositionField, moveYPositionField, reservCurrentTableNumberList,
				 moveLeftButton, moveRightButton, moveUpButton, moveDownButton});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] 
				{uploadMenuButton, appetizerButton, mainButton, dessertButton, 
				 alcoholicBeverageButton, nonAlcoholicBeverageButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{uploadMenuButton, appetizerButton, mainButton, dessertButton, 
				 alcoholicBeverageButton, nonAlcoholicBeverageButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] 
				{selectCategoryTF, selectCategory, newName, newPrice, newNameTF, newPriceTF, selectItem, 
				itemPrice, changedPrice, changedName, changedCategory, remove, selectItemTF, itemPriceTF, changedPriceTF, changedNameTF, 
				changedCategoryTF, removeItem, updateItem, addItem,
				billChooseTableLabel, billChooseSeatLabel, billAddToListButton, billOrderItemButton, billIssueButton, 
				billSeatsField, billCurrentTableNumberList, billCurrentSeatList,
				cancelOrder, tableCanceled, cancelCurrentTableNumberList, cancelOrderButton
				 });
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(restaurantLabel)
						.addComponent(errorMessage)
						.addComponent(restaurantVisualizer)
						.addComponent(horizontalLine1)
						.addGroup(layout.createParallelGroup()
								.addComponent(addTableLabel)
								.addComponent(addTableButton)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(addTableNumberLabel)
								.addComponent(addTableNumberField)
								.addComponent(addWidthLabel)
								.addComponent(addWidthField)
								.addComponent(addXPositionLabel)
								.addComponent(addXPositionField)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(addNumberOfSeatsLabel)
								.addComponent(addNumberOfSeatsList)
								.addComponent(addLengthLabel)
								.addComponent(addLengthField)
								.addComponent(addYPositionLabel)
								.addComponent(addYPositionField)
						)
						.addComponent(horizontalLine2)
						.addGroup(layout.createParallelGroup()
								.addComponent(chooseTableLabel)
								.addComponent(currentTableNumberList)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(editTableLabel)
								.addComponent(editTableButton)
								.addComponent(editTableNumberLabel)
								.addComponent(editTableNumberField)
								.addComponent(editNumberOfSeatsLabel)
								.addComponent(editNumberOfSeatsList)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(moveTableLabel)
								.addComponent(moveTableButton)
								.addComponent(moveXPositionLabel)
								.addComponent(moveXPositionField)
								.addComponent(moveLeftButton)
								.addComponent(moveRightButton)
								.addComponent(moveYPositionLabel)
								.addComponent(moveYPositionField)
								.addComponent(moveUpButton)
								.addComponent(moveDownButton)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(removeTableLabel)
								.addComponent(removeTableButton)
						)
						.addComponent(horizontalLine3)
						.addGroup(layout.createParallelGroup()
								.addComponent(start_EndOrderLabel)
								.addComponent(orderChooseTableLabel)
								.addComponent(orderCurrentTableNumberList)
								.addComponent(orderAddTableButton)
								.addComponent(orderRemoveTableButton)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(orderTablesField)
								.addComponent(startOrderButton)
								.addComponent(endOrderButton)
						)
						.addComponent(horizontalLine7)
						.addGroup(layout.createParallelGroup()
								.addComponent(createCardLabel)	
								.addComponent(createCardButton)	
								.addComponent(cardNameLabel)	
								.addComponent(cardNameField)	
								.addComponent(cardPhoneLabel)	
								.addComponent(cardPhoneField)	
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(cardEmailLabel)	
								.addComponent(cardEmailField)	
								.addComponent(cardPostalCodeLabel)	
								.addComponent(cardPostalCodeField)	
							)
						.addComponent(horizontalLine8)
						.addGroup(layout.createParallelGroup()
								.addComponent(assignCardLabel)	
								.addComponent(assignCardButton)	
								.addComponent(cardChooseTableLabel)	
								.addComponent(cardCurrentTableNumberList)	
								.addComponent(assignCardNumberLabel)	
								.addComponent(assignCardNumberField)
						)
						.addComponent(cardOverviewScrollPane)
						.addComponent(horizontalLine4)
						.addGroup(layout.createParallelGroup()
								.addComponent(makeReservationLabel)
								.addComponent(makeReservationButton)
								.addComponent(reservChooseTableLabel)
								.addComponent(reservCurrentTableNumberList)
								.addComponent(reservTablesField)
								.addComponent(reservAddTableButton)
								.addComponent(reservRemoveTableButton)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(reservDateLabel)
								.addComponent(reservDatePicker)
								.addComponent(reservTimeLabel)
								.addComponent(reservTimeList)
								.addComponent(reservNumPartyLabel)
								.addComponent(reservNumPartyField)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(reservNameLabel)
								.addComponent(reservNameField)
								.addComponent(reservEmailLabel)
								.addComponent(reservEmailField)
								.addComponent(reservPhoneLabel)
								.addComponent(reservPhoneField)
						)
						.addComponent(horizontalLine5)
						.addGroup(layout.createParallelGroup()
								.addComponent(overviewDateLabel)
								.addComponent(overviewDatePicker)
						)
						.addComponent(overviewScrollPane)
				)
				
				.addComponent(verticalLine)
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(menuLabel)
						.addComponent(uploadMenuButton)			
						.addGroup(layout.createParallelGroup()
								.addComponent(appetizerButton)
								.addComponent(mainButton)
								.addComponent(dessertButton)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(alcoholicBeverageButton)
								.addComponent(nonAlcoholicBeverageButton)
						)
						.addComponent(menuFormatter)
						.addGroup(layout.createParallelGroup()
								.addComponent(billChooseTableLabel)
								.addComponent(billCurrentTableNumberList)
								.addComponent(billChooseSeatLabel)
								.addComponent(billCurrentSeatList)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(billAddToListButton)
								.addComponent(billSeatsField)
								.addComponent(billOrderItemButton)
								.addComponent(billIssueButton)
						)
						.addComponent(horizontalLine9)
						.addGroup(layout.createParallelGroup()
								.addComponent(cancelOrder)
								.addComponent(tableCanceled)
								.addComponent(cancelCurrentTableNumberList)
								.addComponent(cancelOrderButton)
						)
						.addComponent(horizontalLine6)
						.addGroup(layout.createParallelGroup()
								.addComponent(selectCategory)
								.addComponent(selectCategoryTF)
								.addComponent(selectItem)
								.addComponent(selectItemTF)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(newName)
								.addComponent(newNameTF)
								.addComponent(itemPrice)
								.addComponent(itemPriceTF)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(newPrice)
								.addComponent(newPriceTF)
								.addComponent(changedPrice)
								.addComponent(changedPriceTF)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(addItem)
								.addComponent(changedName)
								.addComponent(changedNameTF)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(changedCategory)
								.addComponent(changedCategoryTF)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(updateItem)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(remove)
								.addComponent(removeItem)
								)
				)	
		);
		
		pack();	
		appetizerButton.setVisible(false);
		mainButton.setVisible(false);
		dessertButton.setVisible(false);
		alcoholicBeverageButton.setVisible(false);
		nonAlcoholicBeverageButton.setVisible(false);
	}
	
	
	
	@SuppressWarnings("deprecation")
	private void refreshData() {
		
		//error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			
			addTableNumberField.setText("");
			addWidthField.setText("");
			addLengthField.setText("");
			addXPositionField.setText("");
			addYPositionField.setText("");
			editTableNumberField.setText("");
			moveXPositionField.setText("0");
			moveYPositionField.setText("0");
			orderTablesField.setText("");
			cardNameField.setText("");
			cardEmailField.setText("");
			cardPhoneField.setText("");
			cardPostalCodeField.setText("");
			assignCardNumberField.setText("");
			reservTablesField.setText("");
			reservNumPartyField.setText("");
			reservNameField.setText("");
			reservEmailField.setText("");
			reservPhoneField.setText("");


			// Choose Table
			currentTables = new HashMap<Integer, Table>();
			currentTableNumberList.removeAllItems();
			Integer index = 0;
			for (Table table : RestoAppController.getCurrentTables()) {
				currentTables.put(index, table);
				currentTableNumberList.addItem(table.getNumber());
				index++;
			};
			selectedTable = -1;
			currentTableNumberList.setSelectedIndex(selectedTable);
			
			// Seat number for adding
			addNumberOfSeatsList.removeAllItems();
			index = 0;
			for (index = 0; index<11; index++) {
				addNumberOfSeatsList.addItem(index);
			};
			newNumberOfSeats = -1;
			addNumberOfSeatsList.setSelectedIndex(newNumberOfSeats);
			
			// Seat number for edit
			editNumberOfSeatsList.removeAllItems();
			index = 0;
			for (index = 0; index<11; index++) {
				editNumberOfSeatsList.addItem(index);
			};
			editedNumberOfSeats = -1;
			editNumberOfSeatsList.setSelectedIndex(editedNumberOfSeats);
			
			// Choose Table for order
			orderCurrentTables = new HashMap<Integer, Table>();
			orderCurrentTableNumberList.removeAllItems();
			index = 0;
			for (Table table : RestoAppController.getCurrentTables()) {
				orderCurrentTables.put(index, table);
				orderCurrentTableNumberList.addItem(table.getNumber());
				index++;
			};
			orderSelectedTable = -1;
			orderCurrentTableNumberList.setSelectedIndex(orderSelectedTable);
			
			// Choose Table for Loyalty Card
			cardCurrentTables = new HashMap<Integer, Table>();
			cardCurrentTableNumberList.removeAllItems();
			index = 0;
			for (Table table : RestoAppController.getCurrentTables()) {
				cardCurrentTables.put(index, table);
				cardCurrentTableNumberList.addItem(table.getNumber());
				index++;
			};
			cardSelectedTable = -1;
			cardCurrentTableNumberList.setSelectedIndex(cardSelectedTable);
			
			// Choose Table for reservation
			reservCurrentTables = new HashMap<Integer, Table>();
			reservCurrentTableNumberList.removeAllItems();
			index = 0;
			for (Table table : RestoAppController.getCurrentTables()) {
				reservCurrentTables.put(index, table);
				reservCurrentTableNumberList.addItem(table.getNumber());
				index++;
			};
			reservSelectedTable = -1;
			reservCurrentTableNumberList.setSelectedIndex(reservSelectedTable);
			
			
			// daily overview
			overviewDtm = new DefaultTableModel(0, 0);
			overviewDtm.setColumnIdentifiers(overviewColumnNames);
			overviewTable.setModel(overviewDtm);
			if (overviewDatePicker.getModel().getValue() != null) {

				for (Reservation reservation : RestoAppController.getReservationsForDate((Date) overviewDatePicker.getModel().getValue())) {
					Time reservTime = reservation.getTime();
					String time = "";
					time = time + reservTime.getHours() + ":" + reservTime.getMinutes();
					
					
					for (Table table : reservation.getTables()) {
						Object[] obj = {table.getNumber(), time, reservation.getReservationNumber(), reservation.getNumberInParty(),
							reservation.getContactName(), reservation.getContactEmailAddress(), reservation.getContactPhoneNumber()};
						overviewDtm.addRow(obj);
					}
				}
			}
			Dimension d = overviewTable.getPreferredSize();
			overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
			
			
			// Loyalty Card Overview
			cardOverviewDtm = new DefaultTableModel(0, 0);
			cardOverviewDtm.setColumnIdentifiers(cardOverviewColumnNames);
			cardOverviewTable.setModel(cardOverviewDtm);

				for (LoyaltyCard loyaltyCard : RestoAppController.getLoyaltyCards()) {
					
						Object[] obj = {loyaltyCard.getLoyaltyCardNumber(), loyaltyCard.getContactName(), 
								loyaltyCard.getContactPhoneNumber(), loyaltyCard.getContactEmailAddress(), 
								loyaltyCard.getContactPostalCode(),  loyaltyCard.numberOfOrders()};
						cardOverviewDtm.addRow(obj);
				}

			Dimension cardD = overviewTable.getPreferredSize();
			overviewScrollPane.setPreferredSize(new Dimension(cardD.width, HEIGHT_CARD_OVERVIEW_TABLE));
			
		}		
		
		pack();		
	}

	
	
	private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (addTableNumberField.getText() == "") {
			error = "A table number must be entered.";
		}
		if (addXPositionField.getText() == "") {
			error = error + "An x coordinate for the table must be entered.";
		}
		if (addYPositionField.getText() == "") {
			error = error + "A y coordinate for the table must be entered.";
		}
		if (addWidthField.getText() == "") {
			error = error + "A table width must be entered.";
		}
		if (addLengthField.getText() == "") {
			error = error + "A table length must be entered.";
		}
		if (addNumberOfSeatsList.getSelectedIndex() == -1) {
			error = error + "A number of seats for the table must be selected.";
		}
		error = error.trim();
		
		//call the controller
		if (error.length() == 0) {
			try {
					RestoAppController.createTable(Integer.parseInt(addTableNumberField.getText()),
							Integer.parseInt(addXPositionField.getText()), 
							Integer.parseInt(addYPositionField.getText()), Integer.parseInt(addWidthField.getText()),
							Integer.parseInt(addLengthField.getText()), newNumberOfSeats);
				}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
				
		// update visuals
		if (error.length() == 0) {
			restaurantVisualizer.addNewTable(currentTables.get(selectedTable));
		}
		refreshData();
	}
	
	private void editTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (selectedTable < 0)
			error = "A table must be selected .";
		if (editTableNumberField.getText().isEmpty() )
			error = "A table number must be selected.";
		if(editedNumberOfSeats < 0 && selectedTable >= 0 && !editTableNumberField.getText().isEmpty() )
			error = "A number of seats must be selected";
		
		if (error.length() == 0) {
			try {
				RestoAppController.updateTable(currentTables.get(selectedTable), Integer.parseInt(editTableNumberField.getText()), editedNumberOfSeats);
				}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
	
		if (error.length() == 0) {
			restaurantVisualizer.changeTableLocation(currentTables.get(selectedTable));	
		}
		// update visuals
		refreshData();
	}

	
	private void moveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (selectedTable < 0)
			error = "A table must be selected.";

		if (error.length() == 0) {
			// call the controller
			try {
				RestoAppController.moveTable(currentTables.get(selectedTable), 
						currentTables.get(selectedTable).getX() + (Integer.parseInt(moveXPositionField.getText())), 
						currentTables.get(selectedTable).getY() - (Integer.parseInt(moveYPositionField.getText())));
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		
		// update visuals
		if (error.length() == 0) {
			restaurantVisualizer.changeTableLocation(currentTables.get(selectedTable));
		}
		refreshData();
	}
	
	private void moveLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int movingX = Integer.parseInt(moveXPositionField.getText());
		movingX = movingX - 1;
		String movingXStr = Integer.toString(movingX);
		moveXPositionField.setText(movingXStr);
	}
	
	private void moveRightButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int movingX = Integer.parseInt(moveXPositionField.getText());
		movingX = movingX + 1;
		String movingXStr = Integer.toString(movingX);
		moveXPositionField.setText(movingXStr);
	}
	
	private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int movingY = Integer.parseInt(moveYPositionField.getText());
		movingY = movingY + 1;
		String movingYStr = Integer.toString(movingY);
		moveYPositionField.setText(movingYStr);
	}
	
	private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int movingY = Integer.parseInt(moveYPositionField.getText());
		movingY = movingY - 1;
		String movingYStr = Integer.toString(movingY);
		moveYPositionField.setText(movingYStr);
	}
	
	private void removeTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		
		if (selectedTable < 0)
			error = "A table must be selected.";

		if (error.length() == 0) {
			// call the controller
			try {
				RestoAppController.removeTable(currentTables.get(selectedTable), (selectedTable) );
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		
		// update visuals
		if (error.length() == 0) {
			restaurantVisualizer.removeTable(currentTables.get(selectedTable));
		}
		refreshData();
		
	}
	
	private ArrayList<Table> tableOrderList = new ArrayList<Table>();
	private void orderAddTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		if (orderSelectedTable < 0)
			error = "A table must be selected";

		if (error.length() == 0) {
			
			Table table = orderCurrentTables.get(orderSelectedTable);
			
			if (!tableOrderList.contains(table)) {
			
				tableOrderList.add(table);
		
				orderTablesField.setText("");
				
				String stringTable = "";
				
				for(Table textTable: tableOrderList) {
					stringTable = stringTable + textTable.getNumber() + ", ";
					
				}
				
				orderTablesField.setText(stringTable);
			}
			else {
				error += "The table is already in the list.";
			}
		}
		errorMessage.setText(error);
	}
	
	private void orderRemoveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		Table table = orderCurrentTables.get(orderSelectedTable);
		if (orderSelectedTable < 0) 
			error ="A table must be selected";
	
		if (!tableOrderList.contains(table)) 
			error = "This table is not in the list";
		else {
			if (error.length() == 0) {
				tableOrderList.remove(table);
				orderTablesField.setText("");
				String stringTable = "";
				
				for(Table textTable: tableOrderList) {
					stringTable = stringTable + textTable.getNumber() + ", ";
				}
				
				orderTablesField.setText(stringTable);
			}
		}
		errorMessage.setText(error);
	}
	
	private void startOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {		
		error = "";
		
		if (tableOrderList.isEmpty())
			error = "There are no tables selected to start an order with.";
		
		try {
			RestoAppController.startOrder(tableOrderList);
			} 
		catch (InvalidInputException e) {
			error = e.getMessage();
			}
		
		// update visuals
		if (error.length() == 0) {
			restaurantVisualizer.add_EndOrder(tableOrderList);
		}
		tableOrderList.clear();
		refreshData();	
	}
	

	private void endOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
			
		error = "";
		Order order = null;
		ArrayList <Table> manyTables = new ArrayList<Table>();
		
		if (tableOrderList.isEmpty())
			error = "There are no tables selected to end order.";
		
		for (Table table : tableOrderList) {
			Status status = table.getStatus();
			switch(status) {
				case Available:
					error = "At least one of these tables is available already.";
					errorMessage.setText(error);
					return;
			}
			if (order!= null && !(order.equals(table.getOrder(table.numberOfOrders() - 1)))) {
				error = "You have selected more than one order.";
				errorMessage.setText(error);
				return;
			}
			order = table.getOrder(table.numberOfOrders() - 1);	
			for (Table table1 : order.getTables()) {
				manyTables.add(table1);
			}
			for (Table table2 : manyTables) {
				if(!tableOrderList.contains(table2)) {
					error = "Not all of the order tables are selected.";
					errorMessage.setText(error);
					return;
				}
			}

			if (!table.allSeatsBilled()) {
				error = "All seats on the order must be billed.";
				errorMessage.setText(error);
				return;
			}
		}	
			
			try {
	
				RestoAppController.endOrder(order);
			} 
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
			// update visuals
			if (error.length() == 0) {
				restaurantVisualizer.add_EndOrder(tableOrderList);
			}
			tableOrderList.clear();
			refreshData();	
		}
	
	private ArrayList<Table> tableReservList = new ArrayList<Table>();
	private void makeReservationButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		if(reservNameField.getText() == "") {
			error = "Reservation must have a name.";
		}
		if(reservDatePicker.getModel().getValue() == "") {
			error = "Reservation must have a date.";
		}
		if((reservEmailField.getText() == "") && (reservPhoneField.getText() == "")) {
			error = "Reservation must have an e-mail or a phone number.";
		}
		if(reservNumPartyField.getText() == "") {
			error = "Reservation must have a party number.";
		}
		if(reservTimeList.getSelectedIndex() == -1) {
			error = "Reservation must have a time.";
		}
		if(tableReservList.isEmpty() == true) {
			error = "Reservation must have at least one table.";
		}
				
		int timeListIndex = reservTimeList.getSelectedIndex();
		int hour = (timeListIndex/4);
		int minute = (timeListIndex%4);
		minute = minute*15;
		int second = 0;
		
		@SuppressWarnings("deprecation")
		Time time = new Time(hour, minute, second);		
		Date date = (Date) reservDatePicker.getModel().getValue();
		
		if(error == "") {
			try{ 			
				RestoAppController.reserveTable(date, time, Integer.parseInt(reservNumPartyField.getText()), reservNameField.getText(), reservEmailField.getText(), reservPhoneField.getText(), tableReservList);			
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}
		}
		tableReservList.clear();
		refreshData();
	}
	
	private void reservAddTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		if (reservSelectedTable < 0)
			error = "A table must be selected";

		if (error.length() == 0) {
			
			Table table = reservCurrentTables.get(reservSelectedTable);
			
			if (!tableReservList.contains(table)) {
			
				tableReservList.add(table);
		
				reservTablesField.setText("");
				
				String stringTable = "";
				
				for(Table textTable: tableReservList) {
					stringTable = stringTable + textTable.getNumber() + ", ";
				}
				
				reservTablesField.setText(stringTable);
			}
			else {
				error += "The table is already in the list.";
			}
		}
		errorMessage.setText(error);
		
	}
	
	private void reservRemoveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		Table table = reservCurrentTables.get(reservSelectedTable);
		if (reservSelectedTable < 0) 
			error ="A table must be selected";
	
		if (!tableReservList.contains(table)) 
			error = "This table is not in the list";
		else {
			if (error.length() == 0) {
				tableReservList.remove(table);
				reservTablesField.setText("");
				String stringTable = "";
				
				for(Table textTable: tableReservList) {
					stringTable = stringTable + textTable.getNumber() + ", ";
				}
				
				reservTablesField.setText(stringTable);
			}
		}
		errorMessage.setText(error);
	}
	
	
	
	
	private void createCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		
		refreshData();
	}
	
	private void assignCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		
		refreshData();
	}
	
	
	private void billAddToListButtonActionPerformed(java.awt.event.ActionEvent evt) {}
	private void billOrderItemButtonActionPerformed(java.awt.event.ActionEvent evt) {}
	private void billIssueButtonActionPerformed(java.awt.event.ActionEvent evt) {}
	
	private void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		if (selectedTable < 0)
			error = "A table must be selected.";
	
		error = error.trim();
		
		//call the controller
		if (error.length() == 0) {
			try {
					RestoAppController.cancelOrder(currentTables.get(selectedTable));
				}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
			//still unsure if i need to refresh any visuals here	

		refreshData();
	}
	
	private void appetizerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
			try{ 
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.Appetizer);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			selectCategoryTF.setText("Appetizer");
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		
		// update visuals
		refreshData();
	}
	
	private void mainButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
			try {
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.Main);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			selectCategoryTF.setText("Main");
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		
		// update visuals
		refreshData();
	}
	
	private void dessertButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
			try{ 
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.Dessert);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			selectCategoryTF.setText("Dessert");
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		
		// update visuals
		refreshData();
	}
	
	private void alcoholicBeverageButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
		try {
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.AlcoholicBeverage);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			selectCategoryTF.setText("AlcoholicBeverage");
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
		// update visuals
		refreshData();
	}
	
	private void nonAlcoholicBeverageButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
		try {	
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.NonAlcoholicBeverage);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			selectCategoryTF.setText("NonAlcoholicBeverage");
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
		// update visuals
		refreshData();
	}
	
	private void uploadMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		error = "";		
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if (error.length() == 0) {
		try {	
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(MenuItem.ItemCategory.Main);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			appetizerButton.setVisible(true);
			mainButton.setVisible(true);
			dessertButton.setVisible(true);
			alcoholicBeverageButton.setVisible(true);
			nonAlcoholicBeverageButton.setVisible(true);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
			
		// update visuals
		refreshData();
	}
	
	//ROGER BUTTONS
	private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		
		error = "";
		
		if(newNameTF.getText().equals("")) 
			error = "New item has no name.";
		if(selectCategoryTF.getText().equals("")) {
			error = "New item has no category.";
		}
		if(newPriceTF.getText().equals(""))
			error = "New item has no price.";
		else if(!isInteger(newPriceTF.getText()))
			error = "Item price is not valid.";
		else if(Double.parseDouble(newPriceTF.getText()) < 0) {
			error = "New item price cannot be negative.";
		}
		
		
		String name = newNameTF.getText();
		String selectedCategory = selectCategoryTF.getText();
		ItemCategory category = null;
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if(MenuItem.hasWithName(name) && MenuItem.getWithName(name).hasCurrentPricedMenuItem()) {
			error = "Item with that name already exists.";
		}
		
		if(selectedCategory.equals(MenuItem.ItemCategory.AlcoholicBeverage.name()))
			category = ItemCategory.AlcoholicBeverage;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Appetizer.name()))
			category = ItemCategory.Appetizer;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Dessert.name()))
			category = ItemCategory.Dessert;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Main.name()))
			category = ItemCategory.Main;
		else if(selectedCategory.equals(MenuItem.ItemCategory.NonAlcoholicBeverage.name()))
			category = ItemCategory.NonAlcoholicBeverage;
		
		if(error.length() == 0) {
			
		double price = Double.parseDouble(newPriceTF.getText());
			
		try {
			RestoAppController.addMenuItem(name, category, price);
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(category);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			newNameTF.setText("");
			newPriceTF.setText("");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
		refreshData();
		
	}
	
	private void removeItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		
		if(selectItemTF.getText().equals("")) {
			error = "No selected item.";
		}
		
		
		String itemName = selectItemTF.getText();
		MenuItem aMenuItem = MenuItem.getWithName(itemName);
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();
		
		if(error.length() == 0) {
		try {
			RestoAppController.removeMenuItem(aMenuItem);
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(aMenuItem.getItemCategory());
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			selectItemTF.setText("");
			itemPriceTF.setText("");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
		
		refreshData();
		
	}
	
	private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
			
		if(selectItemTF.getText().equals("")) 
			error = "No item to update.";
		if(changedNameTF.getText().equals("") && changedPriceTF.getText().equals("") && changedCategoryTF.getText().equals("")) 
			error = "No information to update.";
		if(!changedPriceTF.getText().equals("") && !isInteger(changedPriceTF.getText()))
			error = "Price is invalid.";
		if(!changedPriceTF.getText().equals("") && isInteger(changedPriceTF.getText()) && Double.parseDouble(changedPriceTF.getText()) < 0)
			error = "Price cannot be negative.";
		
		String selectedCategory = changedCategoryTF.getText();
		if(changedCategoryTF.getText().equals(""))
			selectedCategory = selectCategoryTF.getText();
		ItemCategory category = null;
		String name = changedNameTF.getText();
		
		if(name != "" && MenuItem.hasWithName(name) && MenuItem.getWithName(name).hasCurrentPricedMenuItem()) {
			error = "Item with that name already exists.";
		}
		
		if(selectedCategory.equals(MenuItem.ItemCategory.AlcoholicBeverage.name()))
			category = ItemCategory.AlcoholicBeverage;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Appetizer.name()))
			category = ItemCategory.Appetizer;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Dessert.name()))
			category = ItemCategory.Dessert;
		else if(selectedCategory.equals(MenuItem.ItemCategory.Main.name()))
			category = ItemCategory.Main;
		else if(selectedCategory.equals(MenuItem.ItemCategory.NonAlcoholicBeverage.name()))
			category = ItemCategory.NonAlcoholicBeverage;
		else if(!(selectedCategory.length() == 0) && category == null)
			error = "Category does not exist.";
		
		if(error.length() == 0) {
			
			ArrayList<MenuItem> result = new ArrayList<MenuItem>();
			double price = 0;
			MenuItem menuItem = MenuItem.getWithName(selectItemTF.getText());
			if(changedPriceTF.getText().equals(""))
				price = -1;
			else
				price = Double.parseDouble(changedPriceTF.getText());
			
		try {	
			RestoAppController.updateMenuItem(menuItem, changedNameTF.getText(), category, price);
			result = (ArrayList<MenuItem>) RestoAppController.getMenutItems(category);
			MenuFormatter.createMenuItemsButtons(result, this);
			menuFormatter.removeAll();
			menuFormatter.repaint();
			menuFormatter.revalidate();
			for(JButton button : MenuFormatter.itemButtonList) {
				menuFormatter.add(button, constraint);
			}
			selectItemTF.setText("");
			itemPriceTF.setText("");
			changedNameTF.setText("");
			changedPriceTF.setText("");
			changedCategoryTF.setText("");	
			selectCategoryTF.setText("");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		}
		
		
		refreshData();
			
			
	}
	
	public static boolean isInteger(String s) {
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s);
	 
	         // s is a valid integer
	 
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	 
	      return isValidInteger;
	   }
	
	
}