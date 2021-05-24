/**
 * 
 */
package fr.aka.supermarket.models;

import java.util.List;

/**
 * @author fguichard
 *
 */
public class Checkout {
	
	//List of article in the cart
	private List<ItemToBuy> itemsToBuy;
	
	//Amount of the cart
	private Float bill;

	/**
	 * @return the itemsToBuy
	 */
	public List<ItemToBuy> getItemsToBuy() {
		return itemsToBuy;
	}

	/**
	 * @param itemsToBuy the itemsToBuy to set
	 */
	public void setItemsToBuy(List<ItemToBuy> itemsToBuy) {
		this.itemsToBuy = itemsToBuy;
	}

	/**
	 * @return the bill
	 */
	public Float getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	public void setBill(Float bill) {
		this.bill = bill;
	}


	/**
	 * 
	 */
	public Checkout() {
		super();
	}





}
