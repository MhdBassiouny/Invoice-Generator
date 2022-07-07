package model;

public class InvoiceItem {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private double lineTotal;
    private Invoice header;

    public InvoiceItem(String itemName, double itemPrice, int itemCount, Invoice header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.header = header;
        this.setLineTotal((double)this.itemCount * this.itemPrice);
    }

    public Invoice getHeader() {
        return this.header;
    }

    public String getItemName() {
        return this.itemName;
    }

    public double getItemPrice() {
        return this.itemPrice;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public double getLineTotal() {
        return this.lineTotal;
    }

    private void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getDataAsCSV() {
        return "" + this.getHeader().getInvoiceNumber() + "," + this.getItemName() + "," + this.getItemPrice() + "," + this.getItemCount();
    }
}
