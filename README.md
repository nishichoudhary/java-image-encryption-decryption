**ImgED (Image Encryption and Decryption)**

ImgED is a Java Swing application designed for encrypting and decrypting image files using the AES (Advanced Encryption Standard) algorithm in CBC (Cipher Block Chaining) mode.

### Features:
- **User-friendly Interface:** Simple GUI built with Java Swing components for easy interaction.
- **AES Encryption:** Utilizes the AES algorithm for robust encryption, ensuring security.
- **CBC Mode:** Operates in CBC mode, enhancing security by chaining blocks of plaintext.
- **Error Handling:** Provides clear error messages and status updates during encryption and decryption processes.

### Usage:
1. Launch the application.
2. Enter a 16-character encryption key.
3. Choose between encrypting or decrypting an image file.
4. Select the image file for encryption or the encrypted image file for decryption.
5. Encrypted images are saved as "encrypted_image.jpg," and decrypted images are saved as "decrypted_image.jpg."

### Requirements:
- Java Development Kit (JDK)
- Java Swing library

### Installation:
1. Clone the repository: `git clone https://github.com/nishichoudhary/java-image-encryption-decryption.git`
2. Compile the Java files: `javac ImgED.java`
3. Run the application: `java ImgED`

### License:
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Authors:
- [Nishi Choudhary](https://github.com/nishichoudhary)

### Acknowledgements:
- This project utilizes the AES algorithm provided by the Java Cryptography Extension (JCE).
- Special thanks to [Swing API Documentation](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html) for guidance on building the user interface.

Feel free to contribute to this project by submitting pull requests or reporting issues!
