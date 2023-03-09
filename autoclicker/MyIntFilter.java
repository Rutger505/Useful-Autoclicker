package autoclicker;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class MyIntFilter extends DocumentFilter {

	private int limit;

	MyIntFilter(int limit) {
		super();
		this.limit = limit;
	}

	private boolean test(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		
		System.out.println(doc);
		System.out.println(doc.getLength());
		if (test(sb.toString()) && doc.getLength() + string.length() <= limit) {
			super.insertString(fb, offset, string, attr);
		} else {
			System.out.println("mag niet");
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			// warn the user and don't allow the insert
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (sb.toString().length() == 0) {
			super.replace(fb, offset, length, "", null);
		} else {
			if (test(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				System.out.println(
						"AAAAAAAAAAAAAAHHHHHHHHHHHH ER IS IETS MIS MET DE INT FILTER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! AAAAAAAAAAAAAAAAAJJJJJKKKKKHHHHHHHHHHHHHH");
			}
		}
	}
}
