import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 	@author Ivan Iliev
 *	form window that have a button for 
 *	download a content from cez.bg
 *	and save a bill file 
 */

public class BillsDownloader {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BillsDownloader window = new BillsDownloader();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		final Label lblNewLabel = new Label(shell, SWT.BORDER | SWT.WRAP);
		lblNewLabel.setBounds(10, 195, 226, 37);
		lblNewLabel.setText("New Label");
		
		Button btnGetTheBills = new Button(shell, SWT.NONE);
		btnGetTheBills.setBounds(242, 157, 164, 37);
		btnGetTheBills.setText("\u0412\u0437\u0435\u043C\u0438 \u0441\u043C\u0435\u0442\u043A\u0438\u0442\u0435 \u0437\u0430 \u0442\u043E\u043A");
		btnGetTheBills.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				billcez b1 = new billcez();
				lblNewLabel.setText("Сваля 48713");
				b1.GetMyBill("310187248713", "F4317C");
				lblNewLabel.setText("готово");
			}
		});
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(242, 209, 164, 23);
		btnNewButton.setText("\u0418\u0437\u0445\u043E\u0434");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 10, 408, 130);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Обект");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(203);
		tableColumn.setText("Клиентски номер");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("ПИН");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		
		
		

	}
}
