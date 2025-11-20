package com.example.game_library_backend.service;

import com.example.game_library_backend.exception.customized.BadRequestException;
import com.example.game_library_backend.model.Game;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UploadImageService {

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    @Value("${file.upload-id}")
    private String imagesFolderId;

    @Value("${file.allowed-types}")
    private String allowedTypes;

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "a.json");
        return filePath.toString();
    }

    public void uploadImage(MultipartFile multipartFile, Game game) throws IOException, GeneralSecurityException {
        verifyImageExtension(multipartFile);

        File tempFile = File.createTempFile("temp", null);
        multipartFile.transferTo(tempFile);

        uploadImageToDrive(tempFile, game);
    }

    private void verifyImageExtension(MultipartFile file) {
        String contentType = file.getContentType();
        List<String> allowedMimeTypes = Arrays.asList(allowedTypes.split(","));

        List<String> allowedExtensions = allowedMimeTypes.stream()
                .map(mimeType -> mimeType.substring(mimeType.indexOf("/") + 1))
                .map(ext -> "." + ext)
                .toList();

        if (!allowedMimeTypes.contains(contentType)) {
            throw new BadRequestException("Invalid file type. Allowed types are: " + String.join(", ",
                    allowedExtensions));
        }
    }

    private void uploadImageToDrive(File file, Game game) throws GeneralSecurityException, IOException {

        try {
            String folderId = imagesFolderId;
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            FileContent mediaContent = new FileContent(allowedTypes, file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setSupportsAllDrives(true)
                    .setFields("id").execute();

            Permission permission = new Permission()
                    .setType("anyone")
                    .setRole("reader");
            drive.permissions().create(uploadedFile.getId(), permission).execute();

            String imageUrl = "https://drive.google.com/thumbnail?id=" + uploadedFile.getId() + "&sz=w2000";
            game.setImageUrl(imageUrl);

        } catch (Exception e) {
            throw new BadRequestException("Unable to upload image");
        }
    }

    private Drive createDriveService() throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new FileReader("credentials.json")
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        ).build();
    }
}