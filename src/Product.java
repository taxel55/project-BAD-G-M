
public class Product {
	String id;
	String ProductName;
	String ProductType;
	int price;
	int stock;
	
	public Product() {
		// TODO Auto-generated constructor stub
		
	}
	public Product(String id, String productName, String productType, int price, int stock) {
		this.id = id;
		this.ProductName = productName;
		this.ProductType = productType;
		this.price = price;
		this.stock = stock;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductType() {
		return ProductType;
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	
}
