package io.github.karMiguel.library.services;

import io.github.karMiguel.library.config.FileStorageConfig;
import io.github.karMiguel.library.exceptions.FileStorageException;
import io.github.karMiguel.library.exceptions.MyFileNotFoundException;
import io.github.karMiguel.library.vo.UploadFileResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageServices {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServices(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException(
                    "Could not create the directory where the uploaded files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Filename..txt
            if (filename.contains("..")) {
                throw new FileStorageException(
                        "Sorry! Filename contains invalid path sequence " + filename);
            }
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException(
                    "Could not store file " + filename + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
            else throw new MyFileNotFoundException("File not found");
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found" + filename, e);
        }
    }

    public List<UploadFileResponseVO> listAllFiles() {
        try {
            return Files.walk(this.fileStorageLocation, 1)
                    .filter(path -> !path.equals(this.fileStorageLocation))
                    .map(this.fileStorageLocation::relativize)
                    .map(path -> {
                        try {
                            String filename = path.getFileName().toString();
                            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/file/v1/downloadFile/")
                                    .path(filename)
                                    .toUriString();
                            String fileDeleteUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/file/v1/deleteFile/")
                                    .path(filename)
                                    .toUriString();
                            String fileType = Files.probeContentType(this.fileStorageLocation.resolve(path));
                            if (fileType == null && filename.toLowerCase().endsWith(".yml")) {
                                fileType = "text/yaml";
                            }
                            long size = Files.size(this.fileStorageLocation.resolve(path));

                            return new UploadFileResponseVO(filename, fileDownloadUri, fileDeleteUri, fileType, size);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to read file attributes", e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileStorageException("Failed to list files in the storage directory", e);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path targetPath = this.fileStorageLocation.resolve(filename);
            Files.deleteIfExists(targetPath);
        } catch (IOException e) {
            throw new FileStorageException("Failed to delete file: " + filename, e);
        }
    }
}
