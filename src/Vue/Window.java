package Vue;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Window extends JFrame {

	private final String WINDOW_NAME = "UA-SurveillanceTP";
	private final int WINDOW_WIDTH = 400;
	private final int WINDOW_HEIGHT = 300;

	public Window() {
		super();
		initUI();
		initComponents();
		setVisible(true);
	}

	/*
	 * Initialisation de la fenêtre
	 */
	private void initUI() {
		setTitle(WINDOW_NAME);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	/*
	 * Initialisation de la fenêtre
	 */
	private void initComponents() {
		setLayout(new BorderLayout());
	}
}
