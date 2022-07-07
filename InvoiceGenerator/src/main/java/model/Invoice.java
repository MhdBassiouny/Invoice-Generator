package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Invoice {
    private int invoiceNumber;
    private String customerName;
    private Date invoiceDate;
    private ArrayList<InvoiceItem> lines;

    public Invoice(int invoiceNumber, String customerName, Date invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String toString() {
        String str = "InvoiceDataFrame{invoiceNumber=" + invoiceNumber + ", customerName=" + customerName + ", invoiceDate=" + invoiceDate + '}';

        InvoiceItem line;
        for(Iterator iter = getItems().iterator(); iter.hasNext(); str = str + "\n\t" + line) {
            line = (InvoiceItem)iter.next();
        }
        return str;
    }

    public ArrayList<InvoiceItem> getItems() {
        if (lines == null) {
            lines = new ArrayList();
        }

        return lines;
    }

    public double getInvoiceTotal() {
        double total = 0.0;

        InvoiceItem item;
        for(Iterator iter7 = getItems().iterator(); iter7.hasNext(); total += item.getLineTotal()) {
            item = (InvoiceItem)iter7.next();
        }
        return total;
    }

    public void addInvoiceItem(InvoiceItem item) {
        getItems().add(item);
    }

    public String getDataAsCSV() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoiceNumber() + "," + dateFormat.format(getInvoiceDate()) + "," + getCustomerName();
    }

}



