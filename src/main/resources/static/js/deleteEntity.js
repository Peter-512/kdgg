import csrfHeader from "./csrfHeader.js";

/**
 *
 * @type {NodeListOf<HTMLTableRowElement>}
 */
const allRows = document.querySelectorAll("tbody tr td button.delete");


/**
 *
 * @param e {Event}
 * @returns {Promise<void>}
 */
async function handleDelete(e) {
	e.stopPropagation();
	const row = this.closest("tr");
	const rowID = row.id;
	const id = +rowID.substring(rowID.indexOf("_") + 1);
	const entity = document.URL.substring(document.URL.lastIndexOf("/") + 1);

	const {name, value} = csrfHeader();

	const response = await fetch(`/api/${entity}/${id}`, {
		method: "DELETE",
		headers: {
			[name]: value
		}
	});

	if (response.status === 204) {
		row.remove();
		const toastElement = document.querySelector("#successToast");
		const toast = new bootstrap.Toast(toastElement);
		toastElement.querySelector(".toast-body").textContent = `${entity.substring(0, 1).toUpperCase()}${entity.substring(1)} with id ${id} was deleted.`;
		toast.show();
	}

	if (response.status === 403) {
		const toastElement = document.querySelector("#failToast");
		const toast = new bootstrap.Toast(toastElement);
		toastElement.querySelector(".toast-body").textContent = `You don't have permission to delete this ${entity} with id ${id}.`;
		toast.show();
	}

	if (response.status === 404) {
		const toastElement = document.querySelector("#failToast");
		const toast = new bootstrap.Toast(toastElement);
		toastElement.querySelector(".toast-body").textContent = `Something went wrong while deleting this ${entity} with id ${id}.`;
		toast.show();
	}
}

allRows.forEach(row => row.addEventListener("click", handleDelete));
