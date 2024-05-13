// console.log("메인 js 연결");


const seedNo = document.querySelectorAll(".seedNo");


document.querySelectorAll(".cartAdd").forEach(addBtn => {
    addBtn.addEventListener("click", () =>{

        if(memberNo == null){
            alert("로그인 후 이용해주세요");
            location.href = "/member/login";
            return;
        }
        
        // 이전 형제 엘리먼트에서 seedNo 가져오기
        const obj = {
            "seedNo" : addBtn.previousElementSibling.value,
            "memberNo" : memberNo
        }

        fetch("/cart/addCart",{
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {


            if(result > 0){
                alert("씨앗이 장바구니에 추가되었습니다!");
            }else{
                alert("씨앗 추가 실패...");
            }

        })


    })
})


