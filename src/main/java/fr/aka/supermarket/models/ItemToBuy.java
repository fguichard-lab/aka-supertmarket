/**
 * 
 */
package fr.aka.supermarket.models;

/**
 * @author fguichard
 *
 */
public class ItemToBuy extends Item {
	
	private Integer itemBought;
	
	private float quantity;


	/**
	 * @return the itemBought
	 */
	public Integer getItemBought() {
		return itemBought;
	}

	/**
	 * @param itemBought the itemBought to set
	 */
	public void setItemBought(Integer itemBought) {
		this.itemBought = itemBought;
	}

	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 */
	public ItemToBuy() {
		super();
	}

	/**
	 * @param itemBought
	 * @param quantity
	 */
	public ItemToBuy(Integer itemBought, float quantity) {
		super();
		this.itemBought = itemBought;
		this.quantity = quantity;
	}


//	
//	public Item getItem() {
//		Item item = new Item();
//		item.setCode(itemBought);
//		item.setName(getName());
//		item.setPriceKilo(getPriceKilo());
//		item.setPriceUnit(getPriceUnit());
//		
//		return item;
//	}

}
