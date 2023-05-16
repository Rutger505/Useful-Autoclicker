package textFieldFilter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * DocumentFilter to filter out letters
 */
public class TextFieldFilter extends DocumentFilter {
   private final int limit;

   /**
    * @param limit max length of text field
    */
   public TextFieldFilter(int limit) {
      this.limit = limit;
   }

   /**
    * Tries to parse to int
    *
    * @param text to try to parse
    * @return if text is integer
    */
   private boolean isInt(String text) {
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

      if (isInt(sb.toString()) && sb.length() <= limit) {
         super.insertString(fb, offset, string, attr);
      }
   }

   @Override
   public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
           throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);

      if (isInt(sb.toString()) && sb.length() <= limit) {
         System.out.println("(TextFieldFilter) string replaced");
         System.out.println(sb);
         super.replace(fb, offset, length, text, attrs);
      }
   }

   @Override
   public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);

      if (sb.toString().isEmpty()) {
         super.replace(fb, offset, length, "", null);
      } else {
         if (isInt(sb.toString())) {
            super.remove(fb, offset, length);
         }
      }
   }
}
