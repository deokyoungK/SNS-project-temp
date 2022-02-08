/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

//(0)현재 로그인한 사용자 아이디
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;
function storyLoad() {
	$.ajax({
		url: `/api/image?page=${page}`,
		dataType: "json"
	}).done(res => {
		console.log(res);
		res.data.content.forEach((image) => {
			let item = getStoryItem(image);
			$("#storyList").append(item);
		})
	}).fail(error => {
		console.log(error);
	});
}

storyLoad();

function getStoryItem(image) {
	let item =
		`<div class="story-list__item">
		<div class="sl__item__header">
			<div>
				<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
					onerror="this.src='/images/person.jpeg'" />
			</div>
			<div>${image.user.username}</div>
		</div>

		<div class="sl__item__img">
			<img src="/upload/${image.postImageUrl}" />
		</div>

		<div class="sl__item__contents">
			<div class="sl__item__contents__icon">

				<button>`;

	if (image.likeState) {
		item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	} else {
		item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}


		item += `
				</button>
			</div>

			<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

			<div class="sl__item__contents__content">
				<p>${image.caption}</p>
			</div>

			<div id="storyCommentList-${image.id}">`;

				image.comments.forEach((comment)=>{
					item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
					<p>
						<b>${comment.user.username}</b> ${comment.content}
					</p>`;
					
				if(principalId == comment.user.id)
				{
					item+= `	<button onclick="deleteComment(${comment.id})">
										<i class="fas fa-times"></i>
									</button>`;
				}

					
				item +=`
				</div>`;
				})

		
			item += `
			</div>

			<div class="sl__item__input">
				<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
				<button type="button" onClick="addComment(${image.id})">게시</button>
			</div>

		</div>
	</div>`
	return item;
}

// (2) 스토리 스크롤 페이징하기 --> 문서의높이 - 윈도우높이 = 스크롤높이일때를 찾자
$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());


	if (checkNum < 1 && checkNum > -1) {
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);


	if (likeIcon.hasClass("far")) { //좋아요 클릭하면
		$.ajax({
			type: "post",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => {
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)+1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");

		}).fail(error => {
			console.log("오류", error);
		});
	} else {
		$.ajax({
			type: "delete",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => {
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)-1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error => {
			console.log("오류", error);
		});


	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type: "post",
		url: "/api/comment/",
		data: JSON.stringify(data), //(자바스크립트 데이터를 JSON으로 변환하여 보낸다.)
		contentType: "application/json; charset=utf-8",   //보낼 데이터의 형식
		dataType: "json" //응답받을 데이터의 형식
	}).done(res=>{
		
		let comment = res.data;
		
		let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b>${comment.user.username} :</b>
		      ${comment.content}
		    </p>
		    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
		  </div>
		`;
		commentList.prepend(content);
	}).fail(error=>{

		alert(error.responseJSON.data.content);
	});

	commentInput.val(""); //input 필드를 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		dataType: "json"
	}).done(res=>{
		console.log("성공",res);
		
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("오류",error);
	});
	
}







