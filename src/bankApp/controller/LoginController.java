/**
 * LoginController
 * 
 * Mock View/controller.
 * Class handles no logic.
 * Class connects Main(scene) and View.
 */


package bankApp.controller;

import bankApp.Main;
import bankApp.view.LoginView;
import javafx.scene.layout.GridPane;

public class LoginController {
	private LoginView view;
	private Main main;
	
	/**
	 * constructor.
	 * @param main.
	 */
	public LoginController(Main main) {
		this.view = new LoginView(this);
		this.main = main;
	}
	
	/**
	 * Get view.
	 * @return root/pane/view.
	 */
	public GridPane getView() {
		return view.getRoot();
	}
	
	/**
	 * Login.
	 * Direct user to transaction scene from login.
	 */
	public void btnLogin() {
		// Tests call back to View.
		System.out.println("Login");
		main.setLogin();
	}
}
