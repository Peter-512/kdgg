/**
 *
 * @param event {Event}
 */
export async function vote(event) {
	const id = event.target.closest("div").id;
	const postID = +id.substring(id.indexOf("_") + 1);

	const upVotes = +event.target.closest("div").querySelector("span").innerText;

	const newUpVotes = event.target.closest("button").classList.contains("upvote") ? upVotes + 1 : upVotes - 1;

	const url = `/api/posts/${postID}`;

	const response = await fetch(url, {
		method: "PATCH",
		headers: {
			"Content-Type": "application/json",
			"Accept": "application/json"
		},
		body: JSON.stringify({upVotes: newUpVotes})
	});

	if (response.status === 404) {
		return alert("Post not found");
	}

	if (response.status === 400) {
		return alert("Invalid vote");
	}

	event.target.closest("div").querySelector("span").innerText = newUpVotes;
}
