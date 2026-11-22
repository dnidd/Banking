module banking {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens bankApp to javafx.graphics, javafx.fxml;
}
