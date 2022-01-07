package gr.hua.ds.freetransportation.util;

import gr.hua.ds.freetransportation.entities.User;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static void saveMultipartFile(String uploadDirectory, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }

    }

    public static void saveBufferedImage(String uploadDirectory, BufferedImage image, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
//            Path filePath = uploadPath.resolve(fileName);
            File outputFile = new File(uploadPath + "/" + fileName);
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName);
        }

    }

    public static ByteArrayResource getQRCode(User user) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(Paths.get("user-qr-codes/"+user.getId()+"/barcode.jpg")));
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);

        try {
            Files.list(dirPath).forEach((file) -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        LOGGER.error("Could not delete file: " + file);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.error("Could not list files in dir : " + dirPath);
        }
    }

}
