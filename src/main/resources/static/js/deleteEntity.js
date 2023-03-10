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
	response.ok && row.remove();
}

allRows.forEach(row => row.addEventListener("click", handleDelete));
