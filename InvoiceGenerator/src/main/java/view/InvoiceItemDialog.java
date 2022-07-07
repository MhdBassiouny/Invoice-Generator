package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceItemDialog extends JDialog {
    private JTextField itemNameInput = new JTextField(30);
    private JTextField itemCountInput = new JTextField(30);
    private JTextField itemPriceInput = new JTextField(30);
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");

    public InvoiceItemDialog(InvoiceFrame frame) {
        ok.setActionCommand("OkCreateItem");
        cancel.setActionCommand("CancelCreateItem");
        ok.addActionListener(frame::actionPerformed);
        cancel.addActionListener(frame::actionPerformed);
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Item Name"));
        add(itemNameInput);
        add(new JLabel("Item Count"));
        add(itemCountInput);
        add(new JLabel("Item Price"));
        add(itemPriceInput);
        add(ok);
        add(cancel);
        pack();
    }

    public JTextField getItemNameInput() {
        return this.itemNameInput;
    }

    public JTextField getItemCountInput() {
        return this.itemCountInput;
    }

    public JTextField getItemPriceInput() {
        return this.itemPriceInput;
    }
}
