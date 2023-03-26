import csrfHeader from "./csrfHeader.js";

const deleteButtons = document.querySelectorAll(".delete");
const posts = document.querySelectorAll(".post");


deleteButtons.length && posts.forEach(post => {
	post.addEventListener("mouseover", () => {
		post.children[1].classList.remove("visually-hidden");
	});
	post.addEventListener("mouseout", () => {
		post.children[1].classList.add("visually-hidden");
	});
});

/**
 *
 * @param button {HTMLButtonElement}
 */
export async function deletePost(button) {
	const id = button.dataset.postId;
	const {name, value} = csrfHeader();
	const res = await fetch(`/api/posts/${id}`, {
		method: "DELETE"
		, headers: {
			[name]: value
		}
	});

	if (res.status === 204) {
		const toastElement = document.querySelector("#successToast");
		const toast = new bootstrap.Toast(toastElement);
		toastElement.querySelector(".toast-body").textContent = `Post with id ${id} was deleted.`;
		toast.show();
		button.closest(".post").remove();
	}

	if (res.status === 404) {
		const toastElement = document.querySelector("#failToast");
		const toast = new bootstrap.Toast(toastElement);
		toastElement.querySelector(".toast-body").textContent = `Something went wrong while deleting post with id ${id}.`;
		toast.show();
	}
}

deleteButtons?.forEach(button => {
	button.addEventListener("click", () => deletePost(button));
});
