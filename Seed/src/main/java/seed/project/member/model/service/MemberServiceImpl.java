package seed.project.member.model.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor=Exception.class)
@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

}
