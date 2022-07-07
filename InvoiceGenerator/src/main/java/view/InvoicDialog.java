package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoicDialog extends JDialog {
    private JTextField customerNameInput = new JTextField(20);
    private JTextField invoiceDateInput = new JTextField(20);
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");

    public InvoicDialog(InvoiceFrame frame) {
        ok.setActionCommand("OkCreateInvoice");
        cancel.setActionCommand("CancelCreateInvoice");
        ok.addActionListener(frame::actionPerformed);
        cancel.addActionListener(frame::actionPerformed);
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Invoice Date:"));
        add(invoiceDateInput);
        add(new JLabel("Customer Name:"));
        add(customerNameInput);
        add(ok);
        add(cancel);
        pack();
    }

    public JTextField getCustomerNameInput() {
        return this.customerNameInput;
    }

    public JTextField getInvoiceDateInput() {
        return this.invoiceDateInput;
    }
}
