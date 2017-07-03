package co.aurasphere.batty.window;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import co.aurasphere.batty.utils.ColorUtils;
import co.aurasphere.batty.utils.Enums.NotificationType;

public class AlertWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static AlertWindow instance;

	private AlertWindow(int batteryPercentage, int lifeTime, NotificationType notification) {
		super("Batty");
		
		// Sets the icon.
		URL url = null;
		try {
			url = ClassLoader.getSystemResource("co/aurasphere/batty/resources/batty_icon.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);

		setLayout(new GridBagLayout());

		// It is best practice to set the window's shape in
		// the componentResized method. Then, if the window
		// changes size, the shape will be correctly recalculated.
		addComponentListener(new ComponentAdapter() {
			// Give the window an elliptical shape.
			// If the window is resized, the shape is recalculated here.
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(),
						getHeight(), 45, 45));
			}
		});

		setUndecorated(true);
		setSize(1000, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Sets a random color for the window.
		getContentPane().setBackground(ColorUtils.getRandomColor());
		
		// Shows a message according to the type of notification.
		String message = null;
		if(notification.equals(NotificationType.BATTERY_LOW))
			message = "<html><body><h1>Batteria quasi scarica (" + batteryPercentage + "%)</h1><p>Collega il caricabatterie.</p><p>Operatività residua: " + lifeTime + " minuti.</p></body></html>";
		else message = "<html><body><h1>Batteria carica (" + batteryPercentage + "%)</h1><p>Scollega il caricabatterie.</p><p>Operatività residua: " + lifeTime + " minuti.</p></body></html>";

		// Setting the windows text.
		JLabel label = new JLabel();
		label.setText(message);
		label.setForeground(Color.WHITE);
		
		// Creating the button.
		JButton button = new JButton("OK");
		button.setForeground(Color.WHITE);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AlertWindow.cleanup();
			}
		});
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20; // make this component tall
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		add(label, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.insets = new Insets(0, 500, 0, 0);
		c.gridx = 1; // aligned with button 2
		c.gridy = 1; // third row
		add(button, c);
	}

	private static void cleanup() {
		instance.dispose();
		instance = null;
	}
	
	public static void showWindow(int batteryPercentage, int lifeTime, NotificationType notification){
		// If the windows is already showing I won't show it again.
		if(instance == null){
			instance = new AlertWindow(batteryPercentage, lifeTime, notification);
			instance.toFront();
			instance.setAlwaysOnTop(true);
			// Display the window.
			instance.setVisible(true);
		}
	}

}