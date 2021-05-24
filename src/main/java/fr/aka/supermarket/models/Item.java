/**
 * 
 */
package fr.aka.supermarket.models;

/**
 * @author fguichard
 *
 */
public class Item {

	//number of the item
	private Integer code;
	
	//name of the item
	private String name;
	
	//price Unit
	private float priceUnit;
	
	//Flag price per kilo
	private Boolean priceKilo;
	
	

	
	/**
	 * 
	 * Getters and Setters
	 * 
	 */
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(float priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Boolean getPriceKilo() {
		return priceKilo;
	}

	public void setPriceKilo(Boolean priceKilo) {
		this.priceKilo = priceKilo;
	}


	/**
	 * 
	 */
	public Item() {
		super();
	}

	/**
	 * @param code
	 * @param name
	 * @param priceUnit
	 * @param priceKilo
	 */
	public Item(Integer code, String name, Float priceUnit, Boolean priceKilo) {
		super();
		this.code = code;
		this.name = name;
		this.priceUnit = priceUnit;
		this.priceKilo = priceKilo;
	}

	
}
