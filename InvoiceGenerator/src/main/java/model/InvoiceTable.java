package model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class InvoiceTable extends AbstractTableModel {
    private List<Invoice> invoicesArray;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public InvoiceTable(List<Invoice> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    public int getRowCount() {
        return this.invoicesArray.size();
    }

    public List<Invoice> getInvoicesArray() {
        return this.invoicesArray;
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice row = (Invoice)this.invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getInvoiceNumber();
            case 1:
                return row.getCustomerName();
            case 2:
                return this.dateFormat.format(row.getInvoiceDate());
            case 3:
                return row.getInvoiceTotal();
            default:
                return null;
        }
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "invoice Num";
            case 1:
                return "Customer Name";
            case 2:
                return "Invoice Date";
            case 3:
                return "Invoice Total";
            default:
                return "";
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }
}

