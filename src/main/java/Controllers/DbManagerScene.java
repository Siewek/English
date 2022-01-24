package Controllers;

import Data.Word;
import DesignPatterns.SqliteFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbManagerScene implements Initializable {

    @FXML private TableView<Word> wordsTableView;
    @FXML private TableColumn<Word, String> wordCol;
    @FXML private TableColumn<Word, ByteBuffer> translationCol;
    @FXML private TableColumn<Word, String> difficultyCol;
    @FXML private TextField searchFilter;

    private final ObservableList<Word> words = FXCollections.observableArrayList();
    private Word selectedWord = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationCol.setCellValueFactory(new PropertyValueFactory<>("translation"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            words.addAll(sqliteFacade.getWords());

        } catch (SQLException exception) {
            exception.printStackTrace();
            exception.getCause();
        }

        FilteredList<Word> filteredList = new FilteredList<>(words, p -> true);

        searchFilter.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(word -> {
                if(t1 == null || t1.isEmpty())
                    return true;

                String keyword = t1.toLowerCase();

                if(word.getWord().toLowerCase().contains(keyword) ||
                    word.getDifficulty().toLowerCase().contains(keyword) ||
                    word.getTranslation().toLowerCase().contains(keyword))
                        return true;

                return false;
            });
        });

        SortedList<Word> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(wordsTableView.comparatorProperty());

        wordsTableView.setItems(sortedList);

        wordsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word>() {
            @Override
            public void changed(ObservableValue<? extends Word> observableValue, Word word, Word t1) {
                if(t1 != null)
                    selectedWord = t1;
                else
                    selectedWord = null;
            }
        });
    }

    @FXML public void OnDeleteItem(ActionEvent actionEvent) {
    }
}
