import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Frame extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Image MeSmaller1;
	private Image MeSmaller2;
	private Timer timer;    
    private float alpha;   
	
	JPanel pnlButton = new JPanel();
	static JButton btnStartBlend = new JButton("Start Blend");
	static JButton btnStopBlend = new JButton ("Stop Blend");
	static JButton saveImage = new JButton ("Save Image To File");
	
	
	public Frame() {
		loadImages();
		initTimer();
		
		pnlButton.add(btnStartBlend);
		this.add(pnlButton);
		pnlButton.add(btnStopBlend);
		this.add(pnlButton);
		pnlButton.add(saveImage);
		this.add(pnlButton);
	
	}
		public static void placeComponents (JFrame frame)  {
		frame.setLayout(null);	
	
		ActionListener btnStartBlendListener = (ActionListener) new BtnStartBlendListener();
		btnStartBlend.addActionListener(btnStartBlendListener);
		
		ActionListener btnStopBlendListener = (ActionListener) new BtnStopBlendListener();
		btnStopBlend.addActionListener(btnStopBlendListener);
		
		ActionListener saveImageListener = (ActionListener) new SaveImageListener();
		saveImage.addActionListener(saveImageListener);
	}
	
	private void loadImages() {
		MeSmaller1 = new ImageIcon("MeSmaller1.jpg").getImage();
		MeSmaller2 = new ImageIcon("MeSmaller2.jpg").getImage();
	}

	private void initTimer() {
		timer = new Timer(1000, this);
		timer.start();
		alpha = 1f;
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2Dim = (Graphics2D) g;
		// below sets the size of picture
		BufferedImage buffImage = new BufferedImage(400, 600,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gBuffI = buffImage.createGraphics();
		AlphaComposite aComp = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, alpha);
		// decides where images are drawn in JFrame
		gBuffI.drawImage(MeSmaller1, 28,55, null);
		gBuffI.setComposite(aComp);
		gBuffI.drawImage(MeSmaller2, 30, 48, null);
		g2Dim.drawImage(buffImage, 10, 10, null);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

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
}