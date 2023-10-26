
public class TransactionDetail {
	String transactionid;
	String productid;
	String productname;
	String producttype;
	int quantity;

	public TransactionDetail(String transactionid, String productid, String productname, String producttype,
			int quantity) {
		super();
		this.transactionid = transactionid;
		this.productid = productid;
		this.productname = productname;
		this.producttype = producttype;
		this.quantity = quantity;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	

}
