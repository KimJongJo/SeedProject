// console.log("씨앗 연결");


// 씨앗 등록
const seedName = document.getElementById("seedName");   // 씨앗 이름
const seedPrice = document.getElementById("seedPrice"); // 씨앗 가격
const seedTemp = document.getElementById("seedTemp");   // 씨앗 온도
const seedTime = document.getElementById("seedtime");   // 모종 시기
const seedDistance = document.getElementById("seedDistance");   // 재식 거리
const seedRate = document.getElementById("seedRate");   // 발아율

const addBtn = document.getElementById("addBtn");   // 등록 버튼


// 씨앗 가격 수정
const updateSeedName = document.getElementById("updateSeedName");   // 씨앗 이름
const searchBtn = document.getElementById("seedSearchPrice");   // 검색 버튼

const updateSeedPrice = document.getElementById("updateSeedPrice"); // 씨앗 가격
const updateBtn = document.getElementById("seedUpdatePrice");   // 변경 버튼


// 씨앗 품절 여부
const deleteSeedName1 = document.getElementById("deleteSeedName1"); // 품절 등록 씨앗 이름
const deleteSeedName2 = document.getElementById("deleteSeedName2"); // 품절 취소 씨앗 이름

const deleteBtn1 = document.getElementById("deleteBtn1");   // 풀절 버튼
const deleteBtn2 = document.getElementById("deleteBtn2");   // 풀절 취소 버튼



// // 씨앗 등록
if(addBtn != null){
    seedAdd.addEventListener("click", (e) => {


        if(seedName.value == "" || seedPrice.value == "" || seedTemp.value == "" || seedTime.value == "" || seedDistance.value == "" || seedRate.value == ""){
            alert("모든 항목을 채워주세요");
            e.preventDefault();
        }

    });
}

// 씨앗 가격 검색
if(searchBtn != null){
    searchBtn.addEventListener("click", () => {

        if(updateSeedName.value == ""){
            alert("씨앗 이름을 입력해주세요");
            return;
        }
    
    
        fetch("/seed/seedPrice?seedName=" + updateSeedName.value)
        .then(resp => resp.text())
        .then(result => {
            updateSeedPrice.value = result;
        })
    
    });
}



// 씨앗 가격 수정
if(updateBtn != null){
    updateBtn.addEventListener("click", () => {

        if(updateSeedName.value == ""){
            alert("씨앗 이름을 입력해주세요");
            return;
        }
    
        if(updateSeedPrice.value == ""){
            alert("가격을 입력해주세요");
            return;
        }
    
        const obj = {
            "seedName" : updateSeedName.value,
            "seedPrice" : updateSeedPrice.value
        }
    
        fetch("/seed/seedUpdatePrice", {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {
    
            console.log(result);
    
            
            if(result > 0){
                alert("씨앗 가격 수정 완료");
            }else{
                alert("씨앗 가격 수정 실패...");
            }
    
            
    
            updateSeedName.value = "";
            updateSeedPrice.value = "";
    
        })
    
    });
}




// const deleteSeedName1 = document.getElementById("deleteSeedName1"); // 품절 등록 씨앗 이름
// const deleteSeedName2 = document.getElementById("deleteSeedName2"); // 품절 취소 씨앗 이름

// const deleteBtn1 = document.getElementById("deleteBtn1");   // 풀절 버튼
// const deleteBtn2 = document.getElementById("deleteBtn2");   // 풀절 취소 버튼


// 씨앗 품절
if(deleteBtn1 != null){
    deleteBtn1.addEventListener("click", () => {

        if(deleteSeedName1.value == ""){
            alert("씨앗 이름을 입력해주세요");
            return;
        };

        fetch("/seed/seedDeleteOn", {
            method : "DELETE",
            headers : {"Content-Type" : "application/json"},
            body : deleteSeedName1.value
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0){
                alert(deleteSeedName1.value + "씨앗을 품절시켰습니다.");
            }else{
                alert(deleteSeedName1.value + "씨앗은 이미 품절 상태 입니다.");
            }

            deleteSeedName1.value = "";
        })

    });
}

// 씨앗 품절 취소
if(deleteBtn2 != null){
    deleteBtn2.addEventListener("click", () =>{

        if(deleteSeedName2.value == ""){
            alert("씨앗 이름을 입력해주세요");
            return;
        };

        fetch("/seed/seedDeleteOff", {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : deleteSeedName2.value
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0){
                alert(deleteSeedName2.value + "씨앗을 판매합니다.");
            }else{
                alert(deleteSeedName2.value + "씨앗은 이미 판매중 입니다.");
            }

            deleteSeedName2.value = "";
        })
    })
}