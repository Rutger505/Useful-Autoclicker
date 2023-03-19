package textFieldFilter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * DocumentFilter to filter out letters
 */
public class TextFieldFilter extends DocumentFilter {
	private int limit;

	public TextFieldFilter(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Tries to parse to int
	 * @param text to try and parse
	 * @return if text is integer
	 */
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

		if (test(sb.toString()) && sb.length() <= limit) {
			super.insertString(fb, offset, string, attr);
		} else {
			// warn user this is not allowed
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString()) && sb.length() <= limit) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			// warn user this is not allowed
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
				// warn user this is not allowed
			}
		}
	}
}
