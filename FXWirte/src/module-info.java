/**
 * 
 */
/**
 * 
 */
module FXWirte {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.junit.jupiter.api;

	opens view to javafx.fxml;

	exports view;
}