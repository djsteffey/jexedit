package halfbyte.app;

import javax.swing.table.AbstractTableModel;

public class HexAddressTableModel extends AbstractTableModel {
    // variables
    private int m_numRows;
    private int m_bytesPerRow;

    // methods
    public HexAddressTableModel(int numRows, int bytesPerRow){
        this.m_numRows = numRows;
        this.m_bytesPerRow = bytesPerRow;
    }

    public void changeNumRows(int numRows){
        this.m_numRows = numRows;
        this.fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getRowCount() {
        return this.m_numRows;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            // address
            return String.format("%08X", rowIndex * this.m_bytesPerRow);
        }
        else{
            return "<unknown>";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // not allowed
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0){
            return "Address";
        }
        else{
            return "<unknown>";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}

