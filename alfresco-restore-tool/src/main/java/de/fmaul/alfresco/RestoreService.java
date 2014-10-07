package de.fmaul.alfresco;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestoreService {

	@Autowired
	RestoreQueueDao restoreQueueDao;

	public void runRestore() {
		List<RestoreJob> jobs = restoreQueueDao.getJobs();

		for (RestoreJob job : jobs) {
			System.out.println("=======================");
			System.out.println(job.getNodeRef());
			System.out.println(job.getFilePath());
			
			if (uploadContentToRepo(job.getNodeRef(), job.getFilePath())) {
				restoreQueueDao.setStatus(job.getId(), "done");
			}
			else {
				restoreQueueDao.setStatus(job.getId(), "error");
			}
		}

	}

	private boolean uploadContentToRepo(String nodeRef, String filePath) {

		File file = new File("file.txt");
		HttpPost post = new HttpPost("http://localhost:8080/alfresco/service/upload");
		
		FileBody fileBody = new FileBody(file);
		StringBody stringBody1 = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
		StringBody stringBody2 = new StringBody("Message 2", ContentType.MULTIPART_FORM_DATA);
		// 
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("upfile", fileBody);
		builder.addPart("text1", stringBody1);
		builder.addPart("text2", stringBody2);
		HttpEntity entity = builder.build();
		//
		post.setEntity(entity);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpResponse response = httpclient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return true;
	}

}
