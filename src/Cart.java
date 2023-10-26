
public class Cart {
	String customerId;
	String productId;
	int quantity;
	
	public Cart(String customerId, String productId, int quantity  ) {
		
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
		
	}

	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}




	
	
	
	

}
