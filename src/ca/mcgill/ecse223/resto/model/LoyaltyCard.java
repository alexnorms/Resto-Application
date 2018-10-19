/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 22 "../../../../../RestoAppPersistence.ump"
// line 18 "../../../../../RestoApp.ump"
public class LoyaltyCard implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextLoyaltyCardNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LoyaltyCard Attributes
  private String contactName;
  private String contactEmailAddress;
  private String contactPhoneNumber;
  private String contactPostalCode;

  //Autounique Attributes
  private int loyaltyCardNumber;

  //LoyaltyCard Associations
  private RestoApp restoApp;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LoyaltyCard(String aContactName, String aContactEmailAddress, String aContactPhoneNumber, String aContactPostalCode, RestoApp aRestoApp)
  {
    contactName = aContactName;
    contactEmailAddress = aContactEmailAddress;
    contactPhoneNumber = aContactPhoneNumber;
    contactPostalCode = aContactPostalCode;
    loyaltyCardNumber = nextLoyaltyCardNumber++;
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create loyaltyCard due to restoApp");
    }
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setContactName(String aContactName)
  {
    boolean wasSet = false;
    contactName = aContactName;
    wasSet = true;
    return wasSet;
  }

  public boolean setContactEmailAddress(String aContactEmailAddress)
  {
    boolean wasSet = false;
    contactEmailAddress = aContactEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setContactPhoneNumber(String aContactPhoneNumber)
  {
    boolean wasSet = false;
    contactPhoneNumber = aContactPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setContactPostalCode(String aContactPostalCode)
  {
    boolean wasSet = false;
    contactPostalCode = aContactPostalCode;
    wasSet = true;
    return wasSet;
  }

  public String getContactName()
  {
    return contactName;
  }

  public String getContactEmailAddress()
  {
    return contactEmailAddress;
  }

  public String getContactPhoneNumber()
  {
    return contactPhoneNumber;
  }

  public String getContactPostalCode()
  {
    return contactPostalCode;
  }

  public int getLoyaltyCardNumber()
  {
    return loyaltyCardNumber;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeLoyaltyCard(this);
    }
    restoApp.addLoyaltyCard(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    LoyaltyCard existingLoyaltyCard = aOrder.getLoyaltyCard();
    if (existingLoyaltyCard == null)
    {
      aOrder.setLoyaltyCard(this);
    }
    else if (!this.equals(existingLoyaltyCard))
    {
      existingLoyaltyCard.removeOrder(aOrder);
      addOrder(aOrder);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (orders.contains(aOrder))
    {
      orders.remove(aOrder);
      aOrder.setLoyaltyCard(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeLoyaltyCard(this);
    }
    while( !orders.isEmpty() )
    {
      orders.get(0).setLoyaltyCard(null);
    }
  }

  // line 28 "../../../../../RestoAppPersistence.ump"
   public static  void reinitializeAutouniqueLoyaltyCardNumber(List<LoyaltyCard> loyaltyCards){
    nextLoyaltyCardNumber = 0; 
    for (LoyaltyCard loyaltyCard : loyaltyCards) {
      if (loyaltyCard.getLoyaltyCardNumber() > nextLoyaltyCardNumber) {
        nextLoyaltyCardNumber = loyaltyCard.getLoyaltyCardNumber();
      }
    }
    nextLoyaltyCardNumber++;
  }


  public String toString()
  {
    return super.toString() + "["+
            "loyaltyCardNumber" + ":" + getLoyaltyCardNumber()+ "," +
            "contactName" + ":" + getContactName()+ "," +
            "contactEmailAddress" + ":" + getContactEmailAddress()+ "," +
            "contactPhoneNumber" + ":" + getContactPhoneNumber()+ "," +
            "contactPostalCode" + ":" + getContactPostalCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 25 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = -2315072678279090501L ;

  
}