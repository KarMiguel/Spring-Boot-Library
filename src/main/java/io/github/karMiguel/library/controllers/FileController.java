package io.github.karMiguel.library.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import io.github.karMiguel.library.services.FileStorageServices;
import io.github.karMiguel.library.vo.UploadFileResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

	private Logger logger = Logger.getLogger(FileController.class.getName());

	@Autowired
	private FileStorageServices service;

	@PostMapping("/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		logger.info("Storing file to disk");

		var filename = service.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/v1/downloadFile/")
				.path(filename)
				.toUriString();
		String fileDeleteUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/v1/deleteFile/")
				.path(filename)
				.toUriString();

		return new UploadFileResponseVO(
				filename, fileDownloadUri, fileDeleteUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(
			@RequestParam("files") MultipartFile[] files) {
		logger.info("Storing files to disk");

		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(
			@PathVariable String filename, HttpServletRequest request) {

		logger.info("Reading a file on disk");

		Resource resource = service.loadFileAsResource(filename);
		String contentType = "";

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}

		if (contentType.isBlank()) contentType = "application/octet-stream";

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(
						HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/listAllFiles")
	public List<UploadFileResponseVO> listAllFiles() {
		return service.listAllFiles().stream()
				.map(file -> new UploadFileResponseVO(
						file.getFileName(),
						file.getFileDownloadUri(),
						file.getFileDeleteUri(),
						file.getFileType(),
						file.getSize()))
				.collect(Collectors.toList());
	}

	@DeleteMapping("/deleteFile/{filename:.+}")
	public ResponseEntity<String> deleteFile(@PathVariable String filename) {
		service.deleteFile(filename);
		return ResponseEntity.ok().body("File " + filename + " deleted successfully");
	}
}
