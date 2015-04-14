import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PictureMorph extends JFrame {

	private static final long serialVersionUID = 1L;

	public PictureMorph() {
		initUI();
	}

	private void initUI() {
		setTitle("Facial Image Manipulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new Frame());
		// below set Frame Size around image
		setSize(380, 470);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PictureMorph picMorph = new PictureMorph();
				picMorph.setVisible(true);
			}
		});
	}
}
