import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class BtnStartBlendListener {
	
	public class btnStartBlendListener implements ActionListener {
		@Override 
		public void actionPerformed (ActionEvent e)   {
			JOptionPane.showMessageDialog(null, "Blend button has been pressed!");
			System.out.println("Blend Button has been pressed");
			
		}
	}

}
