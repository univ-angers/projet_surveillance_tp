package com.surveillance.tp.utilitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Properties;

public class personnal_setting {
// goal : create personnal settings for the user
	private String port;
	private String adresse;
	private String data_dir;
	private InputStream inputStream;
	private FileOutputStream outputStream;
	private String propFileName = "setting.properties";

	public personnal_setting() {
		try {
			this.port = getPropValues("port");
			this.adresse = getPropValues("adresse");
			this.data_dir = getPropValues("nb_symbole");
			
		} catch (IOException e) {
			//set default setting in case of problem
			this.port = "8080";
			this.adresse = "127.0.0.1";
			this.data_dir = "/opt/data_dir/";
			e.printStackTrace();
		}
	}

	public void default_value(){
		this.port = "8080";
		this.adresse = "127.0.0.1";
		this.data_dir = "/opt/data_dir/";
	}

	private String getPropValues(String name_value) throws IOException {
		String get_result = "none";
		try {
			Properties prop = new Properties();

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			get_result = prop.getProperty(name_value);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return get_result;
	}

	public String getPort() {
		try {
			return this.getPropValues("port");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getAdresse() {
		try {
			return this.getPropValues("adresse");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getData_dir() {
		try {
			return this.getPropValues("data_dir");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
