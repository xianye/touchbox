package com.xc.touchbox.api.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.UploadView;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/upload")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class UploadifyController extends BaseAction {
	private static final Logger log = Logger
			.getLogger(UploadifyController.class);

	private String type = "image";// image:图片,video:视频
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String widthHeightLimit;// 宽高限制，格式json数组，如：[{w:200,h:150},...]
	private String filename;
	private Object upload;

	/**
	 * 文件上传处理
	 * 
	 * @throws 异常抛出
	 */
	@Action(value = "exec", results = { @Result(name = "success", type = "json", params = {
			"root", "getDataResp", "excludeNullProperties", "true" }) })
	public String execute() throws Exception {
		log.info("UploadifyController -> execute Start execution ");
		StringBuilder logBuilder = new StringBuilder("UploadifyController.uploadFile{");
		int errorCode = 0;
		String jsonResp = null;
		try {
			logBuilder.append("file:").append(file);

			String pathFileName = String.valueOf(System.currentTimeMillis()
					+ fileFileName.substring(fileFileName.lastIndexOf(".")));

			InputStream is = new FileInputStream(file);

			String fileDateDir = type + "/"
					+ DateFormatUtils.ISO_DATE_FORMAT.format(new Date());
			String root = ISysParam.RESOURCE_DIR_ROOTPATH + fileDateDir;
			File deskFile = new File(root, pathFileName);
			if (!deskFile.getParentFile().exists()) {
				deskFile.getParentFile().mkdirs();
			}
			deskFile.createNewFile();
			OutputStream os = new FileOutputStream(deskFile);
			byte[] bytefer = new byte[1024];
			int length = 0;
			while ((length = is.read(bytefer)) != -1) {
				os.write(bytefer, 0, length);
			}
			os.close();
			is.close();

			logBuilder.append(",widthHeightLimit:{params:").append(
					widthHeightLimit);
			boolean widthHeightAllowable = true;// 宽高允许的
			if ("image".equalsIgnoreCase(type)
					&& StringUtils.isNotEmpty(widthHeightLimit)) {// 判断图片宽高限制
				widthHeightAllowable = false;
				File picture = new File(deskFile.getPath());
				BufferedImage sourceImg = ImageIO.read(new FileInputStream(
						picture));

				logBuilder.append(",sourceImg:{w:")
						.append(sourceImg.getWidth()).append(",h:")
						.append(sourceImg.getHeight()).append("}");

				try {
					JSONArray whLimitArr = JSONArray
							.fromObject(widthHeightLimit);
					for (int i = 0; i < whLimitArr.size(); i++) {
						JSONObject param = whLimitArr.getJSONObject(i);
						if (param.containsKey("w")) {// 当前只限制宽
							int width = param.getInt("w");
							widthHeightAllowable = widthHeightAllowable
									|| (width == sourceImg.getWidth());
						}
						if (param.containsKey("h")) {// 当前只限制高
							int height = param.getInt("h");
							widthHeightAllowable = widthHeightAllowable
									|| (height == sourceImg.getHeight());
						}

						if (widthHeightAllowable) {
							logBuilder.append(",allowable:").append(
									param.toString());
							break;
						}
					}
				} catch (Exception e) {
					SimpleUtils.log(e, log);
					logBuilder.append(",exception:").append(e.getMessage());
				}

			}
			logBuilder.append("}");

			if (!widthHeightAllowable) {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_UPLOAD_IMAGE_WIDTHHEIGHT_ERROR;
			} else {
				UploadView a = new UploadView();
				a.setFilePath(fileDateDir + "/" + pathFileName);
				a.setType(type);
				a.setResourceDirUrl(ISysParam.RESOURCE_DIR_URL);
				a.setSourceFileName(fileFileName);
				getDataResp = new GetDataResponse(a, UploadView.class);
				jsonResp = JSONObject.fromObject(getDataResp).toString();
				return SUCCESS;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		} finally {
			baseResp.setErrorCode(errorCode);
			if (jsonResp == null) {
				jsonResp = JSONObject.fromObject(baseResp).toString();
			}

			logBuilder.append("},response:").append(jsonResp);
			log.info(logBuilder);
		}

		log.info("UploadifyController -> execute End execution ");

		return ERROR;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getWidthHeightLimit() {
		return widthHeightLimit;
	}

	public void setWidthHeightLimit(String widthHeightLimit) {
		this.widthHeightLimit = widthHeightLimit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Object getUpload() {
		return upload;
	}

	public void setUpload(Object upload) {
		this.upload = upload;
	}

}
