import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessWordGame extends JFrame {

    private JLabel wordLabel;
    private JTextField guessTextField;
    private JButton submitButton;
    private JLabel messageLabel;
    private String[] words = {"java", "swing", "programming", "computer", "game"};
    private String currentWord;
    private StringBuilder maskedWord;
    private int attemptsLeft;
    private boolean gameFinished;

    public GuessWordGame() {
        setTitle("Guess the Word Game");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        wordLabel = new JLabel();
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        guessTextField = new JTextField();
        guessTextField.setFont(new Font("Arial", Font.PLAIN, 18));

        submitButton = new JButton("Submit Guess");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameFinished) {
                    checkGuess();
                } else {
                    startNewGame();
                }
            }
        });

        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(wordLabel);
        add(guessTextField);
        add(submitButton);
        add(messageLabel);

        startNewGame();
    }

    private void startNewGame() {
        currentWord = getRandomWord();
        maskedWord = new StringBuilder(generateMaskedWord(currentWord));
        attemptsLeft = 6;
        gameFinished = false;
        updateWordLabel();
        guessTextField.setText("");
        messageLabel.setText("Guess the word! Attempts left: " + attemptsLeft);
        submitButton.setText("Submit Guess");
    }

    private String getRandomWord() {
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    private String generateMaskedWord(String word) {
        StringBuilder maskedWord = new StringBuilder();
        Random random = new Random();
        for (char letter : word.toCharArray()) {
            maskedWord.append(random.nextBoolean() ? letter : '_');
        }
        return maskedWord.toString();
    }

    private void updateWordLabel() {
        wordLabel.setText(maskedWord.toString());
    }

    private void checkGuess() {
        String guess = guessTextField.getText().toLowerCase().trim();
        if (guess.equals(currentWord)) {
            messageLabel.setText("Congratulations! You guessed the word!");
            submitButton.setText("Play Again");
            gameFinished = true;
        } else {
            attemptsLeft--;
            guessTextField.setText("");
            if (attemptsLeft <= 0) {
                messageLabel.setText("Game Over! The word was: " + currentWord);
                submitButton.setText("Play Again");
                gameFinished = true;
            } else {
                messageLabel.setText("Incorrect guess! Try Again Attempts left: " + attemptsLeft);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessWordGame().setVisible(true);
            }
        });
    }
}
