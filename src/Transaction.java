
public class Transaction {

	String transactionid;
	//String productid;
	String custid;
//	String productname;
//	String producttype;
//	int quantity;
	String transactiondate;
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
//	public String getProductid() {
//		return productid;
//	}
//	public void setProductid(String productid) {
//		this.productid = productid;
//	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
//	public String getProductname() {
//		return productname;
//	}
//	public void setProductname(String productname) {
//		this.productname = productname;
//	}
//	public String getProducttype() {
//		return producttype;
//	}
//	public void setProducttype(String producttype) {
//		this.producttype = producttype;
//	}
//	public int getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(int quantity) {
//		this.quantity = quantity;
//	}
	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public Transaction(String transactionid, //String productid, 
			String custid, 
			//String productname, String producttype,
			//int quantity, 
			String transactiondate) {
		
		this.transactionid = transactionid;
		//this.productid = productid;
		this.custid = custid;
//		this.productname = productname;
//		this.producttype = producttype;
//		this.quantity = quantity;
		this.transactiondate = transactiondate;
	}
	

}
