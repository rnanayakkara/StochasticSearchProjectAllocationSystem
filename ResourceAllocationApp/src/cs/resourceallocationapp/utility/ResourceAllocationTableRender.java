package cs.resourceallocationapp.utility;

import java.awt.Color;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ResourceAllocationTableRender extends DefaultTableCellRenderer {

	public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		Object val = table.getValueAt(row, 2);
		String sval = val.toString();
		sval = sval.replaceAll(":", "");
		
		if (sval == "No") {
			cellComponent.setForeground(Color.red);
		} else {
			cellComponent.setBackground(Color.white);
			cellComponent.setForeground(Color.black);
		}
		if (isSelected) {
			cellComponent.setForeground(table.getSelectionForeground());
			cellComponent.setBackground(table.getSelectionBackground());
		}

		return cellComponent;

	}
}
