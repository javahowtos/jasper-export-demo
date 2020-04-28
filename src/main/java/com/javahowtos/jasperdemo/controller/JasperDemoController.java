package com.javahowtos.jasperdemo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahowtos.jasperdemo.beans.SampleBean;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("api/document")
public class JasperDemoController {

	@GetMapping()
	public void getDocument(HttpServletResponse response) throws IOException, JRException {

		String sourceFileName = ResourceUtils
				.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "SampleJasperTemplate.jasper").getAbsolutePath();
		List<SampleBean> dataList = new ArrayList<SampleBean>();
		SampleBean sampleBean = new SampleBean();
		sampleBean.setName("some name");
		sampleBean.setColor("red");
		dataList.add(sampleBean);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
		Map parameters = new HashMap();
		JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, beanColDataSource);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
	}
}
