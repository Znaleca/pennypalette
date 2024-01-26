package penny_palette_tracker;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ExpenseIncomeTableModel extends AbstractTableModel {

    private final List<ExpenseIncomeEntry> entries;
    private final String[] columnNames = {"Date/Event","Description","Amount","Type"};

    public ExpenseIncomeTableModel(){
        entries = new ArrayList<>();
    }

    public void addEntry(ExpenseIncomeEntry entry){
        entries.add(entry);
        fireTableRowsInserted(entries.size()-1, entries.size()-1);
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExpenseIncomeEntry entry = entries.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> entry.getDate();
            case 1 -> entry.getDescription();
            case 2 -> entry.getAmount();
            case 3 -> entry.getType();
            default -> null;
        };
    }

    void removeEntry(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < entries.size()) {
            entries.remove(selectedRow);
            fireTableRowsDeleted(selectedRow, selectedRow);
        } else {
            throw new IndexOutOfBoundsException("Invalid row index: " + selectedRow);
        }
    }

    ExpenseIncomeEntry getEntry(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < entries.size()) {
            return entries.get(rowIndex);
        } else {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
    }
}
