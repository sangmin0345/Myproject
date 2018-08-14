package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

// 다운로드 처리할 파일
public class DownloadView extends AbstractView{

	public DownloadView() {
		setContentType("application/download; charset=utf-8");
	}
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
											HttpServletRequest request, 
											HttpServletResponse response)
			throws Exception {
		
		// 컨트롤러에서 넘긴 파일 객체를 받아줌
		File file = (File) model.get("downloadFile");
		
		// 클라이언트에게 전달할 파일 정보 설정
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		
		// response 헤더에 정보 설정
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String filename = null;
		
		if(ie){
			// 파일에 있는 정보를 읽어 드림
			filename = URLEncoder.encode(file.getName(), "utf-8");
		} else {
			// 파일명 패턴이 캐릭터셋이 아니면 바꿈
			filename = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
		}
		
		// 응답 헤더 설정
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
		
		//---------------------------------------------------------------------------------------------
		
		// 설정이 완료되었다면 클라이언트 측으로 파일에있는 데이터를 내보내기 위하여 출력 스트림통로 설정
		OutputStream out = response.getOutputStream();
		
		// 파일의 데이터를 읽어들이기 위한 스트림 통로를 담을 변수
		FileInputStream fis = null;
		
		try {
			// file에 있는 데이터를 읽어 들인다.
			fis = new FileInputStream(file);
			
			// fis를 out에 복사 -> fis를 읽고 out방출
			FileCopyUtils.copy(fis, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null){
				fis.close(); // 파일의 데이터를 읽어 들이기 위한 통로 해제
			}
		}
		// 실제 데이트럴 내보내기 즉시
		out.flush();
		
	}
	
	
}
