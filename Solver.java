//Anthony Xiang

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Solver extends Application {

	TextField[][] t = new TextField[16][16];

	public void start(Stage primaryStage) {
		// Create a pane and set its properties
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.setPrefWidth(1100);

		// Place nodes in the pane at positions column,row
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				t[i][j] = new TextField("x");
				t[i][j].setFont(Font.font("Verdana", 20)); 
				pane.add(t[i][j], i, j);

				if (j%4==3 && j < 13) 		// Add horizontal lines as the unit lines
					t[i][j].setStyle("-fx-border-color: red; -fx-border-width: 0 0 1 0");
			}
		}

		// Add vertical lines for unit lines
		for (int i =0;i < 16;i++) {
			for (int j = 1; j < 4;j++) {
				Line l = new Line(10,0, 10, 40);
				l.setStyle("-fx-stroke: red");
				pane.add(l, 4*j, i);
			}}

		Button solver = new Button("Solve");
		solver.setPrefWidth(200);
		pane.add(solver, 7, 16);
		GridPane.setHalignment(solver, HPos.RIGHT);

		Button clear = new Button("Clear");
		clear.setPrefWidth(200);
		pane.add(clear, 8, 16);
		GridPane.setHalignment(clear, HPos.RIGHT);

		Label a = new Label("Message");
		pane.add(a, 7, 18);
		GridPane.setHalignment(a, HPos.RIGHT);

		Label a2 = new Label("Board");
		pane.add(a2, 8, 18);
		GridPane.setHalignment(a2, HPos.LEFT);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Hexadoku Solver");
		primaryStage.setScene(scene);
		primaryStage.show();

		solver.setOnAction(e -> {
			Board b = new Board();

			for (int i = 0; i < 16; i++) 		// Get the input board
				for (int j = 0; j < 16; j++) 
					b.arr[i][j].setLetter(((TextField)getNodeFromGridPane(pane,i,j)).getText());

			if (b.isBoardLegal()) {
				b.solve(0, 0);				// Board gets solved
				a.setText("Board");
				a2.setText("Solved!");
			}
					
			else {
				a.setText("Bad");
				a2.setText("Input!");
			}

			for (int i = 0; i < 16; i++)       // Set text of solved board here
				for (int j = 0; j < 16; j++) 
					(t[i][j]).setText(b.arr[j][i].letter);
		});

		clear.setOnAction(e -> {    			// Reset board to x's
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					(t[i][j]).setText("x");
				}
			}
			a.setText("Message");
			a2.setText("Board");
		});
	}

	// Get the object from the gridpane, primarily for textfield
	private Node getNodeFromGridPane(GridPane gridPane, int r, int c) { 
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == c && GridPane.getRowIndex(node) == r)
				return node;
		}
		return null;
	}

	// Run the GUI
	public static void main(String[] args) {
		launch(args);
	}
}
