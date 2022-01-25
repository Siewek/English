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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbManagerScene implements Initializable {

    @FXML private Label successLabel;
    @FXML private Label labelWarning;
    @FXML private TextField translationField;
    @FXML private TextField wordField;
    @FXML private RadioButton easyRadioBtn;
    @FXML private RadioButton hardRadioBtn;
    @FXML private RadioButton mediumRadioBtn;
    @FXML private TableView<Word> wordsTableView;
    @FXML private TableColumn<Word, String> wordCol;
    @FXML private TableColumn<Word, String> translationCol;
    @FXML private TableColumn<Word, String> difficultyCol;
    @FXML private TextField searchFilter;

    @FXML private Button deleteSelecetedBtn;

    private final ObservableList<Word> words = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationCol.setCellValueFactory(new PropertyValueFactory<>("translation"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        wordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        translationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        difficultyCol.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            words.addAll(sqliteFacade.getAllWords());

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
                    deleteSelecetedBtn.setDisable(false);
                else
                    deleteSelecetedBtn.setDisable(true);
            }
        });

        wordsTableView.setEditable(true);
        difficultyCol.setEditable(true);
        translationCol.setEditable(true);
        wordCol.setEditable(true);
    }

    @FXML public void OnDeleteItem(ActionEvent actionEvent) {

        Word selectedItem = wordsTableView.getSelectionModel().getSelectedItem();

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            sqliteFacade.deleteWord((int) selectedItem.getId());

            words.removeAll(selectedItem);
        } catch (SQLException exception) {
            exception.printStackTrace();
            exception.getCause();
        }

        wordsTableView.getSelectionModel().clearSelection();
    }

    @FXML public void OnAddWord(ActionEvent actionEvent) {
        if(wordField.getText().trim().isEmpty() || translationField.getText().trim().isEmpty()) {
            labelWarning.setVisible(true);
            successLabel.setVisible(false);
            labelWarning.setText("Fields cannot be empty!");
            return;
        }

        String difficulty = easyRadioBtn.isSelected() ? "easy" : (mediumRadioBtn.isSelected() ? "medium" : "hard" );
        Word word = new Word(0, wordField.getText().trim(),
                translationField.getText(), difficulty);

        try {
            SqliteFacade sqliteFacade = new SqliteFacade();
            long id = sqliteFacade.addWord(word);
            words.add( new Word(id, word.getWord(), word.getTranslation(), word.getDifficulty()));
        } catch(SQLException exception) {
            exception.printStackTrace();
            exception.getCause();
        }

        wordField.setText(null);
        translationField.setText(null);
        easyRadioBtn.setSelected(true);

        successLabel.setVisible(true);
        labelWarning.setVisible(false);
    }

    public void OnWordEditCommit(TableColumn.CellEditEvent<Word, String> wordStringCellEditEvent) {
        Word rowValue = wordStringCellEditEvent.getRowValue();
        Word updatedValue = new Word(rowValue.getId(), wordStringCellEditEvent.getNewValue(), rowValue.getTranslation(), rowValue.getDifficulty());
        words.set(words.indexOf(rowValue), updatedValue);

        this.UpdateWord(updatedValue);
    }

    public void OnTransEditCommit(TableColumn.CellEditEvent<Word, String> wordStringCellEditEvent) {
        Word rowValue = wordStringCellEditEvent.getRowValue();
        Word updatedValue = new Word(rowValue.getId(), rowValue.getWord(), wordStringCellEditEvent.getNewValue(), rowValue.getDifficulty());
        words.set(words.indexOf(rowValue), updatedValue);

        this.UpdateWord(updatedValue);
    }

    public void OnDiffEditCommit(TableColumn.CellEditEvent<Word, String> wordStringCellEditEvent) {
        Word rowValue = wordStringCellEditEvent.getRowValue();
        Word updatedValue = new Word(rowValue.getId(), rowValue.getWord(), rowValue.getTranslation(), wordStringCellEditEvent.getNewValue());
        words.set(words.indexOf(rowValue), updatedValue);

        this.UpdateWord(updatedValue);
    }

    private void UpdateWord(Word word) {
        try {
            SqliteFacade facade = new SqliteFacade();
            facade.updateWord((int) word.getId(), word);
        } catch (SQLException exception) {
            exception.printStackTrace();
            exception.getCause();
        }
    }
}
