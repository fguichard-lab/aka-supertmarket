/**
 * 
 */
package fr.aka.supermarket.models;

/**
 * @author fguichard
 *
 */
public class Discount {
	
	//name of the discount
	private String name;
	
	private Integer itemCode;
	
	private Integer quantity;
	
	private Float priceOfDiscount;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the itemCode
	 */
	public Integer getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the priceOfDiscount
	 */
	public Float getPriceOfDiscount() {
		return priceOfDiscount;
	}

	/**
	 * @param priceOfDiscount the priceOfDiscount to set
	 */
	public void setPriceOfDiscount(Float priceOfDiscount) {
		this.priceOfDiscount = priceOfDiscount;
	}

	/**
	 * 
	 */
	public Discount() {
		super();
	}




	

}
