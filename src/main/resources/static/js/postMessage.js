/**
 *
 * @type {HTMLFormElement}
 */
const addPostForm = document.querySelector("#add-post-form");
const submitButton = document.querySelector("#send-message");
const messageContent = document.querySelector("#message-input");

addPostForm.onsubmit = (event) => event.preventDefault();

/**
 *
 * @param event {Event}
 */
async function postMessage(event) {
	const isValid = addPostForm.checkValidity();
	addPostForm.classList.add("was-validated");

	isValid ? messageContent.classList.add("is-valid") : messageContent.classList.add("is-invalid");
	isValid ? messageContent.classList.remove("is-invalid") : messageContent.classList.remove("is-valid");

	if (!isValid) return;

	const content = messageContent.value;
	const channelID = window.location.href.substring(window.location.href.lastIndexOf("/") + 1);
	const url = `/api/channels/${channelID}/posts`;
	const data = {content};
	const res = await fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			"Accept": "application/json"
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
	post.classList.add("d-flex", "flex-row", "my-3");
	post.innerHTML = `
		<div class="d-flex flex-md-row flex-column align-items-center justify-content-md-around"
		 style="width: 50px">
		<button class="btn p-0" type="submit">
			<i class="bi bi-arrow-up text-success"></i>
		</button>
		<span class="text-warning" id="${postID}">
			${upVotes}
		</span>
		<button class="btn p-0">
			<i class="bi bi-arrow-down text-danger"></i>
		</button>
	</div>
	<article style="width: calc(100% - 50px)">
		<a class="text-decoration-none"
		   href="/users/${userID}">
		   ${username}
		</a>
		<span class="text-secondary">
			${formattedDate}
		</span>
		<p class="m-0">
			${message}
		</p>
	</article>
	`;
	const postList = document.querySelector("main");
	postList.append(post);

	addPostForm.reset();
	addPostForm.classList.remove("was-validated");
	messageContent.blur();
}


submitButton.addEventListener("click", postMessage);

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
