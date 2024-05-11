package seed.project.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardImg {
	
	private int boardImgNo;
	private String boardImgPath;
	private String boardImgOriginalName;
	private String boardImgRename;
	private int boardImgOrder;
	private int boardNo;
	
	// 게시글 이미지 삽입/수정 때 사용
	private MultipartFile uploadFile;
	
}