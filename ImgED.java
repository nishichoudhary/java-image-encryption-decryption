import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.SecureRandom;

public class ImgED extends JFrame implements ActionListener {
    private JPasswordField keyField; // Modified to use JPasswordField
    private JButton encryptButton, decryptButton;
    private JLabel statusLabel;

    public ImgED() {
        setTitle("Image Encryption and Decryption (CBC Mode)");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JLabel keyLabel = new JLabel("Enter Encryption Key (16 characters):");
        keyField = new JPasswordField(); // Use JPasswordField for password-like input
        encryptButton = new JButton("Encrypt Image");
        decryptButton = new JButton("Decrypt Image");
        statusLabel = new JLabel();

        encryptButton.addActionListener(this);
        decryptButton.addActionListener(this);

        panel.add(keyLabel);
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(statusLabel);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImgED app = new ImgED();
            app.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encryptButton) {
            encryptImage();
        } else if (e.getSource() == decryptButton) {
            decryptImage();
        }
    }

    private void encryptImage() {
        try {
            char[] keyChars = keyField.getPassword();
            String keyString = new String(keyChars);
            if (keyString.length() != 16) {
                statusLabel.setText("Key must be 16 characters long.");
                return;
            }

            // Convert the key to a SecretKey
            SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "AES");

            // Generate a random IV (Initialization Vector)
            SecureRandom random = new SecureRandom();
            byte[] ivBytes = new byte[16]; // IV size is 16 bytes for AES
            random.nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // Initialize the cipher for encryption in CBC mode with PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            // Choose an image file to encrypt
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File inputFile = fileChooser.getSelectedFile();
                FileInputStream inputStream = new FileInputStream(inputFile);
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                // Encrypt the image
                byte[] encryptedBytes = cipher.doFinal(inputBytes);

                // Save the encrypted image to a file
                FileOutputStream outputStream = new FileOutputStream("E:/encrypted_image.jpg");
                outputStream.write(ivBytes); // Write the IV as the first bytes
                outputStream.write(encryptedBytes);
                outputStream.close();

                statusLabel.setText("Image encrypted and saved as encrypted_image.jpg");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    private void decryptImage() {
        try {
            char[] keyChars = keyField.getPassword();
            String keyString = new String(keyChars);
            if (keyString.length() != 16) {
                statusLabel.setText("Key must be 16 characters long.");
                return;
            }

            // Convert the key to a SecretKey
            SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "AES");

            // Initialize the cipher for decryption in CBC mode with PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Choose the encrypted image file to decrypt
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File inputFile = fileChooser.getSelectedFile();
                FileInputStream inputStream = new FileInputStream(inputFile);

                // Read the IV (first 16 bytes of the file)
                byte[] ivBytes = new byte[16];
                inputStream.read(ivBytes);
                IvParameterSpec iv = new IvParameterSpec(ivBytes);

                // Initialize the cipher with the IV for decryption
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

                byte[] encryptedBytes = new byte[(int) (inputFile.length() - 16)];
                inputStream.read(encryptedBytes);

                // Decrypt the image
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                // Save the decrypted image to a file
                FileOutputStream outputStream = new FileOutputStream("E:/decrypted_image.jpg");
                outputStream.write(decryptedBytes);
                outputStream.close();

                statusLabel.setText("Image decrypted and saved as decrypted_image.jpg");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }
}
