package seed.project.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import seed.project.board.model.mapper.BoardMapper;

@Service
@Transactional
@RequiredArgsConstructor 
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	
	// 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}
}
