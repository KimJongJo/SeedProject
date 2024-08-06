# Seed_Project
```
씨앗 판매 쇼핑몰/커뮤니티 게시판 프로젝트,
언더 더 씨의 프로젝트 레포지토리입니다.

Spring Secuirty를 사용하여 사용자 비밀번호를 암호화 했습니다.
MyBatis를 사용하여 객체와 데이터베이스 간의 ID를 매핑해서
상품 관리(등록, 수정, 품절), 게시판 관리 기능(등록, 수정, 삭제, 조회)을 구현하였습니다.

인원 : 3명
프로젝트 기간 : 2024/04/29 ~ 2024/05/16
맡은 역할 : 관리자 페이지, 문의 게시판, 마이 페이지(회원탈퇴, 장바구니), 아이디/비밀번호 찾기
```
---

Table
![image](https://github.com/user-attachments/assets/111a2d63-4077-4263-87bd-914263c575aa)


<details>
<summary>
개발 환경
</summary>

  
| Environment | Detail |
| --- | --- |
| 환경 | Windows10 |
| 언어 | Java, Javascript |
| 프레임워크 / 라이브러리|  Spring Boot, Spring Security, HikariCP, MyBatis, Thymeleaf, Lombok |
| 빌드 도구 | Gradle
| 데이터베이스 | Oracle DB | 
| 툴 | Spring Tools 4, DBeaver, vsCode |
| WAS | Apache Tomcat |
| API | Kakao Map |
| 협업 | Github, ERD Cloud, Draw.io, Figma |

  
</details>

### 아이디 찾기

![image](https://github.com/user-attachments/assets/6c6b5d21-1f46-446f-a4a9-ae36b5ea7452)
이메일과 닉네임을 입력 한 뒤에 확인 버튼을 누르면 회원 테이블에서 입력한 회원정보가 존재하는지 먼저 확인합니다. <br>
존재하는 회원일 경우 입력한 이메일로 아이디를 보내줍니다.

<hr>

### 비밀번호 찾기

![image](https://github.com/user-attachments/assets/15ad5de7-5c56-4ff6-9220-cabee74e5f77)
아이디와 이메일을 입력한 뒤 발송버튼을 누르면 입력한 이메일로 인증번호를 보냅니다. <br>
받은 인증번호를 입력하고 인증을 받은 뒤에 찾기 버튼을 누르면 입력했던 아이디와 이메일이 회원 테이블에 존재하는지 확인합니다. <br>
존재하는 회원일 경우 비밀번호를 재설정 할 수 있는 모달창이 나타납니다. <br>
나타난 모달창에서 비밀번호를 변경하면 됩니다.

<hr>

### 회원 탈퇴
![image](https://github.com/user-attachments/assets/40a478ed-d40a-4917-840b-388a650ece64)
회원탈퇴 내용을 읽고 "동의합니다"를 체크해야지만 탈퇴하기 버튼을 눌렀을 때 탈퇴할 수 있는 모달창이 나타납니다. <br>
나타난 모달창에 비밀번호를 입력하고 탈퇴하기 버튼을 누르면 로그인한 회원은 탈퇴한 회원으로 변경되고 바로 로그아웃 됩니다.

<hr>

### 상품 검색, 정렬
![image](https://github.com/user-attachments/assets/b1c86a62-ef1a-4d76-8177-df4c29450242)
메인 검색창에 원하는 상품을 입력하게되면 키워드를 포함하는 모든 상품이 화면에 나타납니다. <br>
검색한 상품이 존재하지 않을 경우에는 "씨앗이 존재하지 않습니다." 라는 화면이 나타납니다.<br><br>
정렬 조건에는 높은 가격순, 낮은 가격순, 종류별, 이름순으로 정렬이 가능합니다.

<hr>

### 상품 등록
![image](https://github.com/user-attachments/assets/322399b8-2986-4549-a75b-a0a20cb82b66)
관리자 계정으로 로그인을 하게되면 씨앗관리 버튼이 생깁니다.<br>
그 안에는 씨앗등록, 가격수정, 씨앗품절 버튼이 있습니다. <br>
씨앗 등록으로 들어가서 씨앗의 정보들을 입력합니다.<br>
등록 버튼을 누르면 입력한 씨앗이 메인 상품 목록에 추가되어 화면에 표시됩니다.


<hr>

### 가격 수정
![image](https://github.com/user-attachments/assets/16d00c7e-825c-403b-afa5-42a428ff643c)
가격을 수정하고 싶은 씨앗의 이름을 입력해준 뒤 검색 버튼을 누르면 입력한 씨앗의 현재 판매되고 있는 금액이 input창 안에 나타납니다.<br>
채워진 input 창을 변경할 가격으로 수정한 뒤에 변경 버튼을 누르면 씨앗은 입력한 가격으로 판매됩니다.


<hr>

### 씨앗 품절
![image](https://github.com/user-attachments/assets/239fd313-894f-491d-8199-a4e9f5bf95ce)
씨앗 품절에는 품절 등록과 품절 취소로 나뉘어 집니다.<br>
품절시키고 싶은 씨앗의 이름을 입력해준 뒤 품절 버튼을 누르면 입력한 씨앗은 품절 상태가 되어 상품 목록에서 제외되어 메인 화면에 표시되지 않습니다. <br>
반대로 품절중인 씨앗을 판매하고 싶을때는 이름을 입력한 뒤에 판매 버튼을 누르면 상품 목록에 추가되오 표시됩니다.


<hr>

### 장바구니
![image](https://github.com/user-attachments/assets/0f38691e-ffbe-49ce-9107-e06839f709e0)
장바구니에 상품을 추가하는 방법으로는 메인페이지에서 추가하는 방법과 상품 상세페이지에서 추가하는 방법이 있습니다. <br>
장바구니로 들어가면 상품마다 체크버튼, 수량 증감버튼, 삭제버튼이 있습니다.<br>
체크버튼은 체크된 상품들만 포함하여 결제 금액을 계산합니다. <br>


<hr>

### 문의 게시판
![image](https://github.com/user-attachments/assets/51b1923e-84da-46a6-896d-fc42c944f5b4)
문의 게시판은 게시글 등록, 게시글 수정, 게시글 삭제가 가능합니다. <br>
조회수와 좋아요 기능, 댓글 기능도 구현했습니다. <br>
관리자는 모든 게시글, 댓글을 관리할 수 있도록 수정, 삭제가 가능합니다.





