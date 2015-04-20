/**
 * @author Miss. Caroline Thom (12001175)
 * @project Face Maniuplation 2015
 * @course	Bsc Honors degree in Computing 2014/2015
 */

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PictureMorph extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static BufferedImage bufferedImage = null;
	static File file = null;
	private static PictureMorph workFrame;
	private Image MeSmaller1;
	private Image MeSmaller2;
	protected Timer timer;
	private float alpha;

	// WorkFrame getter
	public static PictureMorph getWorkFrame() {
		return workFrame;
	}

	// Workframe setter
	public static void setWorkFrame(PictureMorph pictureMorph) {
		workFrame = pictureMorph;
	}

	// Implements and Names the buttons
	JPanel pnlButton = new JPanel();
	static JButton btnStartBlend = new JButton("Start Blend");
	static JButton btnStopBlend = new JButton("Stop Blend");
	static JButton saveImage = new JButton("Save Image To File");

	// organizes the frame of the application and adds the buttons and
	// ActionListeners
	public PictureMorph() {

		loadImages();
		initTimer();

		pnlButton.add(btnStartBlend);
		this.add(pnlButton);
		pnlButton.add(btnStopBlend);
		this.add(pnlButton);
		pnlButton.add(saveImage);
		this.add(pnlButton);

		addListeners();
	}

	// start button action listener
	public void addListeners() {
		btnStartBlend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initTimer();
				timer.start();

				System.out.println("Timer started");
			}
		});
		// stop button actionlistener
		btnStopBlend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();

				System.out.println("Timer stopped");
			}
		});

		// Save button actionlistener
		saveImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent saveImage) {
				save(bufferedImage, getWorkFrame());
				System.out.println("Image Saved to File successfully");
			}
		});
	}

	// loads which images will be used to do transformation from one to another
	// It would of be good if there was a list of images that could be selected
	// and then a variety of face blends could of been done, but didn't get to this
	// point unfortunately...
	private void loadImages() {

		MeSmaller1 = new ImageIcon("MeSmaller1.jpg").getImage();
		MeSmaller2 = new ImageIcon("MeSmaller2.jpg").getImage();
	}

	// Initializes the Timer and start and stop functions and the alpha
	// variable is initialized
	public void initTimer() {

		timer = new Timer(1000, this);
		timer.start();
		timer.stop();
		alpha = 1f;
	}

	// organizes the images within the frame and how they blend
	private void doDrawing(Graphics g) {
		Graphics2D g2Dim = (Graphics2D) g;

		// below sets the size of picture
		bufferedImage = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gBuffI = bufferedImage.createGraphics();

		// implements the AlphaComposite class
		AlphaComposite aComp = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, alpha);

		// decides where images are drawn in JFrame
		gBuffI.drawImage(MeSmaller1, 28, 55, null);
		gBuffI.setComposite(aComp);
		gBuffI.drawImage(MeSmaller2, 30, 48, null);
		g2Dim.drawImage(bufferedImage, 10, 10, null);
	}

	// bring Save File dialogue box into full operation and ..
	// .. allows you to choose wher to save to
	public static String getFileToSaveTo(PictureMorph pictureMorph) {
		JFileChooser fc = new JFileChooser("Save a File");
		fc.setDialogTitle("Save a file...");
		int returnVal = fc.showSaveDialog(pictureMorph);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}

	// Instructs for image within frame to be saved on push of button
	public static void save(BufferedImage img, PictureMorph pictureMorph) {

		String fileFullPath = getFileToSaveTo(pictureMorph);
		file = new File(fileFullPath);
		saveToFile(img, file);

	}

	// method allows saving of jpg and png files only or throws IOexception
	public static void saveToFile(BufferedImage img, File file) {
		String filename = file.getName();

		String suffix = filename.substring(filename.lastIndexOf('.') + 1);
		suffix = suffix.toLowerCase();

		if (suffix.equals("jpg") || suffix.equals("png")) {
			try {
				ImageIO.write(img, suffix, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: filename must end in .jpg or .png");
		}

	}

	// instructs the application to implement pain method
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	// Organizes how fast Timer ticks until it's stops and then prints
	// ... messgae to console to confirm
	@Override
	public void actionPerformed(ActionEvent e) {
		alpha -= 0.1;
		if (alpha <= 0) {
			alpha = 0;
			timer.stop();
			System.out.println("Morph Finished please restart.");
		}
		repaint();
	}

	// sets the PictureMorph size and Title and other GUI operations
	public static void main(String[] args) {
		JFrame theFrame = new JFrame("Image Morph");
		theFrame.setSize(400, 400);
		PictureMorph fr = new PictureMorph();
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.add(fr, BorderLayout.CENTER);
		theFrame.setVisible(true);
		PictureMorph.setWorkFrame(fr);
	}

}