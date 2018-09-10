
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.store.casboxes.Cashbox;
import com.example.store.casboxes.CashboxManager;
import com.example.store.customers.Customer;
import com.example.store.customers.CustomerGenerator;
import com.example.store.customers.CustomerQueue;

public class StoreRunner {
	private final static Logger LOGGER = LoggerFactory.getLogger(StoreRunner.class);
	private final static int INITIAL_CASHBOX = 2;
	private JFrame frame;
	private final int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
	private ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
	private CustomerQueue customers = new CustomerQueue();
	private CashboxManager cashboxManager = new CashboxManager(executor, customers, INITIAL_CASHBOX);
	private JButton startButton;
	private JButton stopButton;
	private JPanel buttonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				StoreRunner window = new StoreRunner();
				window.frame.setVisible(true);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public StoreRunner() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Threading Centre");
		frame.setBounds(100, 100, 700, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		cashboxManager.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(cashboxManager, BorderLayout.CENTER);

		cashboxManager.setBackground(Color.LIGHT_GRAY);
		cashboxManager.setAutoscrolls(true);

		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(Color.ORANGE);

		startButton = new JButton("Open Shop");
		stopButton = new JButton("Close Shop");
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		startButton.addActionListener(event -> openShop(executor));
		stopButton.addActionListener(event -> closeShop(executor));
	}

	private void openShop(ExecutorService executor) {
		Runnable r = () -> {
			executor.execute(new CustomerGenerator(customers, cashboxManager, executor));
			executor.execute(cashboxManager);
			startButton.setText("Shop is opend");
			startButton.setEnabled(false);
			try {
				cashboxManager.drawShopping();
			} catch (InterruptedException e) {
				LOGGER.error("drawShopping is interrupted");
			}

		};
		executor.execute(r);
	}

	private void closeShop(ExecutorService executor) {
		Runnable r = () -> {
			executor.shutdownNow();
			stopButton.setText("Shop is closed");
			stopButton.setEnabled(false);
			LOGGER.info("it were created " + Customer.getCounter() + " customers");
			LOGGER.info("it were created " + Cashbox.getCounter() + " cashboxes");
			customers.getCustomers().clear();
			customers.getCustomers().clear();
			customers = null;
		};
		new Thread(r).start();
	}

}
