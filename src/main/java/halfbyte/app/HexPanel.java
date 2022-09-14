package halfbyte.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HexPanel extends JPanel {
    // variables
    private JTable m_addressTable;
    private JTable m_bytesTable;
    private JTable m_textTable;

    // methods
    public HexPanel() {
        // layout and border of this panel
        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.BLACK, 1));

        // one panel to rule them all...for the sub-panels
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        container.setBackground(Color.BLACK);

        // address table
        this.m_addressTable = new JTable(new HexAddressTableModel(0, 16));
        this.m_addressTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        this.m_addressTable.setShowGrid(false);
        this.m_addressTable.getColumnModel().getColumn(0).setMinWidth(96);
        this.m_addressTable.getColumnModel().getColumn(0).setMaxWidth(96);

        // bytes table
        this.m_bytesTable = new JTable(new HexBytesTableModel(null));
        this.m_bytesTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        this.m_bytesTable.setDefaultRenderer(String.class, new HexBytesTableModel.HexBytesTableCellRenderer());
        this.m_bytesTable.setShowGrid(false);
        for (int i = 0; i < 16; ++i){
            this.m_bytesTable.getColumnModel().getColumn(i).setMaxWidth(32);
        }
        this.m_bytesTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // get handle to the text table model
                AbstractTableModel atm = (AbstractTableModel) HexPanel.this.m_textTable.getModel();

                // inform the model that the rows have changed
                atm.fireTableRowsUpdated(e.getFirstRow(), e.getLastRow());
            }
        });

        // text table
        this.m_textTable = new JTable(new TextTableModel(null));
        this.m_textTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        this.m_textTable.setDefaultRenderer(char.class, new TextTableModel.TextTableCellRenderer());
        this.m_textTable.setShowGrid(false);
        for (int i = 0; i < 16; ++i){
            this.m_textTable.getColumnModel().getColumn(i).setMaxWidth(16);
        }

        // add to container
        container.add(this.m_addressTable, new GridBagConstraints(
                0, 0,
                1, 1,
                0, 1,
                GridBagConstraints.WEST,
                GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2),
                0, 0
        ));
        container.add(this.m_bytesTable, new GridBagConstraints(
                1, 0,
                1, 1,
                0, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2),
                0, 0
        ));
        container.add(this.m_textTable, new GridBagConstraints(
                2, 0,
                1, 1,
                1, 1,
                GridBagConstraints.EAST,
                GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2),
                0, 0
        ));

        // scroller for the container
        JScrollPane scrollPane = new JScrollPane(container);
        this.add(scrollPane, new GridBagConstraints(
                0, 0,
                1, 1,
                1, 1,
                GridBagConstraints.WEST,
                GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2),
                0, 0
        ));
    }

    public void loadFile(String filename) throws IOException {
        // load the file into bytes
        byte[] bytes = Files.readAllBytes(Paths.get(filename));

        // set bytes on hex and text
        ((HexBytesTableModel)this.m_bytesTable.getModel()).changeBytes(bytes);
        ((TextTableModel)this.m_textTable.getModel()).changeBytes(bytes);
        ((HexAddressTableModel)this.m_addressTable.getModel()).changeNumRows(bytes.length % 16 == 0 ? bytes.length / 16 : bytes.length / 16 + 1);
    }

    public void closeFile(){
        // set bytes on hex and text
        ((HexBytesTableModel)this.m_bytesTable.getModel()).changeBytes(null);
        ((TextTableModel)this.m_textTable.getModel()).changeBytes(null);
        ((HexAddressTableModel)this.m_addressTable.getModel()).changeNumRows(0);
    }
}
