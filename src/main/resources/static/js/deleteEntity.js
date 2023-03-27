import csrfHeader from "./csrfHeader.js";

/**
 *
 * @type {NodeListOf<HTMLTableRowElement>}
 */
const allRows = document.querySelectorAll("tbody tr td button.delete");


export function createToast(id) {
	const toastContainer = document.querySelector(".toast-container");
	const toastElement = document.createElement("div");
	toastElement.classList.add("toast", "fade", "bg-dark", "text-light");
	toastElement.setAttribute("id", `toast_${id}`);
	toastElement.setAttribute("role", "alert");
	toastElement.setAttribute("aria-live", "assertive");
	toastElement.setAttribute("aria-atomic", "true");
	return {toastContainer, toastElement};
}

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

		const {toastContainer, toastElement} = createToast(id);
		toastElement.innerHTML = `
  			<div class="toast-header text-success">
    			<i class="bi bi-bell-fill me-2"></i>
    			<strong class="me-auto">Success</strong>
    			<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
  			</div>
  			<div class="toast-body">
    			${entity.substring(0, 1).toUpperCase()}${entity.substring(1)} with id ${id} was deleted.  
  			</div>`;

		toastContainer.appendChild(toastElement);
		const toast = new bootstrap.Toast(toastElement, {delay: 3000});
		toast.isShown() || toast.show();
	}

	if (response.status === 403) {
		const {toastContainer, toastElement} = createToast(id);
		toastElement.innerHTML = `
  			<div class="toast-header text-danger">
    			<i class="bi bi-bell-fill me-2"></i>
    			<strong class="me-auto">Success</strong>
    			<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
  			</div>
  			<div class="toast-body">
    			You don't have permission to delete this ${entity} with id ${id}.  
  			</div>`;

		toastContainer.appendChild(toastElement);
		const toast = new bootstrap.Toast(toastElement);
		toast.isShown() || toast.show();
	}

	if (response.status === 404) {
		const {toastContainer, toastElement} = createToast(id);
		toastElement.innerHTML = `
  			<div class="toast-header text-danger">
    			<i class="bi bi-bell-fill me-2"></i>
    			<strong class="me-auto">Success</strong>
    			<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
  			</div>
  			<div class="toast-body">
    			Something went wrong while deleting this ${entity} with id ${id}.  
  			</div>`;

		toastContainer.appendChild(toastElement);
		const toast = new bootstrap.Toast(toastElement);
		toast.isShown() || toast.show();
	}
}

allRows.forEach(row => row.addEventListener("click", handleDelete));
