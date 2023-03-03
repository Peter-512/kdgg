/**
 *
 * @type {NodeListOf<HTMLTableRowElement>}
 */
const allRows = document.querySelectorAll("tbody tr td button.delete");

async function handleDelete() {
	const row = this.closest("tr");
	const rowID = row.id;
	const id = +rowID.substring(rowID.indexOf("_") + 1);
	const entity = document.URL.substring(document.URL.lastIndexOf("/") + 1);

	const response = await fetch(`/api/${entity}/${id}`, {
		method: "DELETE"
	});
	response.ok && row.remove();
}

allRows.forEach(row => row.addEventListener("click", handleDelete));
