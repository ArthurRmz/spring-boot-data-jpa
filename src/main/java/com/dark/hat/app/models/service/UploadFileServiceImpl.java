package com.dark.hat.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("PathFoto: ".concat(pathFoto.toString()));
		Resource recurso = null;
		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: No se puede cargar la imagen: ".concat(pathFoto.toString()));
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString().concat("-").concat(file.getOriginalFilename());
		Path rootPath = getPath(uniqueFilename);
		log.info("RootPath: ".concat(rootPath.toString()));
		Files.copy(file.getInputStream(), rootPath);
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
