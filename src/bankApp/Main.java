/**
 * Program Entry Point
 * 
 * Class Main hold Stage, scenes & menu.
 * Scenes holds Controller associated to Scene.
 * Scene associated Controller gets associated View.
 */

package bankApp;
	
import bankApp.controller.BankCustomersController;
import bankApp.controller.CreateController;
import bankApp.controller.CustomerInfoController;
import bankApp.controller.EditCustomerController;
import bankApp.controller.LoginController;
import bankApp.controller.SaveLoadController;
import bankApp.controller.TransactionController;
import bankApp.model.BankLogic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	
	private BankLogic bankLogic = new BankLogic();
	private int width = 900;
	private int height = 700;
	private Stage primaryStage;
	
	
	/**
	 * Set start scene.
	 */
	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		try {
			// Check font name used by JavaFX.
			// Use JavaFX font name in CSS even if CSS complains with red marking.			
			Font medium = Font.loadFont(getClass().getResourceAsStream("/bankApp/files/ABCFavorit-Medium-Trial.otf"), 20);
			System.out.println("Font name used by JavaFX = " + medium.getName());
			
			Font mediumMono = Font.loadFont(getClass().getResourceAsStream("/bankApp/files/ABCFavoritMono-Medium-Trial.otf"), 20);
			System.out.println("Font name used by JavaFX = " + mediumMono.getName());
			
			// Set start scene / entry point.
			Scene startScene = loginScene();
			stage.setScene(startScene);
			stage.setTitle("Bank");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Create loginScene.
	 * @return loginScene.
	 */
	private Scene loginScene() {
		LoginController controller = new LoginController(this);
		
		BorderPane root = new BorderPane();
		root.setCenter(controller.getView());
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	/**
	 * Create transactionScene.
	 * @return transactionScene.
	 */
	private Scene transactionScene() {
		TransactionController controller = new TransactionController(bankLogic);
		// create and place menu at the top of BprderPane
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());

		
		// Scene title.
		Label lblScene = new Label("Transaction");
		lblScene.setId("sceneName-label");			// CSS id.
		
		// Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/transaction.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);
		
		// Create & place header.
		HBox sceneHeader = new HBox(lblScene);
		sceneHeader.setPadding(new Insets(10));
		sceneHeader.setAlignment(Pos.CENTER_LEFT);
		sceneHeader.setId("sceneName-header");		// CSS id.
		
		// Place header & View.
		VBox container = new VBox(sceneHeader, controller.getView());
		
		root.setCenter(container);
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	/**
	 * Create createScene.
	 * @return createScene.
	 */
	private Scene createScene() {
		CreateController controller = new CreateController(bankLogic);
		
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());
		
		// Scene title.
		Label lblScene = new Label("New customer & New account");
		lblScene.setId("sceneName-label");			// CSS id
		
		// Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/create.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);
		
		// Create & place header
		HBox sceneHeader = new HBox(lblScene);
		sceneHeader.setId("sceneName-header");		// CSS id.
		sceneHeader.setPadding(new Insets(10));
		sceneHeader.setAlignment(Pos.CENTER_LEFT);
		
		// Place header & View.
		VBox container = new VBox(sceneHeader, controller.getView());
		
		root.setCenter(container);
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	/**
	 * Create saveLoadScene.
	 * @return saveLoadScene.
	 */
	private Scene saveLoadScene() {
		SaveLoadController controller = new SaveLoadController(bankLogic, primaryStage);
		// create and place menu at the top of BorderPane.
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());
		
		// Scene title.
		Label lblScene = new Label("Save & Open");
		lblScene.setId("sceneName-label");			// CSS id.
		
		// Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/saveLoad.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);
		
		// Create & place header.
		HBox sceneHeader = new HBox(lblScene);
		sceneHeader.setId("sceneName-header");		// CSS id.
		sceneHeader.setPadding(new Insets(10));
		sceneHeader.setAlignment(Pos.CENTER_LEFT);
		
		// place header & View.
		VBox container = new VBox(sceneHeader, controller.getView());
		
		root.setCenter(container);
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		
		return scene;
	}

	
	/**
	 * Create customerInfoScene.
	 * @return customerInfoScene.
	 */
	private Scene customerInfoScene() {
		CustomerInfoController controller = new CustomerInfoController(bankLogic);
		
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());
		
		// Scene title.
		Label lblScene = new Label("Customer information");
		lblScene.setId("sceneName-label");			// CSS id.
		
		// Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/customerInfo.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);
		
		// Create & place header.
		HBox sceneHeader = new HBox(lblScene);
		sceneHeader.setId("sceneName-header");		// CSS id.
		sceneHeader.setPadding(new Insets(10));
		sceneHeader.setAlignment(Pos.CENTER_LEFT);
		
		// Place header & View.
		VBox container = new VBox(sceneHeader, controller.getView());
		
		root.setCenter(container);
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	/**
	 * Create bankCustomersScene.
	 * @return bankCustomersScene.
	 */
	private Scene bankCustomersScene() {
		BankCustomersController controller = new BankCustomersController(bankLogic);
		
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());
		
		// Scene title.
		Label lblScene = new Label("Bank customers");
		lblScene.setId("sceneName-label");			// CSS id.
		
		// Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/bankCustomers.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);
		
		// Create and place header.
		HBox sceneHeader = new HBox(lblScene);
		sceneHeader.setId("sceneName-header");		// CSS id.
		sceneHeader.setPadding(new Insets(10));
		sceneHeader.setAlignment(Pos.CENTER_LEFT);
		
		
		// Place header & View.
		VBox container = new VBox(sceneHeader, controller.getView());
		
		root.setCenter(container);
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	/**
	 * Create editCustomerScene.
	 * @return editCustomerScene.
	 */
	private Scene editCustomerScene() {
	    EditCustomerController controller = new EditCustomerController(bankLogic);

	    BorderPane root = new BorderPane();
	    root.setTop(createMenuBar());

	    // Scene title.
	    Label lblScene = new Label("Edit customer");
	    lblScene.setId("sceneName-label");			// CSS id.
	    
		 // Scene icon.
		Image icon = new Image(getClass().getResourceAsStream("/bankApp/files/editCustomer.png"));
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(20);
		iconView.setPreserveRatio(true);
		
		lblScene.setGraphic(iconView);
		lblScene.setGraphicTextGap(10);
		lblScene.setContentDisplay(ContentDisplay.RIGHT);

	    // Create and place header.
	    HBox sceneHeader = new HBox(lblScene);
	    sceneHeader.setId("sceneName-header");		// CSS id.
	    sceneHeader.setPadding(new Insets(10));
	    sceneHeader.setAlignment(Pos.CENTER_LEFT);

	    // Place header & View.
	    VBox container = new VBox(sceneHeader, controller.getView());

	    root.setCenter(container);
	    
	    Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/bankApp/files/style.css").toExternalForm());
		
		return scene;
	}
	
	
	
	/**
	 * Creating menu bar with menu items
	 * @return menu bar.
	 */
	private Node createMenuBar() {
		
		MenuBar menuBar = new MenuBar();
//	    menuBar.setTranslateX(-7);
	    
		// New menu.
		Menu fileMenu = new Menu("File");
		MenuItem transactiontItem = new MenuItem("Transaction");
		transactiontItem.setOnAction(e -> primaryStage.setScene(transactionScene())); // Send to scene on click.
		
		MenuItem createItem = new MenuItem("Create");
		createItem.setOnAction(e -> primaryStage.setScene(createScene())); // Send to scene on click.
		
		MenuItem saveItem = new MenuItem("Save/Open");
		saveItem.setOnAction(e -> primaryStage.setScene(saveLoadScene())); // Send to scene on click.
		
//		MenuItem openItem = new MenuItem("Open");
//		openItem.setOnAction(e -> primaryStage.setScene(openScene())); // send to scene on click.
		
		MenuItem quitBank = new MenuItem("Quit Bank");
		
		// New menu.
		Menu infoMenu = new Menu("Info/View");
		MenuItem customerInfoItem = new MenuItem("Customer info");
		customerInfoItem.setOnAction(e -> primaryStage.setScene(customerInfoScene())); // Send to scene on click.
		MenuItem allCustomersItem = new MenuItem("All customers");
		allCustomersItem.setOnAction(e -> primaryStage.setScene(bankCustomersScene())); // Send to scene on click.
		
		// new menu.
		Menu editMenu = new Menu("Edit");
		MenuItem editCustomerItem = new MenuItem("Edit customer");
		editCustomerItem.setOnAction(e -> primaryStage.setScene(editCustomerScene())); // Send to scene on click.
		
		// Add menu items to menus.
		fileMenu.getItems().addAll(transactiontItem, createItem, new SeparatorMenuItem(), saveItem, new SeparatorMenuItem(), quitBank);
		infoMenu.getItems().addAll(customerInfoItem, allCustomersItem);
		editMenu.getItems().addAll(editCustomerItem);
		
		menuBar.getMenus().addAll(fileMenu, editMenu, infoMenu);
		
		HBox menuWrapper = new HBox(menuBar);
		menuWrapper.setId("menu-wrapper");
		
		return menuWrapper;
	}
	
	/**
	 * Direct user to transaction scene from login
	 */
	public void setLogin() {
		primaryStage.setScene(transactionScene());
	}
	
	/**
	 * Main.
	 * @param args -.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
