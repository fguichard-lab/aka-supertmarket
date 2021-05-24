/**
 * 
 */
package fr.aka.supermarket.utils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.math3.util.Precision;

import fr.aka.supermarket.models.Discount;
import fr.aka.supermarket.models.ItemToBuy;

/**
 * @author fguichard
 *
 */
public class CheckoutUtils {
	
	/*Verify the quantity define for the item has not decimal part
	 *
	 * @param ItemToBuy
	 * @return boolean
	 */
	public static boolean verifyItemQuantity(Boolean priceKilo, float quantity) {
		
		if (!priceKilo && (quantity % 1) != 0) {
			return false;
		}
		else
			return true;
	}
	

	
	/*return the header on the receipt
	 *
	 */
	public static void displayHeaderOnTheReceipt() {
		
	    //Get current date time
       LocalDateTime now = LocalDateTime.now();

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

       String formatDateTime = now.format(formatter);LocalDateTime localDateTime = null;
		
		System.out.println(
		" Aka-supermarket ".concat(formatDateTime)
		.concat("\n")
		);
	}	
	
	/*display the information of the item bought on the receipt
	 *
	 * @param itemToBuy
	 */
	public static void displayItemBoughtOnTheReceipt(ItemToBuy itemToBuy) {
		
		String unit = itemToBuy.getPriceKilo() ? "Kg ": " ";
		String quantitydisplay = itemToBuy.getPriceKilo() ?  String.valueOf(itemToBuy.getQuantity()) : String.valueOf((int)itemToBuy.getQuantity());
		String substotalLine = itemToBuy.getQuantity() > 1  ? "	= "+String.valueOf(itemToBuy.getPriceUnit()*itemToBuy.getQuantity()) : "";
		
		
		System.out.println(itemToBuy.getName().concat(" ")
		.concat(String.valueOf(quantitydisplay))
		.concat(unit)
		.concat(" *")
		.concat(" $")
		.concat(String.valueOf(itemToBuy.getPriceUnit()))
		.concat(substotalLine)
		);
	
	}

	/*display the information of the discount on the receipt
	 *
	 * @param itemToBuy
	 * @param quantity
	 * @param Discount
	 */
	public static void displayDiscountApplicableOnTheReceipt(ItemToBuy itemToBuy, float quantity, Discount discount) {
		
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

	
	/*display the information of the promotion on the receipt
	 *
	 * @param Item
	 * @param Discount
	 */
	public static void displayPromotionApplicableOnTheReceipt(ItemToBuy itemToBuy, Discount discount) {
		
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
	
	
	/*display the amount of the bill on the receipt
	 *
	 * @param float
	 */
	public static void displayAmountOfTheBillOnTheReceipt(float bill) {
		if (bill != 0) {
			System.out.println("\nTotal : $".concat(String.valueOf(Precision.round(bill,2)))
			.concat("\n")
			.concat("Paied by :    Visa card : $").concat(String.valueOf(Precision.round(bill,2)))
			);
		}
		displayFooterOnTheReceipt();
	}

	/*display the amount of the bill on the receipt
	 *
	 * @param 
	 */
	public static void displayFooterOnTheReceipt() {
		System.out.println("\n\n".concat(" Aka-supermarket thank you for your visit"));
	}
	
}
