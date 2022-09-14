package halfbyte.app;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HexBytesTableModel extends AbstractTableModel {
    public static class HexBytesTableCellRenderer extends DefaultTableCellRenderer {
        // variables


        // methods
        public HexBytesTableCellRenderer(){

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // convert the value to a string
            String s = value == null ? "" : value.toString();

            // set text
            this.setText(s);

            // set color
            this.setBackground(Color.WHITE);

            // draw all of the cell
            this.setOpaque(true);

            // font
            this.setFont(table.getFont());

            // done
            return this;
        }
    }

    // variables
    private byte[] m_bytes;

    // methods
    public HexBytesTableModel(byte[] bytes){
        this.m_bytes = bytes;
    }

    public void changeBytes(byte[] bytes){
        this.m_bytes = bytes;
        this.fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getRowCount() {
        if (this.m_bytes == null){
            return 0;
        }

        int rows = this.m_bytes.length / 16;
        if (rows * 16 < this.m_bytes.length){
            rows += 1;
        }
        return rows;
    }

    @Override
    public int getColumnCount() {
        return 16;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // byte
        if (rowIndex * 16 + columnIndex >= this.m_bytes.length) {
            // beyond range of bytes
            return "";
        }

        // get the byte value and return the hex of it
        byte val = this.m_bytes[rowIndex * 16 + columnIndex];
        return Utils.byteToHexString(val);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // check if value is valid
        if (aValue == null){
            // ignore
            return;
        }

        // try to convert from hex to byte
        try{
            this.m_bytes[rowIndex * 16 + columnIndex] = Utils.hexStringToByte(aValue.toString());
            this.fireTableRowsUpdated(rowIndex, rowIndex);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getColumnName(int column) {
        return Utils.byteToHexString((byte) column);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
