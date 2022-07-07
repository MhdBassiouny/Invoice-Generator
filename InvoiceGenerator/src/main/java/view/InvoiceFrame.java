package view;

//import controller.newListener;
import model.Invoice;
import model.InvoiceTable;
import model.InvoiceItem;
import model.InvoiceItemTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class InvoiceFrame extends JFrame {
    JButton createInvoice;
    JButton createItem;
    JButton deleteItem;
    JButton deleteInvoice;
    JButton save;
    JButton cancel;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    JTextField customerName;
    JTextField invoiceDate;

    JTable invoiceItemTable;
    JTable invoicesTable;

    JLabel invoiceNumber;
    JLabel invoiceTotal;
    JScrollPane jScrollPaneInvoice;
    JScrollPane jScrollPaneItems;
    InvoiceTable invoiceTableModel;
    InvoiceItemTable invoiceItemTableModel;

    JMenu jMenu;
    JMenuBar MenuBar;
    JMenuItem loadFile;
    JMenuItem saveFile;
    List<Invoice> invoicesArray = new ArrayList();

    InvoicDialog invoiceDialog;
    InvoiceItemDialog itemDialog;

    int lastSelectedIndex;


    public InvoiceFrame() {
        this.myFrame();
    }

    private void myFrame() {
        setBounds(100,100,600, 350);
        setLayout(new BorderLayout());

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        left.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());

        JPanel leftUp = new JPanel();
        JPanel leftCenter = new JPanel();
        JPanel leftDown = new JPanel();
        left.add(leftUp, BorderLayout.NORTH);
        left.add(leftCenter, BorderLayout.CENTER);
        left.add(leftDown, BorderLayout.SOUTH);

        JPanel rightUp = new JPanel();
        JPanel rightCenter = new JPanel();
        JPanel rightDown =  new JPanel();
        right.add(rightUp, BorderLayout.NORTH);
        right.add(rightCenter, BorderLayout.CENTER);
        right.add(rightDown, BorderLayout.SOUTH);

        leftUp.setLayout(new GridLayout(1, 1));
        leftCenter.setLayout(new FlowLayout());
        leftDown.setLayout(new FlowLayout());

        rightUp.setLayout(new GridLayout(5,2));
        rightCenter.setLayout(new FlowLayout());
        rightDown.setLayout(new FlowLayout());

        jScrollPaneInvoice = new JScrollPane();
        invoicesTable = new JTable();
        invoicesTable.getSelectionModel().addListSelectionListener(this::changeView);
        invoicesTable.setModel(new DefaultTableModel());
        jScrollPaneInvoice.setViewportView(invoicesTable);

        jScrollPaneItems = new JScrollPane();
        invoiceItemTable = new JTable();
        invoiceItemTable.setModel(new DefaultTableModel());
        jScrollPaneItems.setViewportView(invoiceItemTable);

        MenuBar = new JMenuBar();
        jMenu = new JMenu();
        loadFile = new JMenuItem();
        loadFile.setText("Load File");
        loadFile.setActionCommand("LoadFile");
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.ALT_DOWN_MASK));
        loadFile.setMnemonic('L');
        loadFile.addActionListener(this::actionPerformed);
        saveFile = new JMenuItem();
        saveFile.setText("Save File");
        saveFile.setActionCommand("SaveFile");
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
        saveFile.setMnemonic('S');
        saveFile.addActionListener(this::actionPerformed);

        jMenu.setText("File");
        jMenu.add(loadFile);
        jMenu.add(saveFile);
        MenuBar.add(jMenu);
        setJMenuBar(MenuBar);

        createInvoice = new JButton("Create New Invoice");
        createInvoice.setActionCommand("CreateNewInvoice");
        createInvoice.addActionListener(this::actionPerformed);
        deleteInvoice = new JButton("Delete Invoice");
        deleteInvoice.setActionCommand("DeleteInvoice");
        deleteInvoice.addActionListener(this::actionPerformed);
        createItem = new JButton("Create New Item");
        createItem.setActionCommand("CreateNewItem");
        createItem.addActionListener(this::actionPerformed);
        deleteItem = new JButton("Delete Item");
        deleteItem.setActionCommand("DeleteItem");
        deleteItem.addActionListener(this::actionPerformed);

        save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(this::actionPerformed);

        cancel = new JButton("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(this::actionPerformed);

        customerName = new JTextField();
        invoiceDate = new JTextField();
        invoiceTotal = new JLabel();
        invoiceNumber = new JLabel();
        customerName.addActionListener(this::actionPerformed);
        invoiceDate.addActionListener(this::actionPerformed);

        leftUp.add(new JLabel("Invoice Table"));
        leftCenter.add(jScrollPaneInvoice);
        leftDown.add(createInvoice);
        leftDown.add(deleteInvoice);
        rightUp.add(new JLabel("Invoice Number"));
        rightUp.add(invoiceNumber);
        rightUp.add(new JLabel("Invoice Date"));
        rightUp.add(invoiceDate);
        rightUp.add(new JLabel("Customer Name"));
        rightUp.add(customerName);
        rightUp.add(new JLabel("Invoice Total"));
        rightUp.add(invoiceTotal);
        rightUp.add(new JLabel("Invoice Items"));
        rightCenter.add(jScrollPaneItems);
        rightDown.add(save);
        rightDown.add(cancel);
        rightDown.add(createItem);
        rightDown.add(deleteItem);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        new InvoiceFrame().setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CreateNewInvoice":
                invoiceDialog = new InvoicDialog(this);
                invoiceDialog.setVisible(true);
                break;
            case "DeleteInvoice":
                deleteInvoice();
                break;
            case "LoadFile":
                LoadFile();
                break;
            case "SaveFile":
                saveFile();
            case "CreateNewItem":
                itemDialog = new InvoiceItemDialog(this);
                itemDialog.setVisible(true);
                break;
            case "DeleteItem":
                deleteItem();
                break;
            case "CancelCreateInvoice":
                invoiceDialog.setVisible(false);;
                break;
            case "OkCreateInvoice":
                okCreateInvoice();
                break;
            case "CancelCreateItem":
                itemDialog.setVisible(false);
                break;
            case "OkCreateItem":
                oKCreateItem();
                break;

            case "save":
                saveChanges();
                break;

            case "cancel":
                cancelChanges();
                break;
        }
    }

    //some of the follwoing function are written with the help of the following repo: https://github.com/NorhaneM/SalesInvoiceGenerator_FWD/tree/8c5dcaf10a2de0004cf895768335e2eabc869a46/SalesInvoiceGenerator/src/com/udacity
    private void LoadFile() {
        JOptionPane.showMessageDialog(this, "Select invoice file!", "Invoice File", 2);
        JFileChooser openFile = new JFileChooser();
        int result = openFile.showOpenDialog(this);
        if (result == 0) {
            File headerFile = openFile.getSelectedFile();

            try {
                FileReader headerFr = new FileReader(headerFile);
                BufferedReader headerBr = new BufferedReader(headerFr);
                String headerLine = null;

                String linesLine;
                while((headerLine = headerBr.readLine()) != null) {
                    String[] headerParts = headerLine.split(",");
                    String invNumStr = headerParts[0];
                    linesLine = headerParts[1];
                    String custName = headerParts[2];
                    int invNum = Integer.parseInt(invNumStr);
                    Date invDate = dateFormat.parse(linesLine);
                    Invoice invoive = new Invoice(invNum, custName, invDate);
                    invoicesArray.add(invoive);
                }

                JOptionPane.showMessageDialog(this, "Select Items file!", "Items File", 2);
                result = openFile.showOpenDialog(this);
                if (result == 0) {
                    File linesFile = openFile.getSelectedFile();
                    BufferedReader linesBr = new BufferedReader(new FileReader(linesFile));
                    linesLine = null;

                    while((linesLine = linesBr.readLine()) != null) {
                        String[] lineParts = linesLine.split(",");
                        String invNumStr = lineParts[0];
                        String itemName = lineParts[1];
                        String itemPriceStr = lineParts[2];
                        String itemCountStr = lineParts[3];
                        int invNum = Integer.parseInt(invNumStr);
                        double itemPrice = Double.parseDouble(itemPriceStr);
                        int itemCount = Integer.parseInt(itemCountStr);
                        Invoice header = findInvoiceByNumber(invNum);
                        InvoiceItem invLine = new InvoiceItem(itemName, itemPrice, itemCount, header);
                        header.getItems().add(invLine);
                    }

                    invoiceTableModel = new InvoiceTable(invoicesArray);
                    invoicesTable.setModel(invoiceTableModel);
                    invoicesTable.validate();
                }

            } catch (ParseException var21) {
                var21.printStackTrace();
                JOptionPane.showMessageDialog(this, "Date Format Error\n" + var21.getMessage(), "Error", 0);
            } catch (NumberFormatException var22) {
                var22.printStackTrace();
                JOptionPane.showMessageDialog(this, "Number Format Error\n" + var22.getMessage(), "Error", 0);
            } catch (FileNotFoundException var23) {
                var23.printStackTrace();
                JOptionPane.showMessageDialog(this, "File Error\n" + var23.getMessage(), "Error", 0);
            } catch (IOException var24) {
                var24.printStackTrace();
                JOptionPane.showMessageDialog(this, "Read Error\n" + var24.getMessage(), "Error", 0);
            }
        }

    }

    private void saveFile() {
        String headers = "";
        String items = "";
        Iterator itemIterator = invoicesArray.iterator();

        while(itemIterator.hasNext()) {
            Invoice header = (Invoice)itemIterator.next();
            headers = headers + header.getDataAsCSV();
            headers = headers + "\n";

            for(Iterator itemIterator2 = header.getItems().iterator(); itemIterator2.hasNext(); items = items + "\n") {
                InvoiceItem line = (InvoiceItem)itemIterator2.next();
                items = items + line.getDataAsCSV();
            }
        }

        JOptionPane.showMessageDialog(this, "Please, select file to save invoice data!", "Attention", 2);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == 0) {
            File invoiceFile = fileChooser.getSelectedFile();

            try {
                FileWriter invoiceFileWriter = new FileWriter(invoiceFile);
                invoiceFileWriter.write(headers);
                invoiceFileWriter.flush();
                invoiceFileWriter.close();
                JOptionPane.showMessageDialog(this, "Please, select file to save Item data!", "Attention", 2);
                result = fileChooser.showSaveDialog(this);
                if (result == 0) {
                    File itemFile = fileChooser.getSelectedFile();
                    FileWriter itemFileWriter = new FileWriter(itemFile);
                    itemFileWriter.write(items);
                    itemFileWriter.flush();
                    itemFileWriter.close();
                }

                JOptionPane.showMessageDialog(null, "Files Saved Successfully ! ");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", 0);
            }
        }

    }

    private Invoice findInvoiceByNumber(int invoiceNumber) {
        Invoice header = null;
        Iterator iterator0 = invoicesArray.iterator();

        while(iterator0.hasNext()) {
            Invoice inv = (Invoice)iterator0.next();
            if (invoiceNumber == inv.getInvoiceNumber()) {
                header = inv;
                break;
            }
        }
        return header;
    }

    public void changeView(ListSelectionEvent e) {
        rowSelected();
    }

    private void rowSelected() {
        int selectedRowIndex = invoicesTable.getSelectedRow();
        if (selectedRowIndex >= 0) {
            Invoice row = (Invoice)this.invoiceTableModel.getInvoicesArray().get(selectedRowIndex);
            customerName.setText(row.getCustomerName());
            invoiceDate.setText(dateFormat.format(row.getInvoiceDate()));
            invoiceNumber.setText(String.valueOf(row.getInvoiceNumber()));
            invoiceTotal.setText(String.valueOf(row.getInvoiceTotal()));
            ArrayList<InvoiceItem> items = row.getItems();
            invoiceItemTableModel = new InvoiceItemTable(items);
            invoiceItemTable.setModel(invoiceItemTableModel);
            invoiceItemTableModel.fireTableDataChanged();
        }
    }

    private void deleteInvoice() {
        int i = invoicesTable.getSelectedRow();
        invoiceTableModel.getInvoicesArray().remove(i);
        invoiceTableModel.fireTableDataChanged();
        invoiceItemTableModel = new InvoiceItemTable(new ArrayList());
        invoiceItemTable.setModel(invoiceItemTableModel);
        invoiceItemTableModel.fireTableDataChanged();
        customerName.setText("");
        invoiceDate.setText("");
        invoiceNumber.setText("");
        invoiceTotal.setText("");
        JOptionPane.showMessageDialog(null, "Invoice Deleted Successfully!");
    }

    private void deleteItem() {
        int i = invoiceItemTable.getSelectedRow();
        InvoiceItem line = invoiceItemTableModel.getInvoiceItems().get(i);
        invoiceItemTableModel.getInvoiceItems().remove(i);
        invoiceTableModel.fireTableDataChanged();
        invoiceItemTableModel.fireTableDataChanged();
        invoiceTotal.setText(String.valueOf(line.getHeader().getInvoiceTotal()));
        JOptionPane.showMessageDialog(null, "Item Deleted Successfully!");
    }

    private void okCreateInvoice() {
        String invoiceDateInput = invoiceDialog.getInvoiceDateInput().getText();
        String customerName = invoiceDialog.getCustomerNameInput().getText();
        try {
            Date invoiceDate = dateFormat.parse(invoiceDateInput);
            int invoiceNumber = Integer.parseInt(String.valueOf(invoiceTableModel.getValueAt(invoiceTableModel.getRowCount()-1, 0))) +1;
            Invoice invoiceFrame = new Invoice(invoiceNumber, customerName, invoiceDate);
            invoicesArray.add(invoiceFrame);
            invoiceTableModel.fireTableDataChanged();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Wrong date Format", "Error", 0);
            e.printStackTrace();
        }
        invoiceDialog.setVisible(false);
    }

    private void oKCreateItem() {
        String itemPriceInput = itemDialog.getItemPriceInput().getText();
        String itemName = itemDialog.getItemNameInput().getText();
        String itemCountInput = itemDialog.getItemCountInput().getText();
        int invoiceIndex;

        if (invoicesTable.getSelectedRow() != -1) {
            invoiceIndex = invoicesTable.getSelectedRow();
            lastSelectedIndex = invoiceIndex;
        } else {
            invoiceIndex = lastSelectedIndex;
        }
        double itemPrice = Double.parseDouble(itemPriceInput);
        int itemCount = Integer.parseInt(itemCountInput);

        Invoice invoice = invoiceTableModel.getInvoicesArray().get(invoiceIndex);
        InvoiceItem invoiceItem = new InvoiceItem(itemName, itemPrice, itemCount, invoice);
        invoice.addInvoiceItem(invoiceItem);
        invoiceItemTableModel.fireTableDataChanged();
        invoiceTableModel.fireTableDataChanged();
        invoiceTotal.setText(String.valueOf(invoice.getInvoiceTotal()));

        itemDialog.setVisible(false);
    }

    private void saveChanges() {
        int i = invoicesTable.getSelectedRow();
        Invoice selectedRow = (Invoice)this.invoiceTableModel.getInvoicesArray().get(i);
        try {
            selectedRow.setInvoiceDate(dateFormat.parse(String.valueOf(invoiceDate.getText())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        selectedRow.setCustomerName(customerName.getText());
        invoiceTableModel.fireTableDataChanged();
    }

    private void cancelChanges(){
        int i = invoicesTable.getSelectedRow();
        invoiceDate.setText((String) invoiceTableModel.getValueAt(i, 2));
        customerName.setText((String) invoiceTableModel.getValueAt(i, 1));
    }

}

