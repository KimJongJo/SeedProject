package seed.project.Manager.model.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import seed.project.Manager.mapper.SeedMapper;

@Service
@RequiredArgsConstructor
public class SeedServiceImpl implements SeedService{

	private final SeedMapper mapper;
	
}
