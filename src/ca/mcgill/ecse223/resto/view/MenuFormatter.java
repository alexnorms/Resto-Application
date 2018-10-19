package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.Action;
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

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class MenuFormatter extends JPanel implements ActionListener{
	
	static List<JButton> itemButtonList = new ArrayList<JButton>();
	String BUTTON_SIZE = "70";
	String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;
	static RestoAppPage restoAppPage = new RestoAppPage();
	static RestoApp aRestoApp = new RestoApp();
	static MenuItem selectedItem = null;
	//static HashMap<String, JButton> aHashMap = new HashMap<String, JButton>(); 
	
	
	public static void createMenuItemsButtons(ArrayList<MenuItem> result, RestoAppPage resto){
		
		itemButtonList.clear();
		
		MenuFormatter instance = new MenuFormatter();
		
		for(MenuItem item : result) {
		    JButton button = new JButton(item.getName());
		    button.setText(item.getName() + " " + item.getCurrentPricedMenuItem().getPrice() + "$");
		    itemButtonList.add(button);
		    button.addActionListener((ActionListener) instance);
		    button.setActionCommand(item.getName());
		    restoAppPage = resto;
		    aRestoApp = item.getMenu().getRestoApp();
		    //aHashMap.put(item.getName(), button);
		}		
	}
	
	public void actionPerformed (ActionEvent evt) {
		
		String item = evt.getActionCommand();
		MenuItem menuItem = MenuItem.getWithName(item);
		String category = menuItem.getItemCategory().name();
		String price = menuItem.getCurrentPricedMenuItem().getPrice() + "$";
		selectedItem = menuItem;
		
		
		restoAppPage.selectItemTF.setText(item);
		restoAppPage.itemPriceTF.setText(price);
		
		
	}
	
}