/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.ui.pages.rider;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.motogymkhana.server.model.Country;
import eu.motogymkhana.server.model.Rider;
import eu.motogymkhana.server.model.Round;
import eu.motogymkhana.server.properties.GymkhanaUIProperties;
import eu.motogymkhana.server.ui.Constants;
import eu.motogymkhana.server.ui.annotations.RequiresLogin;
import eu.motogymkhana.server.ui.web.RidersServiceLocal;

@RequiresLogin
@Import(library = "FileUpload.js")
public class Profile {

	@Property
	private UploadedFile riderPicturefile;

	@Property
	private UploadedFile bikePicturefile;

	@Property
	private String text = "";

	@Property
	private String title = Constants.PROFILE_TITLE;

	@Property
	private String message;

	@Property
	private String resultCode;

	@Property
	@Persist
	private Rider rider;

	@Inject
	private RidersServiceLocal riderService;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Property
	@Persist
	private String username;

	@Property
	@Persist
	private String riderText;

	@Property
	@Persist
	private String riderBike;

	@Property
	@Persist
	private String imageUrl;

	@Property
	@Persist
	private String bikeImageUrl;

	@Persist
	private String fileDir;

	@Persist
	@Property
	private Country country;

	@Persist
	@Property
	private int season;

	@Property
	@Persist
	private int roundNumber;

	@Persist
	private String password;

	int maxRiderImageSize = 150;
	int maxBikeImageSize = 500;

	@Persist
	private String pictureUrl;

	void onActivate(String countryString, int season, String email, String password, int roundNumber) {

		this.season = season;
		country = Country.valueOf(countryString);
		this.username = email;
		this.password = password;
		this.roundNumber = roundNumber;
	}

	List<String> onPassivate() {

		List returnParams = new ArrayList();
		returnParams.add(country.name());
		returnParams.add(season);
		returnParams.add(roundNumber);

		return returnParams;
	}

	void onPrepareFromProfileForm() {
		riderText = rider.getText();
		riderBike = rider.getBike();
	}

	void onSuccessFromProfileForm() {

		rider.setText(riderText);
		rider.setBike(riderBike);

		try {
			riderService.updateRider(rider, username, password);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	void onSuccessFromUploadAvatarForm() {

		if (riderPicturefile != null) {
			String pictureFileName = writeFile(riderPicturefile, maxRiderImageSize, "rider");
			rider.setImageUrl(pictureUrl + pictureFileName);
			imageUrl = rider.getImageUrl();

			try {
				riderService.updateRider(rider, username, password);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	void onSuccessFromUploadBikeForm() {

		if (bikePicturefile != null) {
			String pictureFileName = writeFile(bikePicturefile, maxBikeImageSize, "bike");
			rider.setBikeImageUrl(pictureUrl + pictureFileName);
			bikeImageUrl = rider.getBikeImageUrl();

			try {
				riderService.updateRider(rider, username, password);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	void setupRender() {

		GymkhanaUIProperties.init();
		fileDir = GymkhanaUIProperties.getProperty("profile_pic_dir");
		pictureUrl = GymkhanaUIProperties.getProperty("profile_pic_url");
		if (!pictureUrl.endsWith("/")) {
			pictureUrl += "/";
		}

		if (rider == null) {
			try {
				rider = riderService.getRider(username, password, country, season);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				message = e.getClass().getSimpleName() + " " + e.getMessage();
			}

			if (rider != null) {
				riderText = rider.getText();
				riderBike = rider.getBike();
				imageUrl = rider.getImageUrl();
				bikeImageUrl = rider.getBikeImageUrl();
				title = Constants.PROFILE_TITLE + " " + rider.getFullName();
			} else {
				rider = new Rider();
			}
		}
	}

	private String writeFile(UploadedFile pictureFile, int maxSize, String defaultName) {

		String fileName = pictureFile.getFileName();
		String extension = fileName.contains(".") ? fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length()) : "";
		File file = new File(fileDir + "/" + defaultName + rider.getRiderNumberString() + "."
				+ extension);

		try {

			BufferedImage bufferedImage = ImageIO.read(pictureFile.getStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			if (width > height) {
				height = maxSize * height / width;
				width = maxSize;
			} else {
				width = maxSize * width / width;
				height = maxSize;
			}

			Image image = bufferedImage.getScaledInstance(width, height,
					BufferedImage.SCALE_DEFAULT);

			bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();

			ImageIO.write(bufferedImage, extension, file);
			return file.getName();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return null;
	}
}
