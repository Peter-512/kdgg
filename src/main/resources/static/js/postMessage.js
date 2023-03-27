import {vote} from "./vote.js";
import csrfHeader from "./csrfHeader.js";
import {deletePost} from "./deleteHoverButton.js";

/**
 *
 * @type {HTMLFormElement}
 */
const addPostForm = document.querySelector("#add-post-form");
const submitButton = document.querySelector("#send-message");
const messageContent = document.querySelector("#message-input");
const role = document.querySelector("body").dataset.role;
const channelID = +document.querySelector("body").dataset.channelId;

addPostForm.onsubmit = (event) => event.preventDefault();

/**
 *
 * @param event {Event}
 */
async function postMessage(event) {
	const isValid = addPostForm.checkValidity();
	addPostForm.classList.add("was-validated");

	if (!isValid) return;

	const content = messageContent.value;
	const url = `/api/posts`;
	const data = {content, channelID};

	const {name, value} = csrfHeader();

	const res = await fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			"Accept": "application/json",
			[name]: value
		},
		body: JSON.stringify(data)
	});

	if (res.status === 404) {
		return alert("Channel not found");
	}

	if (res.status === 400) {
		return alert("Invalid message");
	}

	/**
	 *
	 * @type {{postID:number, content:string, username:string, userID: number, upVotes: number, postedAt: string}}
	 */
	const json = await res.json();
	const {postID, content: message, username, userID, upVotes, postedAt} = json;

	const date = Date.parse(postedAt);

	let formattedDate;
	const timeDiff = Date.now() - date;
	const oneDay = 1000 * 60 * 60 * 24;
	const timeString = new Date(date).toLocaleTimeString("en-US", {
		hour: "numeric",
		minute: "numeric",
		hour12: false
	});
	if (timeDiff < oneDay) {
		formattedDate = `today at ${timeString}`;
	} else if (timeDiff < oneDay * 2) {
		formattedDate = `yesterday at ${timeString}`;
	} else {
		formattedDate = new Date(date).toLocaleString("en-US", {
			year: "numeric",
			month: "long",
			day: "numeric",
			hour: "numeric",
			minute: "numeric",
			hour12: false
		});
	}

	const post = document.createElement("article");
	post.classList.add("d-flex", "py-2", "justify-content-between", "post");
	post.innerHTML = `
			<div class="d-flex">
				<div class="me-2 d-flex flex-md-row flex-column align-items-center justify-content-md-around"
 					style="width: 50px"
 					id="post_${postID}">
					<button class="btn p-0 upvote" type="submit">
						<i class="bi bi-arrow-up text-success"></i>
					</button>
					<span class="text-warning">${upVotes}</span>
					<button class="btn p-0 downvote">
						<i class="bi bi-arrow-down text-danger"></i>
					</button>
				</div>
				<div style="width: calc(100% - 50px)">
					<a class="text-decoration-none" href="/users/${userID}">
						${username}
					</a>
					<span class="text-secondary">
						${formattedDate}
					</span>
					<p class="m-0">
						${message}
					</p>
				</div>
			</div>
				${role === "[ROLE_MOD]" || role === "[ROLE_ADMIN]" ? "<button class='btn btn-outline-danger visually-hidden delete'>" +
		"<i class='bi bi-trash-fill'></i>" +
		"</button>" : ""}
	`;

	const deleteButton = post.querySelector(".delete");
	if (deleteButton) {
		deleteButton.dataset.postId = `${postID}`;
		deleteButton.addEventListener("click", async () => {
			await deletePost(deleteButton);
		});

		post.addEventListener("mouseover", () => {
			deleteButton.classList.remove("visually-hidden");
		});
		post.addEventListener("mouseout", () => {
			deleteButton.classList.add("visually-hidden");
		});
	}

	const postList = document.querySelector("main");
	postList.append(post);

	document.querySelectorAll(`.upvote, .downvote`).forEach(button => button.addEventListener("click", vote));

	addPostForm.reset();
	addPostForm.classList.remove("was-validated");
	messageContent.blur();
}


submitButton.addEventListener("click", postMessage);

messageContent.addEventListener("keyup", (event) => {
	const isValid = addPostForm.checkValidity();
	messageContent.classList.remove(isValid ? "is-invalid" : "is-valid");
	messageContent.classList.add(isValid ? "is-valid" : "is-invalid");
});

window.addEventListener("keydown", (event) => {
	messageContent.focus();

	if (event.key === "Escape") {
		messageContent.blur();
		messageContent.classList.remove("is-invalid");
		addPostForm.classList.remove("was-validated");
		return;
	}

	if (event.key === "Enter") return postMessage(event);
});

document.querySelectorAll(`.upvote, .downvote`).forEach(button => button.addEventListener("click", vote));
