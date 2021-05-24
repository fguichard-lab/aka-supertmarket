/**
 * 
 */
package fr.aka.supermarket;

import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.Item;
import fr.aka.supermarket.models.ItemToBuy;
import fr.aka.supermarket.service.impl.LoadFiles;


/**
 * @author fguichard
 *
 */
@SpringBootTest
class CheckoutTests {
	
	@Value("${path.filenames.test.item}") 
	String itemFileName;
	
	@Value("${path.filenames.test.cart}") 
	String cartFileName;
	
	@Value("${path.filenames.test.discount}") 
	String discountFileName;
	
	List<Discount> discounts;
	
	LinkedHashMap<Discount,Integer> lhpDiscount;
	
	List<ItemToBuy> itemsToBuy;
	
	List<Item> items;
	
	LinkedHashMap<Integer,Float> cart;
	
	@Autowired
	LoadFiles loadFiles;
	


	
	@BeforeEach
	public void initialiser()  {
		
		
		
		try {
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(itemFileName));

		    // convert JSON array to list of items
		    items = new Gson().fromJson(reader, new TypeToken<List<Item>>() {}.getType());

		    // print items
		    items.forEach(System.out::println);
		    

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		discounts = new ArrayList<>();
		try {
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(discountFileName));

		    // convert JSON array to list of discount
		    discounts = new Gson().fromJson(reader, new TypeToken<List<Discount>>() {}.getType());

		    // print discounts
		    discounts.forEach(System.out::println);
		    


		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	
		itemsToBuy = new ArrayList<>();
		try {
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get(cartFileName));

		    // convert JSON array to list of discount
		    itemsToBuy = new Gson().fromJson(reader, new TypeToken<List<ItemToBuy>>() {}.getType());

	
		    loadItem(itemsToBuy, items);

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}


		createListDiscount();
		
		
		
	}


	public void createListDiscount() {
		lhpDiscount = new LinkedHashMap<Discount,Integer>();
		
		float quantity = 0;
		
		//Add all the items of the list in the cart
		for(Discount discount: discounts) {
			
			
			if (!lhpDiscount.containsKey(discount)) {
				lhpDiscount.put(discount, (int) quantity);
			}
			
		}
	}
	
	
	//Add item into the cart 
	@Test
	public void should_Display_a_bill_of_30_1() {
		
		cart=new LinkedHashMap<Integer,Float>();
		String receipt ="";
		
		float bill = (float) 0;	
		
		
		displayHeaderOnTheReceipt();
		
		//Add all the items of the list in the cart
		for(ItemToBuy itemToBuy: itemsToBuy) {
			
			//Recover the item's data from code
			Item item = loadItem(itemToBuy);
					
			if (cart.containsKey(itemToBuy.getItemBought())) {
				
				displayItemBoughtOnTheReceipt(itemToBuy, itemToBuy.getQuantity());
				
				bill += calculatePricing(cart, itemToBuy,  discounts);
				
			}else {
				displayItemBoughtOnTheReceipt(itemToBuy, itemToBuy.getQuantity());
				bill += calculatePricing(cart, itemToBuy, discounts);
				
				cart.put(itemToBuy.getItemBought(), itemToBuy.getQuantity());
			}
		}
		displayAmountOfTheBillOnTheReceipt(bill);
		
		System.out.println(receipt);
		
		if (bill == (float)30.1) {
			assertTrue("the bill is correct ", true);
		}
		else {
			assertTrue("the bill is false", false);
		}
		 
	}


	private float calculatePricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, List<Discount> discounts) {

		float bill = (float) 0;
		if (itemToBuy.getPriceKilo()) {
			bill = calculateDefaultPricing(cart, itemToBuy,  discounts);
		}else {
			bill = calculatePackagePricing(cart, itemToBuy,  discounts);
		}
		
		
		return bill;
	}


	private float calculateDefaultPricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy, 
			List<Discount> discounts) {
		float amount = (float) 0;
		
		float quantity = cart.get(itemToBuy.getItemBought()) == null ?itemToBuy.getQuantity():cart.get(itemToBuy.getItemBought())+itemToBuy.getQuantity();
		
		amount = itemToBuy.getQuantity() * itemToBuy.getPriceUnit();

		cart.put(itemToBuy.getItemBought(), quantity);
		
		
		
		return amount;
	}


	private float calculatePackagePricing(LinkedHashMap<Integer, Float> cart, ItemToBuy itemToBuy,
			List<Discount> discounts) {
		
		float amount = (float) 0;
		float quantity = cart.get(itemToBuy.getItemBought()) == null ?itemToBuy.getQuantity():cart.get(itemToBuy.getItemBought())+itemToBuy.getQuantity();
		
		amount = itemToBuy.getQuantity() * itemToBuy.getPriceUnit();
		
		Discount discount =  checkDiscountApplicable(quantity, +itemToBuy.getItemBought(), discounts);
		
		
		if (discount != null){
			amount -= calculateDiscount(itemToBuy, discount, quantity); 
			
			cart.put(itemToBuy.getItemBought(), (quantity-discount.getQuantity()));
		}else {
			cart.put(itemToBuy.getItemBought(), quantity);
		}
		
		
		return amount;
	}



	private float calculateDiscount(ItemToBuy itemToBuy, Discount discount, float quantity) {

		float discountAmount = 0;
		
		if("BuyFor".contains(discount.getName())){
			displayPromotionApplicableOnTheReceipt(itemToBuy, discount.getQuantity(), discount);
			discountAmount = discount.getQuantity()*itemToBuy.getPriceUnit();
		}else {
			displayDiscountApplicableOnTheReceipt(itemToBuy, quantity-discount.getQuantity(), discount);
			discountAmount = itemToBuy.getPriceUnit();
		}
		
		
		return discountAmount;
	}

	/* Return the discount applicable to the itemCode */
	private Discount checkDiscountApplicable(float quantity, Integer code, List<Discount> discounts) {

		
		for(Discount discount: discounts) {
			if (discount.getItemCode().equals(code)) {
				if (discount.getQuantity()<=quantity) {
					return discount;
				}
			}
		}
		return null;
		
		
	}

	private boolean verifyItemQuantity(ItemToBuy itemToBuy) {
		
		if (!itemToBuy.getPriceKilo() && (itemToBuy.getQuantity() % 1) != 0) {
			return false;
		}
		else
			return true;
	}
	
	public void displayItemBoughtOnTheReceipt(ItemToBuy itemToBuy, float quantity) {
		
		String unit = itemToBuy.getPriceKilo() ? "Kg ": " ";
		String quantitydisplay = itemToBuy.getPriceKilo() ?  String.valueOf(quantity) : String.valueOf((int)quantity);
		String substotalLine = quantity > 1  ? "	= "+String.valueOf(itemToBuy.getPriceUnit()*quantity) : "";
		
		
		System.out.println(itemToBuy.getName().concat(" ")
		.concat(String.valueOf(quantitydisplay))
		.concat(unit)
		.concat(" *")
		.concat(" $")
		.concat(String.valueOf(itemToBuy.getPriceUnit()))
		.concat(substotalLine)
		);
	
	}

	
	/*return the information of the item bought */
	public void displayDiscountApplicableOnTheReceipt(ItemToBuy itemToBuy, float quantity, Discount discount) {
		
		int displayQuantity = quantity != 0 ? (int) quantity : 1;
		
		System.out.println("discount ".concat(String.valueOf(discount.getQuantity()))
		.concat(discount.getName()).concat(" -")
		.concat(String.valueOf(displayQuantity))
		.concat(" ")
		.concat(itemToBuy.getName())
		.concat(" $")
		.concat(String.valueOf(discount.getPriceOfDiscount()))
		);
	}	
	
	//return the information of the item bought
	public void displayPromotionApplicableOnTheReceipt(ItemToBuy itemToBuy, float quantity, Discount discount) {
		
		DecimalFormat frmt = new DecimalFormat();
		frmt.setMaximumFractionDigits(2);
		float discountIemts = itemToBuy.getPriceUnit()*discount.getQuantity();
				
				
		System.out.println("Promotion ".concat(String.valueOf(discount.getQuantity()))
		.concat(discount.getName()).concat(" ")
		.concat(String.valueOf(discount.getQuantity()))
		.concat(" ")
		.concat(itemToBuy.getName())
		.concat("(s)  $")
		.concat(String.valueOf(discount.getPriceOfDiscount()))
		.concat("\n")
		.concat(" 	-")
		.concat(String.valueOf(discount.getQuantity()))
		.concat(" ")
		.concat(itemToBuy.getName())
		.concat("(s)  -$")
		.concat(String.valueOf(frmt.format(discountIemts)))
		);
	}	
	
	
	/*return the amount of the bill */
	public void displayAmountOfTheBillOnTheReceipt(float bill) {
		if (bill != 0) {
			System.out.println("\nTotal : $".concat(String.valueOf(bill))
			.concat("\n")
			.concat("Paied by :    Visa card : $").concat(String.valueOf(bill))
			.concat("\n\n")
			.concat(" Aka-supermarket thank you for your visit")
			);
		}
	}
	
	/*return the amount of the bill */
	public void displayHeaderOnTheReceipt() {
		
	    //Get current date time
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = now.format(formatter);LocalDateTime localDateTime = null;
		
		System.out.println(
		" Aka-supermarket ".concat(formatDateTime)
		.concat("\n")
		);
	}	

	private Item loadItem(ItemToBuy itemToBuy) {

		Item item = new Item();
		
		item.setCode(itemToBuy.getCode());
		item.setName(itemToBuy.getName());
		item.setPriceKilo(itemToBuy.getPriceKilo());
		item.setPriceUnit(itemToBuy.getPriceUnit());
		
		return item;
	}


	private void loadItem(List<ItemToBuy> itemsToBuy, List<Item> items) {
		
		for(ItemToBuy itemToBuy: itemsToBuy) {
			for(Item item: items) {
				if (itemToBuy.getItemBought().equals(item.getCode())) {
			//		itemToBuy.setQuantity(CheckoutUtils.verifyItemQuantity(item.getPriceKilo(), itemToBuy.getQuantity()));
					itemToBuy.setPriceKilo(item.getPriceKilo());	
					itemToBuy.setCode(item.getCode());
					itemToBuy.setName(item.getName());
					itemToBuy.setPriceUnit(item.getPriceUnit());
					break;
				}
			}	
		}

	}
}



