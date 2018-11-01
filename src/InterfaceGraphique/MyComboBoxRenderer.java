package InterfaceGraphique;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MyComboBoxRenderer<T> extends JLabel implements ListCellRenderer<T> {
	private static final long serialVersionUID = 1L;
	private String _title;

    public MyComboBoxRenderer(String title)
    {
        _title = title;
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
			boolean hasFocus) {
		if (index == -1 && value == null) setText(_title);
        else setText(value.toString());
        return this;
	}


}
