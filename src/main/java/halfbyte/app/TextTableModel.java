package halfbyte.app;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TextTableModel extends AbstractTableModel {
    public static class TextTableCellRenderer extends DefaultTableCellRenderer {
        // variables


        // methods
        public TextTableCellRenderer(){

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // it is a char
            char c = (char) value;

            // determine if we need to print a dot
            switch (c){
                case 0x00:
                case 0x0A:{
                    this.setText(".");
                } break;
                default:{
                    if (table.getFont().canDisplay(c)) {
                        this.setText(String.valueOf(c));
                    }
                    else{
                        this.setText(".");
                    }
                } break;
            }

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
    public TextTableModel(byte[] bytes){
        this.m_bytes = bytes;
    }

    public void changeBytes(byte[] bytes){
        this.m_bytes = bytes;
        this.fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return char.class;
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
            return ' ';
        }

        // get the byte
        return (char)this.m_bytes[rowIndex * 16 + columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // check if value is valid
        if (aValue == null){
            // ignore
            return;
        }

        this.m_bytes[rowIndex * 16 + columnIndex] = (byte) aValue;
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

