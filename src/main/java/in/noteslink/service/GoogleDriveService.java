package in.noteslink.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//This Service Class is to Upload the File to our GDrive folder using Google Drive API which is configured using Gmail - noteslinkupload
@Service
public class GoogleDriveService {

    // --- CREDENTIALS HERE ---
    private static final String CLIENT_ID = "180210663874-3oeba856s764g0gc13f9voqb73hhfo9m.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REFRESH_TOKEN = "1//04WX8c82ts6qXCgYIARAAGAQSNwF-L9IrzeLjXM9ynmbMS2EaFMlGeetHAXKF7Z3YOvn7hltKSMVTPOjZ_M0oA3kmRM_wG2Cx8OY";

    public Map<String, String> uploadFile(MultipartFile multipartFile, String targetFolderId, String fileName) throws Exception {

        // 1. Create Credentials using Refresh Token (This impersonates YOU)
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(REFRESH_TOKEN)
                .build();

        // 2. Initialize the Drive Client
        Drive service = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("NotesLink")
                .build();

        // 3. Set File Metadata (Name and Parent Folder)
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(targetFolderId));

        // 4. Create Content from MultipartFile
        java.io.File tempFile = java.io.File.createTempFile("upload", null);
        multipartFile.transferTo(tempFile);
        FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

        // 5. Execute Upload
        // We request 'id', 'webViewLink' (view), and 'webContentLink' (download) fields
        File uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink, webContentLink")
                .execute();

        // 6. Set Permission to PUBLIC (Anyone with link can read)
        // This is crucial so students can view it without requesting access
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        service.permissions().create(uploadedFile.getId(), permission).execute();

        // 7. Cleanup Temp File
        if (tempFile.exists()) {
            tempFile.delete();
        }

        // 8. Return the Result
        Map<String, String> response = new HashMap<>();
        String fileId = uploadedFile.getId();
        response.put("fileId", fileId);
        response.put("url", uploadedFile.getWebViewLink());
        response.put("downloadUrl", uploadedFile.getWebContentLink());

        // --- GENERATE PREVIEW URL AUTOMATICALLY ---
        // This constructs the embeddable link for React iframe
        String previewUrl = "https://drive.google.com/file/d/" + fileId + "/preview";
        response.put("previewUrl", previewUrl);

        return response;
    }
}